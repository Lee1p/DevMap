package com.project.java.roadmap;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.project.java.roadmap.model.SubjectDAO;
import com.project.java.roadmap.model.SubjectDTO;
import com.project.java.roadmap.model.TechDAO;
import com.project.java.roadmap.model.TechDTO;
import com.project.java.roadmap.model.UserTechDAO;

/**
 * 사용자의 기술(Tech) 완료 상태를 판단하여 DB에 반영하는 서블릿입니다.
 *
 * 요청 방식: POST
 * 요청 경로: /roadmap/usertech.do
 *
 * 처리 순서:
 * 1. 로그인 사용자 정보 및 fieldSeq 파라미터 수신
 * 2. 해당 분야의 기술 목록 조회
 * 3. 각 기술에 연결된 과목이 모두 완료됐는지 판단
 * 4. 완료된 기술은 tblUserTech 테이블에 저장
 * 5. 완료된 기술 목록을 JSP에 전달
 */
@WebServlet("/roadmap/usertech.do")
public class UserTech extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. 로그인 사용자 정보 가져오기
        HttpSession session = req.getSession();
        int userSeq = (int) session.getAttribute("userSeq");

        // 2. fieldSeq는 요청 파라미터로 전달됨
        int fieldSeq = Integer.parseInt(req.getParameter("fieldSeq"));

        // 3. DAO 객체 생성
        TechDAO roadmapDAO = new TechDAO();
        SubjectDAO subjectDAO = new SubjectDAO();
        UserTechDAO userTechDAO = new UserTechDAO();

        // 4. 분야별 기술 목록 및 사용자 완료 과목 목록 조회
        List<TechDTO> roadmapList = roadmapDAO.getRoadmapListByField(fieldSeq);
        List<Integer> completedSubjects = subjectDAO.getCompletedSubjectsByUser(userSeq);

        Set<Integer> userCompletedTechSet = new HashSet<>();

        // 5. 기술별 과목 완료 여부 확인
        for (TechDTO tech : roadmapList) {
            List<SubjectDTO> subjectList = subjectDAO.getSubjectsByTech(tech.getTechSeq());

            if (subjectList.isEmpty()) continue;

            // 모든 과목이 완료된 경우에만 기술 완료로 처리
            boolean allCompleted = subjectList.stream()
                .allMatch(sub -> completedSubjects.contains(sub.getSubjectSeq()));

            if (allCompleted) {
                userCompletedTechSet.add(tech.getTechSeq());

                // 중복 저장 방지를 위한 존재 여부 확인
                if (!userTechDAO.exists(userSeq, tech.getTechSeq())) {
                    userTechDAO.insert(userSeq, tech.getTechSeq());
                }
            }
        }

        // 6. 완료된 기술 목록을 request에 저장
        req.setAttribute("userCompletedTechSet", userCompletedTechSet);

        // 7. 결과 페이지로 전달 (또는 JSON 응답 처리 가능)
        req.getRequestDispatcher("/WEB-INF/views/roadmap/techStatus.jsp").forward(req, resp);
    }
}
