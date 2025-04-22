package com.project.java.test.quiz.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 퀴즈의 정보를 담는 DTO입니다.
 * 기본 정보로는 seq, 과목 seq, 퀴즈명과 4지선다형의 문제, 정답, 점수, 생성일과 수정일 정보를 담습니다.
 */
@Getter
@Setter
@ToString
public class QuizDTO {
    private int quizSeq;
    private int subjectSeq;
    private String quizTitle;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;
    private int quizScore;
    private String regDate;
    private String modDate;
    
    // 추가된 필드
    private String explanation; // 문제 해설

	public synchronized int getQuizSeq() {
		return quizSeq;
	}

	public synchronized void setQuizSeq(int quizSeq) {
		this.quizSeq = quizSeq;
	}

	public synchronized int getSubjectSeq() {
		return subjectSeq;
	}

	public synchronized void setSubjectSeq(int subjectSeq) {
		this.subjectSeq = subjectSeq;
	}

	public synchronized String getQuizTitle() {
		return quizTitle;
	}

	public synchronized void setQuizTitle(String quizTitle) {
		this.quizTitle = quizTitle;
	}

	public synchronized String getOptionA() {
		return optionA;
	}

	public synchronized void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public synchronized String getOptionB() {
		return optionB;
	}

	public synchronized void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public synchronized String getOptionC() {
		return optionC;
	}

	public synchronized void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public synchronized String getOptionD() {
		return optionD;
	}

	public synchronized void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public synchronized String getCorrectAnswer() {
		return correctAnswer;
	}

	public synchronized void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public synchronized int getQuizScore() {
		return quizScore;
	}

	public synchronized void setQuizScore(int quizScore) {
		this.quizScore = quizScore;
	}

	public synchronized String getRegDate() {
		return regDate;
	}

	public synchronized void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public synchronized String getModDate() {
		return modDate;
	}

	public synchronized void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public synchronized String getExplanation() {
		return explanation;
	}

	public synchronized void setExplanation(String explanation) {
		this.explanation = explanation;
	}
    
    
}
