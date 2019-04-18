package com.ztzh.ui.service;

import com.ztzh.ui.bo.LoginInfoForRedisBo;

public interface LoginInfoRecordService {
	public void set(String key, LoginInfoForRedisBo loginInfo);
	
	public LoginInfoForRedisBo get(String key);
	
	
}
