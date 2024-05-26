package com.ssafy.ssafit.model.service;

import java.util.Date;
import java.util.List;

import com.ssafy.ssafit.model.dto.Event;
import com.ssafy.ssafit.model.dto.Exercise;
import com.ssafy.ssafit.model.dto.ExerciseSchedule;
import com.ssafy.ssafit.model.dto.KakaoUser;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.model.dto.Video;

public interface UserService {
	// 전체 사용자 목록 불러오기
	public List<User> getUserList();

	// 사용자 등록하기
	public int signup(User user);

	// 로그인 하기
	public User login(String user_id, String user_pw);

	// 아이디 중복검사
	public boolean checkId(String userId);

	// 닉네임 중복검사
	public boolean checkName(String userName);

	public String findName(String userId);

	public List<Video> likeList(String userId);

	public int insertExercise(Exercise exercise);

	public int deleteExercise(Exercise exercise);

	public String findPw(String userId);

	public boolean selectIdEmail(String userId, String userEmail);

	public List<Exercise> getExerciseByDate(String userId, String date);

	public ExerciseSchedule selectExerciseTimeByDate (String userId, Date date);
	
	public List<Exercise> getExercise(String userId);

	public boolean emailCheck(String userEmail);
	public void processKakaoUser(KakaoUser kakaoUser);

	public boolean selectEvent(Event event);

	public int insertEvent(Event event);

	public int addCoupon(String userId);

	public int useCoupon(String userId);
	public int selectCoupon(String userId);
	
}
