package com.ztzh.ui.service;

import java.util.ArrayList;

import com.ztzh.ui.bo.ManagementCanvasBo;

public interface ManagementService {

	//拿到 ->当前用户画板数量       每个画板：下载次数，图片数量，画板名称，画板描述，最近更新日期,最近更新的图片的url，画板id
	public int canvasCount(Long userId);
	public Object searchCanvas(Long userId, String canvasName);
	//拿到 ->每个画板：*下载次数*，图片数量，画板名称，画板描述，最近更新日期,最近更新的图片的url
	public ArrayList<ManagementCanvasBo> searchCanvasInfoByUserId(Long userId);
	
	public boolean addCanvas(Long userId, String canvasName, String canvasDesc);
	//更新画板信息，名字和描述
	public boolean updateCanvas(Long canvasId, String canvasName , String canvasDesc);
	public boolean deleteToUnsort(Long userId, Long canvasId);
	public boolean deleteAll(Long userId, Long canvasId);
	
}
