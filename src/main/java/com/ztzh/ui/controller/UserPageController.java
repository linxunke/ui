package com.ztzh.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

	@RequestMapping(value="head",method = {RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	public String head(){
		return "/head";
	}
	@RequestMapping(value="work_manage",method = {RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	public String manage(){
		return "/work_management";
	}
	//下面的是测试用，随意更改
	@RequestMapping(value="test",method = {RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	public String test(){
		return "/test";
	}
	@RequestMapping(value="toLogin",method = {RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	public String toLogin(){
		return "/login";

	}
	@RequestMapping(value="toMaterialUpload")
	public String toMaterialUpload(){
		return "materialUpload";
	}

	@RequestMapping(value="toSearchIndex")
	public String toSearchIndex(){
		return "/search_index";
	}

	
	@RequestMapping(value="toMaterialManage")
	public String toMaterialManage(){
		return "materialManage";

	}
	
	@RequestMapping(value="toMateiralLibrary")
	public String toMaterialLibrary(){
		return "materialLibrary";
	}
}
