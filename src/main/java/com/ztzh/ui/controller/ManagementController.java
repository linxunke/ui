package com.ztzh.ui.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ztzh.ui.bo.ManagementCanvasBo;
import com.ztzh.ui.service.ManagementService;
import com.ztzh.ui.vo.CanvasResponseVo;

@RestController
@RequestMapping("/managementCon")
public class ManagementController {

	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	ManagementService managementService;
	//载入时
	@RequestMapping(value="managementMain",method = {RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	public Object management(@RequestParam(value="userId",required=true) Long userId){
		//返回画板个数，各个画板具体所需信息
		CanvasResponseVo responseVo = new CanvasResponseVo();
		ArrayList<ManagementCanvasBo> list = managementService.searchCanvasInfoByUserId(userId);
		int canvasCount = managementService.canvasCount(userId);
		responseVo.setCanvasInfo(canvasInfo);
		return "/true";
	}
	//添加画板
	@RequestMapping(value="managementAdd",method = {RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	public Object addCanvas(@RequestParam(value="userId",required=true) Long userId,
			@RequestParam(value="canvasName",required=true) String canvasName,
			@RequestParam(value="canvasDesc",required=true) String canvasDesc){
		return managementService.addCanvas(userId, canvasName, canvasDesc);
	}
	//修改画板信息
	@RequestMapping(value="managementUpdate",method = {RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	public Object updateCanvas(@RequestParam(value="canvasId",required=true) Long canvasId,
			@RequestParam(value="canvasName",required=true) String canvasName,
			@RequestParam(value="canvasDesc",required=true) String canvasDesc){
		return managementService.updateCanvas(canvasId, canvasName, canvasDesc);
	}
	//删除画板，将其中素材移至未分类
	@RequestMapping(value="managementDeleteToUnsort",method = {RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	public Object deleteToUnsort(@RequestParam(value="userId",required=true) Long userId,
			@RequestParam(value="canvasId",required=true) Long canvasId){
		return managementService.deleteToUnsort(userId,canvasId);
	}
	//删除画板，将其中素材一并删除
	@RequestMapping(value="managementDeleteAll",method = {RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	public Object deleteAll(@RequestParam(value="userId",required=true) Long userId,
			@RequestParam(value="canvasId",required=true) Long canvasId){
		return managementService.deleteAll(userId,canvasId );
	}
}
