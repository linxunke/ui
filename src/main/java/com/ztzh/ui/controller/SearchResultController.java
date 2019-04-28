package com.ztzh.ui.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ztzh.ui.bo.MaterialTypeBo;
import com.ztzh.ui.service.SearchResultService;
import com.ztzh.ui.vo.ResponseVo;

@RestController
@RequestMapping(value = "SearchResult")
public class SearchResultController {

	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	SearchResultService searchResultService;
	@RequestMapping(value = "SearchTypeAndChildType", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Object SearchTypeAndChildType(){
		List<MaterialTypeBo> list = searchResultService.selectTypeName();
		ResponseVo responseVo = new ResponseVo();
		if(list == null){
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
			responseVo.setMessage("载入图标信息时出现错误");
		}else{
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVo.setMessage("载入图标信息成功");
			responseVo.setObject(list);
		}
		logger.info("文件内容为：{}", responseVo);
		return responseVo.toString();
	}
}
