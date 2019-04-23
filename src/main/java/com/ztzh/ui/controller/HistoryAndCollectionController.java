package com.ztzh.ui.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ztzh.ui.po.MaterialHistoryCollectionDomain;
import com.ztzh.ui.service.MaterialHistoryCollectionService;
import com.ztzh.ui.vo.ResponseVo;


@RestController
@RequestMapping(value = "historyandcollection")
public class HistoryAndCollectionController {
	Logger logger = LoggerFactory.getLogger(HistoryAndCollectionController.class);
	
	@Autowired
	MaterialHistoryCollectionService materialHistoryCollectionService;
	
	@RequestMapping(value="collectOperate", method = {RequestMethod.GET,
			RequestMethod.POST})
	public String collectOperate(@RequestParam(value="userId") Long userId,
			@RequestParam(value="materialInfoId") Long materialInfoId,
			@RequestParam(value="operationCode") String operationCode,
			@RequestParam(value="type") Integer type
			) {
		logger.info("收藏操作条件是：{}", userId+"+"+materialInfoId+"+"+operationCode+"+"+type);
		MaterialHistoryCollectionDomain materialHistoryCollectionDomain = new MaterialHistoryCollectionDomain();
		materialHistoryCollectionDomain.setMaterialInfoId(materialInfoId);
		materialHistoryCollectionDomain.setOperateTime(new Date());
		materialHistoryCollectionDomain.setType(type);
		materialHistoryCollectionDomain.setUserInfoId(userId);
		boolean isOperated = false;
		try {
			isOperated = materialHistoryCollectionService.collectMaterial(materialHistoryCollectionDomain, operationCode);
		} catch (Exception e) {	
			e.printStackTrace();
		}
		ResponseVo responseVo = new ResponseVo();
		responseVo.setUserId(userId.toString());
		if(isOperated) {
			responseVo.setMessage("操作成功");
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
		}else {
			responseVo.setMessage("操作失败");
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
		}
		return responseVo.toString();
	}
	
}
