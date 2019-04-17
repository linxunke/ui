package com.ztzh.ui.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztzh.ui.constants.UserConstants;
import com.ztzh.ui.dao.MaterialHistoryCollectionDomainMapper;
import com.ztzh.ui.po.MaterialHistoryCollectionDomain;
import com.ztzh.ui.service.MaterialHistoryCollectionService;

@Service
public class MaterialHistoryColletionServiceImpl implements MaterialHistoryCollectionService{
	Logger logger = LoggerFactory.getLogger(MaterialHistoryCollectionService.class);
	@Autowired
	MaterialHistoryCollectionDomainMapper materialHistoryCollectionDomainMapper;

	@Override
	public boolean collectMaterial(MaterialHistoryCollectionDomain materialHistoryCollectionDomain, String operationCode) throws Exception {
		int count = 0;
		if(UserConstants.OPERATION_CODE_CHOOSED.equals(operationCode)) {
			logger.info("userId:{}的用户收藏了MaterialInfoId:{}的素材",materialHistoryCollectionDomain.getUserInfoId(),materialHistoryCollectionDomain.getId());
			count = materialHistoryCollectionDomainMapper.insert(materialHistoryCollectionDomain);
	
		}else if(UserConstants.OPERATION_CODE_CANCEL.equals(operationCode)) {
			logger.info("userId:{}的用户取消了MaterialInfoId:{}的素材的收藏",materialHistoryCollectionDomain.getUserInfoId(),materialHistoryCollectionDomain.getId());
			count = materialHistoryCollectionDomainMapper.cancelCollection(materialHistoryCollectionDomain.getMaterialInfoId(), materialHistoryCollectionDomain.getUserInfoId());
			
		}
		if(count==1) {
			return true;
		}else {
			throw new Exception();
		}
	}

}
