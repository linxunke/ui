package com.ztzh.ui.po;

import java.io.Serializable;
import java.util.Date;

public class UserInfoDomain implements Serializable{

	private static final long serialVersionUID = -3673944971541311289L;

	private Long id;

    private String userAccount;

    private String userNickname;

    private String userWeixin;

    private String userPhotoUrl;

    private String userPassword;

    private Integer authority;

    private Date registerTime;

    private Date updateTime;

    private Integer isValid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount == null ? null : userAccount.trim();
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname == null ? null : userNickname.trim();
    }

    public String getUserWeixin() {
        return userWeixin;
    }

    public void setUserWeixin(String userWeixin) {
        this.userWeixin = userWeixin == null ? null : userWeixin.trim();
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl == null ? null : userPhotoUrl.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

	@Override
	public String toString() {
		return "UserInfoDomain [id=" + id + ", userAccount=" + userAccount
				+ ", userNickname=" + userNickname + ", userWeixin="
				+ userWeixin + ", userPhotoUrl=" + userPhotoUrl
				+ ", userPassword=" + userPassword + ", authority=" + authority
				+ ", registerTime=" + registerTime + ", updateTime="
				+ updateTime + ", isValid=" + isValid + "]";
	}
    
}