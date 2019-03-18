package com.ztzh.ui.bo;

public class LoginResultBo {
	public String status;
	public String id;
	public String userPhotoUrl;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserPhotoUrl() {
		return userPhotoUrl;
	}

	public void setUserPhotoUrl(String userPhotoUrl) {
		this.userPhotoUrl = userPhotoUrl;
	}

	@Override
	public String toString() {
		return "LoginResultBo [status=" + status + ", id=" + id
				+ ", userPhotoUrl=" + userPhotoUrl + "]";
	}

}
