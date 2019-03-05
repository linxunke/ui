package com.ztzh.ui.dao;

import com.ztzh.ui.po.MessageModelDomain;

public interface MessageModelDomainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MessageModelDomain record);

    int insertSelective(MessageModelDomain record);

    MessageModelDomain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MessageModelDomain record);

    int updateByPrimaryKey(MessageModelDomain record);
}