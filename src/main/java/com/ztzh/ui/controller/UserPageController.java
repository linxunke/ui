package com.ztzh.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ztzh.ui.service.UserService;



@Controller
@RequestMapping("/userpage")
public class UserPageController {
	Logger logger = LoggerFactory.getLogger(UserPageController.class);
	
	@Value("${user.photo.address}")
	private String photoAddress;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "toRegist", method = {RequestMethod.GET,RequestMethod.POST}, produces="application/json;charset=UTF-8")
	public String toRegist() {
		return "/register";
	}
	//下面的是测试用，随意更改
	@RequestMapping(value="toLogin",method = {RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	public String toLogin(){
		return "/login";
	}
	
}
