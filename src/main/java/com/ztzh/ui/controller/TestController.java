package com.ztzh.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ztzh.ui.constants.UserConstants;
import com.ztzh.ui.po.UserInfoDomain;
import com.ztzh.ui.service.TestService;
import com.ztzh.ui.utils.MD5Util;

//@RestController
public class TestController {
	Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired TestService testService;
	
	
	@RequestMapping(value="/test")
	public String testInsert() {
		return "test";
	}

	@PostMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile  file) {
         testService.upload(file);
         return "success";
 
    }

}
