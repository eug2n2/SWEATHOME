package com.ssafy.ssafit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.VideoLike;
import com.ssafy.ssafit.model.service.VideoLikeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api-videolike")

@Tag(name = "VideoLikeRestController", description = "찜 관련 API")
public class VideoLikeRestController {
	
	private final VideoLikeService videoLikeService;
	@Autowired
	public VideoLikeRestController(VideoLikeService videoLikeService) {
		this.videoLikeService = videoLikeService;
	}
	
	@PostMapping("/insertlike")
	@Operation(summary = "찜 누르기", description = "찜 가넝")
	public ResponseEntity<?> write(@RequestBody VideoLike videoLike){
		if(videoLikeService.insertVideoLike(videoLike) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/deletelike")
	@Operation(summary = "찜 삭제", description = "찜 삭제 가넝")
	public ResponseEntity<?> delete(@RequestBody VideoLike videoLike){
		if(videoLikeService.removeVideoLike(videoLike) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
