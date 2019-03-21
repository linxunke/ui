package com.ztzh.ui.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ztzh.ui.service.TestService;
import com.ztzh.ui.utils.FileUpload;

@Service
public class TestServiceImpl implements TestService{
	@Value("${user.photo.address}")
	private String filepath;

	@Override
	public void upload(MultipartFile file) {
		FileUpload.writeUploadFile(file, filepath);
		
	}


}
