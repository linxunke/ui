package com.ztzh.ui.service;

import java.util.List;

import com.ztzh.ui.bo.ManagementCanvasBo;

public interface CanvasInfoService {
	
	public void userDeleteCanvasWithMaterials(Long canvasId, Long userId);
	
	public void userDeleteCanvasWithoutMaterials(Long canvasId, Long userId);
	
	public int canvasCount(Long userId);
	
	List<ManagementCanvasBo> selectCanvasByUserId(Long userId);

	public boolean addCanvas(Long userId, String canvasName, String canvasDesc);
	
	public boolean updateCanvas(Long canvasId, String canvasName , String canvasDesc);
}
