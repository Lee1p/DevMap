package com.project.java.roadmap.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 기술(Tech) 정보를 담는 DTO 클래스입니다.
 *
 * 필드 설명:
 * - techSeq: 기술 고유 번호 (PK)
 * - techName: 기술 이름
 * - techDesc: 기술 설명
 * - categorySeq: 기술이 속한 카테고리 번호
 */
@Getter
@Setter
@ToString
public class TechDTO {

    /** 기술 고유 번호 */
    private int techSeq;

    /** 기술 이름 */
    private String techName;

    /** 기술 설명 */
    private String techDesc;

    /** 카테고리 번호 (분류용) */
    private int categorySeq;

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

	public synchronized int getCategorySeq() {
		return categorySeq;
	}

	public synchronized void setCategorySeq(int categorySeq) {
		this.categorySeq = categorySeq;
	}
    
    
}
