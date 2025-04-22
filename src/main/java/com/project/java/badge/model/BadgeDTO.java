package com.project.java.badge.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 뱃지의 정보를 담는 DTO입니다.
 * 기본 정보로는 seq, 뱃지의 타입, 뱃지명, 설명과 지급 조건, 활성화 여부의 데이터를 담습니다.
 */
@Getter
@Setter
@ToString
public class BadgeDTO {
	
	private String badgeSeq;
	private String badgeType;
	private String badgeName;
	private String badgeDesc;
	private String badgeIcon;
	private String badgeCondition;
	private String isActive;
	public synchronized String getBadgeSeq() {
		return badgeSeq;
	}
	public synchronized void setBadgeSeq(String badgeSeq) {
		this.badgeSeq = badgeSeq;
	}
	public synchronized String getBadgeType() {
		return badgeType;
	}
	public synchronized void setBadgeType(String badgeType) {
		this.badgeType = badgeType;
	}
	public synchronized String getBadgeName() {
		return badgeName;
	}
	public synchronized void setBadgeName(String badgeName) {
		this.badgeName = badgeName;
	}
	public synchronized String getBadgeDesc() {
		return badgeDesc;
	}
	public synchronized void setBadgeDesc(String badgeDesc) {
		this.badgeDesc = badgeDesc;
	}
	public synchronized String getBadgeIcon() {
		return badgeIcon;
	}
	public synchronized void setBadgeIcon(String badgeIcon) {
		this.badgeIcon = badgeIcon;
	}
	public synchronized String getBadgeCondition() {
		return badgeCondition;
	}
	public synchronized void setBadgeCondition(String badgeCondition) {
		this.badgeCondition = badgeCondition;
	}
	public synchronized String getIsActive() {
		return isActive;
	}
	public synchronized void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
	
}