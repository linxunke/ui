package com.ztzh.ui.service;

import java.util.List;

import com.ztzh.ui.bo.ManagementCanvasBo;
import com.ztzh.ui.po.CanvasInfoDomain;
import com.ztzh.ui.utils.PageQueryUtil;

public interface CanvasInfoService {
	
	public boolean userDeleteCanvasWithMaterials(Long canvasId, Long userId);
	
	public boolean userDeleteCanvasWithoutMaterials(Long canvasId, Long userId);
	
	public int canvasCount(Long userId);
	
	List<ManagementCanvasBo> selectCanvasByUserId(Long userId,int currentPage,int pageSize);

	public boolean addCanvas(Long userId, String canvasName, String canvasDesc);
	
	public boolean updateCanvas(Long canvasId, String canvasName , String canvasDesc);
	
	List<ManagementCanvasBo> selectAllCanvasByUserId(Long userId);
	
	public CanvasInfoDomain selectCanvasByCanvasId(Long canvasId);
	
	public boolean existIconInCanvasByCanvasId(Long canvasId);
	
	public PageQueryUtil getMaterialInfoWithCanvasIdByPage(int currentPage,int pageSize,Long canvasId);
}
