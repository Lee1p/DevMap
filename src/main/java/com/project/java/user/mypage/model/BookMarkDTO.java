package com.project.java.user.mypage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 북마크 정보를 담는 DTO입니다.
 */
@Getter
@Setter
@ToString
public class BookMarkDTO {
	private String bookmarkSeq;
	private Integer userSeq;
	private String codeTestSeq;
	private String bookmarkedAt;
	private String subjectName;
	private String codetesttitle;
	
	
	
	public String getBookmarkSeq() {
		return bookmarkSeq;
	}
	public void setBookmarkSeq(String bookmarkSeq) {
		this.bookmarkSeq = bookmarkSeq;
	}
	public Integer getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public String getCodeTestSeq() {
		return codeTestSeq;
	}
	public void setCodeTestSeq(String codeTestSeq) {
		this.codeTestSeq = codeTestSeq;
	}
	public String getBookmarkedAt() {
		return bookmarkedAt;
	}
	public void setBookmarkedAt(String bookmarkedAt) {
		this.bookmarkedAt = bookmarkedAt;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getCodetesttitle() {
		return codetesttitle;
	}
	public void setCodetesttitle(String codetesttitle) {
		this.codetesttitle = codetesttitle;
	}
	
	
	
	
}

