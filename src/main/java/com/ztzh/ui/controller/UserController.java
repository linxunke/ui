package com.ztzh.ui.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ztzh.ui.bo.LoginResultBo;
import com.ztzh.ui.bo.RegisterResultBo;
import com.ztzh.ui.constants.UserConstants;
import com.ztzh.ui.po.UserInfoDomain;
import com.ztzh.ui.service.UploadFileService;
import com.ztzh.ui.service.UserService;
import com.ztzh.ui.utils.FTPUtil;
import com.ztzh.ui.utils.FileUpload;
import com.ztzh.ui.vo.ResponseVo;

@RestController
@RequestMapping("/user")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Value("${user.photo.address}")
	private String photoAddress;

	@Autowired
	UserService userService;
	
	@Autowired
	UploadFileService uploadFileService;
	
	@Autowired
	FTPUtil ftpUtil;

	@RequestMapping(value = "register", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@Transactional
	public Object register(
			@RequestParam(value = "account", required = true) String account,
			@RequestParam(value = "nickname", required = true) String nickname,
			@RequestParam(value = "wechat", required = true) String wechat,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "file", required = true) String file) throws FTPConnectionClosedException, FileNotFoundException, IOException, Exception {
		UserInfoDomain user = new UserInfoDomain();
		user.setUserAccount(account);
		user.setUserNickname(nickname);
		user.setUserWeixin(wechat);
		// 处理密码加密
		String passwordMd5 = userService.encrypt(password);
		user.setUserPassword(passwordMd5);
		String imgType = file.substring(11, 14);
		logger.info("文件格式为：{}", imgType);
		String base64 = null;
		String fileName = null;
		StringBuffer photoUrl = new StringBuffer(photoAddress);
		if ("png".equals(imgType)) {
			base64 = file.replace("data:image/png;base64,", "");
			fileName = account + ".png";
		} else if ("jpg".equals(imgType)) {
			base64 = file.replace("data:image/jpg;base64,", "");
			fileName = account + ".jpg";
		}
		photoUrl.append("/" + fileName);
		user.setUserPhotoUrl(UserConstants.FTP_PHOTO_DIRECTORY+"//"+fileName);
		RegisterResultBo result = userService.register(user);
		ResponseVo responseVo = new ResponseVo();
		if (result.getCode() == UserConstants.CHECK_DATA_LENGTH_TRUE) {
			// 跳转页面
			logger.info("成功创建用户");
			FileUpload.base64ToFile(base64, photoAddress, fileName);
			String photoRealPath = photoAddress.replace("/", "\\")+"\\"+fileName;
			File photoFile = new File(photoRealPath);
			//写入ftp
			ftpUtil.uploadToFtp(new FileInputStream(photoFile), fileName, false, UserConstants.FTP_PHOTO_DIRECTORY);
			//删除缓存文件
			List<String> deleteAddressList = new ArrayList<String>();
			deleteAddressList.add(photoUrl.toString());
			FileUpload.deleteFiles(deleteAddressList);
			logger.info("开始创建用户的文件夹");
			boolean isCreatedAllDirectory = false;
			boolean isCreatedMaterialDirectory = false;
			boolean isCreatedThumbnailDirectory = false;
			boolean isCreatedPNGDirectory = false;
			isCreatedMaterialDirectory = uploadFileService.createFTPMaterialDirectoryByAccount(account);
			isCreatedThumbnailDirectory = uploadFileService.createFTPThumbnailDirectoryAccount(account);
			isCreatedPNGDirectory = uploadFileService.createFTPPNGDirectoryAccount(account);
			logger.info("创建用户的文件夹结束");
			isCreatedAllDirectory = isCreatedPNGDirectory==(isCreatedMaterialDirectory==isCreatedThumbnailDirectory?true:false)?true:false;
			if(!isCreatedAllDirectory) {
				responseVo.setStatus(ResponseVo.FAILED_CREATED_FTP_DIRECTORY);
			}else {
				responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			}
			responseVo.setUserId(result.getUserId());
			responseVo.setObject(user);
			return responseVo.toString();
		} else {
			return "/false";
		}
	}

	@RequestMapping(value = "checkuseraccount", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public boolean checkUserAccount(
			@RequestParam(value = "account", required = true) String account) {
		return userService.checkUserAccountIsValue(account);
	}

	@RequestMapping(value = "login", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public String login(
			@RequestParam(value = "account", required = true) String account,
			@RequestParam(value = "password", required = true) String password) {
		// 判断账号密码是否匹配
		logger.info("进入用户登录方法");
		ResponseVo responseVo = new ResponseVo();
		String passwordMD5 = userService.encrypt(password);
		LoginResultBo loginResultBo = userService.login(account, passwordMD5);
		String status = loginResultBo.getStatus();
		responseVo.setStatus(status);
		String message = "";
		if (ResponseVo.STATUS_SUCCESS.equals(status)) {
			message = "登录成功";
		} else if (ResponseVo.STATUS_VALUE_FALSE.equals(status)) {
			message = "账号无效";
		} else if (ResponseVo.STATUS_ACCOUNT_OR_PASSWORD_FALSE.equals(status)) {
			message = "账号密码不匹配";
		}
		responseVo.setMessage(message);
		responseVo.setUserId(loginResultBo.getId());
		responseVo.setObject(loginResultBo.getUserPhotoUrl());
		logger.info(responseVo.toString());
		return responseVo.toString();
	}
	@RequestMapping(value = "getUserName", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public String getUserNameByUserid(
			@RequestParam(value = "userId", required = true) String userId){
		ResponseVo responseVo = new ResponseVo();
		UserInfoDomain user = userService.getUserInfoById(Long.parseLong(userId));
		if(user.getUserNickname()==null){
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
			responseVo.setMessage("获取用户信息失败");
			responseVo.setObject(null);
		}else{
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVo.setMessage("获取用户画板信息成功");
			responseVo.setObject(user);
		}
				return responseVo.toString();
		
	}
			
}
