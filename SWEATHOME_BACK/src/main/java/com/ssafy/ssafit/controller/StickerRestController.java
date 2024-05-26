package com.ssafy.ssafit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.Exercise;
import com.ssafy.ssafit.model.dto.Sticker;
import com.ssafy.ssafit.model.service.StickerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api-sticker")
@Tag(name = "StickerRestController", description = "도감 관련 API")
public class StickerRestController {

	private final StickerService stickerService;

	@Autowired
	public StickerRestController(StickerService stickerService) {
		this.stickerService = stickerService;
	}

	@PostMapping("/stickercheck")
	@Operation(summary = "스티커 중복 여부 확인 ", description = "중복이 아니면 true 중복이면 false")
	public boolean checkSticker(@RequestBody Sticker sticker) {
		
		return stickerService.checkSticker(sticker);
	}

	@PostMapping("/sticker")
	@Operation(summary = "스티커 추가", description = "스티커 추가 ")
	public ResponseEntity<?> insertSticker(@RequestBody Sticker sticker) {

		if (stickerService.insertSticker(sticker) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/sticker")
	@Operation(summary = "스티커 전체 조회", description = "스티커 추가 ")
	public ResponseEntity<List<Integer>> selectSticker(@RequestParam("userId") String userId) {
		List<Integer> list = stickerService.getStickersByUserId(userId);
		if (list != null && !list.isEmpty()) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}
}
