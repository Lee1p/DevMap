package com.project.java.test.codetest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.java.test.codetest.model.CodeTestDAO;
import com.project.java.test.codetest.model.CodeTestDTO;

/**
 * 코드 테스트 문제 풀이 페이지를 제공하는 서블릿 클래스입니다.
 * 이 서블릿은 특정 코드 테스트 문제를 조회하고 풀이 페이지로 전달합니다.
 * URL 패턴 "/test/codetest/codetestsolv.do"로 매핑되어 있습니다.
 *
 * <p>주요 기능:</p>
 * <ul>
 *   <li>요청 파라미터에서 문제 번호(seq) 추출</li>
 *   <li>특정 코드 테스트 문제 데이터 조회</li>
 *   <li>문제 데이터를 풀이 페이지로 전달</li>
 * </ul>
 *
 * <p>요청 파라미터:</p>
 * <ul>
 *   <li>seq: 풀이할 코드 테스트 문제의 고유 번호</li>
 * </ul>
 *
 * @author [박주승]
 * @version 1.0
 */
@WebServlet("/test/codetest/codetestsolv.do")
public class CodeTestSolv extends HttpServlet {

    /**
     * HTTP GET 요청을 처리하는 메서드입니다.
     * 사용자가 특정 코드 테스트 문제를 풀기 위해 접근할 때 호출됩니다.
     *
     * <p>이 메서드의 처리 과정:</p>
     * <ol>
     *   <li>요청 파라미터에서 문제 번호(seq) 추출</li>
     *   <li>디버깅을 위한 파라미터 로그 출력</li>
     *   <li>문제 번호로 해당 코드 테스트 문제 데이터 조회</li>
     *   <li>디버깅을 위한 조회 결과 로그 출력</li>
     *   <li>조회된 문제 데이터를 request 속성에 설정</li>
     *   <li>코드 테스트 풀이 JSP 페이지로 요청 전달</li>
     * </ol>
     *
     * @param req 클라이언트의 HTTP 요청 객체. seq 파라미터 포함
     * @param resp 클라이언트로 보낼 HTTP 응답 객체
     * @throws ServletException 서블릿 처리 중 오류 발생 시
     * @throws IOException I/O 작업 중 오류 발생 시
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 요청 파라미터에서 문제 번호 추출
        String seq = req.getParameter("seq");
        System.out.println("[solv.do] 받은 seq: " + seq); // 디버깅용 로그

        // 문제 번호로 해당 코드 테스트 문제 데이터 조회
        CodeTestDAO dao = new CodeTestDAO();
        CodeTestDTO dto = dao.get(seq); // 문제 1개 가져오기
        System.out.println("[solv.do] 가져온 제목: " + (dto != null ? dto.getCodeTestTitle() : "dto == null")); // 디버깅용 로그

        // 조회된 문제 데이터를 request 속성에 설정
        req.setAttribute("dto", dto);

        // 코드 테스트 풀이 JSP 페이지로 요청 전달
        req.getRequestDispatcher("/WEB-INF/views/test/codetest/codetestsolv.jsp").forward(req, resp);
    }
}