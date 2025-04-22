package com.project.java.devtest;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.java.devtest.model.DevFieldDAO;
import com.project.java.devtest.model.DevFieldDTO;

@WebServlet("/devtest/DevKnowledgeTest.do")
public class DevKnowledgeTestServlet extends HttpServlet {
    
    private DevFieldDAO devFieldDAO = new DevFieldDAO(); // DAO 객체를 필드로 선언하여 재사용

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 개발 분야 리스트를 가져옴
            List<DevFieldDTO> devFields = devFieldDAO.getAllDevFields();
            
            // 가져온 개발 분야 리스트를 request에 저장
            req.setAttribute("devFields", devFields);
            
            // 개발 분야 선택 페이지로 포워딩
            req.getRequestDispatcher("/WEB-INF/views/devtest/devKnowledgeTest.jsp").forward(req, resp);
        } catch (Exception e) {
            // 예외 처리 개선: 로그로 기록하고 사용자에게 상세한 오류 메시지 제공
            log("개발 분야 조회 중 오류 발생", e);  // 로그로 오류를 기록
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "개발 분야를 불러오는 중 오류가 발생했습니다.");
        }
    }
}

