package com.project.java.user.mypage.model;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자의 테스트 결과를 담는 DTO입니다.
 * 많이 틀린 문제를 출력할 떄 사용됩니다.
 * 틀릿 횟수, 테스트 고유 번호, 테스트명을 담습니다.
 */
@Getter
@Setter
@ToString
public class UserCoteResultDTO {
	
	private String cnt;
    private String codetestSeq;
    private String codetesttitle;
    
	public String getCnt() {
		return cnt;
	}
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
	public String getCodetestSeq() {
		return codetestSeq;
	}
	public void setCodetestSeq(String codetestSeq) {
		this.codetestSeq = codetestSeq;
	}
	public String getCodetesttitle() {
		return codetesttitle;
	}
	public void setCodetesttitle(String codetesttitle) {
		this.codetesttitle = codetesttitle;
	}
    
    

}
