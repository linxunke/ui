package com.ztzh.ui.service;

import java.util.List;

import com.ztzh.ui.bo.MaterialTypeBo;
import com.ztzh.ui.po.MaterialTypeDomain;

public interface SearchResultService {

	public List<MaterialTypeBo> selectTypeName();
	public List<MaterialTypeDomain> selectChildTypeName();
	
}
