package com.project.java.devtest;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.java.devtest.model.UserTechScoreDAO;
import com.project.java.devtest.model.UserTechScoreDTO;
import com.project.java.user.model.UserDTO;

/**
 * 사용자의 개발 분야 유형 테스트 결과를 처리하는 서블릿입니다.
 *
 * 이 서블릿은 사용자가 선택한 개발 분야에 대해 유형 테스트를 완료한 후,
 * 테스트 결과(기술 점수)를 조회하고 결과 화면으로 전달하는 역할을 합니다.
 *
 * - 사용자가 개발 분야(백엔드, 프론트엔드, AI개발자)를 선택하고,
 * - 그 분야에 맞는 기술 역량 테스트를 수행한 후,
 * - 테스트가 끝나면 이 서블릿이 호출되어 결과를 보여줍니다.
 *
 * 사용자의 기술 점수는 {@code UserTechScoreDAO}를 통해 DB에서 가져오며,
 * 선택한 분야 이름과 함께 request scope에 저장되어 결과 JSP에 전달됩니다.
 * 
 * 또한, 사용자가 "다시 풀기"를 눌렀을 경우 POST 요청이 발생하고,
 * 기존 점수를 삭제한 뒤 테스트 첫 화면으로 리디렉션됩니다.
 *
 * URL: {@code /devtest/ResultTest.do}
 *
 * @author 이재현
 * @version 1.0
 * @since 2025-04-12
 */


@WebServlet("/devtest/ResultTest.do")
public class DevTestResultServlet extends HttpServlet {
	
	/**
	 * GET 요청을 처리합니다.
	 * 
	 * 사용자 세션에서 userSeq를 가져와 해당 사용자의 기술 점수 데이터를 조회하고,
	 * 선택한 개발 분야 이름과 함께 request에 저장하여 결과 JSP로 포워딩합니다.
	 * 
	 * 세션이 유효하지 않으면 로그인 페이지로 리다이렉트됩니다.
	 *
	 * @param req 클라이언트의 요청 객체
	 * @param resp 클라이언트에게 응답을 보내는 객체
	 * @throws ServletException 서블릿 예외 발생 시
	 * @throws IOException 입출력 예외 발생 시
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false); // 기존 세션만 가져오기

	    if (session == null || session.getAttribute("userSeq") == null) {
	        resp.sendRedirect(req.getContextPath() + "/user/login.do");
	        return;
	    }

	    int userSeq = (int) session.getAttribute("userSeq");

	    System.out.println("🔹 DevTestResultServlet에서 userSeq 확인: " + userSeq);

	    UserTechScoreDAO scoreDAO = new UserTechScoreDAO();
	    List<UserTechScoreDTO> userTechScores = scoreDAO.getUserTechScores(userSeq);

	    System.out.println("🔹 조회된 userTechScores 개수: " + userTechScores.size());
	    
	    String selectedField = (String) session.getAttribute("selectedField");
	    String selectedFieldName = (String) session.getAttribute("selectedFieldName");
	    
	    req.setAttribute("selectedFieldName", selectedFieldName);
	    req.setAttribute("userTechScores", userTechScores);
	    req.getRequestDispatcher("/WEB-INF/views/devtest/devTestResult.jsp").forward(req, resp);
	    
	    
	}
	
	/**
	 * POST 요청을 처리합니다.
	 * 
	 * 사용자가 "다시 풀기" 버튼을 눌렀을 때 호출되며,
	 * 해당 사용자의 기술 점수 데이터를 삭제하고 테스트 상태를 초기화한 뒤,
	 * 테스트 시작 페이지로 리디렉트합니다.
	 *
	 * @param req 클라이언트의 요청 객체
	 * @param resp 클라이언트에게 응답을 보내는 객체
	 * @throws ServletException 서블릿 예외 발생 시
	 * @throws IOException 입출력 예외 발생 시
	 */

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    HttpSession session = req.getSession(false);

	    if (session != null) {
	        Integer userSeq = (Integer) session.getAttribute("userSeq");

	        if (userSeq != null) {
	            UserTechScoreDAO scoreDAO = new UserTechScoreDAO();
	            // 🔹 점수 삭제 및 상태 초기화
	            scoreDAO.deleteUserScores(userSeq, session);
	            System.out.println("✅ 다시 풀기 처리 완료: userSeq = " + userSeq);
	        }
	    }

	    // 테스트 첫 페이지로 리디렉트
	    resp.sendRedirect(req.getContextPath() + "/devtest/DevTestSelection.do");
	}
}
