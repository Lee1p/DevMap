package com.project.java.test.codetest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 기술(Tech) 정보를 담는 데이터 전송 객체(DTO) 클래스입니다.
 * 기술의 기본 정보를 저장하고 전달하는 역할을 합니다.
 *
 * <p>이 클래스는 Lombok 라이브러리의 어노테이션을 사용하여
 * getter, setter, 생성자 메소드를 자동으로 생성합니다.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechDTO {
    /** 기술 고유 식별자(일련번호) */
    private int techSeq;

    /** 기술 이름 */
    private String techName;

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
    
    
}