package com.project.java.devtest;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.java.devtest.model.DevTestDAO;
import com.project.java.devtest.model.DevTestDTO;
import com.project.java.devtest.model.UserTechScoreDAO;

/**
 * 사용자의 개발 분야 유형 테스트를 처리하는 서블릿입니다.
 *
 * 이 서블릿은 두 가지 역할을 합니다:
 * 
 * 1. [GET 요청 처리]  
 *    - 사용자가 선택한 개발 분야(백엔드, 프론트엔드 등)를 기반으로,
 *      해당 분야와 관련된 유형 테스트 질문 목록을 조회하고 JSP에 전달합니다.
 *    - 질문과 보기 데이터는 {@link DevTestDAO#getAllQuestionsWithOptions()}를 통해 불러옵니다.
 *
 * 2. [POST 요청 처리]  
 *    - 사용자가 선택한 보기를 파라미터로 받아 각 보기 코드에 해당하는 기술 점수(optionSeq)를 추출합니다.
 *    - 점수는 {@link UserTechScoreDAO#insertOrUpdateTechScore(int, int)}를 통해 저장되며,
 *      이후 {@link UserTechScoreDAO#updateRecommendedTech(int)}를 통해 추천 기술도 계산됩니다.
 *    - 사용자의 중복 제출을 방지하기 위해 세션에 `isSubmitted` 플래그를 설정합니다.
 *
 * 브라우저 캐시를 방지하기 위한 응답 헤더도 설정되어 있어,
 *     사용자가 뒤로 가기 등을 통해 중복 제출하는 것을 방지합니다.
 *
 *  사용 흐름 요약:
 *    - 개발 분야 선택 → GET 요청으로 질문 조회 → 사용자가 보기 선택 후 제출 → POST로 점수 저장 및 결과 페이지 이동
 *
 * URL: {@code /devtest/DevTestSelection.do}
 *
 * @author 이재현
 * @version 1.0
 * @since 2025-04-12
 */


@WebServlet("/devtest/DevTestSelection.do")
public class DevTestSelectionServlet extends HttpServlet {
	
	/**
	 * 사용자가 선택한 개발 분야에 대한 테스트 질문 목록을 보여주는 메서드입니다.
	 *
	 * 이 메서드는 다음과 같은 역할을 수행합니다:
	 * 
	 *   브라우저 캐싱을 방지하기 위한 HTTP 헤더 설정
	 *   세션에서 선택한 개발 분야 정보를 가져와 request 속성에 저장
	 *   모든 개발 테스트 질문 및 옵션 목록을 DB에서 조회
	 *   질문 리스트와 개발 분야 정보를 JSP에 전달하여 출력
	 * 
	 *
	 * @param req 클라이언트의 요청 정보를 담고 있는 HttpServletRequest 객체
	 * @param resp 클라이언트에게 응답을 보내기 위한 HttpServletResponse 객체
	 * @throws ServletException 서블릿 처리 중 발생한 예외
	 * @throws IOException 입출력 처리 중 발생한 예외
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 🔹 브라우저 캐싱 방지를 위한 헤더 설정
	    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    resp.setHeader("Pragma", "no-cache");
	    resp.setDateHeader("Expires", 0);
		
		
		// 세션에서 선택한 개발 분야 정보 가져오기
	    HttpSession session = req.getSession();
	    String selectedField = (String) session.getAttribute("selectedField");
	    String selectedFieldName = (String) session.getAttribute("selectedFieldName");
	    
	    // 개발 분야 정보를 request 속성에 저장
	    req.setAttribute("selectedField", selectedField);
	    req.setAttribute("selectedFieldName", selectedFieldName);
	    
	    // 질문 목록 가져오기
	    DevTestDAO dao = new DevTestDAO();
	    List<DevTestDTO> questionList = dao.getAllQuestionsWithOptions();
	    req.setAttribute("questionList", questionList);
	   
	    
	    // JSP로 포워딩
	    req.getRequestDispatcher("/WEB-INF/views/devtest/devTestSelection.jsp").forward(req, resp);
	}
	
	/**
	 * 사용자가 개발 테스트 질문에 답변한 결과를 처리하고 점수를 저장하는 메서드입니다.
	 *
	 * 이 메서드는 다음의 작업을 수행합니다:
	 * 
	 *   세션에서 사용자 고유 번호(userSeq)를 확인
	 *   이미 제출한 사용자인 경우 결과 페이지로 리다이렉트
	 *   요청으로부터 선택한 옵션들을 파싱하여 각 항목의 점수 저장
	 *   사용자의 기술 점수를 기반으로 추천 기술군 업데이트
	 *   정상 제출 여부를 세션에 플래그로 저장
	 *   결과 페이지로 리다이렉트
	 * 
	 *
	 * @param req 클라이언트의 요청 정보를 담고 있는 HttpServletRequest 객체
	 * @param resp 클라이언트에게 응답을 보내기 위한 HttpServletResponse 객체
	 * @throws ServletException 서블릿 처리 중 발생한 예외
	 * @throws IOException 입출력 처리 중 발생한 예외
	 */
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    HttpSession session = req.getSession();
	    Integer userSeq = (Integer) session.getAttribute("userSeq");

	    if (userSeq == null) {
	        System.out.println("🚨 userSeq 값이 `null`입니다. 세션이 제대로 유지되지 않은 상태!");
	        resp.sendRedirect(req.getContextPath() + "/devtest/DevTestSelection.do");
	        return;
	    }

	    // 🔹 제출 상태 점검
	    Boolean isSubmitted = (Boolean) session.getAttribute("isSubmitted");
	    if (isSubmitted != null && isSubmitted) {
	        System.out.println("⚠ 이미 제출된 데이터입니다! 결과 페이지로 리다이렉트합니다.");
	        resp.sendRedirect(req.getContextPath() + "/devtest/ResultTest.do");
	        return;
	    }

	    // 🔹 점수 처리
	    UserTechScoreDAO scoreDAO = new UserTechScoreDAO();
	    DevTestDAO devTestDAO = new DevTestDAO();

	    Map<String, String[]> parameters = req.getParameterMap();
	    List<String> selectedOptions = new ArrayList<>();

	    for (String key : parameters.keySet()) {
	        if (key.startsWith("question_")) {
	            selectedOptions.addAll(Arrays.asList(parameters.get(key)));
	        }
	    }

	    System.out.println("🔹 선택된 옵션: " + selectedOptions);
	    if (!selectedOptions.isEmpty()) {
	        for (String optionCode : selectedOptions) {
	            int optionSeq = devTestDAO.getOptionSeqByCode(optionCode);
	            if (optionSeq > 0) {
	                scoreDAO.insertOrUpdateTechScore(userSeq, optionSeq);
	            }
	        }
	        scoreDAO.updateRecommendedTech(userSeq);
	    }

	    // 🔹 제출 완료 플래그 설정
	    session.setAttribute("isSubmitted", true); // 정상적으로 제출된 경우 플래그 설정
	    System.out.println("✅ 제출 완료 상태가 세션에 저장되었습니다!");
	    resp.sendRedirect(req.getContextPath() + "/devtest/ResultTest.do");
	}
}
