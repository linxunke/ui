package com.ztzh.ui.bo;

public class RegisterResultBo {
	public int code;
	public String userId;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "RegisterResultBo [code=" + code + ", userId=" + userId + "]";
	}
	
	
}
