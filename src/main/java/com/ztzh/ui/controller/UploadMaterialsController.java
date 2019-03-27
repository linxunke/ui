package com.ztzh.ui.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.im4java.core.IM4JavaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ztzh.ui.bo.ImageColorBo;
import com.ztzh.ui.bo.UploadMaterialsBo;
import com.ztzh.ui.constants.UserConstants;
import com.ztzh.ui.po.MaterialInfoDomain;
import com.ztzh.ui.po.MaterialTypeDomain;
import com.ztzh.ui.po.MaterialTypeInfoDomain;
import com.ztzh.ui.po.UserInfoDomain;
import com.ztzh.ui.service.UploadMaterialsService;
import com.ztzh.ui.service.UserService;
import com.ztzh.ui.utils.FTPUtil;
import com.ztzh.ui.utils.FileUpload;
import com.ztzh.ui.utils.ImageMagickUtil;
import com.ztzh.ui.utils.ImageUtil;
import com.ztzh.ui.vo.ResponseVo;

@RestController
@RequestMapping("/uploadMaterial")
public class UploadMaterialsController {
	Logger logger = LoggerFactory.getLogger(UploadMaterialsController.class);
	@Autowired
	UserService userService;

	@Autowired
	UploadMaterialsService uploadMaterialsService;

	@Autowired
	ImageMagickUtil imageMagickUtil;

	@Autowired
	FTPUtil ftpUtil;

	@Value("${material.catch.operation.url}")
	private String catchOperationUrl;

	@Value("${material.catch.png.url}")
	private String catchPngUrl;

	@Value("${material.catch.resource.url}")
	private String catchResourceUrl;

	@Value("${material.catch.thumbnail.url}")
	private String catchThumbnailUrl;

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
	@Transactional
	public String commitMaterialInfos(
			@RequestParam(value = "userId", required = true) Long userId,
			@RequestParam(value = "imageName", required = true) String imageName,
			@RequestParam(value = "typeArray", required = true) String typeArray,
			@RequestParam(value = "imageLabel", required = true) String imageLabel,
			@RequestParam(value = "personalCanvasId", required = true) Long canvasId,
			@RequestParam(value = "resourceFile", required = true) MultipartFile resourceFile,
			@RequestParam(value = "previewImg", required = true) String previewImg,
			@RequestParam(value = "pngFileSrc", required = true) String pngFileSrc) {
		UserInfoDomain currentUser = userService.getUserInfoById(userId);
		boolean uploadResult = true;  //记录文件上传至ftp服务器的结果(成功true,失败false)
		ResponseVo responseVo = new ResponseVo();
		responseVo.setUserId(userId.toString());
		/* 将文件写到服务器 */
		/* 上传FTP源文件 */
		InputStream resourceIS = null;
		String resourceName = UUID.randomUUID().toString().replaceAll("-", "");
		String resourceFileType = ""; //原文件的文件类型
		String resourceDir = "";  //原文件存放在ftp服务器的地址
		try {
			resourceIS = resourceFile.getInputStream();
			int length = resourceFile.getOriginalFilename().length();
			/* 获取源文件类型 */
			for (int i = resourceFile.getOriginalFilename().lastIndexOf("."); i < length; i++) {
				resourceFileType += resourceFile.getOriginalFilename()
						.charAt(i);
			}
			resourceDir = "/" + currentUser.getUserAccount() + "/"
					+ UserConstants.FTP_MATERIALS_DIRECTORY;
			boolean tempResult = ftpUtil.uploadToFtp(resourceIS, resourceName + "."
					+ resourceFileType, false, resourceDir);
			uploadResult = tempResult && uploadResult;
			if(!uploadResult){
				responseVo.setStatus(ResponseVo.STATUS_FAILED);
				responseVo.setMessage("上传原文件失败！");
				return responseVo.toString();
			}
		} catch (IOException e) {
			logger.info("获取源文件流失败");
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("上传源文件到ftp服务器失败");
			e.printStackTrace();
		} finally {
			try {
				resourceIS.close();
				//用resourceDir记录原文件的url
				resourceDir = resourceDir + "//" + resourceName
						+ resourceFileType;
			} catch (IOException e) {
				logger.info("关闭源文件流失败");
				e.printStackTrace();
			}
		}
		/* 上传FTP缩略图 */
		previewImg = previewImg.replace("data:image/png;base64,", "");
		//将缩略图写入catch文件夹下的thumbnail文件夹中
		FileUpload.base64ToFile(previewImg, catchThumbnailUrl, resourceName + ".png");
		File thumbnailCacheFile = new File(catchThumbnailUrl + resourceName + ".png");
		FileInputStream thumbnailIS = null;
		String thumbnailDir = ""; //缩略图在ftp服务器中的地址
		String thumbnailName = ""; //缩略图的名字（**.png）
		try {
			thumbnailIS = new FileInputStream(thumbnailCacheFile);
			thumbnailName = resourceName + ".png";
			thumbnailDir = "/" + currentUser.getUserAccount() + "/"
					+ UserConstants.FTP_THUMBNAIL_DIRECTORY;
			boolean tempResult = ftpUtil.uploadToFtp(thumbnailIS, thumbnailName, false, thumbnailDir);
			uploadResult = uploadResult && tempResult;
			if(!uploadResult){
				responseVo.setStatus(ResponseVo.STATUS_FAILED);
				responseVo.setMessage("上传缩略图失败！");
				return responseVo.toString();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FTPConnectionClosedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				thumbnailIS.close();
				List<String> urlList = new ArrayList<String>();
				urlList.add(catchThumbnailUrl + resourceName + ".png");
				FileUpload.deleteFiles(urlList);
				thumbnailDir = thumbnailDir + "//" + thumbnailName;
			} catch (IOException e) {
				logger.info("关闭缩略图文件流失败");
				e.printStackTrace();
			}
		}
		/* 上传FTP的png图片 */
		/* 获取开始缓存在本地的png图片名字 */
		String pngFileName = "";  //png图片的名字(**.png)
		for (int i = pngFileSrc.lastIndexOf("/") + 1; i < pngFileSrc.length(); i++) {
			pngFileName += pngFileSrc.charAt(i);
		}
		/* 上传缓存的png图片至ftp服务器 */
		File pngFile = new File(catchPngUrl + pngFileName);
		ImageColorBo imageColorBo = ImageUtil.getColorPercentage(pngFile);
		FileInputStream pngCatchIS = null;
		String pngDir = "";  //png图片在ftp服务器的地址
		String pngName = ""; //png图片在ftp名字(**.png)
		try {
			pngCatchIS = new FileInputStream(pngFile);
			pngName = resourceName + ".png";
			pngDir = "/" + currentUser.getUserAccount() + "/"
					+ UserConstants.FTP_PNG_DIRECTORY;
			boolean tempResult = ftpUtil.uploadToFtp(pngCatchIS, pngName, false, pngDir);
			uploadResult = uploadResult && tempResult;
			if(!uploadResult){
				responseVo.setStatus(ResponseVo.STATUS_FAILED);
				responseVo.setMessage("上传png图片失败！");
				return responseVo.toString();
			}
		} catch (FileNotFoundException e) {
			logger.info("打开png缓存图片的文件流失败，因为文件不存在");
			e.printStackTrace();
		} catch (FTPConnectionClosedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pngCatchIS.close();
				List<String> urlList = new ArrayList<String>();
				urlList.add(catchPngUrl + pngFileName);
				logger.info(catchPngUrl + pngFileName);
				FileUpload.deleteFiles(urlList);
				pngDir = pngDir + "//" + pngName;
			} catch (IOException e) {
				logger.info("关闭png缓存图片的文件流失败");
				e.printStackTrace();
			}
		}
		/* 操作素材基本信息 */
		MaterialInfoDomain newMaterialInfoDomain = new MaterialInfoDomain();
		newMaterialInfoDomain.setCreateUserId(currentUser.getId());
		newMaterialInfoDomain.setMaterialName(imageName);
		newMaterialInfoDomain.setMaterialDescription(imageLabel);
		newMaterialInfoDomain.setMaterialType(resourceFileType.replaceFirst(".", ""));
		newMaterialInfoDomain.setUploadTime(new Date());
		newMaterialInfoDomain.setCanvasInfoIdPrivate(canvasId);
		newMaterialInfoDomain.setCanvasInfoIdPublic(canvasId);
		newMaterialInfoDomain.setIsValid(1);
		newMaterialInfoDomain.setColorPercentage(imageColorBo.getColorPercentage());
		newMaterialInfoDomain.setColorType(Integer.parseInt(imageColorBo.getColorType()));
		newMaterialInfoDomain.setMaterialUrl(resourceDir);
		newMaterialInfoDomain.setPngUrl(pngDir);
		newMaterialInfoDomain.setThumbnailUrl(thumbnailDir);
		int addMaterialInfoResult = uploadMaterialsService.addMaterialInfo(newMaterialInfoDomain);

