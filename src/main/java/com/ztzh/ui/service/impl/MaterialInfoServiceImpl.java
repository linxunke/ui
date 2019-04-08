package com.ztzh.ui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztzh.ui.bo.IconMaterialBo;
import com.ztzh.ui.bo.IconUrlBo;
import com.ztzh.ui.bo.IconUrlResultBo;
import com.ztzh.ui.bo.ThreeRecentUrlResultBo;
import com.ztzh.ui.dao.CanvasInfoDomainMapper;
import com.ztzh.ui.dao.MaterialInfoDomainMapper;
import com.ztzh.ui.service.MaterialInfoService;

@Service
public class MaterialInfoServiceImpl implements MaterialInfoService{
	Logger logger = LoggerFactory.getLogger(MaterialInfoServiceImpl.class);
	@Autowired
	MaterialInfoDomainMapper materialInfoDomainMapper;
	
	@Autowired
	CanvasInfoDomainMapper canvasInfoDomainMapper;
	
	@Override
	public List<IconUrlResultBo> getDisplayUrlByThumbnailUrl(List<String> thumbnailUrls) {
	    List<IconMaterialBo> iconMaterialBos = materialInfoDomainMapper.getIconMaterialBoByThumbnailUrls(thumbnailUrls);
	    List<String> materialInfoIdList = new ArrayList<String>();
	    for(IconMaterialBo iconMaterialBo:iconMaterialBos) {
	    	materialInfoIdList.add(iconMaterialBo.getMaterialInfoId());
	    }
	    List<IconUrlBo> iconUrlBoList = canvasInfoDomainMapper.getMaterialInfosOfCanvasByMaterialInfoId(materialInfoIdList);
	    List<IconUrlResultBo> iconUrlResultBoList = new ArrayList<IconUrlResultBo>();
	    for(int i=0;i<iconUrlBoList.size();i++) {
	    	IconUrlResultBo iconUrlResultBo = new IconUrlResultBo();
	    	String canvasId = iconUrlBoList.get(i).getCanvasId();
	    	List<String> iconUrls = materialInfoDomainMapper.getIconUrlsByCanvasId(canvasId);
	    	iconUrlResultBo.setCanvasId(canvasId);
	    	iconUrlResultBo.setPngUrls(iconUrls);
	    	iconUrlResultBoList.add(iconUrlResultBo);
	    }
		return iconUrlResultBoList;
	}
	
	public List<ThreeRecentUrlResultBo> getThreeRecentMaterial(){
		String materialType;
		String childType;
		List<ThreeRecentUrlResultBo> list = materialInfoDomainMapper.getThreeRecentMaterial();
		for(int i=0; i<3; i++){
			childType = materialInfoDomainMapper.selectTypeNameByChildCode(list.get(i).getChildCode());
			materialType= list.get(i).getMaterialType();
			list.get(i).setMaterialType(materialType+"—"+childType);
		}
		return list;
	}
	public int selectIconCount(){
		return materialInfoDomainMapper.selectIconCount();
		
	}
	public int selectDrawingCount(){
		return materialInfoDomainMapper.selectDrawingCount();
		
	}
	public List<String> selectTypeNameForBox(){
		return  materialInfoDomainMapper.selectTypeNameForBox();
	}
}
