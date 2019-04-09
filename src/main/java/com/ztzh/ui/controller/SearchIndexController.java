package com.ztzh.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ztzh.ui.bo.ThreeRecentUrlResultBo;
import com.ztzh.ui.service.MaterialInfoService;
import com.ztzh.ui.vo.ResponseVo;

@RestController
@RequestMapping(value = "SearchIndex")
public class SearchIndexController {

	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	MaterialInfoService materialInfoService;
	
	@RequestMapping(value = "SearchThreePhoto", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Object SearchThreePhoto(){
		List<ThreeRecentUrlResultBo> list = materialInfoService.getThreeRecentMaterial();
		ResponseVo responseVO = new ResponseVo();
		if(list == null){
			responseVO.setStatus(ResponseVo.STATUS_FAILED);
			responseVO.setMessage("载入图片时出现错误");
		}else{
			responseVO.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVO.setMessage("成功");
			responseVO.setObject(list);
		}
		logger.info("文件内容为：{}", responseVO);
		return responseVO.toString();
	}
	@RequestMapping(value = "countNumber", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Object countNumber(){
		int iconCount = materialInfoService.selectIconCount();
		int drawingCount = materialInfoService.selectDrawingCount();
		List<Integer> list = new ArrayList<Integer>();
		list.add(iconCount);
		list.add(drawingCount);
		ResponseVo responseVO = new ResponseVo();
		if(list.size() == 0){
			responseVO.setStatus(ResponseVo.STATUS_FAILED);
			responseVO.setMessage("统计素材数量时出现错误");
		}else{
			responseVO.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVO.setMessage("成功");
			responseVO.setObject(list);
		}
		return responseVO.toString();
	}
	@RequestMapping(value = "chooseSearchWord", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Object chooseSearchWord(){
		List<String> list = materialInfoService.selectTypeNameForBox();
		logger.info("type的信息为：{}", list);
		ResponseVo responseVO = new ResponseVo();
		if(list.size() == 0){
			responseVO.setStatus(ResponseVo.STATUS_FAILED);
			responseVO.setMessage("载入素材类型时出现错误");
		}else{
			responseVO.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVO.setMessage("成功");
			responseVO.setObject(list);
		}
		return responseVO.toString();
	}
}
