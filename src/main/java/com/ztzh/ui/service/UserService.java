package com.ztzh.ui.service;

import com.ztzh.ui.po.UserInfoDomain;

public interface UserService {

	public int register(UserInfoDomain user);
	public String encrypt(String password);
	public String accountValidate(String account);
	public String getUserIdByAccount(String account);
}
