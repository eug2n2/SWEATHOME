package com.ssafy.ssafit.model.service;

import java.util.List;

import com.ssafy.ssafit.model.dto.Sticker;

public interface StickerService {
	public boolean checkSticker(Sticker sticker);

	public int insertSticker(Sticker sticker);
	
	public List<Integer> getStickersByUserId(String userId);
}
