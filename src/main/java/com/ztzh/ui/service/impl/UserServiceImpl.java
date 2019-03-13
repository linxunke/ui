package com.ztzh.ui.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztzh.ui.constants.UserConstants;
import com.ztzh.ui.dao.UserInfoDomainMapper;
import com.ztzh.ui.po.UserInfoDomain;
import com.ztzh.ui.service.UserService;
import com.ztzh.ui.utils.GetSYSTime;
import com.ztzh.ui.utils.MD5Util;
import com.ztzh.ui.utils.VerifyLengthUtil;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserInfoDomainMapper userInfoDomainMapper;
	@Override
	public int register(UserInfoDomain user) {
		// TODO Auto-generated method stub
		int accountResult = VerifyLengthUtil.objectLengthShortThan20(user.getUserAccount());
		int nicknameResult = VerifyLengthUtil.objectLengthShortThan20(user.getUserNickname());
		int weiXinResult = VerifyLengthUtil.objectLengthShortThan20(user.getUserWeixin());
		int passwordResult = VerifyLengthUtil.objectLengthForPassword(user.getUserPassword());
		if(accountResult == 1){
			//检验不过关
			return 10;
		}else if(nicknameResult == 1){
			//检验不过关
			return 10;
		}else if(weiXinResult == 1){
			//检验不过关
			return 10;
		}else if(passwordResult == 1){
			//检验不过关
			return 10;
		}else{
			user.setAuthority(new Integer(UserConstants.USER_AUTHORITY_NORMAL));
			user.setIsValid(new Integer(UserConstants.USER_IS_VALID_YES));
			user.setRegisterTime(GetSYSTime.systemTime());
			//执行dao层方法
			return userInfoDomainMapper.insert(user);
		}
		
	}
	@Override
	public String encrypt(String password) {
		String result = MD5Util.encode2hex(password);
		return result;
	}
	
	@Override
	public String accountValidate(String account) {
		// TODO Auto-generated method stub
		
		return null;
	}
	@Override
	public String getUserIdByAccount(String account) {
		
		return userInfoDomainMapper.getUserIdByAccount(account);
	}
	
}
