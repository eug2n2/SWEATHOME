package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.VideoLike;

public interface VideoLikeService {
	public int insertVideoLike(VideoLike videoLike);
	
	public int removeVideoLike(VideoLike videoLike);
	
}
