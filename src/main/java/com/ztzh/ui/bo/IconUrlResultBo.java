package com.ztzh.ui.bo;

import java.util.List;

public class IconUrlResultBo {
	private String canvasId;
	private List<String> pngUrls;
	public String getCanvasId() {
		return canvasId;
	}
	public void setCanvasId(String canvasId) {
		this.canvasId = canvasId;
	}
	public List<String> getPngUrls() {
		return pngUrls;
	}
	public void setPngUrls(List<String> pngUrls) {
		this.pngUrls = pngUrls;
	}
	@Override
	public String toString() {
		return "IconUrlResultBo [canvasId=" + canvasId + ", pngUrls=" + pngUrls + "]";
	}
	

}
