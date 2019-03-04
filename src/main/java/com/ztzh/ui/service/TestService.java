package com.ztzh.ui.service;

import java.util.List;

import com.ztzh.ui.po.UserInfoDomain;

public interface TestService {
	public int insert(UserInfoDomain userInfoDomain);	
	
	public List<UserInfoDomain> selectAll();

}
