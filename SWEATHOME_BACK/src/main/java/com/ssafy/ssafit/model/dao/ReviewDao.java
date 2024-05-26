package com.ssafy.ssafit.model.dao;

import java.util.List;

import com.ssafy.ssafit.model.dto.Review;

public interface ReviewDao {
	
	public List<Review> selectReviewAll(int videoId);
	
	public int insertReview(Review review);
	
	public int updateReview(Review review);
	
	public int deleteReview(int id);
	
}
