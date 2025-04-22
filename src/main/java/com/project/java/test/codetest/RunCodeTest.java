package com.project.java.test.codetest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.project.java.test.codetest.model.CodeTestDAO;
import com.project.java.test.codetest.model.TestCaseDTO;

/**
 * 사용자가 제출한 자바 코드를 실시간으로 컴파일하고 실행하여 테스트 케이스와 비교하는 서블릿입니다.
 * 이 서블릿은 코드 테스트 플랫폼에서 실시간 코드 실행 및 평가 기능을 담당합니다.
 *
 * <p>주요 기능:</p>
 * <ul>
 *   <li>사용자가 작성한 Java 코드를 동적으로 파일 생성</li>
 *   <li>생성된 파일을 컴파일하고 실행</li>
 *   <li>테스트 케이스를 통한 실행 결과 검증</li>
 *   <li>각 테스트 케이스별 통과 여부 JSON 형태로 응답</li>
 * </ul>
 *
 * <p>프로세스 순서:</p>
 * <ol>
 *   <li>사용자 코드와 문제 번호 수신</li>
 *   <li>문제에 해당하는 테스트 케이스 로드</li>
 *   <li>각 테스트 케이스에 대해 사용자 코드 실행</li>
 *   <li>실행 결과와 기대 결과 비교</li>
 *   <li>모든 테스트 케이스 결과를 JSON으로 응답</li>
 * </ol>
 *
 * @author [박주승]
 * @version 1.0
 */
@WebServlet("/test/codetest/run.do")
public class RunCodeTest extends HttpServlet {

	/**
	 * 사용자 코드를 실행하고 그 결과를 반환하는 메서드입니다.
	 *
	 * <p>처리 과정:</p>
	 * <ol>
	 *   <li>사용자 코드를 포함한 자바 파일 생성 (/tmp/UserSolution.java)</li>
	 *   <li>javac 명령을 통한 컴파일 실행</li>
	 *   <li>java 명령으로 컴파일된 클래스 실행</li>
	 *   <li>표준 입력으로 테스트 케이스 입력값 전달</li>
	 *   <li>표준 출력에서 실행 결과 수집 및 반환</li>
	 * </ol>
	 *
	 * @param code 사용자가 제출한 Java 코드
	 * @param input 테스트 케이스 입력값
	 * @return 실행 결과 문자열, 오류 발생 시 오류 메시지 반환
	 */
	private String executeUserCode(String code, String input) {
		try {
			// 1. .java 파일 생성
			String className = "UserSolution";
			String filePath = "/tmp/" + className + ".java";

			String fullCode = "import java.util.*;\n" +
					"public class " + className + " {\n" +
					"public static void main(String[] args) {\n" +
					"Scanner sc = new Scanner(System.in);\n" +
					"// 여기에 유저 코드 들어감\n" +
					code + "\n" +
					"}\n}";

			Files.write(Paths.get(filePath), fullCode.getBytes());

			// 2. javac 컴파일
			Process compile = Runtime.getRuntime().exec("javac " + filePath);
			compile.waitFor();

			// 3. java 실행
			Process run = Runtime.getRuntime().exec("java -cp /tmp " + className);
			try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(run.getOutputStream()))) {
				writer.write(input);
				writer.flush();
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(run.getInputStream()));
			StringBuilder output = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line).append("\n");
			}

			return output.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "실행 중 오류 발생";
		}
	}

	/**
	 * HTTP POST 요청을 처리하여 사용자 코드를 실행하고 테스트 케이스 결과를 반환합니다.
	 *
	 * <p>요청 매개변수:</p>
	 * <ul>
	 *   <li>code: 사용자가 제출한 Java 코드</li>
	 *   <li>codeTestSeq: 테스트할 문제 번호</li>
	 * </ul>
	 *
	 * <p>응답 형식 (JSON):</p>
	 * <pre>
	 * {
	 *   "testCases": [
	 *     {
	 *       "input": "테스트 케이스 입력",
	 *       "expected": "기대 출력",
	 *       "actual": "실제 출력",
	 *       "passed": true/false
	 *     },
	 *     ...
	 *   ]
	 * }
	 * </pre>
	 *
	 * @param req HTTP 요청 객체
	 * @param resp HTTP 응답 객체
	 * @throws ServletException 서블릿 처리 중 오류 발생 시
	 * @throws IOException 입출력 처리 중 오류 발생 시
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String code = req.getParameter("code");
		String codeTestSeq = req.getParameter("codeTestSeq");
		CodeTestDAO dao = new CodeTestDAO();
		// 1. 테스트 케이스 불러오기
		List<TestCaseDTO> testCases = dao.getTestCases(codeTestSeq);

		// 2. 코드 파일 생성 및 실행
		// 3. 결과 비교 후 JSON 응답
		JSONArray resultArray = new JSONArray();
		for (TestCaseDTO test : testCases) {
			String input = test.getInput();
			String expected = test.getOutput();
			String actual = executeUserCode(code, input); // 유저 코드 실행 결과

			JSONObject obj = new JSONObject();
			obj.put("input", input);
			obj.put("expected", expected);
			obj.put("actual", actual);
			obj.put("passed", expected.trim().equals(actual.trim()));

			resultArray.add(obj);
		}

		JSONObject result = new JSONObject();
		result.put("testCases", resultArray);

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().write(result.toJSONString());
	}
}