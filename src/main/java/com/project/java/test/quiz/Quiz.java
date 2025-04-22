package com.project.java.test.quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.java.test.quiz.model.QuizDAO;
import com.project.java.test.quiz.model.QuizDTO;
import com.project.java.test.quiz.model.SubjectDAO;
import com.project.java.test.quiz.model.SubjectQuizDTO;

/**
 * 퀴즈와 관련된 모든 정보를 처리하는 퀴즈 서블렛입니다.
 * 퀴즈의 목록을 보여줍니다.
 * 
 * @author 조인제
 * @version 1.0
 */
@WebServlet("/test/quiz/quiz.do")
public class Quiz extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 세션 확인 - 로그인 상태 확인
        HttpSession session = req.getSession();
        if (session.getAttribute("userSeq") == null) {
            resp.sendRedirect("/devmap/user/login.do");
            return;
        }
        
        
        
        // 테스트를 위한 임시 세션 설정 (실제 배포 시 제거)
//        if (session.getAttribute("userSeq") == null) {
//            // 임시 사용자 정보 설정 (테스트용)
//            session.setAttribute("userSeq", 1); // 임의의 사용자 ID
//            session.setAttribute("userName", "테스트사용자");
//        }
        
        
        // 과목 ID 파라미터 가져오기 (기본값 1)
        int subjectSeq = 6;
        try {
            subjectSeq = Integer.parseInt(req.getParameter("subjectSeq"));
        } catch (Exception e) {
            // 파라미터가 없거나 유효하지 않은 경우 기본값 사용
        }
        
        int userSeq = (int) session.getAttribute("userSeq");
        
        // 기술 ID 파라미터 가져오기 (기본값 1)
        int techSeq = 1;
        try {
            techSeq = Integer.parseInt(req.getParameter("techSeq"));
        } catch (Exception e) {
            // 파라미터가 없거나 유효하지 않은 경우 기본값 사용
        }
        
        
        
        // 과목 목록과 퀴즈 진행 상황 가져오기
        SubjectDAO subjectDAO = new SubjectDAO();
        List<SubjectQuizDTO> subjectList = subjectDAO.getSubjectQuizList(techSeq, userSeq);
        
        
        
        

        
        // 퀴즈 목록 가져오기
        QuizDAO dao = new QuizDAO();
        ArrayList<QuizDTO> quizList = dao.getQuizListBySubject(subjectSeq);
        
        //System.out.println(quizList);
        
        
     // 현재 페이지 파라미터 가져오기
        int currentPage = 1;
        System.out.println("currentPage: " + currentPage);
        try {
            currentPage = Integer.parseInt(req.getParameter("page"));
        } catch (Exception e) {
            // 파라미터가 없거나 유효하지 않은 경우 기본값 사용
        }

        // 페이지네이션 정보 계산
        int totalSubjects = subjectList.size();
        System.out.println("totalSubjects: " + totalSubjects);
        int subjectsPerPage = 10; // 한 페이지당 표시할 과목 수
        int totalPages = (int) Math.ceil((double) totalSubjects / subjectsPerPage);

        // 현재 페이지에 해당하는 과목 목록 필터링
        int startIndex = (currentPage - 1) * subjectsPerPage;
        System.out.println("startIndex: " + startIndex);

        int endIndex = Math.min(startIndex + subjectsPerPage, totalSubjects);
        System.out.println("endIndex: " + endIndex);
        // 인덱스 유효성 검사 추가
        if (startIndex >= totalSubjects) {
            startIndex = 0;
            currentPage = 1;
            endIndex = Math.min(subjectsPerPage, totalSubjects);
        }
        List<SubjectQuizDTO> currentPageSubjects = subjectList.subList(startIndex, endIndex);

        // 요청 속성에 추가
        req.setAttribute("subjectList", currentPageSubjects);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("currentPage", currentPage);

        
        req.getRequestDispatcher("/WEB-INF/views/test/quiz/quiz.jsp").forward(req, resp);
    }
}