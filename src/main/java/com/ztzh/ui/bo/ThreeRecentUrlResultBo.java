package com.ztzh.ui.bo;

public class ThreeRecentUrlResultBo {

	public String userName;
	public String userPhotoUrl;
	public String thumbnailUrl;
	public String materialType;
	public String childCode;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhotoUrl() {
		return userPhotoUrl;
	}
	public void setUserPhotoUrl(String userPhotoUrl) {
		this.userPhotoUrl = userPhotoUrl;
	}
	public String getthumbnailUrl() {
		return thumbnailUrl;
	}
	public void setthumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public String getChildCode() {
		return childCode;
	}
	public void setChildCode(String childCode) {
		this.childCode = childCode;
	}
	@Override
	public String toString() {
		return "ThreeRecentUrlResultBo [userName=" + userName
				+ ", userPhotoUrl=" + userPhotoUrl + ", thumbnailUrl="
				+ thumbnailUrl + ", materialType=" + materialType
				+ ", childCode=" + childCode + "]";
	}
	
	
	
}
