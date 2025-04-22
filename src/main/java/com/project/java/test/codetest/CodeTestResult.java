package com.project.java.test.codetest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.java.test.codetest.model.CodeTestDAO;
import com.project.java.test.codetest.model.UserCoteResultDTO;

/**
 * 코드 테스트 결과를 조회하는 서블릿 클래스입니다.
 * 이 서블릿은 사용자의 특정 코드 테스트에 대한 최신 결과를 조회하고 결과 페이지로 이동시킵니다.
 * URL 패턴 "/test/codetest/codetestresult.do"로 매핑되어 있습니다.
 *
 * <p>주요 기능:</p>
 * <ul>
 *   <li>사용자 인증 확인</li>
 *   <li>특정 코드 테스트에 대한 사용자의 최신 결과 조회</li>
 *   <li>결과 데이터를 결과 페이지로 전달</li>
 * </ul>
 *
 * <p>요청 파라미터:</p>
 * <ul>
 *   <li>codeTestSeq: 결과를 조회할 코드 테스트의 고유 번호</li>
 * </ul>
 *
 * @author [박주승]
 * @version 1.0
 */
@WebServlet("/test/codetest/codetestresult.do")
public class CodeTestResult extends HttpServlet {

	/**
	 * HTTP GET 요청을 처리하는 메서드입니다.
	 * 사용자가 특정 코드 테스트의 결과를 조회할 때 호출됩니다.
	 *
	 * <p>이 메서드의 처리 과정:</p>
	 * <ol>
	 *   <li>사용자 세션 확인 및 인증되지 않은 사용자 리다이렉트</li>
	 *   <li>요청 파라미터에서 코드 테스트 고유 번호(codeTestSeq) 추출</li>
	 *   <li>사용자와 코드 테스트에 대한 최신 결과 조회</li>
	 *   <li>결과 데이터를 request 속성에 설정</li>
	 *   <li>코드 테스트 결과 JSP 페이지로 요청 전달</li>
	 * </ol>
	 *
	 * @param req 클라이언트의 HTTP 요청 객체. codeTestSeq 파라미터 포함
	 * @param resp 클라이언트로 보낼 HTTP 응답 객체
	 * @throws ServletException 서블릿 처리 중 오류 발생 시
	 * @throws IOException I/O 작업 중 오류 발생 시
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// 세션 확인 및 사용자 인증
		HttpSession session = req.getSession();
		if (session.getAttribute("userSeq") == null) {
			resp.sendRedirect("/devmap/user/login.do");
			return;
		}

		int userSeq = (int) session.getAttribute("userSeq");

		// 코드 테스트 고유 번호 파라미터 추출
		String codeTestSeq = req.getParameter("codeTestSeq");

		// 사용자의 최신 코드 테스트 결과 조회
		CodeTestDAO dao = new CodeTestDAO();
		UserCoteResultDTO result = dao.getLatestUserResult(userSeq, codeTestSeq);

		// 결과 데이터를 request 속성에 설정
		req.setAttribute("result", result);

		// 코드 테스트 결과 JSP 페이지로 요청 전달
		req.getRequestDispatcher("/WEB-INF/views/test/codetest/codetestresult.jsp").forward(req, resp);
	}
}