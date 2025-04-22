package com.project.java.test.codetest.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자의 코딩 테스트 결과 정보를 담는 데이터 전송 객체(DTO) 클래스입니다.
 * 제출한 코드, AI 피드백, 테스트 케이스 통과 여부 등 코딩 테스트 결과와 관련된 데이터를 저장합니다.
 *
 * <p>이 클래스는 Lombok 라이브러리의 어노테이션을 사용하여
 * getter, setter, toString 메소드를 자동으로 생성합니다.</p>
 */
@Getter
@Setter
@ToString
public class UserCoteResultDTO {

    /** 사용자가 제출한 코드 */
    private String submittedCode;

    /** AI가 생성한 코드 피드백 */
    private String aiFeedback;

    /** 사용자의 취약 개념 영역 */
    private String weakConcepts;

    /** 코딩 테스트 통과 여부 ("Y" 또는 "N") */
    private String isPassed;

    /** 통과한 테스트 케이스 수 */
    private int testCasePassed;

    /** 전체 테스트 케이스 수 */
    private int totalTestCases;

    /** 코드 실행 시간(초 단위) */
    private double executionTime;

    /** 코딩 테스트 제목 */
    private String codeTestTitle;

	public synchronized String getSubmittedCode() {
		return submittedCode;
	}

	public synchronized void setSubmittedCode(String submittedCode) {
		this.submittedCode = submittedCode;
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

	public synchronized String getIsPassed() {
		return isPassed;
	}

	public synchronized void setIsPassed(String isPassed) {
		this.isPassed = isPassed;
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

	public synchronized String getCodeTestTitle() {
		return codeTestTitle;
	}

	public synchronized void setCodeTestTitle(String codeTestTitle) {
		this.codeTestTitle = codeTestTitle;
	}
    
    
}