package com.ztzh.ui.service;

import java.util.List;
import java.util.Map;

import com.ztzh.ui.bo.IconUrlResultBo;
import com.ztzh.ui.bo.MaterialAndTypeInfoBo;
import com.ztzh.ui.po.MaterialInfoDomain;
import com.ztzh.ui.vo.ResponseVo;

public interface MaterialInfoService {
	
	List<IconUrlResultBo> getDisplayUrlByThumbnailUrl(List<String> thumbnailUrls);
	
	List<MaterialInfoDomain> getMaterialListByCanvasId(Long canvasId);
	
	int getMaterialNumOfCanvasByCanvasId(long canvasId);
	
	ResponseVo updateMaterialsInfo(Long materialInfoId, String imageLabel, Long canvasId, String imageName, String typeArray) throws Exception;
	
	ResponseVo deleteMaterialsById(String materialIdsJson);
	
	MaterialAndTypeInfoBo getMaterialDetailInfoById(Long materialId);
	
	MaterialInfoDomain getMaterialInfoById(Long materialId);
	
	Map<String,String> getImageUrlAndName(MaterialInfoDomain materialInfoDomain, String imageType, boolean isIcon, Integer iconSize);
}
