package com.ssafy.ssafit.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 DTO")
public class User {
	private String userId;
	private String userPw;
	private String userName;
	private String userEmail;
	private int coach;
	private int gachacoupon;

	public User() {
	}
 
	public User(String userId, String userPw, String userName, String userEmail, int coach, int gachacoupon) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.userName = userName;
		this.userEmail = userEmail;
		this.coach = coach;
		this.gachacoupon = gachacoupon;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getCoach() {
		return coach;
	}

	public void setCoach(int coach) {
		this.coach = coach;
	}

	public int getGachacoupon() {
		return gachacoupon;
	}

	public void setGachacoupon(int gachacoupon) {
		this.gachacoupon = gachacoupon;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userPw=" + userPw + ", userName=" + userName + ", userEmail=" + userEmail
				+ ", coach=" + coach + ", gachacoupon=" + gachacoupon + "]";
	}

}
