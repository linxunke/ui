package com.ztzh.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.im4java.core.IM4JavaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ztzh.ui.bo.IconUrlBo;
import com.ztzh.ui.bo.IconUrlResultBo;
import com.ztzh.ui.bo.ManagementCanvasBo;
import com.ztzh.ui.po.CanvasInfoDomain;
import com.ztzh.ui.po.MaterialInfoDomain;
import com.ztzh.ui.service.CanvasInfoService;
import com.ztzh.ui.utils.QueryByPage;
import com.ztzh.ui.service.MaterialInfoService;
import com.ztzh.ui.utils.ImageMagickUtil;
import com.ztzh.ui.vo.CanvasResponseVo;
import com.ztzh.ui.vo.ResponseVo;

@RestController
@RequestMapping(value = "canvasInfo")
public class CanvasInfoController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	CanvasInfoService canvasInfoService;

	@Autowired
	MaterialInfoService materialInfoService;

	@Autowired
	ImageMagickUtil imageMagickUtil;

	@Value("${web.upload-path}")
	String ftpAddress;

	@Value("${material.catch.png.url}")
	private String catchOperationUrl;

	@RequestMapping(value = "getCanvasByUserId", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public String getCanvasInfo(
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "currentPage", required = false) int currentPage,
			@RequestParam(value = "onlyData", required = false) String onlyData) {
		logger.info("开始获取用户userId:{}的画板信息", userId);
		logger.info("currentPage{}", currentPage);
		ResponseVo responseVo = new ResponseVo();
		// 1.查询总条数，调用noteDao里的方法获取
		int totalCount = canvasInfoService.canvasCount(Long.parseLong(userId));
		logger.info("totalCount{}", totalCount);
		// 2.计算总页数
		int pageSize = 9;
		int pageCount = totalCount % pageSize == 0 ? totalCount / pageSize
				: (totalCount / pageSize + 1);
		if (currentPage < 1) {
			currentPage = 1;
		} else if (currentPage > pageCount) {
			currentPage = pageCount;
		}
		List<ManagementCanvasBo> userCanvas = canvasInfoService
				.selectCanvasByUserId(Long.parseLong(userId), currentPage,
						pageSize);
		// 拼接图标判断
		if (!"1".equals(onlyData)) {
			// 判断哪些源素材为图标类型
			List<String> thumbnailUrls = new ArrayList<String>();
			for (ManagementCanvasBo userCanva : userCanvas) {
				if (null != userCanva.getLastMaterialUrl()) {
					thumbnailUrls.add(userCanva.getLastMaterialUrl());
				}
			}
			List<IconUrlResultBo> iconUrlList = materialInfoService
					.getDisplayUrlByThumbnailUrl(thumbnailUrls);
			List<IconUrlBo> iconUrlBoList = new ArrayList<IconUrlBo>();
			for (IconUrlResultBo iconUrlResultBo : iconUrlList) {
				List<String> iconUrls = iconUrlResultBo.getPngUrls();
				List<String> realIconUrls = new ArrayList<String>();
				for (String iconUrl : iconUrls) {
					realIconUrls.add(ftpAddress.replace("/", "\\\\") + "\\"
							+ iconUrl.replace("/", "\\"));
				}
				if (realIconUrls.size() > 0) {
					String displayIconUrl = null;
					try {
						logger.info("开始拼接图标");
						displayIconUrl = imageMagickUtil
								.iconDisplay(realIconUrls);
						logger.info("拼接图标成功");
					} catch (IOException | InterruptedException
							| IM4JavaException e) {
						logger.info("拼接图标失败");
						responseVo.setStatus(ResponseVo.STATUS_FAILED);
						responseVo.setMessage("拼接图标失败");
						responseVo.setUserId(userId);
						responseVo.setObject(null);
						return responseVo.toString();
					}
					IconUrlBo iconUrlBo = new IconUrlBo();
					iconUrlBo.setCanvasId(iconUrlResultBo.getCanvasId());
					iconUrlBo.setPngUrl("/images//"
							+ displayIconUrl.replace(catchOperationUrl, "")
									.replace("\\", "//"));
					iconUrlBoList.add(iconUrlBo);
				}
			}
			if (iconUrlBoList.size() > 0) {
				for (int i = 0; i < userCanvas.size(); i++) {
					for (IconUrlBo iconUrlBo : iconUrlBoList) {
						// 将是放图标的文件夹中的图片地址替换成拼接好的图片地址
						if (userCanvas.get(i).getCanvasId().toString()
								.equals(iconUrlBo.getCanvasId())) {
							userCanvas.get(i).setLastMaterialUrl(
									iconUrlBo.getPngUrl());
						}
					}
				}
			}
		}
		int canvasCount = canvasInfoService.canvasCount(Long.parseLong(userId));
		CanvasResponseVo canvasVo = new CanvasResponseVo();
		/*
		 * //1.查询总条数，调用noteDao里的方法获取 int totalCount =
		 * canvasInfoService.canvasCount(Long.parseLong(userId)); //2.计算总页数 int
		 * pageCount = totalCount%pageSize == 0 ?
		 * totalCount/pageSize:(totalCount/pageSize+1);
		 */
		canvasVo.setCanvasCount(canvasCount);
		canvasVo.setCanvasInfo(userCanvas);
		canvasVo.setPageCount(pageCount);
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

	@RequestMapping(value = "getAllCanvasInfoByUserId", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public String getAllCanvasInfoByUserId(
			@RequestParam(value = "userId", required = true) String userId) {
		ResponseVo responseVo = new ResponseVo();
		List<ManagementCanvasBo> userCanvasInfoBoList = canvasInfoService.selectAllCanvasByUserId(new Long(userId));
		if (userCanvasInfoBoList.size() != 0) {
			List<CanvasInfoDomain> canvasInfoList = new ArrayList<CanvasInfoDomain>();
			for (int i = 0; i < userCanvasInfoBoList.size(); i++) {
				CanvasInfoDomain canvasInfo = new CanvasInfoDomain();
				canvasInfo.setId(userCanvasInfoBoList.get(i).getCanvasId());
				canvasInfo.setCanvasName(userCanvasInfoBoList.get(i).getCanvasName());
				canvasInfoList.add(canvasInfo);
			}
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVo.setMessage("获取个人全部画板信息成功");
			responseVo.setObject(canvasInfoList);
		}else {
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
			responseVo.setMessage("个人画板信息为空");
		}
		responseVo.setUserId(userId);
		return responseVo.toString();
	}
	
	@RequestMapping(value = "getCanvasInfoById", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public String getCanvasInfoById(
			@RequestParam(value = "canvasId", required = true) String canvasId){
		ResponseVo responseVo = new ResponseVo();
		CanvasInfoDomain canvasInfo = canvasInfoService.selectCanvasByCanvasId(new Long(canvasId));
		List<MaterialInfoDomain> materialList = materialInfoService.getMaterialListByCanvasId(new Long(canvasId));
		if(canvasInfo != null && materialList.size() != 0){
			Map<String,Object> result = new HashMap<String, Object>();
			result.put("canvasInfo", canvasInfo);
			result.put("materialList", materialList);
			result.put("materialNum", materialList.size());
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVo.setMessage("获取当前画板的信息及内容成功");
			responseVo.setObject(result);
		}else {
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
			responseVo.setMessage("获取当前画板失败，信息为空");
		}
		return responseVo.toString();
	}
}
