package com.ssafy.ssafit.model.dto;

public class KakaoUser {
	private String userName;
	private String userId;

	public KakaoUser() {
	}

	public KakaoUser(String userName, String userId) {
		super();
		this.userName = userName;
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "KakaoUser [userName=" + userName + ", userId=" + userId + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}



}
