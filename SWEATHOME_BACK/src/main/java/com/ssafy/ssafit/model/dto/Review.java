package com.ssafy.ssafit.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 DTO")
public class Review {
	
	private int reviewId;
	private int videoId;
	private String userId;
	private String content;
	private double rate;
	private String writeTime;
	private String userName;
	
	public Review () {}
	
	
	
	public Review(int reviewId, int videoId, String userId, String content, double rate, String writeTime,
			String userName) {
		super();
		this.reviewId = reviewId;
		this.videoId = videoId;
		this.userId = userId;
		this.content = content;
		this.rate = rate;
		this.writeTime = writeTime;
		this.userName = userName;
	}



	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public int getVideoId() {
		return videoId;
	}
	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(String writeTime) {
		this.writeTime = writeTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
