package com.ztzh.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztzh.ui.constants.UserConstants;
import com.ztzh.ui.po.UserInfoDomain;
import com.ztzh.ui.service.TestService;

@RestController
public class TestController {
	Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired TestService testService;
	
	@RequestMapping(value="/test")
	public String testInsert() {
		return "test";
	}
	@RequestMapping(value="/register")
	public UserInfoDomain register(HttpServletRequest request){
		UserInfoDomain user = new UserInfoDomain();
		user.setUserAccount(request.getParameter("account"));
		user.setUserNickname(request.getParameter("account"));
		user.setUserWeixin(request.getParameter("account"));
		user.setUserPassword(request.getParameter("account"));
		user.setAuthority(new Integer(UserConstants.USER_AUTHORITY_NORMAL));
		user.setUserPhotoUrl("");
		return user;
	}

}
