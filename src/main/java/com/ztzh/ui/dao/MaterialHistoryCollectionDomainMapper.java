package com.ztzh.ui.dao;

import com.ztzh.ui.po.MaterialHistoryCollectionDomain;

public interface MaterialHistoryCollectionDomainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MaterialHistoryCollectionDomain record);

    int insertSelective(MaterialHistoryCollectionDomain record);

    MaterialHistoryCollectionDomain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MaterialHistoryCollectionDomain record);

    int updateByPrimaryKey(MaterialHistoryCollectionDomain record);
}