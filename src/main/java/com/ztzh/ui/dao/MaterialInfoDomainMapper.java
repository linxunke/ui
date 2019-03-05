package com.ztzh.ui.dao;

import com.ztzh.ui.po.MaterialInfoDomain;

public interface MaterialInfoDomainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MaterialInfoDomain record);

    int insertSelective(MaterialInfoDomain record);

    MaterialInfoDomain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MaterialInfoDomain record);

    int updateByPrimaryKey(MaterialInfoDomain record);
}