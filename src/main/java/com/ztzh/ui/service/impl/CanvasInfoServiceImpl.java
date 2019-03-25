package com.ztzh.ui.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ztzh.ui.dao.CanvasInfoDomainMapper;
import com.ztzh.ui.dao.MaterialHistoryCollectionDomainMapper;
import com.ztzh.ui.dao.MaterialInfoDomainMapper;
import com.ztzh.ui.dao.MaterialTypeInfoDomainMapper;
import com.ztzh.ui.po.MaterialInfoDomain;
import com.ztzh.ui.service.CanvasInfoService;
import com.ztzh.ui.utils.FileUpload;

@Service
public class CanvasInfoServiceImpl implements CanvasInfoService{
	Logger logger = LoggerFactory.getLogger(CanvasInfoServiceImpl.class);
	
	@Autowired
	CanvasInfoDomainMapper canvasInfoDomainMapper;
	
	@Autowired
	MaterialInfoDomainMapper materialInfoDomainMapper;
	
	@Autowired
	MaterialTypeInfoDomainMapper materialTypeInfoDomainMapper;
	
	@Autowired
	MaterialHistoryCollectionDomainMapper materialHistoryCollectionDomainMapper;
	

	@SuppressWarnings("unused")
	@Override
	@Transactional
	public void userDeleteCanvasWithMaterials(Long canvasId, Long userId) {
		logger.info("开始删除画板canvasId:{}",canvasId);
		int count = canvasInfoDomainMapper.deleteByPrimaryKey(canvasId);
		logger.info("开始删除数据库中素材数据");
		List<MaterialInfoDomain> materialInfoDomainList = materialInfoDomainMapper.selectByCanvasId(canvasId);
		List<String> fileList = new ArrayList<String>();
		List<Long> materialIdList = new ArrayList<Long>();
		for(MaterialInfoDomain materialInfoDomain:materialInfoDomainList) {
			fileList.add(materialInfoDomain.getMaterialUrl());
			fileList.add(materialInfoDomain.getThumbnailUrl());
			fileList.add(materialInfoDomain.getPngUrl());
			materialIdList.add(materialInfoDomain.getId());
		}
		int countDeletedMaterials = materialInfoDomainMapper.deleteByCanvasId(canvasId, userId);
		//删除分类中的数据
		int materialTypeInfoCount = materialTypeInfoDomainMapper.deleteByMaterialInfoIds(materialIdList);
		int materialHistoryCollectionCount = materialHistoryCollectionDomainMapper.deleteByMaterialInfoIds(materialIdList);
		FileUpload.deleteFiles(fileList);
		logger.info("总共删除{}件素材",countDeletedMaterials);
		logger.info("删除磁盘中的素材文件成功");
	}

}
