package com.ztzh.ui.vo;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ztzh.ui.bo.ManagementCanvasBo;

public class CanvasResponseVo {

	public int canvasCount;
	public List<ManagementCanvasBo> canvasInfo;

	public int getCanvasCount() {
		return canvasCount;
	}

	public void setCanvasCount(int canvasCount) {
		this.canvasCount = canvasCount;
	}

	public List<ManagementCanvasBo> getCanvasInfo() {
		return canvasInfo;
	}

	public void setCanvasInfo(List<ManagementCanvasBo> canvasInfo) {
		this.canvasInfo = canvasInfo;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
