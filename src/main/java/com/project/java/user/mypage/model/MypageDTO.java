package com.project.java.user.mypage.model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * Mypage DTO 객체입니다.
 * 유저 번호, 소셜 로그인여부, 이메일, 비밀번호, 이름, 전화번호, 프로필 사진
 * 권한, 가입일, 수정일, 마지막 로그인 시간, 탈퇴여부, 유저의 랭킹 정보를 담습니다.
 */
@Getter
@Setter
@ToString
public class MypageDTO {
	private Integer userSeq;
	private String social_type;
	private String email;
	private String password;
	private String name;
	private String phone_number;
	private String profile_image;
	private String role;
	private String join_date;
	private String mod_date;
	private String last_login_date;
	private String is_active;
	private String user_level;
	
	public Integer getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public String getSocial_type() {
		return social_type;
	}
	public void setSocial_type(String social_type) {
		this.social_type = social_type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getProfile_image() {
		return profile_image;
	}
	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getMod_date() {
		return mod_date;
	}
	public void setMod_date(String mod_date) {
		this.mod_date = mod_date;
	}
	public String getLast_login_date() {
		return last_login_date;
	}
	public void setLast_login_date(String last_login_date) {
		this.last_login_date = last_login_date;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
	public String getUser_level() {
		return user_level;
	}
	public void setUser_level(String user_level) {
		this.user_level = user_level;
	}
	
	
	
	
}