package com.ssafy.ssafit.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.VideoDao;
import com.ssafy.ssafit.model.dto.Video;

@Service
public class VideoServiceImpl implements VideoService {

	private final VideoDao videoDao;

	@Autowired
	public VideoServiceImpl(VideoDao videoDao) {
		this.videoDao = videoDao;
	}

	@Override
	public List<Video> getVideoList() {
		return videoDao.selectAll();
	}

	@Override
	public Video readVideo(int videoId) {
		videoDao.updateViewCnt(videoId);
		return videoDao.selectOne(videoId);
	}

	@Override
	public int insertVideo(Video video) {
		return videoDao.insertVideo(video);
	}

	@Override
	public int modifyVideo(Video video) {
		return videoDao.updateVideo(video);
	}

	@Override
	public int removeVideo(int videoId) {
		return videoDao.deleteVideo(videoId);
	}

	@Override
	public List<Video> search(String word) {
		return videoDao.search(word);
	}

	@Override
	public List<Video> searchPart(String word) {
		return videoDao.searchPart(word);
	}
	@Override
	public List<Video> selectPart(Video video) {
		return videoDao.selectPart(video);
	}
	
}
