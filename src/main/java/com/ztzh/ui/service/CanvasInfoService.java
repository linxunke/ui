package com.ztzh.ui.service;

import java.util.List;

import com.ztzh.ui.po.CanvasInfoDomain;

public interface CanvasInfoService {
	
	public void userDeleteCanvasWithMaterials(Long canvasId, Long userId);

	List<CanvasInfoDomain> selectCanvasByUserId(Long userId);

}
