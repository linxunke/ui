package com.ztzh.ui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztzh.ui.bo.MaterialInfoIndex;
import com.ztzh.ui.constants.UserConstants;
import com.ztzh.ui.dao.MaterialHistoryCollectionDomainMapper;
import com.ztzh.ui.po.MaterialHistoryCollectionDomain;
import com.ztzh.ui.po.MaterialInfoDomain;
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

	@Override
	public List<Long> SelectByUserInfoId(Long userInfoId) {
		List<Long> list = materialHistoryCollectionDomainMapper.SelectByUserInfoId(userInfoId);
		return list;
	}

	@Override
	public List<MaterialInfoIndex> SelectByUserIdForHistory(Long userId,int type,int currentPage,int pageSize) {
		int start = (currentPage - 1)*pageSize;
		List<MaterialInfoDomain> listDomain = 
				materialHistoryCollectionDomainMapper.SelectByUserIdForHistory(userId,type,start,pageSize);
		List<MaterialInfoIndex> list = new ArrayList<MaterialInfoIndex>();
		for(int i = 0; i <= listDomain.size()-1; i++){
			MaterialInfoIndex materialInfoIndex = new MaterialInfoIndex();
			materialInfoIndex.setId(listDomain.get(i).getId());
			materialInfoIndex.setThumbnailUrl(listDomain.get(i).getThumbnailUrl());
			materialInfoIndex.setMaterialName(listDomain.get(i).getMaterialName());
			list.add(materialInfoIndex);
		}
		List<Long> listCollectionId = SelectByUserInfoId(userId);
		for(int i = 0; i <= list.size()-1; i++){
			for(int j = 0; j<= listCollectionId.size()-1; j++){
				if(list.get(i).getId() == listCollectionId.get(j)){
					list.get(i).setIsCollection(1);
				}else{
					if(list.get(i).getIsCollection() != 1){
						list.get(i).setIsCollection(0);
					}
				}
			}
		}
		return list;
	}

	
}
