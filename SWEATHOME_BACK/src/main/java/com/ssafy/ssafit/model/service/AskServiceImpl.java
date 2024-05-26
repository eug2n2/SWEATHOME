package com.ssafy.ssafit.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.AskDao;
import com.ssafy.ssafit.model.dto.Ask;

@Service
public class AskServiceImpl implements AskService {
	
	private final AskDao askDao;
	
	public AskServiceImpl(AskDao askDao) {
		this.askDao = askDao;
	}

	@Override
	public List<Ask> getAskList() {
		return askDao.selectAskAll();
	}
	@Override
	public List<Ask> getPersonalAskList(String userId) {
		return askDao.selectAsk(userId);
	}
	@Override
	public int insertAsk(Ask ask) {
		return askDao.insertAsk(ask);
	}

	@Override
	public int deleteAsk(int askId) {
		return askDao.deleteAsk(askId);
	}
	
	

}
