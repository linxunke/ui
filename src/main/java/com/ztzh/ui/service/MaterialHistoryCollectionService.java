package com.ztzh.ui.service;

import java.util.List;

import com.ztzh.ui.bo.MaterialInfoIndex;
import com.ztzh.ui.po.MaterialHistoryCollectionDomain;

public interface MaterialHistoryCollectionService {
	/**
	 * 收藏
	 */
	public boolean collectMaterial(MaterialHistoryCollectionDomain materialHistoryCollectionDomain, String operationCode) throws Exception;
	
	List<Long> SelectByUserInfoId(Long userInfoId);
	
	List<MaterialInfoIndex> SelectByUserIdForHistory(Long userId, int type);
}
