package com.ssafy.ssafit.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.ssafit.model.dto.Event;
import com.ssafy.ssafit.model.dto.Exercise;
import com.ssafy.ssafit.model.dto.KakaoUser;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.model.dto.Video;

public interface UserDao {
	public List<User> selectAll();

	public int insertUser(User user);

	public void saveKakaoUser(KakaoUser kakaoUser);

	public int kakaouserId(KakaoUser kakaoUser);

	public User selectOne(Map<String, String> info);

	public int checkId(String userId);

	public int checkName(String userName);

	public String findName(String userId);

	public List<Video> likeList(String userId);

	public int insertExercise(Exercise exercise);

	public int deleteExercise(Exercise exercise);

	public List<Exercise> selectExerciseByDate(Map<String, Object> params);

	public Integer selectExerciseTimeByDate(Map<String, Object> params);

	public List<Exercise> selectExercise(String userId);

	public int getTotalExerciseTimeFor7Days(String startDate);

	public int selectIdEmail(Map<String, String> params);

	public String findPw(String userId);

	public int emailCheck(String userEmail);

	public int useCoupon(String userId);

	public int addCoupon(String userId);

	public int insertEvent(Event event);

	public int selectCoupon(String userId);

	public int selectEvent(Event event);

}