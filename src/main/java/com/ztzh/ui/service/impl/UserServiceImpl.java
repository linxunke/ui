package com.ztzh.ui.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ztzh.ui.bo.LoginResultBo;
import com.ztzh.ui.bo.RegisterResultBo;
import com.ztzh.ui.constants.CanvasInfoConstants;
import com.ztzh.ui.constants.UserConstants;
import com.ztzh.ui.dao.CanvasInfoDomainMapper;
import com.ztzh.ui.dao.UserInfoDomainMapper;
import com.ztzh.ui.po.CanvasInfoDomain;
import com.ztzh.ui.po.UserInfoDomain;
import com.ztzh.ui.service.UserService;
import com.ztzh.ui.utils.GetSYSTime;
import com.ztzh.ui.utils.MD5Util;
import com.ztzh.ui.utils.VerifyLengthUtil;
import com.ztzh.ui.vo.ResponseVo;

@Service
public class UserServiceImpl implements UserService {
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserInfoDomainMapper userInfoDomainMapper;
	
	@Autowired
	CanvasInfoDomainMapper canvasInfoDomainMapper;

	@Override
	@Transactional
	public RegisterResultBo register(UserInfoDomain user) {
		RegisterResultBo registerResBo = new RegisterResultBo();
		int accountResult = VerifyLengthUtil.objectLengthShortThan20(user
				.getUserAccount());
		int nicknameResult = VerifyLengthUtil.objectLengthShortThan20(user
				.getUserNickname());
		int weiXinResult = VerifyLengthUtil.objectLengthShortThan20(user
				.getUserWeixin());
		int passwordResult = VerifyLengthUtil.objectLengthForPassword(user
				.getUserPassword());
		if (accountResult == UserConstants.CHECK_DATA_LENGTH_FALSE) {
			registerResBo.setCode(UserConstants.CHECK_DATA_LENGTH_FALSE);
			return registerResBo;
		} else if (nicknameResult == UserConstants.CHECK_DATA_LENGTH_FALSE) {
			registerResBo.setCode(UserConstants.CHECK_DATA_LENGTH_FALSE);
			return registerResBo;
		} else if (weiXinResult == UserConstants.CHECK_DATA_LENGTH_FALSE) {
			registerResBo.setCode(UserConstants.CHECK_DATA_LENGTH_FALSE);
			return registerResBo;
		} else if (passwordResult == UserConstants.CHECK_DATA_LENGTH_FALSE) {
			registerResBo.setCode(UserConstants.CHECK_DATA_LENGTH_FALSE);
			return registerResBo;
		} else {
			user.setAuthority(new Integer(UserConstants.USER_AUTHORITY_NORMAL));
			user.setIsValid(new Integer(UserConstants.USER_IS_VALID_YES));
			user.setRegisterTime(GetSYSTime.systemTime());
			// 执行dao层方法
			userInfoDomainMapper.insert(user);
			String userId = userInfoDomainMapper.getUserIdByAccount(user
					.getUserAccount());
			registerResBo.setCode(UserConstants.CHECK_DATA_LENGTH_TRUE);
			registerResBo.setUserId(userId);
			insertDefaltCanvas(Long.parseLong(userId));
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
		if (null == userId) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public LoginResultBo login(String account, String password) {
		UserInfoDomain userInfoDomain = userInfoDomainMapper
				.getUserInfoByAccount(account);
		boolean isValidResult;
		boolean isAccountPasswordMatchResult;
		LoginResultBo loginResultBo = new LoginResultBo();
		if (null != userInfoDomain) {
			Integer isValid = userInfoDomain.getIsValid();
			if (UserConstants.USER_IS_VALID_YES.equals(isValid.toString())) {
				isValidResult = true; // 用户有效
			} else {
				isValidResult = false; // 用户无效
			}
			String correctPassword = userInfoDomain.getUserPassword();
			if (correctPassword.equals(password)) {
				isAccountPasswordMatchResult = true; // 账号密码匹配
			} else {
				isAccountPasswordMatchResult = false; // 账号密码不匹配
			}
			if (isAccountPasswordMatchResult) {
				if (isValidResult) {
					loginResultBo.setStatus(ResponseVo.STATUS_SUCCESS);
				} else {
					loginResultBo.setStatus(ResponseVo.STATUS_VALUE_FALSE);
				}
			} else {
				loginResultBo.setStatus(ResponseVo.STATUS_ACCOUNT_OR_PASSWORD_FALSE);
			}
			loginResultBo.setId(userInfoDomain.getId().toString());
			loginResultBo.setUserPhotoUrl(userInfoDomain.getUserPhotoUrl());
		} else{
			loginResultBo.setStatus(ResponseVo.STATUS_ACCOUNT_OR_PASSWORD_FALSE);
		}
		return loginResultBo;
	}
	
	private int insertDefaltCanvas(long userId) {
		logger.info("开始注册未分类画板");
		CanvasInfoDomain canvasInfoDomain = new CanvasInfoDomain();
		canvasInfoDomain.setCanvasName(CanvasInfoConstants.CANVAS_DEFALT_NAME);
		canvasInfoDomain.setCanvasType(CanvasInfoConstants.CANVAS_TYPE_PRIVATE);
		canvasInfoDomain.setCreateTime(GetSYSTime.systemTime());
		canvasInfoDomain.setIsValid(CanvasInfoConstants.CANVAS_IS_VALID);
		canvasInfoDomain.setUserId(userId);
		int result = canvasInfoDomainMapper.insert(canvasInfoDomain);
		return result;
	}

}
