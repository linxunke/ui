package com.ztzh.ui.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;
import com.ztzh.ui.constants.UserConstants;
import com.ztzh.ui.po.UserInfoDomain;
import com.ztzh.ui.service.CanvasInfoService;
import com.ztzh.ui.service.ImageConvertService;
import com.ztzh.ui.service.TestService;
import com.ztzh.ui.service.UploadFileService;
import com.ztzh.ui.utils.FTPUtil;
import com.ztzh.ui.utils.ImageMagickUtil;
import com.ztzh.ui.utils.ImageUtil;
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
	
	@Autowired
	ImageMagickUtil imageMagicUtil;
	
	@Autowired
	CanvasInfoService canvasInfoService;
	
	@Value("${material.catch.operation.url}")
	private String catchOperationUrl;
	
	@Autowired
	ImageConvertService imageConvertService;
	
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
		 * List<String> urls = new ArrayList<String>(); imageMagicUtil.convertType(new
		 * String[]{"D:\\house\\ui\\src\\main\\resources\\photo\\红包.ai"},
		 * "D:\\house\\ui\\src\\main\\resources\\photo\\red.png");
		 */
		canvasInfoService.userDeleteCanvasWithMaterials(5L, 1L);
		
		return "";
	}
	
	@GetMapping("/delete")
    public void upload() {
		canvasInfoService.userDeleteCanvasWithMaterials(3L, 3L);
 
    }
	
	@RequestMapping(value="base")
	public String base() throws FTPConnectionClosedException, IOException, Exception{
		/*
		 * File file = new File("C:\\Users\\25002\\Desktop\\photo\\12.jpg"); InputStream
		 * input = new FileInputStream(file); ftpUtil.uploadToFtp(input, "123.jpg",
		 * false, "/12345678902/"+UserConstants.FTP_PNG_DIRECTORY);
		 */
		
	        return "";
	}

}
