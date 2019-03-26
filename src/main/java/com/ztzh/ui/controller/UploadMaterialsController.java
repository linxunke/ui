package com.ztzh.ui.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.im4java.core.IM4JavaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ztzh.ui.bo.UploadMaterialsBo;
import com.ztzh.ui.po.MaterialInfoDomain;
import com.ztzh.ui.po.MaterialTypeDomain;
import com.ztzh.ui.service.UploadMaterialsService;
import com.ztzh.ui.utils.FileUpload;
import com.ztzh.ui.utils.ImageMagickUtil;
import com.ztzh.ui.vo.ResponseVo;

@RestController
@RequestMapping("/uploadMaterial")
public class UploadMaterialsController {
	Logger logger = LoggerFactory.getLogger(UploadMaterialsController.class);
	@Autowired
	UploadMaterialsService uploadMaterialsService;

	@Autowired
	ImageMagickUtil imageMagickUtil;

	@Value("${material.catch.operation.url}")
	private String catchOperationUrl;

	@Value("${material.catch.png.url}")
	private String catchPngUrl;

	@Value("${material.catch.resource.url}")
	private String catchResourceUrl;

	/* 获取素材分类信息 */
	@RequestMapping("/getMaterialTypes")
	public String getMaterialTypes(
			@RequestParam(value = "userId", required = false) String userId) {
		UploadMaterialsBo uploadMaterialsBo = uploadMaterialsService
				.getMaterialTypes();
		Map<String, List<MaterialTypeDomain>> types = new HashMap<String, List<MaterialTypeDomain>>();
		types.put("materialTypes", uploadMaterialsBo.getMaterialTypes());
		types.put("materialSegmentations",
				uploadMaterialsBo.getMaterialSegmentations());
		types.put("materialStyles", uploadMaterialsBo.getMaterialStyles());
		ResponseVo responseVo = new ResponseVo();
		responseVo.setUserId(userId);
		responseVo.setObject(types);
		responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
		responseVo.setMessage("获取素材分类信息成功！");
		return responseVo.toString();
	}

	/* 对用户上传的文件进行类型转换 */
	@RequestMapping(value = "/getMaterialFiles", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String getMaterialFiles(
			@RequestParam(value = "file") MultipartFile file) {
		String resource = FileUpload.writeUploadFile(file, catchResourceUrl);
		/* 获取缓存的源文件的文件名 */
		String[] str1 = resource.split("\\\\");
		String newPath = catchPngUrl + str1[str1.length - 1].split("\\.")[0]
				+ ".png";
		String[] imagePath = { resource };
		ResponseVo responseVo = new ResponseVo();
		try {
			imageMagickUtil.convertType(imagePath, newPath);
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVo.setMessage("后台转换文件成功");
			File f = new File(newPath);
			responseVo.setObject(f.getName());
		} catch (IOException | InterruptedException | IM4JavaException e) {
			e.printStackTrace();
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
			responseVo.setMessage("后台转换文件类型失败");
			logger.warn("resource file convert to png file failed...");
		} finally {
			List<String> urlList = new ArrayList<String>();
			urlList.add(resource);
			FileUpload.deleteFiles(urlList);
		}
		return responseVo.toString();
	}

	/* 获取提交的素材详细信息 */
	@RequestMapping(value = "/commitMaterialInfos", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String commitMaterialInfos(
			@RequestParam(value = "userId", required = true) int userId,
			@RequestParam(value = "imageName", required = true) String imageName,
			@RequestParam(value = "typeArray", required = true) String typeArray,
			@RequestParam(value = "imageLabel", required = true) String imageLabel,
			@RequestParam(value = "personalCanvasId", required = true) int canvasId,
			@RequestParam(value = "resourceFile", required = true) MultipartFile resourceFile,
			@RequestParam(value = "previewImg", required = true) String previewImg) {
		JSONArray typesJsonList = JSONArray.parseArray(typeArray); // 将json字符串变为json数组
		// list用来存储多个分类信息
		List<Map<String, Object>> typesList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < typesJsonList.size(); i++) {
			JSONArray typeJson = (JSONArray) typesJsonList.get(i);
			Map<String, Object> typeMap = new HashMap<String, Object>(); // map用来存放类别、细分、风格信息
			typeMap.put("materialType", typeJson.get(0));
			typeMap.put("materialSegmentation", typeJson.get(1));
			typeMap.put("materialStyle", typeJson.get(2));
			logger.info("map:--" + typeMap.entrySet());
			typesList.add(typeMap);
		}

		return null;
	}
}
