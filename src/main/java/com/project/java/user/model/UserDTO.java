package com.project.java.user.model;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
/**
 * 유저 정보를 답는 DTO입니다.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    //private int userId;
    //private String socialId;
	private int userSeq;
    private String socialType;
    private String email;
    private String password;
    private String name;
    private String profileImage;
    private String role;
    private Timestamp joinDate;
    private Timestamp modDate;
    private Timestamp lastLoginDate;
    private String is_active;
    private String phoneNumber;
    private String devtest;
    


    // 회원가입용 생성자
    public UserDTO(String email, String password, String name) {//, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.socialType = "local";
        //this.phoneNumber = phoneNumber;
        this.role = "user";
        this.is_active = "Y";
       
    }



	public UserDTO() {
	}



	public synchronized int getUserSeq() {
		return userSeq;
	}



	public synchronized void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}



	public synchronized String getSocialType() {
		return socialType;
	}



	public synchronized void setSocialType(String socialType) {
		this.socialType = socialType;
	}



	public synchronized String getEmail() {
		return email;
	}



	public synchronized void setEmail(String email) {
		this.email = email;
	}



	public synchronized String getPassword() {
		return password;
	}



	public synchronized void setPassword(String password) {
		this.password = password;
	}



	public synchronized String getName() {
		return name;
	}



	public synchronized void setName(String name) {
		this.name = name;
	}



	public synchronized String getProfileImage() {
		return profileImage;
	}



	public synchronized void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}



	public synchronized String getRole() {
		return role;
	}



	public synchronized void setRole(String role) {
		this.role = role;
	}



	public synchronized Timestamp getJoinDate() {
		return joinDate;
	}



	public synchronized void setJoinDate(Timestamp joinDate) {
		this.joinDate = joinDate;
	}



	public synchronized Timestamp getModDate() {
		return modDate;
	}



	public synchronized void setModDate(Timestamp modDate) {
		this.modDate = modDate;
	}



	public synchronized Timestamp getLastLoginDate() {
		return lastLoginDate;
	}



	public synchronized void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}



	public synchronized String getIs_active() {
		return is_active;
	}



	public synchronized void setIs_active(String is_active) {
		this.is_active = is_active;
	}



	public synchronized String getPhoneNumber() {
		return phoneNumber;
	}



	public synchronized void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	public synchronized String getDevtest() {
		return devtest;
	}



	public synchronized void setDevtest(String devtest) {
		this.devtest = devtest;
	}
    
	
    
    
    //로드맵 생성자
    
}
