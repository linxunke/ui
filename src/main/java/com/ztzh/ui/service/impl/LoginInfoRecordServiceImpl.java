package com.ztzh.ui.service.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ztzh.ui.bo.LoginInfoForRedisBo;
import com.ztzh.ui.controller.UserController;
import com.ztzh.ui.service.LoginInfoRecordService;

@Service
public class LoginInfoRecordServiceImpl implements LoginInfoRecordService{
	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private RedisTemplate<String, Object>  redisTemplate;

	@Override
	public void set(String key, LoginInfoForRedisBo loginInfo) {
		redisTemplate.opsForValue().set(key, loginInfo, 1440*60, TimeUnit.SECONDS);
	}

	@Override
	public LoginInfoForRedisBo get(String key) {
		return (LoginInfoForRedisBo)redisTemplate.boundValueOps(key).get();
	}
	@Override
	public void loginOut(String key) {
		logger.info("进入退出登录方法2:{}",key);
		boolean a = redisTemplate.hasKey(key);
		logger.info("返回值:{}",a);
		redisTemplate.delete(key);
		boolean b = redisTemplate.hasKey(key);
		logger.info("返回值:{}",b);
	}
}
