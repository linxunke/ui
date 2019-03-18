package com.ztzh.ui.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPConnectionClosedException;
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
import com.ztzh.ui.service.UploadFileService;
import com.ztzh.ui.utils.FTPUtil;
import com.ztzh.ui.utils.MD5Util;

@RestController
public class TestController {
	Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired 
	TestService testService;
	
	@Autowired
	FTPUtil ftpUtil;
	
	@Autowired
	UploadFileService uploadFileService;
	
	
	@RequestMapping(value="/test")
	public String testInsert() {
		return "test";
	}

	@PostMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile  file) {
         testService.upload(file);
         return "success";
 
    }
	
	@RequestMapping(value="ftptest")
	public String hello(HttpServletRequest request, HttpServletResponse response) throws FTPConnectionClosedException, IOException, Exception{
		/*
		 * File file = new File("C:\\Users\\25002\\Desktop\\photo\\12.jpg"); InputStream
		 * inputStream = new FileInputStream(file); ftpUtil.uploadToFtp(inputStream,
		 * UUID.randomUUID().toString()+".jpg", false);
		 */
		uploadFileService.createFTPMaterialDirectoryByAccount("lxk");
		return "success";
	}

}
