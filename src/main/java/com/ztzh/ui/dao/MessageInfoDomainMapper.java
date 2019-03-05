package com.ztzh.ui.dao;

import com.ztzh.ui.po.MessageInfoDomain;

public interface MessageInfoDomainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MessageInfoDomain record);

    int insertSelective(MessageInfoDomain record);

    MessageInfoDomain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MessageInfoDomain record);

    int updateByPrimaryKey(MessageInfoDomain record);
}