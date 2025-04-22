package com.project.java.roadmap;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.project.java.roadmap.model.*;

/**
 * 기술 정보에 따라 과목 리스트를 조회하고 난이도별로 분류하여 사이드바로 전달하는 서블릿입니다.
 *
 * 요청 방식: GET
 * 요청 경로: /roadmap/techsidebar.do
 *
 * 처리 흐름:
 * 1. techName, techSeq, subjectName 중 하나를 기준으로 과목 목록 조회
 * 2. 과목을 난이도별로 분류 (초급/중급/고급)
 * 3. 완료한 과목 리스트와 함께 JSP로 전달
 */
@WebServlet("/roadmap/techsidebar.do")
public class TechSidebar extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            // 1. 파라미터 추출
            String techName = req.getParameter("techName");
            String subjectName = req.getParameter("subjectName");
            String techSeqStr = req.getParameter("techSeq");

            List<SubjectDTO> subjectList = new ArrayList<>();
            SubjectDAO subjectDao = new SubjectDAO();

            if (techName != null) {
                // 기술명으로 기술 및 과목 조회
                TechDAO techDao = new TechDAO();
                TechDTO tech = techDao.getTechByName(techName);
                if (tech == null) throw new Exception("기술 정보 없음: " + techName);

                req.setAttribute("tech", tech);
                subjectList = subjectDao.getSubjectsByTech(tech.getTechSeq());

            } else if (techSeqStr != null) {
                // 기술 번호로 기술 및 과목 조회
                int techSeq = Integer.parseInt(techSeqStr);

                TechDAO techDao = new TechDAO();
                TechDTO tech = techDao.getTechBySeq(techSeq);
                if (tech == null) throw new Exception("기술 정보 없음 (techSeq=" + techSeq + ")");

                req.setAttribute("tech", tech);
                subjectList = subjectDao.getSubjectsByTech(techSeq);

            } else if (subjectName != null) {
                // 과목명으로 과목 리스트 조회
                subjectList = subjectDao.getSubjectsByName(subjectName);

            } else {
                throw new Exception("techName, techSeq, subjectName 중 하나는 반드시 필요합니다.");
            }

            subjectDao.close();

            if (subjectList == null || subjectList.isEmpty()) {
                throw new Exception("과목 목록이 비어 있습니다.");
            }

            // 2. 과목 난이도별 분류
            Map<String, List<SubjectDTO>> levelMap = new HashMap<>();
            levelMap.put("초급", new ArrayList<>());
            levelMap.put("중급", new ArrayList<>());
            levelMap.put("고급", new ArrayList<>());

            for (SubjectDTO dto : subjectList) {
                try {
                    int difficulty = Integer.parseInt(dto.getSubjectDifficulty());
                    String level = getLevelName(difficulty);
                    levelMap.get(level).add(dto);
                } catch (Exception e) {
                    System.out.println("[난이도 변환 오류] subjectSeq: " + dto.getSubjectSeq());
                }
            }

            req.setAttribute("levelMap", levelMap);
            req.setAttribute("levels", new String[] { "초급", "중급", "고급" });

            // 3. 사용자 완료 과목 리스트 조회
            int userSeq = -1;
            Object sessionUserSeq = req.getSession().getAttribute("userSeq");
            if (sessionUserSeq != null) {
                userSeq = Integer.parseInt(sessionUserSeq.toString());
            }

            List<Integer> completedList = new ArrayList<>();
            if (userSeq != -1) {
                UserSubjectDAO userDao = new UserSubjectDAO();
                completedList = userDao.getCompletedSubjectSeqs(userSeq);
                userDao.close();
            }

            req.setAttribute("completedList", completedList);

            // 4. JSP로 포워딩
            req.getRequestDispatcher("/WEB-INF/views/roadmap/techsidebar.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("text/plain; charset=UTF-8");
            resp.getWriter().write("서버 오류:\n" + e.getMessage());
        }
    }

    /**
     * 난이도 숫자 값을 문자열 이름으로 변환합니다.
     *
     * @param difficulty 난이도 숫자 (1~3)
     * @return 난이도 명칭
     */
    private String getLevelName(int difficulty) {
        switch (difficulty) {
            case 1: return "초급";
            case 2: return "중급";
            case 3: return "고급";
            default: return "기타";
        }
    }
}
