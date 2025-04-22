package com.project.java.user.mypage.model;

/**
 * 출결 정보를 받아오는 DTO입니다.
 */
public class AttendanceDTO {
	
	private String attendanceSeq;
	private Integer userSeq;
	private String attendanceDate;
	private String countNum;
	
	
	public String getAttendanceSeq() {
		return attendanceSeq;
	}
	public void setAttendanceSeq(String attendanceSeq) {
		this.attendanceSeq = attendanceSeq;
	}
	public Integer getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public String getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(String attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	public String getCountNum() {
		return countNum;
	}
	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}

	
	
	
	

}