		JSONArray typesJsonList = JSONArray.parseArray(typeArray); // 将json字符串变为json数组
		// 将JSONArray的数据存到typeList中
		List<Map<String, Object>> typesList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < typesJsonList.size(); i++) {
			JSONArray typeJson = (JSONArray) typesJsonList.get(i);
			Map<String, Object> typeMap = new HashMap<String, Object>(); // map用来存放类别、细分、风格信息
			typeMap.put("materialType", typeJson.get(0));
			typeMap.put("materialSegmentation", typeJson.get(1));
			typeMap.put("materialStyle", typeJson.get(2));
			typesList.add(typeMap);
		}
		/*通过原文件在ftp服务器的地址获取到materialInfo的id*/
		Long currentMterialInfoId = uploadMaterialsService.getMaterialIdByMaterialUrl(resourceDir);
		/*将每个素材分类信息存到一个List当中*/
		List<MaterialTypeInfoDomain> materialTypeInfoList = new ArrayList<MaterialTypeInfoDomain>();
		for (Map<String, Object> map : typesList) {
			MaterialTypeInfoDomain newMaterialTypeInfoDomain = new MaterialTypeInfoDomain();
			newMaterialTypeInfoDomain.setMaterailInfoId(currentMterialInfoId);
			newMaterialTypeInfoDomain.setMaterialTypeCodeParent(map.get("materialType").toString());
			newMaterialTypeInfoDomain.setMaterialTypeCodeChild(map.get("materialSegmentation").toString());
			newMaterialTypeInfoDomain.setMaterialStyleCode(map.get("materialStyle").toString());
			materialTypeInfoList.add(newMaterialTypeInfoDomain);
		}
		for (MaterialTypeInfoDomain materialTypeInfoDomain : materialTypeInfoList) {
			logger.info(materialTypeInfoDomain.toString());
		}
		int addMaterialTypeInfoResult = uploadMaterialsService.addMaterialTypeInfo(materialTypeInfoList);
		if(addMaterialInfoResult != 0 && addMaterialTypeInfoResult != 0 && uploadResult){
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVo.setMessage("上传素材文件成功！");
		}else{
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
			responseVo.setMessage("上传素材文件失败！");
		}
		return null;
	}

}
