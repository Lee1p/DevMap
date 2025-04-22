package com.project.java.test.quiz;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.java.test.quiz.model.QuizDAO;
import com.project.java.test.quiz.model.UserQuizResultDTO;

/**
 * 퀴즈 결과와 관련된 모든 정보를 처리하는 퀴즈 서블렛입니다.
 * 퀴즈의 결과를 보여줍니다.
 * 
 * @author 조인제
 * @version 1.0
 */


@WebServlet("/test/quiz/quizfeedback.do")
public class QuizFeedback extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 세션 확인 - 로그인 상태 확인
        HttpSession session = req.getSession();
        if (session.getAttribute("userSeq") == null) {
            resp.sendRedirect("/user/login.do");
            return;
        }
        
        
        // 테스트를 위한 임시 세션 설정 (실제 배포 시 제거)
//        if (session.getAttribute("userSeq") == null) {
//            // 임시 사용자 정보 설정 (테스트용)
//            session.setAttribute("userSeq", 1); // 임의의 사용자 ID
//            session.setAttribute("userName", "테스트사용자");
//        }
        
        
        
        int userSeq = (int) session.getAttribute("userSeq");
        int subjectSeq = Integer.parseInt(req.getParameter("subjectSeq"));
        
        QuizDAO dao = new QuizDAO();
        ArrayList<UserQuizResultDTO> results = dao.getQuizResults(userSeq, subjectSeq);
        int totalScore = dao.calculateTotalScore(userSeq, subjectSeq);
        
        req.setAttribute("results", results);
        req.setAttribute("totalScore", totalScore);
        req.setAttribute("subjectSeq", subjectSeq);
        
        
        if (totalScore >= 60) {
            com.project.java.roadmap.model.UserSubjectDAO userSubjectDao = new com.project.java.roadmap.model.UserSubjectDAO();
            
            if (userSubjectDao.checkIfExists(userSeq, subjectSeq)) {
                userSubjectDao.updateEndDate(userSeq, subjectSeq);
            } else {
                userSubjectDao.insertCompletedSubject(userSeq, subjectSeq);
            }
            
            userSubjectDao.close();
        }


        
        req.getRequestDispatcher("/WEB-INF/views/test/quiz/quizfeedback.jsp").forward(req, resp);
    }
}
