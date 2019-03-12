package com.ztzh.ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ztzh.ui.po.UserInfoDomain;
import com.ztzh.ui.service.UserService;


@Controller
@RequestMapping("/test2")
public class UserController {
	Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "upload", method = {RequestMethod.GET,RequestMethod.POST}, produces="application/json;charset=UTF-8")
	public String upload() {
		return "/usertest";
	}
	
	@RequestMapping(value="register",method = {RequestMethod.GET,RequestMethod.POST})
	public Object register(HttpServletRequest request,HttpServletResponse response){
		UserInfoDomain user = new UserInfoDomain();
		user.setId(1111111L);
		user.setUserAccount(request.getParameter("account"));
		user.setUserNickname(request.getParameter("nike_name"));
		user.setUserWeixin(request.getParameter("wei_xin"));
		//处理密码加密
		String password = userService.encrypt(request.getParameter("password"));
		user.setUserPassword(password);
		
		user.setUserPhotoUrl("");
		
		int result = userService.register(user);
		if(result == 0){
			//跳转页面
			System.out.println("成功创建用户");
			return 0;
		}else{
			return result;
		}
	}
}
