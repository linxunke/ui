package com.ztzh.ui.controller;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.im4java.core.IM4JavaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;
import com.ztzh.ui.bo.LoginInfoForRedisBo;
import com.ztzh.ui.constants.UserConstants;
import com.ztzh.ui.po.UserInfoDomain;
import com.ztzh.ui.service.CanvasInfoService;
import com.ztzh.ui.service.ImageConvertService;
import com.ztzh.ui.service.LoginInfoRecordService;
import com.ztzh.ui.service.TestService;
import com.ztzh.ui.service.UploadFileService;
import com.ztzh.ui.utils.FTPUtil;
import com.ztzh.ui.utils.ImageMagickUtil;
import com.ztzh.ui.utils.ImageUtil;
import com.ztzh.ui.utils.MD5Util;

@RestController
public class TestController {
	Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired 
	TestService testService;
	
	@Autowired
	FTPUtil ftpUtil;
	
	@Autowired
	UploadFileService uploadFileService;
	
	@Autowired
	ImageMagickUtil imageMagicUtil;
	
	@Autowired
	CanvasInfoService canvasInfoService;
	
	@Value("${material.catch.operation.url}")
	private String catchOperationUrl;
	
	@Autowired
	ImageConvertService imageConvertService;
	
	@Autowired
	LoginInfoRecordService loginInfoRecordService;
	
	@RequestMapping(value="/test")
	public String testInsert() {
		return "test";
	}

	@PostMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile  file) {
         testService.upload(file);
         return "success";
 
    }
	
	@RequestMapping(value="ftptest")
	public String hello(HttpServletRequest request, HttpServletResponse response) throws FTPConnectionClosedException, IOException, Exception{
		/*
		 * List<String> urls = new ArrayList<String>(); imageMagicUtil.convertType(new
		 * String[]{"D:\\house\\ui\\src\\main\\resources\\photo\\红包.ai"},
		 * "D:\\house\\ui\\src\\main\\resources\\photo\\red.png");
		 */
		String[] a = {"E:\\中天智慧\\中天智慧素材管理系统\\源文件\\test.psd"};
		imageMagicUtil.convertType(a, "D:\\catch\\photo\\1.pdf");
		return "";
	}
	
	@GetMapping("/delete")
    public void upload() throws IOException, InterruptedException, IM4JavaException {
		String[] s = {"D:\\catch\\mouse.ai"};
		imageMagicUtil.convertType(s, "D:\\catch\\12.png");
 
    }
	
	@RequestMapping(value="base")
	public String base() throws FTPConnectionClosedException, IOException, Exception{
			File file = new File("D:\\catch\\png\\15997457249846.png");
			InputStream input = new FileInputStream(file);
			ftpUtil.uploadToFtp(input, "123.jpg", false, "/12345678902/"+UserConstants.FTP_PNG_DIRECTORY);
		/*
		 * File file = new File("C:\\Users\\25002\\Desktop\\photo\\12.jpg"); InputStream
		 * input = new FileInputStream(file); ftpUtil.uploadToFtp(input, "123.jpg",
		 * false, "/12345678902/"+UserConstants.FTP_PNG_DIRECTORY);
		 */
	        return "";
	}
	
	@RequestMapping(value="similar")
	public String similar(@RequestParam(value="filePath") String filePath) throws IOException {
		File inputFile = new File(filePath);
		BufferedImage sourceImage = ImageIO.read(inputFile);
		
		int width = 8;
		int height =8;
		int type = sourceImage.getType();
		BufferedImage thumbImage = null;
		double sx = (double) width / sourceImage.getWidth();
		double sy = (double) height / sourceImage.getHeight();
		if(sx>sy) {
			sx=sy;
			width = (int)(sx*sourceImage.getWidth());
		}else {
			sy=sx;
			height = (int)(sy*sourceImage.getHeight());
		}
		if(type==BufferedImage.TYPE_CUSTOM) {
			ColorModel cm = sourceImage.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
			boolean alphaPremultipiled = cm.isAlphaPremultiplied();
			thumbImage = new BufferedImage(cm, raster, alphaPremultipiled, null);
		}else {
			thumbImage = new BufferedImage(width, height, type);
		}
		Graphics2D g = thumbImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(sourceImage, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		int[] pixels = new int[width*height];
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				logger.info("{}",thumbImage.getRGB(i, j));
				pixels[i*height+j] = rgbToGray(thumbImage.getRGB(i, j));
			}
		}
		int avgPixel = 0;
		int m = 0;
		for(int i=0;i<pixels.length;i++) {
			m+=pixels[i];
		}
		m = m/pixels.length;
		avgPixel = m;
		int[] comps = new int[width*height];
		for(int i=0;i<comps.length;i++) {
			if(pixels[i]>=avgPixel) {
				comps[i] = 1;
			}else {
				comps[i] = 0;
			}
		}
		StringBuffer hashCode = new StringBuffer();
		for(int i=0;i<comps.length;i+=4) {
			int result = comps[i] * (int) Math.pow(2, 3) + comps[i + 1] * (int) Math.pow(2, 2)+ comps[i + 2] * (int) Math.pow(2, 1) + comps[i + 2];
			hashCode.append(Integer.toHexString(result&0xff));
		}
		String sourceHashCode = hashCode.toString();
		
		return sourceHashCode;
	}
	
	private static int rgbToGray(int pixels) {
		int _red = _red = (pixels >> 16)&0xFF;
		int _green = (pixels >> 8)&0xFF;
		int _blue = (pixels)&0xFF;
		return (int)(0.3*_red+0.59*_green+0.11*_blue);
	}
	
	private static int difference(String sourceHashCode, String targetHashCode) {
		int difference = 0;
		int len = sourceHashCode.length();
		for(int i=0;i<len;i++) {
			if(sourceHashCode.charAt(i) != targetHashCode.charAt(i)) {
				difference++;
			}
		}
		return difference;
	}
	
	@RequestMapping(value="recorde")
	public String recorde(HttpServletRequest request) {
		LoginInfoForRedisBo loginInfoForRedisBo = new LoginInfoForRedisBo();
		loginInfoForRedisBo.setUserId(15L);
		loginInfoForRedisBo.setLoginTime(new Date());
		loginInfoForRedisBo.setIpAddress(getIpAddress(request));
		logger.info("{}",loginInfoRecordService.get("15"));
		loginInfoRecordService.set("15", loginInfoForRedisBo);
		logger.info("{}",loginInfoRecordService.get("15"));
		return "";
	}
	
	private String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
