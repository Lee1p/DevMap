package com.project.java.test.codetest.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.project.java.test.codetest.model.AIResultDTO;

/**
 * 사용자가 제출한 코드를 AI 서버로 전송하여 분석 결과를 받아오는 유틸리티 클래스입니다.
 * OpenAI(GPT) API를 활용한 외부 분석 서버와 통신하여 코드의 품질, 정확성, 성능 등을 평가합니다.
 *
 * <p>주요 기능:</p>
 * <ul>
 *   <li>사용자 코드와 문제 정보를 AI 분석 서버로 전송</li>
 *   <li>HTTP 통신을 통한 JSON 기반 요청/응답 처리</li>
 *   <li>분석 결과를 구조화된 형태(AIResultDTO)로 변환</li>
 * </ul>
 *
 * <p>분석 서버 응답 포함 정보:</p>
 * <ul>
 *   <li>코드 통과 여부 (isPassed)</li>
 *   <li>AI 피드백 (aiFeedback)</li>
 *   <li>취약 개념 (weakConcepts)</li>
 *   <li>통과한 테스트 케이스 수 (testCasePassed)</li>
 *   <li>전체 테스트 케이스 수 (totalTestCases)</li>
 *   <li>실행 시간 (executionTime)</li>
 * </ul>
 *
 * @author [박주승]
 * @version 1.0
 */
public class AIAnalyzer {

	/**
	 * 사용자 코드와 문제 정보를 AI 분석 서버로 전송하고 분석 결과를 받아옵니다.
	 *
	 * <p>처리 과정:</p>
	 * <ol>
	 *   <li>HTTP 연결 설정 및 요청 헤더 구성</li>
	 *   <li>코드와 문제 정보를 포함한 JSON 요청 생성</li>
	 *   <li>HTTP POST 요청 전송</li>
	 *   <li>응답 데이터를 AIResultDTO 객체로 변환</li>
	 * </ol>
	 *
	 * @param code 사용자가 제출한 코드
	 * @param codeTestSeq 코드 테스트 문제 번호
	 * @param subjectList 관련 과목 목록 (쉼표로 구분된 문자열)
	 * @param codeTestTitle 코드 테스트 문제 제목
	 * @param codeTestDesc 코드 테스트 문제 설명
	 * @param inputDescription 입력 형식 설명
	 * @param outputDescription 출력 형식 설명
	 * @param sampleInput 샘플 입력 데이터
	 * @param sampleOutput 샘플 출력 데이터
	 * @return AI 분석 결과를 담은 AIResultDTO 객체. 통신 실패 시 null 반환
	 */
	public static AIResultDTO sendToAI(String code, String codeTestSeq, String subjectList,
									   String codeTestTitle, String codeTestDesc,
									   String inputDescription, String outputDescription,
									   String sampleInput, String sampleOutput) {
		try {
			// AI 분석 서버 URL 설정 및 연결
			URL url = new URL("http://localhost:5000/api/analyze");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// HTTP 요청 설정
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);

			// 요청 JSON 구성
			CodeRequest request = new CodeRequest(code, codeTestSeq, subjectList, codeTestTitle, codeTestDesc,
					inputDescription, outputDescription, sampleInput, sampleOutput);

			String json = new Gson().toJson(request);

			// 요청 데이터 전송
			try (OutputStream os = conn.getOutputStream()) {
				os.write(json.getBytes("UTF-8"));
			}

			// 응답 데이터 수신 및 변환
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			AIResultDTO result = new Gson().fromJson(reader, AIResultDTO.class);
			reader.close();

			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * AI 분석 서버로 전송할 요청 데이터를 담는 내부 클래스입니다.
	 * 사용자 코드와 문제 관련 정보를 구조화하여 JSON 형태로 변환하기 위해 사용됩니다.
	 */
	private static class CodeRequest {
		/** 사용자가 제출한 코드 */
		private String code;
		/** 코드 테스트 문제 번호 */
		private String codeTestSeq;
		/** 관련 과목 목록 (쉼표로 구분된 문자열) */
		private String subjectList;
		/** 코드 테스트 문제 제목 */
		private String codeTestTitle;
		/** 코드 테스트 문제 설명 */
		private String codeTestDesc;
		/** 입력 형식 설명 */
		private String inputDescription;
		/** 출력 형식 설명 */
		private String outputDescription;
		/** 샘플 입력 데이터 */
		private String sampleInput;
		/** 샘플 출력 데이터 */
		private String sampleOutput;

		/**
		 * CodeRequest 객체를 생성합니다.
		 *
		 * @param code 사용자가 제출한 코드
		 * @param codeTestSeq 코드 테스트 문제 번호
		 * @param subjectList 관련 과목 목록 (쉼표로 구분된 문자열)
		 * @param codeTestTitle 코드 테스트 문제 제목
		 * @param codeTestDesc 코드 테스트 문제 설명
		 * @param inputDescription 입력 형식 설명
		 * @param outputDescription 출력 형식 설명
		 * @param sampleInput 샘플 입력 데이터
		 * @param sampleOutput 샘플 출력 데이터
		 */
		public CodeRequest(String code, String codeTestSeq, String subjectList,
						   String codeTestTitle, String codeTestDesc,
						   String inputDescription, String outputDescription,
						   String sampleInput, String sampleOutput) {
			this.code = code;
			this.codeTestSeq = codeTestSeq;
			this.subjectList = subjectList;
			this.codeTestTitle = codeTestTitle;
			this.codeTestDesc = codeTestDesc;
			this.inputDescription = inputDescription;
			this.outputDescription = outputDescription;
			this.sampleInput = sampleInput;
			this.sampleOutput = sampleOutput;
		}
	}
}