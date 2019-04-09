package com.ztzh.ui.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ztzh.ui.vo.ResponseVo;

@RestController
@RequestMapping("/materialInfo")
public class MaterialInfoController {
	
	@RequestMapping(value = "/getMaterialDetailInfo", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String getMaterialDetailInfoByMaterialId(
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "materialId", required = true) String materialId){
		ResponseVo responseVo = new ResponseVo();
		/*调用service的接口*/
		responseVo.setUserId(userId);
		return responseVo.toString();
	}
}
