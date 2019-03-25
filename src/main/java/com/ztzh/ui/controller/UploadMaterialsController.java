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

import sun.misc.BASE64Encoder;

import com.ztzh.ui.bo.UploadMaterialsBo;
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
	
	/*获取素材分类信息*/
	@RequestMapping("/getMaterialTypes")
	public String getMaterialTypes(@RequestParam(value="userId" , required=false) String userId){
		UploadMaterialsBo uploadMaterialsBo = uploadMaterialsService.getMaterialTypes();
		Map<String, List<MaterialTypeDomain>> types = new HashMap<String, List<MaterialTypeDomain>>();
		types.put("materialTypes", uploadMaterialsBo.getMaterialTypes());
		types.put("materialSegmentations", uploadMaterialsBo.getMaterialSegmentations());
		types.put("materialStyles", uploadMaterialsBo.getMaterialStyles());
		ResponseVo responseVo = new ResponseVo();
		responseVo.setUserId(userId);
		responseVo.setObject(types);
		responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
		responseVo.setMessage("获取素材分类信息成功！");
		return responseVo.toString();
	}
	
	/*对用户上传的文件进行类型转换*/
	@RequestMapping(value="/getMaterialFiles",method= {RequestMethod.POST,RequestMethod.GET})
	public String getMaterialFiles(@RequestParam(value="file")MultipartFile file ,
			@RequestParam(value="userId" , required=false) String userId){
		String resource = FileUpload.writeUploadFile(file, "D:\\catch\\resourceFile");
		/*获取缓存的源文件的文件名*/
		String[] str1 = resource.split("\\\\");
		String newPath = "D:\\catch\\png\\" + str1[str1.length-1].split("\\.")[0] + ".png";
		String[] imagePath = {resource};
		ResponseVo responseVo = new ResponseVo();
		try {
			imageMagickUtil.convertType(imagePath, newPath);
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVo.setMessage("后台转换文件成功");
			
			/*responseVo.setObject();*/
		} catch (IOException | InterruptedException | IM4JavaException e) {
			e.printStackTrace();
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
			responseVo.setMessage("后台转换文件类型失败");
			logger.warn("resource file convert to png file failed...");
		} finally{
			List<String> urlList = new ArrayList<String>();
			urlList.add(resource);
			FileUpload.deleteFiles(urlList);
			responseVo.setUserId(userId);
		}
		return responseVo.toString();
	}
}
