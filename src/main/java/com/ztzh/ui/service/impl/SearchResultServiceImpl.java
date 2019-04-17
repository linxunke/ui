package com.ztzh.ui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztzh.ui.bo.MaterialTypeBo;
import com.ztzh.ui.dao.MaterialTypeDomainMapper;
import com.ztzh.ui.po.MaterialTypeDomain;
import com.ztzh.ui.service.SearchResultService;

@Service
public class SearchResultServiceImpl implements SearchResultService{

	Logger logger = LoggerFactory.getLogger(SearchResultServiceImpl.class);
	@Autowired
	MaterialTypeDomainMapper materialTypeDomainMapper;
	
	@Override
	public List<MaterialTypeBo> selectTypeName() {
		List<MaterialTypeDomain> list1 = materialTypeDomainMapper.selectTypeNameParent();
		// 把childName放入MaterialTypeBo对象中
		List<MaterialTypeDomain> list2 = selectChildTypeName();
		List<MaterialTypeBo> list4 = new ArrayList<MaterialTypeBo>();
		for(int i = 0 ;i <= list1.size()-1;i++){
			MaterialTypeBo materialTypeBo = new MaterialTypeBo();
			List<String> list3 = new ArrayList<String>();
			for(int j = 0; j<= list2.size()-1;j++){
				if(list2.get(j).getParentCode().equals(list1.get(i).getTypeCode()) ){
					list3.add(list2.get(j).getTypeName());
				}
			}
			materialTypeBo.setTypeName(list1.get(i).getTypeName());
			materialTypeBo.setTypeCode(list1.get(i).getTypeCode());
			materialTypeBo.setChildTypeName(list3);
			list4.add(materialTypeBo);
		}
		logger.info("list4所有type的集合:{}",list4);
		return list4;
	}
	//以下未完成
	@Override
	public List<MaterialTypeDomain> selectChildTypeName() {
		List<MaterialTypeDomain> list = materialTypeDomainMapper.selectTypeNameChild();
		logger.info("list2子type的集合:{}",list);
		return list;
	}

}
