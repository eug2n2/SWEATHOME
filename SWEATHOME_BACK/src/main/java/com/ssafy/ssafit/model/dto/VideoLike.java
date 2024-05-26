package com.ssafy.ssafit.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "비디오 찜 DTO")
public class VideoLike {
	private int videoId;
	private String userId;

	public VideoLike() {
	}

	public VideoLike(int videoId, String userId) {
		this.videoId = videoId;
		this.userId = userId;
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

}
