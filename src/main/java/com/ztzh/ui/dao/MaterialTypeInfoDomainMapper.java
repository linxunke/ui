package com.ztzh.ui.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.ztzh.ui.po.MaterialTypeInfoDomain;

public interface MaterialTypeInfoDomainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MaterialTypeInfoDomain record);

    int insertSelective(MaterialTypeInfoDomain record);

    MaterialTypeInfoDomain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MaterialTypeInfoDomain record);

    int updateByPrimaryKey(MaterialTypeInfoDomain record);
    
    int deleteByMaterialInfoIds(@Param("materialInfoIds")List<Long> materialInfoIds);
    
    int addMaterialTypeInfo(MaterialTypeInfoDomain materialTypeInfo);
    
    int deleteByMaterialInfoId(Long materialInfoId);
    
    int insertBybatch(@Param("materialTypeInfoDomains") List<MaterialTypeInfoDomain> list);
    
    List<MaterialTypeInfoDomain> selectMaterialTypeInfosByMaterialInfoId(@Param("materialInfoId")Long materialInfoId);
    
    int selectMaterialNumberByChildTypeCode(String childCode);
    
    List<String> selectMaterialPngUrlListByIconChildCode(String childTypeCode);
    
    String selectMaterialThumbnailUrlByImgChildCode(String childTypeCode);
    
    int selectMaterialNumByChildTypeCode(String childTypeCode);
}