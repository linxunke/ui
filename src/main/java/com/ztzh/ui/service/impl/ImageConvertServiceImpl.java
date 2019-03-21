package com.ztzh.ui.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ztzh.ui.service.ImageConvertService;
import com.ztzh.ui.utils.FileUpload;
import com.ztzh.ui.utils.ImageMagickUtil;

@Service
public class ImageConvertServiceImpl implements ImageConvertService{
	Logger logger = LoggerFactory.getLogger(ImageConvertServiceImpl.class);
	
	@Value("${material.catch.operation.url}")
	private String catchOperationUrl;
	
	@Autowired
	ImageMagickUtil imageMagickUtil;

	@Override
	public String iconsImagesTOPng(List<String> urls) {
		String randomName = UUID.randomUUID().toString().replaceAll("-", "");
		int urlsSize = urls.size();
		String[] urlsArray = new String[urlsSize];
		urls.toArray(urlsArray);
		List<String> iconsPngList = new ArrayList<String>();
		String iconTargetUrl = "";
		try {
			imageMagickUtil.convertType(urlsArray,catchOperationUrl+randomName+".png");
			for(int i=0;i<urlsSize;i++) {
				iconsPngList.add(catchOperationUrl+randomName+"-"+i+".png");
			}
			iconTargetUrl = imageMagickUtil.iconDisplay(iconsPngList);
			FileUpload.deleteFiles(iconsPngList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return iconTargetUrl;
	}

}
