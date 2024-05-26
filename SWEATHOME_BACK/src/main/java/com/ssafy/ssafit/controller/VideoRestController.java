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

import com.ssafy.ssafit.model.dto.Video;
import com.ssafy.ssafit.model.service.VideoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api-video")
@Tag(name = "VideoRestController", description = "영상 관련 API")
public class VideoRestController {

	private final VideoService videoService;

	@Autowired
	public VideoRestController(VideoService videoService) {
		this.videoService = videoService;
	}

	@GetMapping("/video")
	@Operation(summary = "영상 목록 조회", description = "영상 목록 조회 가넝")
	public ResponseEntity<?> list() {
		List<Video> list = videoService.getVideoList(); // 전체조회
		return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
	}

	@PostMapping("/video/recommend")
	@Operation(summary = "영상 파트별 추천 조회", description = "영상 부위에 따른 조회 가넝")
	public ResponseEntity<?> recommend(@RequestBody Video video) {
		List<Video> list = videoService.selectPart(video); // 검색 조회
		if (list == null || list.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
	}

	@GetMapping("/video/search/{word}")
	@Operation(summary = "영상 조건 조회", description = "영상 조건에 따른 조회 가넝")
	public ResponseEntity<?> list(@PathVariable("word") String word) {
		List<Video> list = videoService.search(word); // 검색 조회

		if (list == null || list.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
	}

	@GetMapping("/video/searchpart/{word}")
	@Operation(summary = "영상 부위 조회", description = "영상 부위에 따른 조회 가넝")
	public ResponseEntity<?> searchpart(@PathVariable("word") String word) {
		List<Video> list = videoService.searchPart(word); // 검색 조회

		if (list == null || list.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/video/{id}")
	@Operation(summary = "영상 상세 조회", description = "영상 상세 조회 가넝")
	public ResponseEntity<?> detail(@PathVariable("id") int id) {
		Video video = videoService.readVideo(id);
		return new ResponseEntity<>(video, HttpStatus.OK);
	}

	@PostMapping("/video")
	@Operation(summary = "영상 추가", description = "영상 작성 가넝")
	public ResponseEntity<?> write(@RequestBody Video video) {
		if (videoService.insertVideo(video) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	

	@PutMapping("/video/{id}")
	@Operation(summary = "영상 수정", description = "영상 수정 가넝")
	public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody Video video) {
		video.setVideoId(id);
		if (videoService.modifyVideo(video) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@DeleteMapping("/video/{id}")
	@Operation(summary = "영상 삭제", description = "영상 삭제 가넝")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		if (videoService.removeVideo(id) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

}
