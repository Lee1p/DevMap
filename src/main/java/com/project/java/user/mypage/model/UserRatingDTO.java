package com.project.java.user.mypage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 유저 개개별로 획득한 랭킹 점수를 담는 DTO입니다.
 * 유저 고유번호, 전체 풀었던 문제, 현재 점수, 업데이트일, 이름, rownum을 담습니다.
 * 유저는 코드테스트 문제를 완료할 때 마다 10점심 획득합니다.
 * 획득한 점수는 갱신되어 다시 테이블에 저장됩니다. 
 */
@Getter
@Setter
@ToString
public class UserRatingDTO {

	private Integer userSeq;
	private String totalSolved;
	private String currentRating;
	private String lastUpdated;
	private String name;
	private String rn;
	
	
	
	public Integer getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public String getTotalSolved() {
		return totalSolved;
	}
	public void setTotalSolved(String totalSolved) {
		this.totalSolved = totalSolved;
	}
	public String getCurrentRating() {
		return currentRating;
	}
	public void setCurrentRating(String currentRating) {
		this.currentRating = currentRating;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRn() {
		return rn;
	}
	public void setRn(String rn) {
		this.rn = rn;
	}
	
	
	
	
	
	
	 
}
