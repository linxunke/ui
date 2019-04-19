package com.ztzh.ui.service;

import java.util.List;
import java.util.Map;

import com.ztzh.ui.bo.MaterialChildTypeCoverInfosBo;
import com.ztzh.ui.bo.MaterialDetailsInfoBo;
import com.ztzh.ui.po.MaterialTypeDomain;
import com.ztzh.ui.utils.PageQueryUtil;

public interface MaterialLibraryService {
	List<MaterialTypeDomain> getAllMaterialParentType();
	
	List<MaterialChildTypeCoverInfosBo> getChildTypeInfoByParentCode(String parentCode,boolean isIcon);
	
	PageQueryUtil getMaterialsByChildTypeCode(String childTypeCode, int currentPage, int pageSize);
	
	List<MaterialTypeDomain> getMaterialTypeInfoByChildTypeCode(String childTypeCode);
	
	Map<String,Object> getMaterialAndUserInfoById(Long materialId);
}
