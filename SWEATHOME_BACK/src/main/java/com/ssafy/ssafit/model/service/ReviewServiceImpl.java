package com.ssafy.ssafit.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.ReviewDao;
import com.ssafy.ssafit.model.dto.Review;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	private final ReviewDao reviewDao;
	
	public ReviewServiceImpl(ReviewDao reviewDao) {
		this.reviewDao = reviewDao;
	}

	@Override
	public List<Review> getReviewList(int videoId) {
		return reviewDao.selectReviewAll(videoId);
	}
	
	@Override
	public int insertReview(Review review) {
		return reviewDao.insertReview(review);
	}

	@Override
	public int modifyReview(Review review) {
		return reviewDao.updateReview(review);
	}

	@Override
	public int removeReview(int reviewId) {
		return reviewDao.deleteReview(reviewId);
	}

}
