package com.project.java.test.codetest;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.java.test.codetest.model.SubjectDAO;
import com.project.java.test.codetest.model.TechDAO;
import com.project.java.test.codetest.model.TechDTO;

/**
 * 코드 테스트 기능을 담당하는 서블릿 클래스입니다.
 * 이 서블릿은 사용자의 코드 테스트 페이지 요청을 처리하고, 필요한 데이터를 제공합니다.
 * URL 패턴 "/test/codetest/codetest.do"로 매핑되어 있습니다.
 *
 * <p>주요 기능:</p>
 * <ul>
 *   <li>사용자 인증 확인</li>
 *   <li>주제(Subject) 목록 조회</li>
 *   <li>기술(Tech) 목록 조회</li>
 *   <li>코드 테스트 페이지로 이동</li>
 * </ul>
 *
 * @author [박주승]
 * @version 1.0
 */
@WebServlet("/test/codetest/codetest.do")
public class CodeTest extends HttpServlet {

    /**
     * HTTP GET 요청을 처리하는 메서드입니다.
     * 사용자가 코드 테스트 페이지에 접근할 때 호출됩니다.
     *
     * <p>이 메서드의 처리 과정:</p>
     * <ol>
     *   <li>사용자 세션 확인 및 인증되지 않은 사용자 리다이렉트</li>
     *   <li>모든 주제(Subject) 이름 목록 조회</li>
     *   <li>기술(Tech) 목록 조회</li>
     *   <li>조회된 데이터를 request 속성에 설정</li>
     *   <li>코드 테스트 JSP 페이지로 요청 전달</li>
     * </ol>
     *
     * @param req 클라이언트의 HTTP 요청 객체
     * @param resp 클라이언트로 보낼 HTTP 응답 객체
     * @throws ServletException 서블릿 처리 중 오류 발생 시
     * @throws IOException I/O 작업 중 오류 발생 시
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 세션 확인 및 사용자 인증
        HttpSession session = req.getSession();
        if (session.getAttribute("userSeq") == null) {
            resp.sendRedirect("/devmap/user/login.do");
            return;
        }

        int userSeq = (int) session.getAttribute("userSeq");

        // 모든 주제 이름 목록 가져오기
        SubjectDAO dao = new SubjectDAO();
        List<String> subjectList = dao.getAllSubjectNames();

        // 기술 목록 가져오기
        TechDAO techDAO = new TechDAO();
        List<TechDTO> techList = techDAO.getTechList();

        // 조회된 데이터를 request 속성에 설정
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("techList", techList);

        // 코드 테스트 JSP 페이지로 요청 전달
        req.getRequestDispatcher("/WEB-INF/views/test/codetest/codetest.jsp").forward(req, resp);
    }
}