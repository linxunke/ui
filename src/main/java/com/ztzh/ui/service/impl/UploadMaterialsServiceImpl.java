package com.ztzh.ui.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztzh.ui.bo.MaterialInfoIndex;
import com.ztzh.ui.bo.UploadMaterialsBo;
import com.ztzh.ui.dao.MaterialInfoDomainMapper;
import com.ztzh.ui.dao.MaterialTypeDomainMapper;
import com.ztzh.ui.dao.MaterialTypeInfoDomainMapper;
import com.ztzh.ui.po.MaterialInfoDomain;
import com.ztzh.ui.po.MaterialTypeDomain;
import com.ztzh.ui.po.MaterialTypeInfoDomain;
import com.ztzh.ui.service.ElasticSearchService;
import com.ztzh.ui.service.UploadMaterialsService;

@Service
public class UploadMaterialsServiceImpl implements UploadMaterialsService{
	Logger logger = LoggerFactory.getLogger(UploadMaterialsServiceImpl.class);
	@Autowired
	MaterialTypeDomainMapper materialTypeDomainMapper;
	
	@Autowired
	MaterialInfoDomainMapper materialInfoDomainMapper;
	
	@Autowired

	MaterialTypeInfoDomainMapper materialTypeInfoDomainMapper;
	
	@Autowired
	ElasticSearchService elasticSearchService;

	@Override
	public UploadMaterialsBo getMaterialTypes() {
		UploadMaterialsBo uploadMaterialsBo = new UploadMaterialsBo();
		List<MaterialTypeDomain> materialTypes = materialTypeDomainMapper.selectType();
		List<MaterialTypeDomain> materialSegmentations = materialTypeDomainMapper.selectSegmentation();
		List<MaterialTypeDomain> materialStyles = materialTypeDomainMapper.selectStyle();
		uploadMaterialsBo.setMaterialTypes(materialTypes);
		uploadMaterialsBo.setMaterialSegmentations(materialSegmentations);
		uploadMaterialsBo.setMaterialStyles(materialStyles);
		return uploadMaterialsBo;
	}
	
	@Override
	public int addMaterialInfo(MaterialInfoDomain materialInfo) {
		return materialInfoDomainMapper.addMaterialInfo(materialInfo);
	}
	
	@Override
	public boolean putAllMaterialInfoInElasticsearch() {
		boolean result = false;
		List<MaterialInfoIndex> materialInfoIndexList = materialInfoDomainMapper.getValidMaterialInfoForIndex();
		elasticSearchService.saveDocument(materialInfoIndexList);
		result = true;
		logger.info("已经将materialinfo信息全部更新到搜索引擎中");
		return result;
	}

	@Override
	public int addMaterialTypeInfo(List<MaterialTypeInfoDomain> typesInfoList) {
		int result = 1;
		for (int i = 0; i < typesInfoList.size(); i++) {
			result *= materialTypeInfoDomainMapper.addMaterialTypeInfo(typesInfoList.get(i));
		}
		return result;
	}

	@Override
	public Long getMaterialIdByMaterialUrl(String materialUrl) {
		return materialInfoDomainMapper.selectMaterialIdByMaterialUrl(materialUrl);
	}
}
