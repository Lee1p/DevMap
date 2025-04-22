package com.project.java.test.codetest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * AI 분석 결과를 담는 데이터 전송 객체(DTO)입니다.
 * AIAnalyzer에서 코드 분석 후 받아온 결과를 저장하고 전달하는 역할을 합니다.
 *
 * <p>포함하는 정보:</p>
 * <ul>
 *   <li>코드 테스트 통과 여부</li>
 *   <li>AI가 제공하는 피드백</li>
 *   <li>취약한 개념 영역</li>
 *   <li>통과한 테스트 케이스 수</li>
 *   <li>전체 테스트 케이스 수</li>
 *   <li>코드 실행 시간</li>
 * </ul>
 *
 * <p>Lombok 애노테이션을 사용하여 getter, setter, 생성자, toString 메서드가 자동 생성됩니다.</p>
 *
 * @author [박주승]
 * @version 1.0
 * @see com.project.java.test.codetest.util.AIAnalyzer
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AIResultDTO {

    /**
     * 코드 테스트 통과 여부를 나타내는 문자열입니다.
     * "true"인 경우 통과, "false"인 경우 미통과를 의미합니다.
     */
    private String isPassed;

    /**
     * AI가 제공하는 코드에 대한 피드백 문자열입니다.
     * 코드의 품질, 구조, 개선점 등에 대한 상세한 설명을 포함합니다.
     */
    private String aiFeedback;

    /**
     * 사용자가 코드 작성 시 취약한 개념적 영역을 나타내는 문자열입니다.
     * 콤마(,)로 구분된 개념 목록 형태로 제공됩니다.
     * 예: "반복문, 재귀함수, 메모리 관리"
     */
    private String weakConcepts;

    /**
     * 통과한 테스트 케이스의 수입니다.
     */
    private int testCasePassed;

    /**
     * 전체 테스트 케이스의 수입니다.
     */
    private int totalTestCases;

    /**
     * 코드 실행에 소요된 시간(초 단위)입니다.
     */
    private double executionTime;

	public AIResultDTO(String string, String string2, String string3, int i, int j, double d) {
		// 추가해둠
	}

	public synchronized String getIsPassed() {
		return isPassed;
	}

	public synchronized void setIsPassed(String isPassed) {
		this.isPassed = isPassed;
	}

	public synchronized String getAiFeedback() {
		return aiFeedback;
	}

	public synchronized void setAiFeedback(String aiFeedback) {
		this.aiFeedback = aiFeedback;
	}

	public synchronized String getWeakConcepts() {
		return weakConcepts;
	}

	public synchronized void setWeakConcepts(String weakConcepts) {
		this.weakConcepts = weakConcepts;
	}

	public synchronized int getTestCasePassed() {
		return testCasePassed;
	}

	public synchronized void setTestCasePassed(int testCasePassed) {
		this.testCasePassed = testCasePassed;
	}

	public synchronized int getTotalTestCases() {
		return totalTestCases;
	}

	public synchronized void setTotalTestCases(int totalTestCases) {
		this.totalTestCases = totalTestCases;
	}

	public synchronized double getExecutionTime() {
		return executionTime;
	}

	public synchronized void setExecutionTime(double executionTime) {
		this.executionTime = executionTime;
	}

    // Getter & Setter 생략 (Lombok에 의해 자동 생성됨)
    
    
}