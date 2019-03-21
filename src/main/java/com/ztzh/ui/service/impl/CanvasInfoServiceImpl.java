package com.ztzh.ui.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztzh.ui.dao.CanvasInfoDomainMapper;
import com.ztzh.ui.po.CanvasInfoDomain;
import com.ztzh.ui.service.CanvasInfoService;

@Service
public class CanvasInfoServiceImpl implements CanvasInfoService{
	@Autowired
	CanvasInfoDomainMapper canvasInfoDomainMapper;
	
	@Override
	public List<CanvasInfoDomain> selectCanvasByUserId(Long userId) {
		return canvasInfoDomainMapper.selectByUserId(userId);
	}

}
