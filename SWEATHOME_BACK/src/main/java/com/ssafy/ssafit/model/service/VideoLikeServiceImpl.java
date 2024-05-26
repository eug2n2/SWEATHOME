package com.ssafy.ssafit.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.VideoLikeDao;
import com.ssafy.ssafit.model.dto.VideoLike;
@Service
public class VideoLikeServiceImpl implements VideoLikeService{
	private final VideoLikeDao videoLikeDao;

	@Autowired
	public VideoLikeServiceImpl(VideoLikeDao videoLikeDao) {
		this.videoLikeDao = videoLikeDao;
	}
	@Override
	public int insertVideoLike(VideoLike videoLike) {
		return videoLikeDao.insertVideoLike(videoLike);
	}
	@Override
	public int removeVideoLike(VideoLike videolike) {
		return videoLikeDao.deleteVideoLike(videolike);
	}
}
