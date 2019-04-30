package com.ztzh.ui.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ztzh.ui.bo.LoginInfoForRedisBo;
import com.ztzh.ui.service.LoginInfoRecordService;

@Service
public class LoginInfoRecordServiceImpl implements LoginInfoRecordService{
	@Autowired
	private RedisTemplate<String, Object>  redisTemplate;

	@Override
	public void set(String key, LoginInfoForRedisBo loginInfo) {
		redisTemplate.opsForValue().set(key, loginInfo, 60*60, TimeUnit.SECONDS);
	}

	@Override
	public LoginInfoForRedisBo get(String key) {
		return (LoginInfoForRedisBo)redisTemplate.boundValueOps(key).get();
	}
}
