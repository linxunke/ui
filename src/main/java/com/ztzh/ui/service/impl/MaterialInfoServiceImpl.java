package com.ztzh.ui.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ztzh.ui.bo.IconMaterialBo;
import com.ztzh.ui.bo.IconUrlBo;
import com.ztzh.ui.bo.IconUrlResultBo;
import com.ztzh.ui.bo.ThreeRecentUrlResultBo;
import com.ztzh.ui.dao.CanvasInfoDomainMapper;
import com.ztzh.ui.dao.MaterialHistoryCollectionDomainMapper;
import com.ztzh.ui.dao.MaterialInfoDomainMapper;
import com.ztzh.ui.dao.MaterialTypeInfoDomainMapper;
import com.ztzh.ui.po.MaterialInfoDomain;
import com.ztzh.ui.po.MaterialTypeInfoDomain;
import com.ztzh.ui.service.MaterialInfoService;
import com.ztzh.ui.utils.FTPUtil;
import com.ztzh.ui.utils.GetSYSTime;
import com.ztzh.ui.vo.ResponseVo;

@Service
public class MaterialInfoServiceImpl implements MaterialInfoService{
	Logger logger = LoggerFactory.getLogger(MaterialInfoServiceImpl.class);
	@Autowired
	MaterialInfoDomainMapper materialInfoDomainMapper;
	
	@Autowired
	CanvasInfoDomainMapper canvasInfoDomainMapper;
	
	@Autowired
	MaterialTypeInfoDomainMapper materialTypeInfoDomainMapper;
	
	@Autowired
	FTPUtil ftpUtil;
	
	@Autowired
	MaterialHistoryCollectionDomainMapper materialHistoryCollectionDomainMapper;
	
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

	@Override
	public List<MaterialInfoDomain> getMaterialListByCanvasId(Long canvasId) {
		return materialInfoDomainMapper.selectByCanvasId(canvasId);
	}

	@Override
	public int getMaterialNumOfCanvasByCanvasId(long canvasId) {
		return materialInfoDomainMapper.getMaterialNumOfCanvasByCanvasId(canvasId);
	}
	
	@Transactional
	public ResponseVo updateMaterialsInfo(Long materialInfoId, String imageLabel, Long canvasId, String imageName, String typeArray) throws Exception {
		logger.info("获取源文件基本信息");
		MaterialInfoDomain materialInfo = materialInfoDomainMapper.selectByPrimaryKey(materialInfoId);
		logger.info("删除源文件信息");
		int count = materialInfoDomainMapper.deleteByPrimaryKey(materialInfoId);
		if(count>1) {
			throw new Exception("删除源文件数据失败");
		}
		materialInfo.setMaterialDescription(imageLabel);
		materialInfo.setCanvasInfoIdPrivate(canvasId);
		materialInfo.setCanvasInfoIdPublic(canvasId);
		materialInfo.setMaterialName(imageName);
		materialInfo.setUploadTime(new Date());
		materialInfoDomainMapper.insert(materialInfo);
		logger.info("删除所属类型信息");
		materialTypeInfoDomainMapper.deleteByMaterialInfoId(materialInfoId);
		JSONArray typesJsonList = JSONArray.parseArray(typeArray);
		List<MaterialTypeInfoDomain> typesList = new ArrayList<MaterialTypeInfoDomain>();
		for (int i = 0; i < typesJsonList.size(); i++) {
			JSONArray typeJson = (JSONArray) typesJsonList.get(i);
			Map<String, String> typeMap = new HashMap<String, String>(); // map用来存放类别、细分、风格信息
			typeMap.put("materialTypeCodeParent", typeJson.get(0).toString());
			typeMap.put("materialTypeCodeChild", typeJson.get(1).toString());
			typeMap.put("materialStyleCode", typeJson.get(2).toString());
			String json = JSONObject.toJSONString(typeMap);
			MaterialTypeInfoDomain materialTypeInfoDomain = JSONObject.parseObject(json, MaterialTypeInfoDomain.class);
			materialTypeInfoDomain.setMaterailInfoId(materialInfoId);
			materialTypeInfoDomain.setCreateTime(new Date());
			typesList.add(materialTypeInfoDomain);
		}
		int materialTypeCount = 0;
		try {
			materialTypeCount = materialTypeInfoDomainMapper.insertBybatch(typesList);
		}catch(Exception e) {
			e.printStackTrace();
			throw new Exception("删除源文件数据失败");
		}
				
		logger.info("成功插入{}条源文件类型信息",materialTypeCount);
		ResponseVo responseVo = new ResponseVo();
		responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
		responseVo.setMessage("更新文件信息成功");
		return responseVo;
	}

	@Override
	@Transactional
	public ResponseVo deleteMaterialsById(String materialIdsJson) {
		ResponseVo responseVo = new ResponseVo();
		logger.info("准备删除源文件信息");
		List<Long> materialIds = new ArrayList<Long>();
		JSONArray materialIdsJsonList = JSONArray.parseArray(materialIdsJson);
		for (int i = 0; i < materialIdsJsonList.size(); i++) {
			String typeJson = materialIdsJsonList.get(i).toString();
			MaterialTypeInfoDomain materialTypeInfoDomain = JSONObject.parseObject(typeJson, MaterialTypeInfoDomain.class);
			materialIds.add(materialTypeInfoDomain.getId());
		}
		logger.info("删除有关该文件所有磁盘中的文件");
		logger.info("获取所有磁盘地址");
		List<MaterialInfoDomain> materialInfoDomainList = materialInfoDomainMapper.queryByIds(materialIds);
		List<String> materialUrlList = new ArrayList<String>();
		List<String> thumbnailUrlList = new ArrayList<String>();
		List<String> pngUrlList = new ArrayList<String>();
		for(MaterialInfoDomain materialInfoDomain:materialInfoDomainList) {
			materialUrlList.add(materialInfoDomain.getMaterialUrl());
			thumbnailUrlList.add(materialInfoDomain.getThumbnailUrl());
			pngUrlList.add(materialInfoDomain.getPngUrl());
		}
		deleteMaterialsInFTP(materialUrlList, thumbnailUrlList, pngUrlList);
		logger.info("删除完ftp中有关文件");
		int countMaterials = materialInfoDomainMapper.deleteByIds(materialIds);
		int countMaterialTypes = materialTypeInfoDomainMapper.deleteByMaterialInfoIds(materialIds);
		int countHistory = materialHistoryCollectionDomainMapper.deleteByMaterialInfoIds(materialIds);
		logger.info("成功删除{}个文件信息,{}条文件类型信息,{}条关注信息",countMaterials,countMaterialTypes,countHistory);
		responseVo.setMessage("批量删除文件成功");
		responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
		return responseVo;
	}	
	private void deleteMaterialsInFTP(List<String> materialUrlList, List<String> thumbnailUrlList, List<String> pngUrlList) {
		//有可能删除失败，后面需要一张事务表管理未删除掉的ftp文件
		ftpUtil.deleteFtpFile(materialUrlList);
		ftpUtil.deleteFtpFile(thumbnailUrlList);
		ftpUtil.deleteFtpFile(pngUrlList);
	}

}
