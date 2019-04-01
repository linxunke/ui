package com.ztzh.ui.bo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ManagementCanvasBo {

	public Long canvasId;
	public String canvasName;
	public String canvasDesc;

	public int materialCount;
	public Date lastMaterialUploadTime;	
	public String lastMaterialUrl;
	public String lastMaterialUploadTimeFormate;

	public String getLastMaterialUploadTimeFormate() {
		if(this.lastMaterialUploadTime!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(this.lastMaterialUploadTime);
		}else {
			return "";
		}
		
	}
	public Long getCanvasId() {
		return canvasId;
	}

	public void setCanvasId(Long canvasId) {
		this.canvasId = canvasId;
	}

	public String getCanvasName() {
		return canvasName;
	}

	public void setCanvasName(String canvasName) {
		this.canvasName = canvasName;
	}

	public String getCanvasDesc() {
		return canvasDesc;
	}

	public void setCanvasDesc(String canvasDesc) {
		this.canvasDesc = canvasDesc;
	}

	public int getMaterialCount() {
		return materialCount;
	}

	public void setMaterialCount(int materialCount) {
		this.materialCount = materialCount;
	}

	public Date getLastMaterialUploadTime() {
		return lastMaterialUploadTime;
	}

	public void setLastMaterialUploadTime(Date lastMaterialUploadTime) {
		this.lastMaterialUploadTime = lastMaterialUploadTime;
	}

	public String getLastMaterialUrl() {
		return lastMaterialUrl;
	}

	public void setLastMaterialUrl(String lastMaterialUrl) {
		this.lastMaterialUrl = lastMaterialUrl;
	}

	@Override
	public String toString() {
		return "ManagementCanvasBo [canvasId=" + canvasId + ", canvasName="
				+ canvasName + ", canvasDesc=" + canvasDesc
				+ ", materialCount=" + materialCount
				+ ", lastMaterialUploadTime=" + lastMaterialUploadTime
				+ ", lastMaterialUrl=" + lastMaterialUrl + "]";
	}

}
