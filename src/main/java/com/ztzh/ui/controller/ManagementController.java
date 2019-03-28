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
import com.ztzh.ui.service.CanvasInfoService;
import com.ztzh.ui.service.ManagementService;
import com.ztzh.ui.vo.CanvasResponseVo;

@RestController
@RequestMapping("/managementCon")
public class ManagementController {

	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	ManagementService managementService;
	@Autowired
	CanvasInfoService canvasInfoService;
	//载入时
	@RequestMapping(value="managementMain",method = {RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	public Object management(@RequestParam(value="userid",required=true) Long userId){
		//返回画板个数，各个画板具体所需信息
		CanvasResponseVo responseVo = new CanvasResponseVo();
		ArrayList<ManagementCanvasBo> list = managementService.searchCanvasInfoByUserId(userId);
		int canvasCount = managementService.canvasCount(userId);
		responseVo.setCanvasInfo(list);
		responseVo.setCanvasCount(canvasCount);
		return responseVo.toString();
	}
	//添加画板
	@RequestMapping(value="managementAdd",method = {RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	public Object addCanvas(@RequestParam(value="userid",required=true) String userid,
			@RequestParam(value="canvasName",required=true) String canvasName,
			@RequestParam(value="canvasDesc",required=true) String canvasDesc){
		   managementService.addCanvas(Long.parseLong(userid), canvasName, canvasDesc);
		   return "success";
	}
	//修改画板信息
	@RequestMapping(value="managementUpdate",method = {RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	public Object updateCanvas(@RequestParam(value="canvasid",required=true) String canvasid,
			@RequestParam(value="boardName",required=true) String canvasName,
			@RequestParam(value="board_des",required=true) String canvasDesc){
		Long canvasId = Long.parseLong(canvasid);
		managementService.updateCanvas(canvasId, canvasName, canvasDesc);
		return "success";
	}
	//删除画板，将其中素材移至未分类
	@RequestMapping(value="managementDeleteToUnsort",method = {RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	public Object deleteToUnsort(@RequestParam(value="userid",required=true) Long userId,
			@RequestParam(value="canvasid",required=true) Long canvasId){
		canvasInfoService.userDeleteCanvasWithoutMaterials(canvasId, userId);
		return true;
	}
	//删除画板，将其中素材一并删除
	@RequestMapping(value="managementDeleteAll",method = {RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	public Object deleteAll(@RequestParam(value="userid",required=true) Long userId,
			@RequestParam(value="canvasid",required=true) Long canvasId){
		canvasInfoService.userDeleteCanvasWithMaterials(canvasId, userId);
		return true;
	}
}
