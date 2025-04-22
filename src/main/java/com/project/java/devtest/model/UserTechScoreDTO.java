package com.project.java.devtest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserTechScoreDTO {
    private int userSeq;          // 사용자 ID
    private int techSeq;          // 기술 ID
    private int totalScore;       // 누적 점수
    private String isRecommended;   // 추천 여부 ('Y' or 'N')
    private String techName;      // 🔹 기술 이름 (추가)
    private int categorySeq;      // 🔹 카테고리 ID (추가)
	public synchronized int getUserSeq() {
		return userSeq;
	}
	public synchronized void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public synchronized int getTechSeq() {
		return techSeq;
	}
	public synchronized void setTechSeq(int techSeq) {
		this.techSeq = techSeq;
	}
	public synchronized int getTotalScore() {
		return totalScore;
	}
	public synchronized void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public synchronized String getIsRecommended() {
		return isRecommended;
	}
	public synchronized void setIsRecommended(String isRecommended) {
		this.isRecommended = isRecommended;
	}
	public synchronized String getTechName() {
		return techName;
	}
	public synchronized void setTechName(String techName) {
		this.techName = techName;
	}
	public synchronized int getCategorySeq() {
		return categorySeq;
	}
	public synchronized void setCategorySeq(int categorySeq) {
		this.categorySeq = categorySeq;
	}
    
    
}