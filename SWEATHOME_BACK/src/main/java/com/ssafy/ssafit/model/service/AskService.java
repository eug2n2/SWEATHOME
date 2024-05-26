package com.ssafy.ssafit.model.service;

import java.util.List;

import com.ssafy.ssafit.model.dto.Ask;

public interface AskService {
	public List<Ask> getAskList();
	
	public List<Ask> getPersonalAskList(String userId) ;
	
	public int insertAsk(Ask ask);
	
	public int deleteAsk(int askId);
}
