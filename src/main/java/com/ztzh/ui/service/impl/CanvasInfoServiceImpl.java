package com.ztzh.ui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ztzh.ui.bo.ManagementCanvasBo;
import com.ztzh.ui.constants.CanvasInfoConstants;
import com.ztzh.ui.dao.CanvasInfoDomainMapper;
import com.ztzh.ui.dao.MaterialHistoryCollectionDomainMapper;
import com.ztzh.ui.dao.MaterialInfoDomainMapper;
import com.ztzh.ui.dao.MaterialTypeInfoDomainMapper;
import com.ztzh.ui.po.CanvasInfoDomain;
import com.ztzh.ui.po.MaterialInfoDomain;
import com.ztzh.ui.service.CanvasInfoService;
import com.ztzh.ui.utils.FTPUtil;
import com.ztzh.ui.utils.GetSYSTime;

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
	
	@Autowired
	FTPUtil ftpUtil;
	

	
	@SuppressWarnings("unused")
	@Override
	@Transactional
	public boolean userDeleteCanvasWithMaterials(Long canvasId, Long userId) {
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
		ftpUtil.deleteFtpFile(fileList);
		logger.info("删除磁盘中的素材文件成功");
		int countDeletedMaterials = materialInfoDomainMapper.deleteByCanvasId(canvasId, userId);
		//删除分类中的数据
		int materialTypeInfoCount = materialTypeInfoDomainMapper.deleteByMaterialInfoIds(materialIdList);
		int materialHistoryCollectionCount = materialHistoryCollectionDomainMapper.deleteByMaterialInfoIds(materialIdList);
		logger.info("总共删除{}件素材",countDeletedMaterials);
		return true;
	}
	
	@SuppressWarnings("unused")
	@Override
	@Transactional
	public boolean userDeleteCanvasWithoutMaterials(Long canvasId, Long userId) {
		logger.info("开始删除画板canvasId:{}",canvasId);
		int count = canvasInfoDomainMapper.deleteByPrimaryKey(canvasId);
		logger.info("开始将所属该画板的素材转移到未分类画板中");
		CanvasInfoDomain canvasInfoDomain = canvasInfoDomainMapper.selectByCanvasName(CanvasInfoConstants.CANVAS_DEFALT_NAME,userId);
		MaterialInfoDomain materialInfoDomain = new MaterialInfoDomain();
		materialInfoDomain.setCanvasInfoIdPrivate(canvasInfoDomain.getId());
		materialInfoDomain.setCanvasInfoIdPublic(canvasInfoDomain.getId());
		materialInfoDomain.setCreateUserId(userId);
		materialInfoDomain.setUploadTime(GetSYSTime.systemTime());
		int updateCount = materialInfoDomainMapper.updateByCanvasInfoIdPrivate(materialInfoDomain,canvasId,userId);
		logger.info("总共将{}件素材转入未分类",updateCount);
		return true;
	}

	@Override
	public int canvasCount(Long userId) {
		return canvasInfoDomainMapper.selectCountByUserId(userId);
	}
	@Override
	public List<ManagementCanvasBo> selectCanvasByUserId(Long userId) {
		return canvasInfoDomainMapper.selectByUserId(userId);
	}
	@SuppressWarnings("null")
	@Override
	public boolean addCanvas(Long userId, String canvasName, String canvasDesc) {
		CanvasInfoDomain canvasInfo = null;
		canvasInfo.setUserId(userId);
		canvasInfo.setCanvasName(canvasName);
		canvasInfo.setDescribeInfo(canvasDesc);
		canvasInfo.setCanvasType(2);
		canvasInfo.setCreateTime(GetSYSTime.systemTime());
		canvasInfo.setIsValid(1);
		canvasInfoDomainMapper.insert(canvasInfo);
		return true;
	}
	@SuppressWarnings("null")
	@Override
	public boolean updateCanvas(Long canvasId, String canvasName, String canvasDesc) {
		CanvasInfoDomain canvasInfo = null;
		canvasInfo.setId(canvasId);
		canvasInfo.setCanvasName(canvasName);
		canvasInfo.setDescribeInfo(canvasDesc);
		canvasInfoDomainMapper.updateByPrimaryKey(canvasInfo);
		return true;
	}
}
