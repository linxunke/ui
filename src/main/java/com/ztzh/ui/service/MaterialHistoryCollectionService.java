package com.ztzh.ui.service;

import org.springframework.web.bind.annotation.RequestParam;

import com.ztzh.ui.po.MaterialHistoryCollectionDomain;

public interface MaterialHistoryCollectionService {
	/**
	 * 收藏
	 */
	public boolean collectMaterial(MaterialHistoryCollectionDomain materialHistoryCollectionDomain, String operationCode) throws Exception;
}
