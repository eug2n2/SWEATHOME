package com.ssafy.ssafit.model.dao;

import java.util.List;

import com.ssafy.ssafit.model.dto.Ask;

public interface AskDao {
	public List<Ask> selectAskAll();

	public List<Ask> selectAsk(String userId);

	public int insertAsk(Ask ask);

	public int deleteAsk(int askId);

}
