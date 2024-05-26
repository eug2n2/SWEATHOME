package com.ssafy.ssafit.model.dao;

import java.util.List;

import com.ssafy.ssafit.model.dto.Video;

public interface VideoDao {

	public List<Video> selectAll();

	public Video selectOne(int videoId);
	
	public void updateViewCnt(int videoId);
	
	public int insertVideo(Video video);
	
	public int updateVideo(Video video);
	
	public int deleteVideo(int videoId);

	public List<Video> search(String word);

	public List<Video> searchPart(String word);

	public int likeCnt(int videoId);

	public List<Video> selectPart(Video video);
	

}
