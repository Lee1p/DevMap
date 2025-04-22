package com.project.java.roadmap.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  과목(Subject) 정보를 담는 DTO 클래스
 *
 * 과목의 식별자, 이름, 설명, 난이도 및 관련 자료 리스트를 포함합니다.
 * 주로 DB 조회 결과를 전달하거나, 뷰(JSP)로 값을 넘길 때 사용됩니다.
 */
@Getter
@Setter
@ToString
public class SubjectDTO {

    /** 과목 고유 번호 (PK) */
    private int subjectSeq;

    /** 과목 이름 */
    private String subjectName;

    /** 과목 설명 */
    private String subjectDesc;

    /**  과목 난이도 (예: 초급, 중급, 고급) */
    private String subjectDifficulty;

    /**  과목에 연결된 자료 리스트 */
    private List<ResourceDTO> resources;

	public synchronized int getSubjectSeq() {
		return subjectSeq;
	}

	public synchronized void setSubjectSeq(int subjectSeq) {
		this.subjectSeq = subjectSeq;
	}

	public synchronized String getSubjectName() {
		return subjectName;
	}

	public synchronized void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public synchronized String getSubjectDesc() {
		return subjectDesc;
	}

	public synchronized void setSubjectDesc(String subjectDesc) {
		this.subjectDesc = subjectDesc;
	}

	public synchronized String getSubjectDifficulty() {
		return subjectDifficulty;
	}

	public synchronized void setSubjectDifficulty(String subjectDifficulty) {
		this.subjectDifficulty = subjectDifficulty;
	}

	public synchronized List<ResourceDTO> getResources() {
		return resources;
	}

	public synchronized void setResources(List<ResourceDTO> resources) {
		this.resources = resources;
	}
    
    

    
}
