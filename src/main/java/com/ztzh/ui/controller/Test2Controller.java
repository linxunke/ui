package com.ztzh.ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/test2")
public class Test2Controller {
	
	@RequestMapping(value="hello")
	public String hello(HttpServletRequest request, HttpServletResponse response){
		return "Login";
	}
	
	@RequestMapping(value="register")
	public String hello2(HttpServletRequest request, HttpServletResponse response){
		return "/cutpic";
	}
	@RequestMapping(value="toMaterialUpload")
	public String toMaterialUpload(){
		return "materialUpload";
	}
}