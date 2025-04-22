package com.project.java.user.mypage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.project.java.user.mypage.model.BookMarkDAO;
import com.project.java.user.mypage.model.BookMarkDTO;
import com.project.java.user.mypage.model.UserCoteResultDAO;
import com.project.java.user.mypage.model.UserCoteResultDTO;

/**
 * 북마크 데이터를 수신하는 클래스입니다.
 * Ajax로 보낸 북마크 리스트를 수신합니다.
 */
@WebServlet("/user/mypage/mypage_bmarkdata.do")
public class Mypage_bmarkdata extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BookMarkDAO bookmarkdao = new BookMarkDAO();

		// 세션 확인 - 로그인 상태 확인
		/*
		 * HttpSession session = req.getSession(); if (session.getAttribute("userSeq")
		 * == null) { resp.sendRedirect("/user/login.do"); return; }
		 * 
		 * int userSeq = (int) session.getAttribute("userSeq");
		 */

		// 세션에서 userSeq 가져오기 (임시로 "1" 사용)

		int userSeq = req.getSession().getAttribute("userseq") != null ? (int) req.getSession().getAttribute("userseq")
				: 1;

		ArrayList<BookMarkDTO> bookmarklist = bookmarkdao.getBookMark(userSeq);
		req.setAttribute("bookmarklist", bookmarklist);

		System.out.println(bookmarklist);

		// JSON 배열 생성
		JSONArray arr = new JSONArray();

		for (BookMarkDTO dto : bookmarklist) {
			JSONObject obj = new JSONObject();
			obj.put("codeTestSeq", dto.getCodeTestSeq());
			obj.put("codetesttitle", dto.getCodetesttitle());
			obj.put("subjectName", dto.getSubjectName());

			arr.add(obj);
		}

		// 응답 설정
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		// 클라이언트에 JSON 전송
		PrintWriter writer = resp.getWriter();
		writer.print(arr.toJSONString());
		writer.close();

	}
}
