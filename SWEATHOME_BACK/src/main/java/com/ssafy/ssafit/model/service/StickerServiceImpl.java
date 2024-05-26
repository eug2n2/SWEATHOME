package com.ssafy.ssafit.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.StickerDao;
import com.ssafy.ssafit.model.dto.Sticker;

@Service
public class StickerServiceImpl implements StickerService {
	private final StickerDao stickerDao;

	public StickerServiceImpl(StickerDao stickerDao) {
		this.stickerDao = stickerDao;
	}

	@Override
	public boolean checkSticker(Sticker sticker) {
		return (stickerDao.checkSticker(sticker) < 1) ? true : false;
	}

	@Override
	public int insertSticker(Sticker sticker) {
		return stickerDao.insertSticker(sticker);
	}
	
	public List<Integer> getStickersByUserId(String userId){
		return stickerDao.getStickersByUserId(userId);
	}
}
