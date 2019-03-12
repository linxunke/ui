package com.ztzh.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@Controller
@RequestMapping("/test2")
public class Test2Controller {
	/*@RequestMapping(value = "upload", method = {RequestMethod.GET,RequestMethod.POST}, produces="application/json;charset=UTF-8")
	public String upload() {
		return "/register";
	}
	*/
	@RequestMapping(value="hello")
	public String hello(){
		return "cutpic";
	}
	
}
