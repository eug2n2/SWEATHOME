package com.ssafy.ssafit.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "비디오 DTO")
public class Video {
	private int videoId;
	private String videoTitle;
	private String part;
	private int viewCnt;
	private int likeCnt;
	private double rateAvg;
	private String link;

	public Video() {
	}

	public Video(int videoId, String videoTitle, String part, int viewCnt, String link, int likeCnt, double rateAvg) {
		this.videoId = videoId;
		this.videoTitle = videoTitle;
		this.part = part;
		this.viewCnt = viewCnt;
		this.likeCnt = likeCnt;
		this.rateAvg = rateAvg;
		this.link = link;
	}

	public int getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}

	public int getVideoId() {
		return videoId;
	}

	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}

	public double getRateAvg() {
		return rateAvg;
	}

	public void setRateAvg(double rateAvg) {
		this.rateAvg = rateAvg;
	}

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
