package com.ztzh.ui.service;

import java.util.List;

import com.ztzh.ui.po.CanvasInfoDomain;

public interface CanvasInfoService {
	List<CanvasInfoDomain> selectCanvasByUserId(Long userId);
}
