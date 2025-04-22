package com.project.java.test.codetest;

import java.io.IOException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.project.java.test.codetest.model.AIResultDTO;
import com.project.java.test.codetest.model.CodeTestDAO;
import com.project.java.test.codetest.model.CodeTestDTO;
import com.project.java.test.codetest.model.SubjectDAO;
import com.project.java.test.codetest.util.AIAnalyzer;

/**
 * 코드 테스트 제출을 처리하는 서블릿 클래스입니다.
 * 이 서블릿은 사용자가 작성한 코드를 제출받아 저장하고, AI를 통해 분석한 후 결과를 저장합니다.
 * URL 패턴 "/test/codetest/codetestsubmit.do"로 매핑되어 있습니다.
 *
 * <p>주요 기능:</p>
 * <ul>
 *   <li>사용자 인증 확인</li>
 *   <li>제출된 코드와 문제 정보 수집</li>
 *   <li>사용자 코드 제출 내역 데이터베이스 저장</li>
 *   <li>AI를 통한 코드 분석 및 평가</li>
 *   <li>분석 결과 데이터베이스 저장</li>
 *   <li>기본 문제 통과 시 과목 복습 이력 업데이트</li>
 *   <li>결과 페이지로 리다이렉트</li>
 * </ul>
 *
 * <p>요청 파라미터:</p>
 * <ul>
 *   <li>code: 사용자가 제출한 코드</li>
 *   <li>codeTestSeq: 코드 테스트 문제의 고유 번호</li>
 * </ul>
 *
 * @author [박주승]
 * @version 1.0
 */
@WebServlet("/test/codetest/codetestsubmit.do")
public class CodeTestSubmit extends HttpServlet {

    /**
     * HTTP POST 요청을 처리하는 메서드입니다.
     * 사용자가 코드 테스트 문제 풀이를 제출할 때 호출됩니다.
     *
     * <p>이 메서드의 처리 과정:</p>
     * <ol>
     *   <li>요청 인코딩 설정 및 파라미터 추출</li>
     *   <li>사용자 세션 확인 및 인증되지 않은 사용자 리다이렉트</li>
     *   <li>문제 정보 조회</li>
     *   <li>사용자 코드 제출 데이터베이스 저장</li>
     *   <li>과목 리스트 조회</li>
     *   <li>AI를 통한 코드 분석 및 결과 수신</li>
     *   <li>분석 결과 데이터베이스 저장</li>
     *   <li>기본 문제 통과 시 과목 복습 이력 업데이트</li>
     *   <li>결과에 따라 결과 페이지 또는 재풀이 페이지로 리다이렉트</li>
     * </ol>
     *
     * @param req 클라이언트의 HTTP 요청 객체. code, codeTestSeq 파라미터 포함
     * @param resp 클라이언트로 보낼 HTTP 응답 객체
     * @throws ServletException 서블릿 처리 중 오류 발생 시
     * @throws IOException I/O 작업 중 오류 발생 시
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 요청 인코딩 설정 및 파라미터 추출
        req.setCharacterEncoding("UTF-8");
        String code = req.getParameter("code");
        String codeTestSeq = req.getParameter("codeTestSeq");

        // 사용자 세션 확인 및 인증
        HttpSession session = req.getSession();
        if (session.getAttribute("userSeq") == null) {
            resp.sendRedirect("/devmap/user/login.do");
            return;
        }

        int userSeq = (int) session.getAttribute("userSeq");

        // 디버깅용 로그
        System.out.println("[Submit] 받은 문제번호: " + codeTestSeq);
        System.out.println("[Submit] 제출 코드: " + code);

        CodeTestDAO dao = new CodeTestDAO();

        // 문제 정보 조회
        CodeTestDTO testInfo = dao.get(codeTestSeq);
        String codeTestTitle = testInfo.getCodeTestTitle();
        String codeTestDesc = testInfo.getContent();
        String inputDescription = testInfo.getInputDescription();
        String outputDescription = testInfo.getOutputDescription();
        String sampleInput = testInfo.getSampleInput();
        String sampleOutput = testInfo.getSampleOutput();

        // 사용자 코드 제출 데이터베이스 저장 및 결과 ID 반환
        int userCoteResultSeq = dao.insertUserCode(codeTestSeq, userSeq, code);

        // 과목 리스트 조회
        SubjectDAO subjectDAO = new SubjectDAO();
        List<String> subjectNames = subjectDAO.getAllSubjectNames();
        String subjectList = String.join(", ", subjectNames);

        if (userCoteResultSeq > 0) {
            // AI 분석 요청 및 결과 수신
            AIResultDTO aiResult = AIAnalyzer.sendToAI(
                    code, codeTestSeq, subjectList,
                    codeTestTitle, codeTestDesc,
                    inputDescription, outputDescription,
                    sampleInput, sampleOutput
            );

            // AI 분석 실패 시 기본값 설정
            if (aiResult == null) {
                System.out.println("⚠️ AI 분석 실패!");
                aiResult = new AIResultDTO("N", "AI 분석 실패", "미분석", 0, 5, 0.0);
            }
            System.out.println("✅ updateAnalysisInfo() 진입 전");

            // 분석 결과 데이터베이스 저장
            dao.updateAnalysisInfo(userCoteResultSeq,
                    aiResult.getAiFeedback(),
                    aiResult.getWeakConcepts(),
                    aiResult.getIsPassed(),
                    aiResult.getTestCasePassed(),
                    aiResult.getTotalTestCases(),
                    aiResult.getExecutionTime()
            );

            System.out.println(" userCoteResultSeq: " + userCoteResultSeq);

            // 기본 문제 통과 시 과목 복습 이력 업데이트
            if ("Y".equals(aiResult.getIsPassed()) && dao.isBasicProblem(codeTestSeq)) {
                int subjectSeq = dao.getSubjectSeqByCodeTest(codeTestSeq);
                dao.insertOrUpdateSubjectReview(userSeq, subjectSeq);
            }

            // 결과 페이지로 리다이렉트
            resp.sendRedirect("/devmap/test/codetest/codetestresult.do?codeTestSeq=" + codeTestSeq);
        } else {
            // 제출 실패 시 재풀이 페이지로 리다이렉트
            resp.sendRedirect("/devmap/test/codetest/codetestsolv.jsp?seq=" + codeTestSeq);
        }
    }
}