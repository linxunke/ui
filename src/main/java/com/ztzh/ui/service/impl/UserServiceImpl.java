package com.ztzh.ui.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ztzh.ui.bo.RegisterResultBo;
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
	@Transactional
	public RegisterResultBo register(UserInfoDomain user) {
		RegisterResultBo registerResBo = new RegisterResultBo();
		int accountResult = VerifyLengthUtil.objectLengthShortThan20(user.getUserAccount());
		int nicknameResult = VerifyLengthUtil.objectLengthShortThan20(user.getUserNickname());
		int weiXinResult = VerifyLengthUtil.objectLengthShortThan20(user.getUserWeixin());
		int passwordResult = VerifyLengthUtil.objectLengthForPassword(user.getUserPassword());
		if(accountResult == UserConstants.CHECK_DATA_LENGTH_FALSE){
			registerResBo.setCode(UserConstants.CHECK_DATA_LENGTH_FALSE);
			return registerResBo;
		}else if(nicknameResult == UserConstants.CHECK_DATA_LENGTH_FALSE){
			registerResBo.setCode(UserConstants.CHECK_DATA_LENGTH_FALSE);
			return registerResBo;
		}else if(weiXinResult == UserConstants.CHECK_DATA_LENGTH_FALSE){
			registerResBo.setCode(UserConstants.CHECK_DATA_LENGTH_FALSE);
			return registerResBo;
		}else if(passwordResult == UserConstants.CHECK_DATA_LENGTH_FALSE){
			registerResBo.setCode(UserConstants.CHECK_DATA_LENGTH_FALSE);
			return registerResBo;
		}else{
			user.setAuthority(new Integer(UserConstants.USER_AUTHORITY_NORMAL));
			user.setIsValid(new Integer(UserConstants.USER_IS_VALID_YES));
			user.setRegisterTime(GetSYSTime.systemTime());
			//执行dao层方法
			userInfoDomainMapper.insert(user);
			String userId = userInfoDomainMapper.getUserIdByAccount(user.getUserAccount());
			registerResBo.setCode(UserConstants.CHECK_DATA_LENGTH_TRUE);
			registerResBo.setUserId(userId);
			return registerResBo;
		}
		
	}
	@Override
	public String encrypt(String password) {
		String result = MD5Util.encode2hex(password);
		return result;
	}
	
	@Override
	public String accountValidate(String account) {
		
		return null;
	}
	@Override
	public String getUserIdByAccount(String account) {
		
		return userInfoDomainMapper.getUserIdByAccount(account);
	}
	@Override
	public boolean checkUserAccountIsValue(String account) {
		String userId = userInfoDomainMapper.getUserIdByAccount(account);
		if(null==userId){
			return true;
		}else{
			return false;
		}
		
	}
	
}
