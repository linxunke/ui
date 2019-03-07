package com.ztzh.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztzh.ui.constants.UserConstants;
import com.ztzh.ui.po.UserInfoDomain;
import com.ztzh.ui.service.UserService;


@RestController
public class UserController {
	Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/register")
	public int register(HttpServletRequest request){
		UserInfoDomain user = new UserInfoDomain();
		user.setUserAccount(request.getParameter("account"));
		user.setUserNickname(request.getParameter("account"));
		user.setUserWeixin(request.getParameter("account"));
		user.setUserPassword(request.getParameter("account"));
		user.setUserPhotoUrl("");
		int result = userService.register(user);
		if(result == 0){
			//跳转页面
			return 0;
		}else{
			return result;
		}
	}
}
