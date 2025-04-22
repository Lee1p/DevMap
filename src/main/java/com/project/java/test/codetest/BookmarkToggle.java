package com.project.java.test.codetest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.java.test.codetest.model.CodeTestDAO;

/**
 * 코딩 테스트 북마크 기능을 토글(추가/제거)하는 서블릿 클래스입니다.
 * 사용자가 코딩 테스트를 북마크하거나 북마크 해제하는 요청을 처리합니다.
 *
 * <p>이 서블릿은 POST 요청만 처리하며, 응답으로 JSON 형식의 북마크 상태를 반환합니다.</p>
 * <p>로그인되지 않은 사용자의 경우 로그인 페이지로 리다이렉트됩니다.</p>
 */
@WebServlet("/test/codetest/bookmark-toggle.do")
public class BookmarkToggle extends HttpServlet {

    /**
     * HTTP POST 요청을 처리하여 코딩 테스트의 북마크 상태를 토글합니다.
     *
     * @param req 클라이언트의 HTTP 요청
     * @param resp 서버의 HTTP 응답
     * @throws ServletException 서블릿 처리 중 오류 발생 시
     * @throws IOException 입출력 처리 중 오류 발생 시
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 요청에서 코딩 테스트 시퀀스 번호 파라미터 추출
        int codeTestSeq = Integer.parseInt(req.getParameter("codeTestSeq"));

        // 세션에서 사용자 정보 확인
        HttpSession session = req.getSession();
        if (session.getAttribute("userSeq") == null) {
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            resp.sendRedirect("/devmap/user/login.do");
            return;
        }

        // 세션에서 사용자 시퀀스 번호 추출
        int userSeq = (int) session.getAttribute("userSeq");

        // DAO를 통해 북마크 상태 토글 수행
        CodeTestDAO dao = new CodeTestDAO();
        boolean isBookmarked = dao.toggleBookmark(userSeq, codeTestSeq);

        // JSON 형식으로 북마크 상태 응답
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{\"bookmarked\": " + isBookmarked + "}");
    }
}