package com.ztzh.ui.service;

import com.ztzh.ui.bo.RegisterResultBo;
import com.ztzh.ui.po.UserInfoDomain;

public interface UserService {

	public RegisterResultBo register(UserInfoDomain user);
	public String encrypt(String password);
	public String accountValidate(String account);
	public String getUserIdByAccount(String account);
	public boolean checkUserAccountIsValue(String account);
}
