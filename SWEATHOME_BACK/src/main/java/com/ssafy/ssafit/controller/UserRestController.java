package com.ssafy.ssafit.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.Event;
import com.ssafy.ssafit.model.dto.Exercise;
import com.ssafy.ssafit.model.dto.ExerciseSchedule;
import com.ssafy.ssafit.model.dto.KakaoUser;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.model.dto.Video;
import com.ssafy.ssafit.model.service.UserService;
import com.ssafy.ssafit.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.internet.MimeMessage;

@RestController
@RequestMapping("/api-user")
@Tag(name = "UserRestController", description = "유저 관련 API")
public class UserRestController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
	private final UserService userService;
	private final JwtUtil jwtUtil;
	private final JavaMailSender mailSender;

	@Autowired
	public UserRestController(UserService userService, JwtUtil jwtUtil, JavaMailSender mailSender) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
		this.mailSender = mailSender; // mailSender 빈 주입
	}

	@GetMapping("/likeList")
	@Operation(summary = "사용자 별 영상 좋아요 누른 목록 ", description = "내가 좋아요한 영상 모음")
	public ResponseEntity<?> likeList(@RequestParam("userId") String userId) {
		List<Video> list = userService.likeList(userId); // 전체조회
		return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
	}

	@GetMapping("/checkId")
	@Operation(summary = "아이디 중복", description = "아이디 중복 불가넝")
	public Boolean checkId(@RequestParam("userId") String userId) {
		return userService.checkId(userId);
	}

	@GetMapping("/checkName")
	@Operation(summary = "닉네임 중복", description = "닉네임 중복 불가넝")
	public Boolean checkNickname(@RequestParam("userName") String userName) {
		return userService.checkName(userName);
	}

	@PostMapping("/login")
	@Operation(summary = "로그인", description = "로그인 가넝")
	public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
		HttpStatus status = null;
		Map<String, Object> result = new HashMap<>();
		User loginUser = userService.login(user.getUserId(), user.getUserPw());

		if (loginUser.getUserId() != null) {
			result.put("message", SUCCESS);
			result.put("access-token", jwtUtil.createToken(user.getUserId()));
			status = HttpStatus.ACCEPTED;
		} else {
			result.put("message", FAIL);
			status = HttpStatus.NO_CONTENT;
		}
		return new ResponseEntity<>(result, status);
	}

	@PostMapping("/signup")
	@Operation(summary = "회원가입", description = "회원가입 가넝")
	public ResponseEntity<?> signup(@RequestBody User user) {
		if (userService.signup(user) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/exercise")
	@Operation(summary = "운동 기록 추가", description = "운동 기록 추가")
	public ResponseEntity<?> writeExercise(@RequestBody Exercise exercise) {
		// 시작 시간에 ':00' 추가
		LocalTime startTime = LocalTime.parse(exercise.getStartTime());
		LocalTime endTime = LocalTime.parse(exercise.getEndTime());
		Duration duration = Duration.between(endTime,startTime );
		long exerciseTimeMinutes = duration.toMinutes();
		exercise.setExerciseTime(Math.abs((int)exerciseTimeMinutes));
		// 변환된 LocalTime 객체를 Exercise 객체에 설정
		exercise.setStartTime(startTime.toString());
		exercise.setEndTime(endTime.toString());

		if (userService.insertExercise(exercise) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/exercise/weeklyTime")
	public ExerciseSchedule getWeeklyExerciseTime(@RequestParam String userId, @RequestParam String startDate)
			throws ParseException {

		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
		return userService.selectExerciseTimeByDate(userId, date);

	}

	@DeleteMapping("/exercise")
	@Operation(summary = "운동 기록 삭제", description = "운동 기록 삭제 ")
	public ResponseEntity<?> deleteExercise(@RequestBody Exercise exercise) {
		if (userService.deleteExercise(exercise) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/exercise/{id}/{date}")
	@Operation(summary = "날짜별 운동 기록 조회 ", description = "운동 기록 날짜별 조회  ")
	public ResponseEntity<List<Exercise>> getExerciseByDate(@PathVariable("id") String userId,
			@PathVariable("date") String date) {

		List<Exercise> exercises = userService.getExerciseByDate(userId, date);
		if (exercises != null && !exercises.isEmpty()) {
			return new ResponseEntity<>(exercises, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/exercise/{id}")
	@Operation(summary = "사람당 운동 기록 조회 ", description = "운동 조회 , 사용자 id입력  ")
	public ResponseEntity<List<Exercise>> getExerciseByDate(@PathVariable("id") String userId) {
		List<Exercise> exercises = userService.getExercise(userId);
		if (exercises != null && !exercises.isEmpty()) {
			return new ResponseEntity<>(exercises, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/selectnum")
	@Operation(summary = "인증번호 전송  ", description = "인증번호 전송 ")
	public ResponseEntity<?> sendnum(@RequestParam("userId") String userId,
			@RequestParam("userEmail") String userEmail) {
		if (!userService.selectIdEmail(userId, userEmail)) {
			return new ResponseEntity<>("해당 회원은 가입하지 않았습니다", HttpStatus.NOT_FOUND);
		} else {
			// 인증번호(난수) 생성
			Random random = new Random();
			int checkNum = random.nextInt(888888) + 111111;
			logger.info("인증번호" + checkNum);
			String setFrom = "oyj5775@naver.com";
			String toMail = userEmail;
			String title = "[SSAFIT] 비밀번호 찾기 인증 이메일 입니다.";
			String content = "<div style='font-family: Arial, sans-serif; background-color: #ff99cc; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);'>"
					+ "<p style='font-size: 18px; font-weight: bold; color: #333;'> SSAFIT 홈페이지를 방문해주셔서 감사합니다. ‍</p>"
					+ "<br>" + "<p style='font-size: 16px; color: #555;'>인증 번호는 <strong style='color: #000;'>"
					+ checkNum + "</strong> 입니다.</p>"
					+ "<p style='font-size: 16px; color: #555;'>해당 인증번호를 인증번호 확인란에 기입하여 주세요.</p>" + "</div>";

			try {

				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
				helper.setFrom(setFrom);
				helper.setTo(toMail);
				helper.setSubject(title);
				helper.setText(content, true);
				mailSender.send(message);

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("email 전송 실패 ");
			}
			String num = Integer.toString(checkNum);
			return new ResponseEntity<>(num, HttpStatus.OK);
		}
	}

	@GetMapping("/findpw")
	@Operation(summary = "인증번호 확인후 비밀번호 찾기 ", description = "아이디에 맞는 비번 주기 ")
	public ResponseEntity<?> findpw(@RequestParam("userId") String userId) {
		return new ResponseEntity<>(userService.findPw(userId), HttpStatus.OK);
	}

	 @PostMapping("/kakaologin")
	    public ResponseEntity<?> kakaoLogin(@RequestBody KakaoUser kakaoUser) {
		 
	        userService.processKakaoUser(kakaoUser);
	        HttpStatus status = null;
			Map<String, Object> result = new HashMap<>();
			
			if (kakaoUser.getUserId() != null) {
				result.put("message", SUCCESS);
				result.put("access-token", jwtUtil.createToken(kakaoUser.getUserId()));
				status = HttpStatus.ACCEPTED;
			} else {
				result.put("message", FAIL);
				status = HttpStatus.NO_CONTENT;
			}
			return new ResponseEntity<>(result, status);
	    }
	
	
	@PutMapping("/usecoupon")
	@Operation(summary = "쿠폰 사용 ", description = "쿠폰 사용 ")
	public ResponseEntity<?> usecoupon(@RequestParam("userId") String userId) {
		return new ResponseEntity<>(userService.useCoupon(userId), HttpStatus.OK);
	}

	@GetMapping("/coupon")
	@Operation(summary = "쿠폰 개수조회 ", description = "쿠폰 개수 조회")
	public ResponseEntity<?> selectcoupon(@RequestParam("userId") String userId) {
		return new ResponseEntity<>(userService.selectCoupon(userId), HttpStatus.OK);
	}

	@PutMapping("/addcoupon")
	@Operation(summary = "쿠폰 추가 ", description = "쿠폰 추가 ")
	public ResponseEntity<?> addcoupon(@RequestParam("userId") String userId) {
		return new ResponseEntity<>(userService.addCoupon(userId), HttpStatus.OK);
	}

	@PostMapping("/event")
	@Operation(summary = " 쿠폰 보상 지급 내역 추가 ", description = "쿠폰 보상 지급 내역 추가")
	public ResponseEntity<?> insertevent(@RequestParam("userId") String userId) {
		Event event = new Event();
		event.setUserId(userId);
		event.setReceiveTime(LocalDate.now());
		return new ResponseEntity<>(userService.insertEvent(event), HttpStatus.OK);
	}

	@GetMapping("/event")
	@Operation(summary = "쿠폰 보상 지급 내역 확인 ", description = "쿠폰 보상 지급 내역 확인")
	public ResponseEntity<?> selectevent(@RequestParam("userId") String userId) {
		Event event = new Event();
		event.setUserId(userId);
		event.setReceiveTime(LocalDate.now());
		return new ResponseEntity<>(userService.selectEvent(event), HttpStatus.OK);
	}

}
