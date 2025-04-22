package com.project.java.test.quiz.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 유저별, 퀴즈의 결과를 담는 DTO입니다.
 */
@Getter
@Setter
@ToString
public class UserQuizResultDTO {
    private int userQuizResultSeq;
    private int userSeq;
    private int quizSeq;
    private String selectedAnswer;
    private String isCorrect;
    private String attemptDate;
    
    // 조인 결과를 저장하기 위한 필드
    private String quizTitle;
    private String correctAnswer;
    private int quizScore;
    private String explanation;
	public synchronized int getUserQuizResultSeq() {
		return userQuizResultSeq;
	}
	public synchronized void setUserQuizResultSeq(int userQuizResultSeq) {
		this.userQuizResultSeq = userQuizResultSeq;
	}
	public synchronized int getUserSeq() {
		return userSeq;
	}
	public synchronized void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public synchronized int getQuizSeq() {
		return quizSeq;
	}
	public synchronized void setQuizSeq(int quizSeq) {
		this.quizSeq = quizSeq;
	}
	public synchronized String getSelectedAnswer() {
		return selectedAnswer;
	}
	public synchronized void setSelectedAnswer(String selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}
	public synchronized String getIsCorrect() {
		return isCorrect;
	}
	public synchronized void setIsCorrect(String isCorrect) {
		this.isCorrect = isCorrect;
	}
	public synchronized String getAttemptDate() {
		return attemptDate;
	}
	public synchronized void setAttemptDate(String attemptDate) {
		this.attemptDate = attemptDate;
	}
	public synchronized String getQuizTitle() {
		return quizTitle;
	}
	public synchronized void setQuizTitle(String quizTitle) {
		this.quizTitle = quizTitle;
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
	public synchronized String getExplanation() {
		return explanation;
	}
	public synchronized void setExplanation(String explanation) {
		this.explanation = explanation;
	}
    
    
}
