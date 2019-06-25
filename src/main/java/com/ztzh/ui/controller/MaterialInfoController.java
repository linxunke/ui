package com.ztzh.ui.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ztzh.ui.bo.MaterialAndTypeInfoBo;
import com.ztzh.ui.constants.MaterialTypeConstants;
import com.ztzh.ui.dao.MaterialHistoryCollectionDomainMapper;
import com.ztzh.ui.dao.MaterialTypeDomainMapper;
import com.ztzh.ui.po.MaterialHistoryCollectionDomain;
import com.ztzh.ui.po.MaterialInfoDomain;
import com.ztzh.ui.service.MaterialInfoService;
import com.ztzh.ui.vo.ResponseVo;

@RestController
@RequestMapping("/materialInfo")
public class MaterialInfoController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	MaterialInfoService materialInfoService;
	
	@Autowired
	MaterialHistoryCollectionDomainMapper materialHistoryCollectionDomainMapper;
	
	@Autowired
	MaterialTypeDomainMapper materialTypeDomainMapper;
	
	@RequestMapping(value = "/getMaterialDetailInfo", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String getMaterialDetailInfoByMaterialId(
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "materialId", required = true) String materialId){
		ResponseVo responseVo = new ResponseVo();
		/*调用service的接口*/
		MaterialAndTypeInfoBo resultBo = materialInfoService.getMaterialDetailInfoById(new Long(materialId));
		if(resultBo != null){
			responseVo.setObject(resultBo);
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVo.setMessage("获取当前素材的详细信息成功");
		}else {
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
			responseVo.setMessage("获取图片详细信息失败");
		}
		responseVo.setUserId(userId);
		return responseVo.toString();
	}
	
	@RequestMapping(value = "/downloadImage", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String downloadImage(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "materialId", required = true) String materialId,
			@RequestParam(value = "imageType", required = true) String imageType,
			@RequestParam(value = "isIcon", required = true) boolean isIcon,
			@RequestParam(value = "iconSize", required = false) Integer iconSize){
		ResponseVo responseVo = new ResponseVo();
		responseVo.setUserId(userId);
		MaterialInfoDomain materialInfoDomain = materialInfoService.getMaterialInfoById(new Long(materialId));
		if(materialInfoDomain != null){
			logger.info("下载记录");
			MaterialHistoryCollectionDomain materialHistoryCollectionDomain = new MaterialHistoryCollectionDomain();
			materialHistoryCollectionDomain.setMaterialInfoId(Long.parseLong(materialId));
			materialHistoryCollectionDomain.setOperateTime(new Date());
			materialHistoryCollectionDomain.setType(MaterialHistoryCollectionDomain.OPERATION_TYPE_HISTORY);
			materialHistoryCollectionDomain.setUserInfoId(Long.parseLong(userId));
			materialHistoryCollectionDomainMapper.insert(materialHistoryCollectionDomain);
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVo.setMessage("获取文件的路径成功");
			Map<String,String> imageMap  = materialInfoService.getImageUrlAndName(materialInfoDomain, imageType, isIcon, iconSize);
			logger.info("下载文件具体信息:{}",materialInfoDomain.toString());
			responseVo.setObject(imageMap);
		}else {
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
			responseVo.setMessage("获取文件的路径失败！");
		}
		return responseVo.toString();
	}
	
	/*获得图标分类的typeCode*/
	@RequestMapping(value = "/getParentTypeCodeOfIcon", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String getParentTypeCodeOfIcon(
			@RequestParam(value = "userId", required = true) String userId){
		ResponseVo responseVo = new ResponseVo();
		responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
		responseVo.setMessage("获取图标的typecode成功");
		responseVo.setObject(MaterialTypeConstants.MATERIAL_TYPE_ICON_PARENT_TYPE);
		responseVo.setUserId(userId);
		return responseVo.toString();
	}
}
