package com.ztzh.ui.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ztzh.ui.po.UserInfoDomain;
import com.ztzh.ui.service.TestService;

@Controller
public class TestController {
	Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired TestService testService;
	
	@RequestMapping(value="db/insert")
	public void testInsert() {
		UserInfoDomain userInfoDomain = new UserInfoDomain();
		userInfoDomain.setAddress("test_address");
		userInfoDomain.setUsername("test_name");
		userInfoDomain.setAge(26);
		testService.insert(userInfoDomain);
		logger.info("写入user_info成功："+userInfoDomain.toString());
	}
	
	@RequestMapping("list")
	public String getAllUserInfo(Model model) {
		List<UserInfoDomain> userInfoList = testService.selectAll();
		model.addAttribute("users", userInfoList);
		return "usertest";
		
	}

}
