package com.ztzh.ui.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
	
	/**
	 * 上传头像到服务器
	 * @param file
	 * @param filepath
	 * @return
	 */
	public String uploadUserPhonto(MultipartFile file,String filepath);

}
