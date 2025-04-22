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

import com.project.java.user.mypage.model.UserCoteResultDAO;
import com.project.java.user.mypage.model.UserCoteResultDTO;
/**
 * 코드 테스트 데이터를 수신하는 클래스입니다.
 * Ajax로 보낸 코드 테스트 점수 리스트를 수신합니다.
 */
@WebServlet("/user/mypage/mypage_cotedata.do")
public class Mypage_cotedata extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserCoteResultDAO codetestresult = new UserCoteResultDAO();

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

		ArrayList<UserCoteResultDTO> cotersult = codetestresult.getCoteResult(userSeq);
		req.setAttribute("cotersult", cotersult);

		System.out.println(cotersult);

		// JSON 배열 생성
		JSONArray arr = new JSONArray();

		for (UserCoteResultDTO dto : cotersult) {
			JSONObject obj = new JSONObject();
			obj.put("codetestSeq", dto.getCodetestSeq());
			obj.put("codetesttitle", dto.getCodetesttitle());
			obj.put("cnt", dto.getCnt());

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
