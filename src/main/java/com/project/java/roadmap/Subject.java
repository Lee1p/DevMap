package com.project.java.roadmap;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.java.roadmap.model.SubjectDAO;
import com.project.java.roadmap.model.SubjectDTO;

/**
 *  과목 상세 조회 서블릿
 * 
 * 사용자가 로드맵에서 과목을 클릭했을 때, 해당 과목의 상세 정보를 조회하여
 * JSP 뷰에 전달합니다.
 * 
 * <p>요청 경로: /roadmap/subject.do</p>
 * 
 * <p>처리 순서:</p>
 * <ol>
 *   <li>파라미터(subjectSeq) 유효성 검사</li>
 *   <li>DAO를 통해 과목 상세 정보 조회</li>
 *   <li>JSP 페이지로 전달 (subject.jsp)</li>
 * </ol>
 * 
 * ❗ 유효하지 않은 요청 또는 조회 실패 시, 적절한 에러 코드 반환
 */
@WebServlet("/roadmap/subject.do")
public class Subject extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //  1. 파라미터 받기
        String subjectSeqParam = req.getParameter("subjectSeq");

        //  필수 파라미터 누락 방지 처리
        if (subjectSeqParam == null || subjectSeqParam.trim().equals("")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다. subjectSeq 누락");
            return;
        }

        int subjectSeq = 0;
        try {
            // ✅ 숫자인지 검증
            subjectSeq = Integer.parseInt(subjectSeqParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "subjectSeq는 숫자여야 합니다.");
            return;
        }

        //  2. DAO 통해 데이터 가져오기
        SubjectDAO dao = new SubjectDAO();
        SubjectDTO dto = dao.getSubjectDetail(subjectSeq);
        dao.close();

        //  해당 과목이 없을 경우
        if (dto == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "해당 과목을 찾을 수 없습니다.");
            return;
        }

        //  3. JSP로 데이터 전달
        req.setAttribute("subject", dto);
        req.getRequestDispatcher("/WEB-INF/views/roadmap/subject.jsp").forward(req, resp);
    }
}
