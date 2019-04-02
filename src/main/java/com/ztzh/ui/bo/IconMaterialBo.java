package com.ztzh.ui.bo;


public class IconMaterialBo {
	
	private String materialInfoId;
	
	private String thumbnailUrl;

	public String getMaterialInfoId() {
		return materialInfoId;
	}

	public void setMaterialInfoId(String materialInfoId) {
		this.materialInfoId = materialInfoId;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	@Override
	public String toString() {
		return "IconMaterialBo [materialInfoId=" + materialInfoId + ", thumbnailUrl=" + thumbnailUrl + "]";
	}
	

}
