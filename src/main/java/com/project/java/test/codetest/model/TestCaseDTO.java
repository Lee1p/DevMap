package com.project.java.test.codetest.model;

import lombok.Data;

/**
 * 코드 테스트 케이스 정보를 담는 데이터 전송 객체(DTO) 클래스입니다.
 * 테스트 케이스의 입력값과 예상 출력값 정보를 저장하고 전달하는 역할을 합니다.
 *
 * <p>이 클래스는 Lombok 라이브러리의 {@code @Data} 어노테이션을 사용하여
 * getter, setter, equals, hashCode, toString 메소드를 자동으로 생성합니다.</p>
 */
@Data
public class TestCaseDTO {
    /** 테스트 케이스 고유 식별자(일련번호) */
    private int testCaseSeq;

    /** 이 테스트 케이스가 속한 코드 테스트의 일련번호 */
    private int codeTestSeq;

    /** 테스트 케이스 입력값 */
    private String input;

    /** 테스트 케이스 예상 출력값 */
    private String output;

	public synchronized int getTestCaseSeq() {
		return testCaseSeq;
	}

	public synchronized void setTestCaseSeq(int testCaseSeq) {
		this.testCaseSeq = testCaseSeq;
	}

	public synchronized int getCodeTestSeq() {
		return codeTestSeq;
	}

	public synchronized void setCodeTestSeq(int codeTestSeq) {
		this.codeTestSeq = codeTestSeq;
	}

	public synchronized String getInput() {
		return input;
	}

	public synchronized void setInput(String input) {
		this.input = input;
	}

	public synchronized String getOutput() {
		return output;
	}

	public synchronized void setOutput(String output) {
		this.output = output;
	}
    
    
}