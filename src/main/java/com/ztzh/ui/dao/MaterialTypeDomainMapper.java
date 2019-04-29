package com.ztzh.ui.dao;

import java.util.List;
import com.ztzh.ui.po.MaterialTypeDomain;

public interface MaterialTypeDomainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MaterialTypeDomain record);

    int insertSelective(MaterialTypeDomain record);

    MaterialTypeDomain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MaterialTypeDomain record);

    int updateByPrimaryKey(MaterialTypeDomain record);
    
    List<MaterialTypeDomain> selectType();
    
    List<MaterialTypeDomain> selectSegmentation();
    
    List<MaterialTypeDomain> selectStyle();
    //这里是查询parent的集合
  	List<MaterialTypeDomain> selectTypeNameParent();
  	//这里是查询child的集合
  	List<MaterialTypeDomain> selectTypeNameChild();
    
    List<MaterialTypeDomain> selectChildTypeByParentCode(String parentCode);
    
    String selectParentTypeCodeByChildTypeCode(String childTypeCode);
    
    List<MaterialTypeDomain> selectTypeInfosByChildTypeCode(String childTypeCode);
}