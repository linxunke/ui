package com.ztzh.ui.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ztzh.ui.bo.ManagementCanvasBo;
import com.ztzh.ui.service.CanvasInfoService;
import com.ztzh.ui.vo.CanvasResponseVo;
import com.ztzh.ui.vo.ResponseVo;

@RestController
@RequestMapping(value = "canvasInfo")
public class CanvasInfoController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	CanvasInfoService canvasInfoService;

	@RequestMapping(value = "getCanvasByUserId", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public String getCanvasInfo(@RequestParam(value = "userId", required = false) String userId) {
		logger.info("开始获取用户userId:{}的画板信息",userId);
		List<ManagementCanvasBo> userCanvas = canvasInfoService
				.selectCanvasByUserId(Long.parseLong(userId));
		int canvasCount = canvasInfoService.canvasCount(Long.parseLong(userId));
		CanvasResponseVo canvasVo = new CanvasResponseVo();
		canvasVo.setCanvasCount(canvasCount);
		canvasVo.setCanvasInfo(userCanvas);
		ResponseVo responseVo = new ResponseVo();
		if (userCanvas.size() > 0) {
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVo.setMessage("获取个人画板信息成功");
			responseVo.setObject(canvasVo);
		} else {
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
			responseVo.setMessage("获取个人画板信息失败");
			responseVo.setObject(null);
		}
		responseVo.setUserId(userId);
		return responseVo.toString();
	}
}
