package com.ztzh.ui.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ztzh.ui.po.UserInfoDomain;
import com.ztzh.ui.service.UserService;
import com.ztzh.ui.utils.FileUpload;


@Controller
@RequestMapping("/userpage")
public class UserPageController {
	Logger logger = LoggerFactory.getLogger(UserPageController.class);
	
	@Value("${user.photo.address}")
	private String photoAddress;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "regist", method = {RequestMethod.GET,RequestMethod.POST}, produces="application/json;charset=UTF-8")
	public String upload() {
		return "/register";
	}
	
	
	
}
