package com.ztzh.ui.service;

import java.util.List;

import com.ztzh.ui.bo.IconUrlResultBo;

public interface MaterialInfoService {
	
	List<IconUrlResultBo> getDisplayUrlByThumbnailUrl(List<String> thumbnailUrls);
}
