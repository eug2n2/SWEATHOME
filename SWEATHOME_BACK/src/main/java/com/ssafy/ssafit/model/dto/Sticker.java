package com.ssafy.ssafit.model.dto;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "도감 DTO")
public class Sticker {
	private String userId;
	private int stickerNo;

	public Sticker() {
	}
	
	@Autowired
	public Sticker(String userId, int stickerNo) {
		this.userId = userId;
		this.stickerNo = stickerNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getStickerNo() {
		return stickerNo;
	}

	public void setStickerNo(int stickerNo) {
		this.stickerNo = stickerNo;
	}

	@Override
	public String toString() {
		return "Sticker [userId=" + userId + ", stickerNo=" + stickerNo + "]";
	}

}
