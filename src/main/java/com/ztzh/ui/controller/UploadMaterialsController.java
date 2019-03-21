package com.ztzh.ui.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ztzh.ui.bo.UploadMaterialsBo;
import com.ztzh.ui.po.MaterialTypeDomain;
import com.ztzh.ui.service.UploadMaterialsService;
import com.ztzh.ui.utils.FileUpload;
import com.ztzh.ui.vo.ResponseVo;

@RestController
@RequestMapping("/uploadMaterial")
public class UploadMaterialsController {
	@Autowired
	UploadMaterialsService uploadMaterialsService;
	
	/*获取素材分类信息*/
	@RequestMapping("/getMaterialTypes")
	public String getMaterialTypes(){
		UploadMaterialsBo uploadMaterialsBo = uploadMaterialsService.getMaterialTypes();
		Map<String, List<MaterialTypeDomain>> types = new HashMap<String, List<MaterialTypeDomain>>();
		types.put("materialTypes", uploadMaterialsBo.getMaterialTypes());
		types.put("materialSegmentations", uploadMaterialsBo.getMaterialSegmentations());
		types.put("materialStyles", uploadMaterialsBo.getMaterialStyles());
		ResponseVo responseVo = new ResponseVo();
		responseVo.setObject(types);
		responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
		responseVo.setMessage("请求成功！");
		return responseVo.toString();
	}
	@RequestMapping("/getMaterialFiles")
	public String getMaterialFiles(@RequestParam(value="file")MultipartFile file){
		FileUpload.writeUploadFile(file, "D://");
	 	
		return "success";
	}
}
