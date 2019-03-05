package com.ztzh.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztzh.ui.service.TestService;

@RestController
public class TestController {
	Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired TestService testService;
	
	@RequestMapping(value="/test")
	public String testInsert() {
		return "test";
	}
	

}
