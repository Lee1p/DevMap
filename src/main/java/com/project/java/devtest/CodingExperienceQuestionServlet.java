package com.project.java.devtest;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.java.user.model.UserDAO;
import com.project.java.user.model.UserDTO;
import com.project.java.devtest.DevTypeTest;

/**
 * 사용자의 코딩 경험 여부를 판단하여,
 * 다음 질문 페이지로 분기하는 서블릿입니다. 
 * 
 * 사용자의 세션을 확인하고, 로그인되어 있지 않으면 로그인 페이지로 이동합니다.
 * 로그인된 경우 사용자 정보를 조회해서 JSP로 전달합니다.
 * 
 * 사용자가 코딩 경험 여부에 "예" 라고 응답하면 DevFieldSelection 페이지로,
 * "아니오" 라고 응답하면  DevFieldSelectionNoExp 페이지로 리다이렉트합니다.
 * 
 * 
 * 
 * @author 이재현
 * @since 2025-04-12
 * @version 1.0
 * 
 */


@WebServlet("/devtest/CodingQuestion.do")
public class CodingExperienceQuestionServlet extends HttpServlet {
	
	
	 /** 사용자 정보를 조회하기 위한 DAO 객체 */
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO(); // UserDAO 객체 초기화
    }
    
    /**
     * GET 요청을 처리합니다.
     * 사용자의 로그인 여부를 확인하고, 로그인된 경우 사용자 정보를 조회하여 JSP로 전달합니다.
     *
     * @param req  클라이언트의 요청 객체
     * @param resp 응답을 보낼 객체
     * @throws ServletException 서블릿 처리 중 예외 발생 시
     * @throws IOException      입출력 예외 발생 시
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	// 세션에서 userSeq 속성을 가져와 로그인 여부 확인
    	HttpSession session = req.getSession();
        if (session.getAttribute("userSeq") == null) {
        	// 로그인하지 않은 경우 로그인 페이지로 리다이렉트
            resp.sendRedirect("/devmap/user/login.do");
            return;
        }
    	
        // userSeq 사용
        int userSeq = (int) session.getAttribute("userSeq");
        UserDTO user = userDAO.selectUser(userSeq); //  세션에서 받은 값을 사용
        
        
        // 조회된 사용자 정보를 request에 저장
        req.setAttribute("user", user);
        
        // JSP로 포워딩
        req.getRequestDispatcher("/WEB-INF/views/devtest/codingExperienceQuestion.jsp").forward(req, resp);
    }
    
    /**
     * POST 요청을 처리합니다.
     * 사용자의 코딩 경험 여부("yes" 또는 "no")에 따라 다음 페이지로 리다이렉트합니다.
     *
     * - "yes" 선택 시: DevFieldSelection.do로 이동 (경험자용 개발 분야 선택 페이지)
     * - "no" 선택 시: DevFieldSelectionNoExp.do로 이동 (비경험자용 개발 분야 선택 페이지)
     *
     * @param req  클라이언트의 요청 객체 (코딩 경험 여부 파라미터 포함)
     * @param resp 클라이언트에게 응답을 보내는 객체
     * @throws ServletException 서블릿 처리 중 예외 발생 시
     * @throws IOException      입출력 예외 발생 시
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	 	// 사용자가 선택한 코딩 경험 여부("yes" 또는 "no")를 파라미터로 받음
        String experience = req.getParameter("experience");
        
        // 경험이 있다면 '개발 분야 선택' 페이지로 이동
        if ("yes".equals(experience)) {
            resp.sendRedirect(req.getContextPath() + "/devtest/DevFieldSelection.do");
        } else {
        	// 경험이 없다면 '비경험자 개발 분야 선택' 페이지로 이동
            resp.sendRedirect(req.getContextPath() + "/devtest/DevFieldSelectionNoExp.do");
        }
    }
}
