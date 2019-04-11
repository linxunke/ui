package com.ztzh.ui.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import com.ztzh.ui.po.MaterialInfoDomain;
import com.ztzh.ui.service.MaterialInfoService;
import com.ztzh.ui.vo.ResponseVo;

@RestController
@RequestMapping("/materialInfo")
public class MaterialInfoController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	MaterialInfoService materialInfoService;
	
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
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVo.setMessage("获取文件的路径成功");
			Map<String,String> imageMap  = materialInfoService.getImageUrlAndName(materialInfoDomain, imageType, isIcon, iconSize);
			responseVo.setObject(imageMap);
		}else {
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
			responseVo.setMessage("获取文件的路径失败！");
		}
		return responseVo.toString();
	}
}
