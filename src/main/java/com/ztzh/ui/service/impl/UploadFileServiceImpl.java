package com.ztzh.ui.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ztzh.ui.service.UploadFileService;
import com.ztzh.ui.utils.FTPUtil;
import com.ztzh.ui.utils.FileUpload;

@Service
public class UploadFileServiceImpl implements UploadFileService{
	Logger logger = LoggerFactory.getLogger(UploadFileService.class);
	
	@Value("${user.photo.address}")
	private String fileAddress;
	
	@Autowired
	FTPUtil ftpUtil;

	@Override
	public String uploadUserPhonto(MultipartFile file,String filepath) {
		filepath = fileAddress;
		return FileUpload.writeUploadFile(file, filepath);
	}

	@Override
	public boolean createFTPMaterialDirectoryByAccount(String account) {
		boolean isExisted = false;
		boolean isSuccess = false;
		try {
			isExisted = ftpUtil.existDirectory(account);
			if(!isExisted) {
				ftpUtil.createDirectory(account);
			}
			isSuccess = ftpUtil.createDirectory(account+"/material");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}

	@Override
	public boolean createFTPThumbnailDirectoryAccount(String account) {
		boolean isExisted = false;
		boolean isSuccess = false;
		try {
			isExisted = ftpUtil.existDirectory(account);
			if(!isExisted) {
				ftpUtil.createDirectory(account);
			}
			isSuccess = ftpUtil.createDirectory(account+"/thumbnail");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}

}
