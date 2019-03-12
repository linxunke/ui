package com.ztzh.ui.dao;

import com.ztzh.ui.po.UserInfoDomain;

public interface UserInfoDomainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserInfoDomain record);

    int insertSelective(UserInfoDomain record);

    UserInfoDomain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfoDomain record);

    int updateByPrimaryKey(UserInfoDomain record);
    
    String accountValidate(String account);
}