package com.ztzh.ui.dao;

import com.ztzh.ui.po.MaterialTypeDomain;

public interface MaterialTypeDomainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MaterialTypeDomain record);

    int insertSelective(MaterialTypeDomain record);

    MaterialTypeDomain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MaterialTypeDomain record);

    int updateByPrimaryKey(MaterialTypeDomain record);
}