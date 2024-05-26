package com.ssafy.ssafit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.Review;
import com.ssafy.ssafit.model.service.ReviewService;
import com.ssafy.ssafit.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api-review")
@Tag(name = "ReviewRestController", description = "리뷰 관련 API")
public class ReviewRestController {

	private final ReviewService reviewService;
	private final UserService userService;

	@Autowired
	public ReviewRestController(ReviewService reviewService, UserService userService) {
		this.reviewService = reviewService;
		this.userService = userService;
	}

	@PostMapping("/review")
	@Operation(summary = "리뷰 작성", description = "리뷰 작성 가넝")
	public ResponseEntity<?> write(@RequestBody Review review, HttpServletRequest request) {
		review.setUserName(userService.findName(review.getUserId()));
		
		if (reviewService.insertReview(review) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/review/{id}")
	@Operation(summary = "리뷰 수정", description = "리뷰 수정 가넝")
	public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody Review review) {
		review.setReviewId(id);
		if (reviewService.modifyReview(review) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@DeleteMapping("/review/{id}")
	@Operation(summary = "리뷰 삭제", description = "리뷰 삭제 가넝")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		if (reviewService.removeReview(id) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/review/{id}")
	@Operation(summary = "리뷰 조회", description = "리뷰 조회 가넝")
	public ResponseEntity<?> select(@PathVariable("id") int id) {
		List<Review > list  = reviewService.getReviewList(id);
		return new  ResponseEntity<>(list,HttpStatus.OK);
	}

}
