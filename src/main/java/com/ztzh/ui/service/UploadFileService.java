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
	
	/**
	 * 根据账号创建素材文件夹
	 * @param account
	 * @return
	 */
	public boolean createFTPMaterialDirectoryByAccount(String account);
	
	/**
	 * 根据账号创建缩略图文档
	 * @param account
	 * @return
	 */
	public boolean createFTPThumbnailDirectoryAccount(String account);

}
