package com.project.java.roadmap;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.project.java.roadmap.model.UserSubjectDAO;

/**
 * 사용자의 과목 완료 정보를 저장하는 서블릿입니다.
 *
 * 요청 방식: POST
 * 요청 경로: /roadmap/usersubject.do
 *
 * 기능:
 * - 사용자가 특정 과목을 완료했는지 확인
 * - 미완료 상태면 완료로 등록
 * - 완료 이력은 tblUserSubject에 저장
 */
@WebServlet("/roadmap/usersubject.do")
public class UserSubject extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            // 1. 사용자 및 과목 번호 파라미터 수신
            int userSeq = Integer.parseInt(req.getParameter("userSeq"));
            int subjectSeq = Integer.parseInt(req.getParameter("subjectSeq"));

            // 2. DAO를 통해 완료 여부 확인 및 저장
            UserSubjectDAO dao = new UserSubjectDAO();
            boolean exists = dao.checkIfExists(userSeq, subjectSeq);

            if (!exists) {
                dao.insertCompletedSubject(userSeq, subjectSeq);
            }

            dao.close();

            // 3. 정상 처리 응답
            resp.setStatus(HttpServletResponse.SC_OK); // 200

        } catch (Exception e) {
            e.printStackTrace();
            // 오류 응답
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 오류: 학습 완료 저장 실패");
        }
    }
}
