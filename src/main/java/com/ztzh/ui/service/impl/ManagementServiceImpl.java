package com.ztzh.ui.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.ztzh.ui.bo.ManagementCanvasBo;
import com.ztzh.ui.dao.CanvasInfoDomainMapper;
import com.ztzh.ui.po.CanvasInfoDomain;
import com.ztzh.ui.service.ManagementService;
import com.ztzh.ui.utils.GetSYSTime;

//@Service
public class ManagementServiceImpl implements ManagementService{

	@Autowired
	CanvasInfoDomainMapper canvasInfoDomainMapper;
	@Override
	public int canvasCount(Long userId) {
		return canvasInfoDomainMapper.selectCountByUserId(userId);
	}

	@Override
	public Object searchCanvas(Long userId, String canvasName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ManagementCanvasBo> searchCanvasInfoByUserId(Long userId) {
		
		return canvasInfoDomainMapper.selectCanvasInfoByUserId(userId);
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

	@Override
	public boolean deleteToUnsort(Long userId, Long canvasId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(Long userId, Long canvasId) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
