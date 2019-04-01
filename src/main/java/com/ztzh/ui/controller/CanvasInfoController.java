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
import com.ztzh.ui.utils.QueryByPage;
import com.ztzh.ui.vo.CanvasResponseVo;
import com.ztzh.ui.vo.ResponseVo;

@RestController
@RequestMapping(value = "canvasInfo")
public class CanvasInfoController {
	int pageSize = 7;
	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	CanvasInfoService canvasInfoService;

	@RequestMapping(value = "getCanvasByUserId", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public String getCanvasInfo(@RequestParam(value = "userid", required = false) String userid,
			@RequestParam(value = "currentPage", required = false) int currentPage) {
		logger.info("userid{}",userid);
		logger.info("currentPage{}",currentPage);
		//1.查询总条数，调用noteDao里的方法获取
		int totalCount = canvasInfoService.canvasCount(Long.parseLong(userid));
		logger.info("totalCount{}",totalCount);
		//2.计算总页数
		int pageCount = totalCount%pageSize == 0 ? totalCount/pageSize:(totalCount/pageSize+1);
		if(currentPage < 1){
			currentPage = 1;
		}else if(currentPage > pageCount){
			currentPage = pageCount;
		}
		List<ManagementCanvasBo> userCanvas = canvasInfoService
				.selectCanvasByUserId(Long.parseLong(userid),currentPage,pageSize);
		int canvasCount = canvasInfoService.canvasCount(Long.parseLong(userid));
		CanvasResponseVo canvasVo = new CanvasResponseVo();
		/*//1.查询总条数，调用noteDao里的方法获取
		int totalCount = canvasInfoService.canvasCount(Long.parseLong(userid));
		//2.计算总页数
		int pageCount = totalCount%pageSize == 0 ? totalCount/pageSize:(totalCount/pageSize+1);*/
		canvasVo.setCanvasCount(canvasCount);
		canvasVo.setCanvasInfo(userCanvas);
		canvasVo.setPageCount(pageCount);
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
		responseVo.setUserId(userid);
		return responseVo.toString();
	}
}
