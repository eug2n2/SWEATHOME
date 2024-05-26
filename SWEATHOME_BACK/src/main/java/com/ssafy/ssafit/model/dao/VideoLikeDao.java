package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.VideoLike;

public interface VideoLikeDao {
	public int insertVideoLike(VideoLike videoLike);

	public int deleteVideoLike(VideoLike videoLike);
}
