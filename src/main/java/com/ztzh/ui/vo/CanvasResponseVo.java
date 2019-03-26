package com.ztzh.ui.vo;

import java.util.ArrayList;

import com.ztzh.ui.bo.ManagementCanvasBo;


public class CanvasResponseVo {

	public int materialDownloadCount;
	public ArrayList<ManagementCanvasBo> canvasInfo;

	
	public int getmaterialDownloadCount() {
		return materialDownloadCount;
	}

	public void setmaterialDownloadCount(int materialDownloadCount) {
		this.materialDownloadCount = materialDownloadCount;
	}

	public ArrayList<ManagementCanvasBo> getCanvasInfo() {
		return canvasInfo;
	}

	public void setCanvasInfo(ArrayList<ManagementCanvasBo> canvasInfo) {
		this.canvasInfo = canvasInfo;
	}

	@Override
	public String toString() {
		return "CanvasResponseVo [materialDownloadCount=" + materialDownloadCount
				+ ", canvasInfo=" + canvasInfo + "]";
	}
	
	

}
