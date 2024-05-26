package com.ssafy.ssafit.model.dto;

public class Ask {
	private int askId;
	private String userId;
	private String askTitle;
	private String link;
	private String userName;
	public Ask() {}

	public Ask(int askId, String userId, String askTitle, String userName, String link) {
		this.askId = askId;
		this.userId = userId;
		this.askTitle = askTitle;
		this.link = link;
		this.userName=userName;
	}

	public int getAskId() {
		return askId;
	}

	public void setAskId(int askId) {
		this.askId = askId;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAskTitle() {
		return askTitle;
	}

	public void setAskTitle(String askTitle) {
		this.askTitle = askTitle;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
