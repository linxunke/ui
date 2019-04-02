package com.ztzh.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.ztzh.ui.service.CanvasInfoService;
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
    private  String catchOperationUrl;
	
	@RequestMapping(value = "getCanvasByUserId", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public String getCanvasInfo(@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "onlyData", required = false) String onlyData) {
		logger.info("开始获取用户userId:{}的画板信息",userId);
		ResponseVo responseVo = new ResponseVo();
		List<ManagementCanvasBo> userCanvas = canvasInfoService
				.selectCanvasByUserId(Long.parseLong(userId));
		//拼接图标判断
		if(!"1".equals(onlyData)) {
			//判断哪些源素材为图标类型
			List<String> thumbnailUrls = new ArrayList<String>();
			for(ManagementCanvasBo userCanva:userCanvas) {
				if(null!=userCanva.getLastMaterialUrl()) {
					thumbnailUrls.add(userCanva.getLastMaterialUrl());
				}
			}
			List<IconUrlResultBo> iconUrlList = materialInfoService.getDisplayUrlByThumbnailUrl(thumbnailUrls);
			List<IconUrlBo> iconUrlBoList  = new ArrayList<IconUrlBo>();
			for(IconUrlResultBo iconUrlResultBo:iconUrlList) {
				List<String> iconUrls = iconUrlResultBo.getPngUrls();
				List<String> realIconUrls = new ArrayList<String>();
				for(String iconUrl:iconUrls) {
					realIconUrls.add(ftpAddress.replace("/", "\\\\")+"\\"+iconUrl.replace("/", "\\"));
				}
				if(realIconUrls.size()>0) {
					String displayIconUrl = null;
					try {
						logger.info("开始拼接图标");			
						displayIconUrl = imageMagickUtil.iconDisplay(realIconUrls);
						logger.info("拼接图标成功");	
					} catch (IOException | InterruptedException | IM4JavaException e) {
						logger.info("拼接图标失败");		
						responseVo.setStatus(ResponseVo.STATUS_FAILED);
						responseVo.setMessage("拼接图标失败");
						responseVo.setUserId(userId);
						responseVo.setObject(null);
						return responseVo.toString();
					}
					IconUrlBo iconUrlBo = new IconUrlBo();
					iconUrlBo.setCanvasId(iconUrlResultBo.getCanvasId());
					iconUrlBo.setPngUrl("/images//"+displayIconUrl.replace(catchOperationUrl, "").replace("\\", "//"));
					iconUrlBoList.add(iconUrlBo);
				}
			}
			if(iconUrlBoList.size()>0) {
				for(int i=0;i<userCanvas.size();i++) {
					for(IconUrlBo iconUrlBo:iconUrlBoList) {
						//将是放图标的文件夹中的图片地址替换成拼接好的图片地址
						if(userCanvas.get(i).getCanvasId().toString().equals(iconUrlBo.getCanvasId())) {
							userCanvas.get(i).setLastMaterialUrl(iconUrlBo.getPngUrl());
						}
					}
				}
			}
		}
		int canvasCount = canvasInfoService.canvasCount(Long.parseLong(userId));
		CanvasResponseVo canvasVo = new CanvasResponseVo();
		canvasVo.setCanvasCount(canvasCount);
		canvasVo.setCanvasInfo(userCanvas);
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
