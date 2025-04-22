package com.project.java.roadmap.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
/**
 * 
 * DB에서 읽을 로드맵 카테고리(ex:Pick a Language) 데이터를 저장할 객체 구조
 */
public class RoadCategoryDTO {
	private int categorySeq; 
	private String categoryName;
	public synchronized int getCategorySeq() {
		return categorySeq;
	}
	public synchronized void setCategorySeq(int categorySeq) {
		this.categorySeq = categorySeq;
	}
	public synchronized String getCategoryName() {
		return categoryName;
	}
	public synchronized void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	} 
	
	
}
