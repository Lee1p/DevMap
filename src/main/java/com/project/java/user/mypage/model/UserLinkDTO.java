package com.project.java.user.mypage.model;

/**
 * 유저 개인 정보를 출력 할 떄, 보여주는 LinkDTO입니다.
 * 사용자는 자신의 개인 링크를 DevMap에서 연동하여 볼 수 있습니다.
 * 특히, GitHub 링크 입력시, 유저 ID만 추출하여 GitHub 잔디를 제공합니다.
 */
public class UserLinkDTO {
	
	private String userLinkSeq;
	private Integer userSeq;
	private String LinkName;
	private String LinkType;
	private String regDate;
	private String modDate;
	
	
	
	public String getUserLinkSeq() {
		return userLinkSeq;
	}
	public void setUserLinkSeq(String userLinkSeq) {
		this.userLinkSeq = userLinkSeq;
	}
	public Integer getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public String getLinkName() {
		return LinkName;
	}
	public void setLinkName(String linkName) {
		LinkName = linkName;
	}
	public String getLinkType() {
		return LinkType;
	}
	public void setLinkType(String linkType) {
		LinkType = linkType;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getModDate() {
		return modDate;
	}
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	
	

}