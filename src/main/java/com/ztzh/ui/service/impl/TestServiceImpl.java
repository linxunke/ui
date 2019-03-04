package com.ztzh.ui.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztzh.ui.dao.UserInfoDomainMapper;
import com.ztzh.ui.po.UserInfoDomain;
import com.ztzh.ui.service.TestService;

@Service
public class TestServiceImpl implements TestService{
	@Autowired
	private UserInfoDomainMapper userInfoDomainMapper;
	
	@Override
	public int insert(UserInfoDomain userInfoDomain) {
		int i = userInfoDomainMapper.insert(userInfoDomain);
		return i; 
	}

	@Override
	public List<UserInfoDomain> selectAll() {
		List<UserInfoDomain> userInfoList = userInfoDomainMapper.selectAll();
		return userInfoList;
	}
	

	

}
