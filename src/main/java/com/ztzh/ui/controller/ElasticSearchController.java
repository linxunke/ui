package com.ztzh.ui.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ztzh.ui.bo.MaterialInfoIndex;
import com.ztzh.ui.service.ElasticSearchService;
import com.ztzh.ui.vo.ResponseVo;

@RestController
@RequestMapping(value = "elasticsearch")
public class ElasticSearchController {
	Logger logger = LoggerFactory.getLogger(ElasticSearchController.class);
	
	@Autowired
	ElasticSearchService elasticSearchService;
	
	@RequestMapping(value = "/createMaterialInfoIndex", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public String createMaterialInfoIndex() {
		boolean result = elasticSearchService.createIndex(MaterialInfoIndex.class);
		ResponseVo responseVo = new ResponseVo();
		if(result) {
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVo.setMessage("创建索引成功");
		}else {
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
			responseVo.setMessage("创建索引失败");
		}
		return responseVo.toString();
	}
	

}
