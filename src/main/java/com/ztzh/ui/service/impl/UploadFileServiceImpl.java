package com.ztzh.ui.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ztzh.ui.service.UploadFileService;
import com.ztzh.ui.utils.FileUpload;

@Service
public class UploadFileServiceImpl implements UploadFileService{
	@Value("${user.photo.address}")
	private String fileAddress;

	@Override
	public String uploadUserPhonto(MultipartFile file,String filepath) {
		filepath = fileAddress;
		return FileUpload.writeUploadFile(file, filepath);
	}

}
