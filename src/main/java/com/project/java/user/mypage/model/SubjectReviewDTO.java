package com.project.java.user.mypage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 각 과목의 결과 값을 가져오는 DTO입니다.
 * 결과 번호, 유저번호, 과목 번호, 망각곡선 그래프, 완료여부, 과목명 등을 담습니다.
 */
@Getter
@Setter
@ToString
public class SubjectReviewDTO {
	private String reviewSeq;
	private Integer userSeq;
	private String subjectSeq;
	private String lastReviewDate;
	private String memoryScore;
	private String isComplete;
	private String subjectName;
	
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getReviewSeq() {
		return reviewSeq;
	}
	public void setReviewSeq(String reviewSeq) {
		this.reviewSeq = reviewSeq;
	}
	public Integer getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public String getSubjectSeq() {
		return subjectSeq;
	}
	public void setSubjectSeq(String subjectSeq) {
		this.subjectSeq = subjectSeq;
	}
	public String getLastReviewDate() {
		return lastReviewDate;
	}
	public void setLastReviewDate(String lastReviewDate) {
		this.lastReviewDate = lastReviewDate;
	}
	public String getMemoryScore() {
		return memoryScore;
	}
	public void setMemoryScore(String memoryScore) {
		this.memoryScore = memoryScore;
	}
	public String getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}
	
	
	 
}
