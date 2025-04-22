package com.project.java.devtest;

import java.io.IOException;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.java.devtest.model.DevFieldDAO;
import com.project.java.devtest.model.DevFieldDTO;

/**
 * 개발 분야 선택 서블릿입니다.
 * 
 * 사용자가 코딩 경험이 있다고 응답한 후, 개발 분야를 선택할 수 있도록
 * 개발 분야 목록을 조회하여 JSP로 전달하거나, 선택된 분야를 세션에 저장한 뒤
 * 다음 테스트 페이지로 이동시킵니다.
 *
 * URL: /devtest/DevFieldSelection.do
 * 
 * @author 이재현
 * @since 2025-04-12
 * @version 1.0
 */

@WebServlet("/devtest/DevFieldSelection.do")
public class DevFieldSelectionServlet extends HttpServlet {

	
	/**
	 * GET 요청을 처리합니다.
	 * 
	 * 개발 분야 목록을 데이터베이스에서 조회하고, 이를 request에 담아
	 * JSP 페이지(devFieldSelection.jsp)로 전달합니다.
	 *
	 * @param req 클라이언트의 요청 객체
	 * @param resp 클라이언트에게 응답을 보내는 객체
	 * @throws ServletException 서블릿 관련 예외 발생 시
	 * @throws IOException 입출력 예외 발생 시
	 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 요청마다 새로운 DAO 객체 생성 (Thread-Safe)
            DevFieldDAO devFieldDAO = new DevFieldDAO();
            
            // 개발 분야 리스트를 가져옴
            List<DevFieldDTO> devFields = devFieldDAO.getAllDevFields();
            
            // 가져온 개발 분야 리스트를 request에 저장
            req.setAttribute("devFields", devFields);
            
            // 개발 분야 선택 페이지로 포워딩
            req.getRequestDispatcher("/WEB-INF/views/devtest/devFieldSelection.jsp").forward(req, resp);
        } catch (Exception e) {
            System.err.println("개발 분야 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "개발 분야를 불러오는 중 오류가 발생했습니다.");
        }
    }
    
    /**
     * POST 요청을 처리합니다.
     * 
     * 사용자가 선택한 개발 분야의 ID 및 이름을 받아 세션에 저장한 후,
     * 다음 테스트 선택 페이지로 리디렉션합니다.
     * 
     * @param req 클라이언트의 요청 객체 (선택한 개발 분야 정보 포함)
     * @param resp 클라이언트에게 응답을 보내는 객체
     * @throws ServletException 서블릿 관련 예외 발생 시
     * @throws IOException 입출력 예외 발생 시
     */
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.setCharacterEncoding("UTF-8");
        String selectedField = req.getParameter("selectedField");
        String selectedFieldName = req.getParameter("selectedFieldName");
        
        // 선택한 개발 분야 정보를 세션에 저장 (다음 페이지에서 사용하기 위해)
        HttpSession session = req.getSession();
        session.setAttribute("selectedField", selectedField);
        session.setAttribute("selectedFieldName", selectedFieldName);
        
        // 다음 페이지로 리디렉션
        resp.sendRedirect(req.getContextPath() + "/devtest/DevTestSelection.do");
    }

}
