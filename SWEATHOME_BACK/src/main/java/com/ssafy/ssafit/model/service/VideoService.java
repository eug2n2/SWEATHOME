package com.ssafy.ssafit.model.service;

import java.util.List;

import com.ssafy.ssafit.model.dto.Video;

public interface VideoService {

	public List<Video> getVideoList();

	public Video readVideo(int videoId);

	public List<Video> selectPart(Video video);

	public List<Video> search(String word);

	public List<Video> searchPart(String word);

	public int insertVideo(Video video);

	public int modifyVideo(Video video);

	public int removeVideo(int videoId);


}
