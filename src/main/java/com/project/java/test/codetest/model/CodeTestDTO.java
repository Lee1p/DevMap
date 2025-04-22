package com.project.java.test.codetest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 코드 테스트 정보를 담는 데이터 전송 객체(DTO) 클래스입니다.
 * 코드 테스트의 기본 정보와 사용자별 상태를 저장하고 전달하는 역할을 합니다.
 *
 * <p>이 클래스는 코드 테스트의 식별 정보, 내용 정보, 상태 정보, 통계 정보 및
 * 입출력 관련 정보를 포함합니다.</p>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CodeTestDTO {

    /** 코드 테스트 고유 식별자(일련번호) */
    private int codeTestSeq;

    /** 코드 테스트 제목 */
    private String codeTestTitle;

    /** 코드 테스트 난이도 (숫자 형태) */
    private int difficulty;

    /** 코드 테스트 문제 내용 */
    private String content;

    /** 코드 테스트가 속한 과목 이름 */
    private String subjectName;

    /** 코드 테스트에 사용되는 기술 이름 */
    private String techName;

    /**
     * 코드 테스트 상태 표시
     * "y", "n" 또는 null 값을 가질 수 있음
     */
    private String status;

    /** 이 문제를 해결한 사용자 수 */
    private int solvedCount;

    /** 이 문제를 시도한 총 사용자 수 */
    private int totalCount;

    /** 정답률 (백분율, solvedCount와 totalCount 기반 계산값) */
    private int accuracy;

    /** 현재 사용자의 코드 테스트 통과 여부 */
    private String isPassed;

    /** 현재 사용자의 북마크 여부 */
    private boolean isBookmarked;

    /** 입력 형식에 대한 설명 */
    private String inputDescription;

    /** 출력 형식에 대한 설명 */
    private String outputDescription;

    /** 샘플 입력 예제 */
    private String sampleInput;

    /** 샘플 출력 예제 */
    private String sampleOutput;

	public synchronized int getCodeTestSeq() {
		return codeTestSeq;
	}

	public synchronized void setCodeTestSeq(int codeTestSeq) {
		this.codeTestSeq = codeTestSeq;
	}

	public synchronized String getCodeTestTitle() {
		return codeTestTitle;
	}

	public synchronized void setCodeTestTitle(String codeTestTitle) {
		this.codeTestTitle = codeTestTitle;
	}

	public synchronized int getDifficulty() {
		return difficulty;
	}

	public synchronized void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public synchronized String getContent() {
		return content;
	}

	public synchronized void setContent(String content) {
		this.content = content;
	}

	public synchronized String getSubjectName() {
		return subjectName;
	}

	public synchronized void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public synchronized String getTechName() {
		return techName;
	}

	public synchronized void setTechName(String techName) {
		this.techName = techName;
	}

	public synchronized String getStatus() {
		return status;
	}

	public synchronized void setStatus(String status) {
		this.status = status;
	}

	public synchronized int getSolvedCount() {
		return solvedCount;
	}

	public synchronized void setSolvedCount(int solvedCount) {
		this.solvedCount = solvedCount;
	}

	public synchronized int getTotalCount() {
		return totalCount;
	}

	public synchronized void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public synchronized int getAccuracy() {
		return accuracy;
	}

	public synchronized void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public synchronized String getIsPassed() {
		return isPassed;
	}

	public synchronized void setIsPassed(String isPassed) {
		this.isPassed = isPassed;
	}

	public synchronized boolean isBookmarked() {
		return isBookmarked;
	}

	public synchronized void setBookmarked(boolean isBookmarked) {
		this.isBookmarked = isBookmarked;
	}

	public synchronized String getInputDescription() {
		return inputDescription;
	}

	public synchronized void setInputDescription(String inputDescription) {
		this.inputDescription = inputDescription;
	}

	public synchronized String getOutputDescription() {
		return outputDescription;
	}

	public synchronized void setOutputDescription(String outputDescription) {
		this.outputDescription = outputDescription;
	}

	public synchronized String getSampleInput() {
		return sampleInput;
	}

	public synchronized void setSampleInput(String sampleInput) {
		this.sampleInput = sampleInput;
	}

	public synchronized String getSampleOutput() {
		return sampleOutput;
	}

	public synchronized void setSampleOutput(String sampleOutput) {
		this.sampleOutput = sampleOutput;
	}
    
    
}