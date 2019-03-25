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
}