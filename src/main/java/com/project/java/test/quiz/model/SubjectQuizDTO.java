package com.project.java.test.quiz.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 과목의 정보를 담는 DTO입니다.
 */
@Getter
@Setter
@ToString
public class SubjectQuizDTO {
    private int subjectSeq;
    private String subjectName;
    private int progressPercentage;
    private int correctRate;
    private String progressBarColor;
    private String badgeColor;
	public synchronized int getSubjectSeq() {
		return subjectSeq;
	}
	public synchronized void setSubjectSeq(int subjectSeq) {
		this.subjectSeq = subjectSeq;
	}
	public synchronized String getSubjectName() {
		return subjectName;
	}
	public synchronized void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public synchronized int getProgressPercentage() {
		return progressPercentage;
	}
	public synchronized void setProgressPercentage(int progressPercentage) {
		this.progressPercentage = progressPercentage;
	}
	public synchronized int getCorrectRate() {
		return correctRate;
	}
	public synchronized void setCorrectRate(int correctRate) {
		this.correctRate = correctRate;
	}
	public synchronized String getProgressBarColor() {
		return progressBarColor;
	}
	public synchronized void setProgressBarColor(String progressBarColor) {
		this.progressBarColor = progressBarColor;
	}
	public synchronized String getBadgeColor() {
		return badgeColor;
	}
	public synchronized void setBadgeColor(String badgeColor) {
		this.badgeColor = badgeColor;
	}
    
    
    
}