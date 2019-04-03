package com.ztzh.ui.service;

import java.util.List;

import com.ztzh.ui.bo.IconUrlResultBo;
import com.ztzh.ui.po.MaterialInfoDomain;

public interface MaterialInfoService {
	
	List<IconUrlResultBo> getDisplayUrlByThumbnailUrl(List<String> thumbnailUrls);
	
	List<MaterialInfoDomain> getMaterialListByCanvasId(Long canvasId);
}
