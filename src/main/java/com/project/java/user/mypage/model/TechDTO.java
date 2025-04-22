package com.project.java.user.mypage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 언어에 대한 정보를 받아오는 DTO입니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechDTO {
    private int techSeq;
    private String techName;
	public int getTechSeq() {
		return techSeq;
	}
	public void setTechSeq(int techSeq) {
		this.techSeq = techSeq;
	}
	public String getTechName() {
		return techName;
	}
	public void setTechName(String techName) {
		this.techName = techName;
	}
    
    
}

