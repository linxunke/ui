package com.ztzh.ui.bo;

public class IconUrlBo {
	private String materialInfoId;
	private String canvasId;
	private String pngUrl;
	public String getMaterialInfoId() {
		return materialInfoId;
	}
	public void setMaterialInfoId(String materialInfoId) {
		this.materialInfoId = materialInfoId;
	}
	public String getCanvasId() {
		return canvasId;
	}
	public void setCanvasId(String canvasId) {
		this.canvasId = canvasId;
	}
	public String getPngUrl() {
		return pngUrl;
	}
	public void setPngUrl(String pngUrl) {
		this.pngUrl = pngUrl;
	}
	@Override
	public String toString() {
		return "IconUrlBo [materialInfoId=" + materialInfoId + ", canvasId=" + canvasId + ", pngUrl=" + pngUrl + "]";
	}
	

}
