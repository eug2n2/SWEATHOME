package com.ssafy.ssafit.model.dao;

import java.util.List;

import com.ssafy.ssafit.model.dto.Sticker;

public interface StickerDao {
	public int checkSticker(Sticker sticker);

	public int insertSticker(Sticker sticker);
	
	public List<Integer> getStickersByUserId(String userId);
}
