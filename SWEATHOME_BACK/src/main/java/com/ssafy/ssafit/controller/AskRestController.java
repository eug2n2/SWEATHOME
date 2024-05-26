package com.ssafy.ssafit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.Ask;
import com.ssafy.ssafit.model.service.AskService;
import com.ssafy.ssafit.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api-ask")
@Tag(name = "AskRestController", description = "영상 요청 관련 API")
public class AskRestController {
	
	private final AskService askService;
	private final UserService userService;
	
	@Autowired
	public AskRestController(AskService askService, UserService userService) {
		this.askService = askService;
		this.userService = userService;
	}
	
	@GetMapping("/ask")
	@Operation(summary = "관리자 요청 목록 전체 조회", description = "관리자 요청 목록 조회 가넝")
	public ResponseEntity<?> list() {
		List<Ask> list = askService.getAskList(); 
		return new ResponseEntity<List<Ask>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/ask/{id}")
	@Operation(summary = "개인 요청 목록 조회 ", description = "요청  목록 조회  가넝")
	public ResponseEntity<?> select(@PathVariable("id") String userId) {
		List<Ask> list = askService.getPersonalAskList(userId); 
		return new ResponseEntity<List<Ask>>(list, HttpStatus.OK);
	}
	
	@PostMapping("/ask")
	@Operation(summary = "요청 추가", description = "요청 작성 가넝")
	public ResponseEntity<?> write(@RequestBody Ask ask) {
		ask.setUserName(userService.findName(ask.getUserId()));
		
		if (askService.insertAsk(ask) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/ask/{id}")
	@Operation(summary = "요청 삭제", description = "요청 삭제 가넝")
	public ResponseEntity<?> remove(@PathVariable("id") int id) {
		if (askService.deleteAsk(id) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
