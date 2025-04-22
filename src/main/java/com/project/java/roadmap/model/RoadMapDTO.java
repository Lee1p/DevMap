package com.project.java.roadmap.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
/**
 * 
 * DB에서 읽을 로드맵 기술(ex:Java) 데이터를 저장할 객체 구조
 */
public class RoadMapDTO {
	
	private int techSeq;
	private String techName;
	private String techDesc;
	private String techImgUrl;
	private String techLearningTime;
	private String regDate;
	private String modDate;
	private String delDate;
	private String categorySeq;
	private String categoryName;
	public synchronized int getTechSeq() {
		return techSeq;
	}
	public synchronized void setTechSeq(int techSeq) {
		this.techSeq = techSeq;
	}
	public synchronized String getTechName() {
		return techName;
	}
	public synchronized void setTechName(String techName) {
		this.techName = techName;
	}
	public synchronized String getTechDesc() {
		return techDesc;
	}
	public synchronized void setTechDesc(String techDesc) {
		this.techDesc = techDesc;
	}
	public synchronized String getTechImgUrl() {
		return techImgUrl;
	}
	public synchronized void setTechImgUrl(String techImgUrl) {
		this.techImgUrl = techImgUrl;
	}
	public synchronized String getTechLearningTime() {
		return techLearningTime;
	}
	public synchronized void setTechLearningTime(String techLearningTime) {
		this.techLearningTime = techLearningTime;
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
	public synchronized String getDelDate() {
		return delDate;
	}
	public synchronized void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	public synchronized String getCategorySeq() {
		return categorySeq;
	}
	public synchronized void setCategorySeq(String categorySeq) {
		this.categorySeq = categorySeq;
	}
	public synchronized String getCategoryName() {
		return categoryName;
	}
	public synchronized void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	

	
}