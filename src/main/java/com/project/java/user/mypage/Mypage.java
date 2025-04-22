package com.project.java.user.mypage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.java.badge.model.BadgeDTO;
import com.project.java.test.codetest.model.CodeTestDAO;
import com.project.java.test.codetest.model.CodeTestDTO;
import com.project.java.user.mypage.model.AttendanceDAO;
import com.project.java.user.mypage.model.AttendanceDTO;
import com.project.java.user.mypage.model.BookMarkDAO;
import com.project.java.user.mypage.model.BookMarkDTO;
import com.project.java.user.mypage.model.MypageDAO;
import com.project.java.user.mypage.model.MypageDTO;
import com.project.java.user.mypage.model.RatingHistoryDAO;
import com.project.java.user.mypage.model.RatingHistoryDTO;
import com.project.java.user.mypage.model.SubjectReviewDAO;
import com.project.java.user.mypage.model.SubjectReviewDTO;
import com.project.java.user.mypage.model.TechDAO;
import com.project.java.user.mypage.model.TechDTO;
import com.project.java.user.mypage.model.UserCoteResultDAO;
import com.project.java.user.mypage.model.UserCoteResultDTO;
import com.project.java.user.mypage.model.UserLinkDTO;
import com.project.java.user.mypage.model.UserRatingDAO;
import com.project.java.user.mypage.model.UserRatingDTO;

/**
 * 마이페이지와 관련된 모든 정보를 처리하는 서블릿입니다.
 * 사용자 개인정보 처리: 기본 정보, 외부 링크, GitHub 활동 정보(GitHub 잔디 등)를 관리합니다.
 * 사용자 획득 정보 처리: 뱃지 및 랭킹 정보를 제공합니다.
 * 사용자 확인 정보 처리: 망각 곡선 기반 복습 리스트, 자주 틀린 문제 리스트, 북마크, 알림 목록을 제공합니다.
 * 
 * @author 정다음
 * @version 1.0
 */
@WebServlet("/user/mypage/mypage.do")
public class Mypage extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MypageDAO dao = new MypageDAO();
		TechDAO techdao = new TechDAO();
		BookMarkDAO bookmarkdao = new BookMarkDAO();
		SubjectReviewDAO subjectreviedao = new SubjectReviewDAO();
		UserRatingDAO ratingdao = new UserRatingDAO();
		AttendanceDAO attendancedao = new AttendanceDAO();
		UserCoteResultDAO codetestresult = new UserCoteResultDAO();

		RatingHistoryDAO ratinghistorydao = new RatingHistoryDAO();

		// 세션 확인 - 로그인 상태 확인

		HttpSession session = req.getSession();
		if (session.getAttribute("userSeq") == null) {
			resp.sendRedirect("/user/login.do");
			return;
		}

		int userSeq = (int) session.getAttribute("userSeq");

		// 세션에서 userSeq 가져오기 (임시로 "1" 사용)

		/*
		 * int userSeq = req.getSession().getAttribute("userseq") != null ? (int)
		 * req.getSession().getAttribute("userseq") : 1;
		 */

		// 사용자 정보 및 뱃지 목록 조회
		MypageDTO dto = dao.getUser(userSeq);
		ArrayList<BadgeDTO> badgelist = dao.getBadge(userSeq);
		ArrayList<UserLinkDTO> linklist = dao.getUserLink(userSeq);
		ArrayList<BookMarkDTO> bookmarklist = bookmarkdao.getBookMark(userSeq);
		ArrayList<SubjectReviewDTO> reviewlist = subjectreviedao.getReviewList(userSeq);
		ArrayList<AttendanceDTO> attendancelist = attendancedao.getAttendance(userSeq);
		ArrayList<UserCoteResultDTO> cotersult = codetestresult.getCoteResult(userSeq);

		// 그래프
		ArrayList<RatingHistoryDTO> ratinghisotry = ratinghistorydao.getratinghistory(userSeq);
		ArrayList<RatingHistoryDTO> ratinghisotryall = ratinghistorydao.getratinghistoryall();

		CodeTestDAO codetestdao = new CodeTestDAO();

		List<CodeTestDTO> recommendreview = codetestdao.getRecommendedReviewProblems(userSeq);

		TechDTO tech = techdao.getTechList();

		ArrayList<UserRatingDTO> ratinglist = ratingdao.getUserRatingList();
		UserRatingDTO userrating = ratingdao.getUserRating(userSeq);

		int bookmarkCount = bookmarklist.size();
		int wrongCount = cotersult.size();
		int reviewCount = reviewlist.size();

		// 알림 기능 구현중
		String notification = "";
		int notiCount = 0;

		if (bookmarkCount > 0 || wrongCount > 0 || reviewCount > 0) {
			notification = "새로운 메시지가 도착했습니다!";
		} else {
			notification = "현재 확인할 메시지가 없습니다.";
			notiCount = 0;
		}

		ArrayList<String> notiList = new ArrayList<>();

		// 알림 기능
		if (bookmarkCount > 0) {
			notiList.add("✅ 북마크한 문제 " + bookmarkCount + "개가 기다리고 있어요!");
			notiCount += 1;
		}
		if (wrongCount > 0) {
			notiList.add("⚠️ 자주 틀린 문제 " + wrongCount + "개, 다시 도전해볼까요?");
			notiCount += 1;
		}
		if (reviewCount > 0) {
			notiList.add("🔁 오늘 복습할 문제 " + reviewCount + "개가 있어요!");
			notiCount += 1;
		}
		
		

		req.setAttribute("notification", notification);
		req.setAttribute("notiList", notiList);
		req.setAttribute("notiCount", notiCount);

		ratingdao.close();

		// 데이터 전달
		req.setAttribute("dto", dto);
		req.setAttribute("badgelist", badgelist);
		req.setAttribute("tech", tech);
		req.setAttribute("linklist", linklist);
		req.setAttribute("bookmarklist", bookmarklist);
		req.setAttribute("reviewlist", reviewlist);
		req.setAttribute("ratinglist", ratinglist);
		req.setAttribute("attendancelist", attendancelist);
		req.setAttribute("cotersult", cotersult);
		req.setAttribute("userratinghistory", ratinghisotry);
		req.setAttribute("ratinghisotryall", ratinghisotryall);
		req.setAttribute("userrating", userrating);
		req.setAttribute("recommendreview", recommendreview);
		
		System.out.println(recommendreview);

		// JSP로 포워딩
		req.getRequestDispatcher("/WEB-INF/views/user/mypage/mypage_change.jsp").forward(req, resp);
	}
}
