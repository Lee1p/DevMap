package com.project.java.test.codetest;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.project.java.test.codetest.model.CodeTestDAO;
import com.project.java.test.codetest.model.CodeTestDTO;
import com.project.java.test.codetest.model.TechDAO;
import com.project.java.test.codetest.model.TechDTO;

/**
 * 코드 테스트 목록을 제공하는 서블릿 클래스입니다.
 * 이 서블릿은 사용자의 코드 테스트 목록 요청을 처리하고, 필터링된 결과를 JSON 형식으로 반환합니다.
 * URL 패턴 "/test/codetest/list.do"로 매핑되어 있습니다.
 *
 * <p>주요 기능:</p>
 * <ul>
 *   <li>사용자 인증 확인</li>
 *   <li>코드 테스트 목록 필터링(상태, 난이도, 기술별)</li>
 *   <li>필터링된 코드 테스트 목록 JSON 형식으로 반환</li>
 * </ul>
 *
 * <p>응답 JSON 데이터 형식:</p>
 * <pre>
 * [
 *   {
 *     "codeTestSeq": 문제 고유 번호,
 *     "title": 문제 제목,
 *     "difficulty": 난이도,
 *     "subjectName": 주제명,
 *     "techName": 기술명,
 *     "status": 풀이 상태,
 *     "accuracy": 정답률,
 *     "isPassed": 통과 여부
 *   },
 *   ...
 * ]
 * </pre>
 *
 * @author [박주승]
 * @version 1.0
 */
@WebServlet("/test/codetest/list.do")
public class CodeTestList extends HttpServlet {

	/**
	 * HTTP GET 요청을 처리하는 메서드입니다.
	 * 사용자가 코드 테스트 목록을 요청할 때 호출됩니다.
	 *
	 * <p>이 메서드의 처리 과정:</p>
	 * <ol>
	 *   <li>사용자 세션 확인 및 인증되지 않은 사용자 리다이렉트</li>
	 *   <li>요청 파라미터(상태, 난이도, 기술)를 추출</li>
	 *   <li>사용자 ID와 필터 조건에 맞는 코드 테스트 목록 조회</li>
	 *   <li>기술 목록 조회 및 설정</li>
	 *   <li>조회된 코드 테스트 데이터를 JSON 형식으로 변환</li>
	 *   <li>JSON 데이터를 클라이언트에 응답</li>
	 * </ol>
	 *
	 * <p>필터 파라미터:</p>
	 * <ul>
	 *   <li>status: 풀이 상태 필터 (solved, unsolved)</li>
	 *   <li>difficulty: 난이도 필터 (1, 2, 3)</li>
	 *   <li>tech: 기술 고유 번호 필터</li>
	 * </ul>
	 *
	 * @param req 클라이언트의 HTTP 요청 객체. 필터 파라미터 포함
	 * @param resp 클라이언트로 보낼 HTTP 응답 객체. JSON 데이터가 포함됨
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

		// 필터 파라미터 추출
		String status = req.getParameter("status");         // solved, unsolved
		String difficulty = req.getParameter("difficulty"); // 1,2,3
		String tech = req.getParameter("tech");             // techSeq

		// 필터링된 코드 테스트 목록 조회
		CodeTestDAO dao = new CodeTestDAO();
		List<CodeTestDTO> list = dao.getFilteredList(userSeq, status, difficulty, tech);

		// 기술 목록 조회 및 설정
		TechDAO techDAO = new TechDAO();
		List<TechDTO> techList = techDAO.getTechList();
		req.setAttribute("techList", techList);

		// 코드 테스트 목록을 JSON 배열로 변환
		JSONArray arr = new JSONArray();
		for (CodeTestDTO dto : list) {
			JSONObject obj = new JSONObject();
			obj.put("codeTestSeq", dto.getCodeTestSeq());
			obj.put("title", dto.getCodeTestTitle());
			obj.put("difficulty", dto.getDifficulty());
			obj.put("subjectName", dto.getSubjectName());
			obj.put("techName", dto.getTechName());
			obj.put("status", dto.getStatus());
			obj.put("accuracy", dto.getAccuracy());
			obj.put("isPassed", dto.getIsPassed());
			arr.add(obj);
		}

		// JSON 응답 설정 및 전송
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().write(arr.toJSONString());
	}
}