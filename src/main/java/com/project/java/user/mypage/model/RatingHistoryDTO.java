package com.project.java.user.mypage.model;

public class RatingHistoryDTO {
	private String ratingHistorySeq;
	private Integer userSeq;
	private String rating;
	private String reason;
	private String changedAt;
	
	
	public String getRatingHistorySeq() {
		return ratingHistorySeq;
	}
	public void setRatingHistorySeq(String ratingHistorySeq) {
		this.ratingHistorySeq = ratingHistorySeq;
	}
	public Integer getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getChangedAt() {
		return changedAt;
	}
	public void setChangedAt(String changedAt) {
		this.changedAt = changedAt;
	}
	
	
}

