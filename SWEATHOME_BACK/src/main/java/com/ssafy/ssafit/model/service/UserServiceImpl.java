package com.ssafy.ssafit.model.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.UserDao;
import com.ssafy.ssafit.model.dto.Event;
import com.ssafy.ssafit.model.dto.Exercise;
import com.ssafy.ssafit.model.dto.ExerciseSchedule;
import com.ssafy.ssafit.model.dto.KakaoUser;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.model.dto.Video;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;

	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<User> getUserList() {
		return userDao.selectAll();
	}

	@Override
	public int signup(User user) {
		return userDao.insertUser(user);
	}

	@Override
	public User login(String userId, String userPw) {
		Map<String, String> info = new HashMap<>();
		info.put("userId", userId);
		info.put("userPw", userPw);
		return userDao.selectOne(info);
	}

	// 아이디 중복검사
	public boolean checkId(String userId) {
		return (userDao.checkId(userId) < 1) ? true : false;
	}

	// 닉네임 중복검사
	public boolean checkName(String userName) {
		return (userDao.checkName(userName) < 1) ? true : false;
	}

	@Override
	public String findName(String userId) {

		return userDao.findName(userId);
	}

	@Override
	public String findPw(String userId) {

		return userDao.findPw(userId);
	}

	@Override
	public List<Video> likeList(String userId) {
		return userDao.likeList(userId);
	}

	@Override
	public int insertExercise(Exercise exercise) {
		return userDao.insertExercise(exercise);
	}

	@Override
	public int deleteExercise(Exercise exercise) {
		return userDao.deleteExercise(exercise);
	}

	@Override
	public List<Exercise> getExerciseByDate(String userId, String date) {
		Map<String, Object> params = new HashMap<>();
//		params.put(userId, date);
		params.put("userId", userId);
		params.put("date", date);
		return userDao.selectExerciseByDate(params);
	}

	@Override
	public ExerciseSchedule selectExerciseTimeByDate(String userId, Date date) {
		ExerciseSchedule exerciseSchedule = new ExerciseSchedule();

		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);

		// 주어진 날짜를 기준으로 7일간의 운동 시간을 계산합니다.
		for (int i = 0; i < 7; i++) {
			Date currentDate = addDays(date, -i); // 현재 날짜로부터 i일 전
			params.put("date", currentDate);

			Integer exerciseTimeObject = userDao.selectExerciseTimeByDate(params);
			int exerciseTime = (exerciseTimeObject != null) ? Math.abs(exerciseTimeObject) : 0;

			// 7일간의 운동 시간을 순서대로 설정합니다.
			switch (i) {
			case 0:
				exerciseSchedule.setSeven(exerciseTime);
				break;
			case 1:
				exerciseSchedule.setSix(exerciseTime);
				break;
			case 2:
				exerciseSchedule.setFive(exerciseTime);
				break;
			case 3:
				exerciseSchedule.setFour(exerciseTime);
				break;
			case 4:
				exerciseSchedule.setThree(exerciseTime);
				break;
			case 5:
				exerciseSchedule.setTwo(exerciseTime);
				break;
			case 6:
				exerciseSchedule.setOne(exerciseTime);
				break;
			}
		}

		return exerciseSchedule;
	}

	// 날짜를 기준으로 일 수를 더하거나 빼는 메서드
	private Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	@Override
	public boolean selectIdEmail(String userId, String userEmail) {
		Map<String, String> params = new HashMap<>();
		params.put("userId", userId);
		params.put("userEmail", userEmail);
		if (userDao.selectIdEmail(params) == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Exercise> getExercise(String userId) {
		return userDao.selectExercise(userId);
	}

	@Override
	public boolean emailCheck(String userEmail) {
		int result = 0;
		result = userDao.emailCheck(userEmail);

		return (result >= 1) ? true : false;
	}

	@Override
	public int useCoupon(String userId) {
		return userDao.useCoupon(userId);
	}

	@Override
	public int addCoupon(String userId) {
		return userDao.addCoupon(userId);
	}
	
	@Override
	public int selectCoupon(String userId) {
		return userDao.selectCoupon(userId);
	}

	@Override
	public int insertEvent(Event event) {
		return userDao.insertEvent(event);
	}

	@Override
	public boolean selectEvent(Event event) {
		int result = userDao.selectEvent(event);

		return (result >= 1) ? true : false;
	}

	@Override
	public void processKakaoUser(KakaoUser kakaoUser) {
		if(userDao.kakaouserId(kakaoUser)>=1) { // 이미있으면 굳이 회원가입할 필요없음
			return;
		}
		userDao.saveKakaoUser(kakaoUser);
	}
}
