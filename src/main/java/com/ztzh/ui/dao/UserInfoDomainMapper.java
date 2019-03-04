package com.ztzh.ui.dao;

import java.util.List;

import org.mapstruct.Mapper;

import com.ztzh.ui.po.UserInfoDomain;


@Mapper
public interface UserInfoDomainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfoDomain record);

    int insertSelective(UserInfoDomain record);

    UserInfoDomain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfoDomain record);

    int updateByPrimaryKey(UserInfoDomain record);
    
    List<UserInfoDomain> selectAll();
}