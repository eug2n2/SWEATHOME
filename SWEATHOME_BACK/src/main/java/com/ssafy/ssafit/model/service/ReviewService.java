package com.ssafy.ssafit.model.service;

import java.util.List;

import com.ssafy.ssafit.model.dto.Review;

public interface ReviewService {
	
	public List<Review> getReviewList(int videoId);
	
	public int insertReview(Review review);
	
	public int modifyReview(Review review);
	
	public int removeReview(int reviewId);
	
}
