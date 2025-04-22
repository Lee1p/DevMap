package com.project.java;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.java.user.mypage.model.BookMarkDAO;
import com.project.java.user.mypage.model.BookMarkDTO;
import com.project.java.user.mypage.model.SubjectReviewDAO;
import com.project.java.user.mypage.model.SubjectReviewDTO;
import com.project.java.user.mypage.model.UserCoteResultDAO;
import com.project.java.user.mypage.model.UserCoteResultDTO;
import com.project.java.user.mypage.model.UserRatingDAO;

@WebServlet("/index.do")
/**
 * 서비스의 메인 랜딩 페이지로 사용자가 처음 접근하는 화면을 제공하는 서블렛입니다.
 * 로그인 여부와 성향 테스트 진행 여부에 따라 동적으로 다음 페이지가 결정됩니다.
 * 로그인한 사용자 중 성향 테스트를 완료한 경우, 문제 풀이, 마이페이지, 로드맵 보기 등의 네비게이션 바가 활성화됩니다.
 * 로그인한 사용자 중 성향 테스트를 하지 않은 경우, 성향 테스트 페이지로 리다이렉트됩니다.
 * 로그인하지 않은 사용자는 회원가입 페이지로 이동됩니다.
 * 
 */
public class Index extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Index.java
		// 테스트입니다.
		// 확인중
		// 세션 확인 - 로그인 상태 확인

		/*
		 * int userSeq = req.getSession().getAttribute("userseq") != null ? (int)
		 * req.getSession().getAttribute("userseq") : 1;
		 */
		
		/*
		 * BookMarkDAO bookmarkdao = new BookMarkDAO(); SubjectReviewDAO subjectreviedao
		 * = new SubjectReviewDAO(); UserRatingDAO ratingdao = new UserRatingDAO();
		 * UserCoteResultDAO codetestresult = new UserCoteResultDAO();
		 * 
		 * ArrayList<BookMarkDTO> bookmarklist = bookmarkdao.getBookMark(userSeq);
		 * ArrayList<SubjectReviewDTO> reviewlist =
		 * subjectreviedao.getReviewList(userSeq); ArrayList<UserCoteResultDTO>
		 * cotersult = codetestresult.getCoteResult(userSeq);
		 */

		req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
	}
}



