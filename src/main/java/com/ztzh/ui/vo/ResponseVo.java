package com.ztzh.ui.vo;

import com.alibaba.fastjson.JSON;




public class ResponseVo {
	/**
	 * 成功code
	 */
	public static String STATUS_SUCCESS = "200";
	/**
	 * 失败code
	 */
	public static String STATUS_FAILED = "500";
	public static String STATUS_VALUE_FALSE = "01";
	public static String STATUS_ACCOUNT_OR_PASSWORD_FALSE = "02";
	/**
	 * 创建用户私人文件夹不成功
	 */
	public static String FAILED_CREATED_FTP_DIRECTORY = "03";
	
	public String status;
	public String message;
	public String userId;
	public Object object;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
