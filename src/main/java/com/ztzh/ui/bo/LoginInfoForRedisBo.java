package com.ztzh.ui.bo;

import java.io.Serializable;
import java.util.Date;

public class LoginInfoForRedisBo implements Serializable{
	private static final long serialVersionUID = 158803863744258933L;
	private Long userId;
	private String ipAddress;
	private Date loginTime;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	@Override
	public String toString() {
		return "LoginInfoForRedis [userId=" + userId + ", ipAddress=" + ipAddress + ", loginTime=" + loginTime + "]";
	}
	

}
