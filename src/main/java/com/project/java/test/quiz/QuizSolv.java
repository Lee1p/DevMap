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


/**
 * 퀴즈 풀이와 관련된 모든 정보를 처리하는 퀴즈 풀이 서블렛입니다.
 * 
 * @author 조인제
 * @version 1.0
 */
@WebServlet("/test/quiz/quizsolv.do")
public class QuizSolv extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 세션 확인 - 로그인 상태 확인
        HttpSession session = req.getSession();
        if (session.getAttribute("userSeq") == null) {
            resp.sendRedirect("/user/login.do");
            return;
        }
    	
    	// 세션 확인 및 임시 세션 설정
//        HttpSession session = req.getSession();
//        if (session.getAttribute("userSeq") == null) {
//            // 임시 사용자 정보 설정 (테스트용)
//            session.setAttribute("userSeq", 1);
//            session.setAttribute("userName", "테스트사용자");
//        }
        
        // 여기에 userSeq 변수 선언 및 초기화 추가
        int userSeq = (int) session.getAttribute("userSeq");
        
        // 파라미터 가져오기
        int subjectSeq = Integer.parseInt(req.getParameter("subjectSeq"));
        int quizSeq = 0;
        
        try {
            quizSeq = Integer.parseInt(req.getParameter("quizSeq"));
        } catch (NumberFormatException e) {
            // quizSeq가 없는 경우 처리
        }
        
        QuizDAO dao = new QuizDAO();
        
        // 타이머 설정 - 세션에 타이머 값이 없으면 초기화 (30분)
        // 과목별로 타이머를 관리하기 위해 subjectSeq를 키로 사용
        String timerKey = "timer_" + subjectSeq;
        if (session.getAttribute(timerKey) == null) {
            // 타이머 초기화 (30분 = 1800초)
            session.setAttribute(timerKey, 1800);
        }
        
        // 남은 시간 가져오기
        int timeLeft = (int) session.getAttribute(timerKey);
        req.setAttribute("timeLeft", timeLeft);
        
        // 이미 답변한 문제 목록 가져오기 (UI 개선을 위해)
        List<Integer> answeredQuizzes = dao.getAnsweredQuizzes(userSeq, subjectSeq);
        req.setAttribute("answeredQuizzes", answeredQuizzes);
        
        // quizSeq가 없는 경우 해당 과목의 첫 번째 퀴즈 가져오기
        if (quizSeq == 0) {
            quizSeq = dao.getFirstQuizSeqBySubject(subjectSeq);
            if (quizSeq == 0) {
                // 해당 과목에 퀴즈가 없는 경우
                resp.sendRedirect("/devmap/test/quiz/quiz.do");
                return;
            }
        }
        
        // 문제 정보 가져오기
        QuizDTO quiz = dao.getQuiz(quizSeq);
        
        // 이전 문제 번호 가져오기 (이전 버튼 기능 추가)
        int prevQuizSeq = dao.getPrevQuizSeq(quizSeq, subjectSeq);
        req.setAttribute("prevQuizSeq", prevQuizSeq);
        
        // 이전에 선택한 답변 가져오기
        String selectedAnswer = (String) session.getAttribute("selectedAnswer_" + quizSeq);
        req.setAttribute("selectedAnswer", selectedAnswer);
        
        // 과목의 모든 문제 목록 가져오기 (답안지 표시용)
        ArrayList<QuizDTO> quizList = dao.getQuizListBySubject(subjectSeq);
        
        // 요청 속성에 추가
        req.setAttribute("quiz", quiz);
        req.setAttribute("quizList", quizList);
        
        // 문제 풀기 페이지로 포워딩
        req.getRequestDispatcher("/WEB-INF/views/test/quiz/quizsolv.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 세션 확인
        HttpSession session = req.getSession();
        if (session.getAttribute("userSeq") == null) {
            resp.sendRedirect("/user/login.do");
            return;
        }
        
        // 파라미터 가져오기
        int userSeq = (int) session.getAttribute("userSeq");
        int quizSeq = Integer.parseInt(req.getParameter("quizSeq"));
        int subjectSeq = Integer.parseInt(req.getParameter("subjectSeq"));
        String selectedAnswer = req.getParameter("selectedAnswer");
        
        // 타이머 업데이트
        String timerKey = "timer_" + subjectSeq;
        if (req.getParameter("timeLeft") != null) {
            int timeLeft = Integer.parseInt(req.getParameter("timeLeft"));
            session.setAttribute(timerKey, timeLeft);
        }
        
        session.setAttribute("selectedAnswer_" + quizSeq, selectedAnswer);

        // 정답 확인 및 결과 저장
        QuizDAO dao = new QuizDAO();
        QuizDTO quiz = dao.getQuiz(quizSeq);
        String isCorrect = selectedAnswer.equals(quiz.getCorrectAnswer()) ? "Y" : "N";
        
        // 기존 결과 삭제 후 새 결과 저장 (최신 결과만 유지)
        dao.deleteQuizResult(userSeq, quizSeq);
        dao.saveQuizResult(userSeq, quizSeq, selectedAnswer, isCorrect);
        
        // 다음 문제로 이동 또는 결과 페이지로 이동
        int nextQuizSeq = dao.getNextQuizSeq(quizSeq, subjectSeq);
        // QuizSolv.java의 doPost 메서드 수정
        if (nextQuizSeq > 0) {
            resp.sendRedirect("/devmap/test/quiz/quizsolv.do?quizSeq=" + nextQuizSeq + "&subjectSeq=" + subjectSeq);
        } else {
            resp.sendRedirect("/devmap/test/quiz/quizfeedback.do?subjectSeq=" + subjectSeq);
        }
    }
}
