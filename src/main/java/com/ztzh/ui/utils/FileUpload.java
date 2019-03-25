package com.ztzh.ui.utils;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


public class FileUpload {
	private static Logger logger = LoggerFactory.getLogger(FileUpload.class);
	/**
     * 文件上传处理
     *
     * @param file
     * @return
     */
    public static String writeUploadFile(MultipartFile file,String filepath) {
    	logger.info(filepath);
        String filename = file.getOriginalFilename();
        File fileDir = new File(filepath);
        if (!fileDir.exists())
            fileDir.mkdirs();
 
        String extname = FilenameUtils.getExtension(filename);
       /* String allowImgFormat = "gif,jpg,jpeg,png";
        if (!allowImgFormat.contains(extname.toLowerCase())) {
            return "NOT_IMAGE";
        }*/
         
        filename = Math.abs(file.getOriginalFilename().hashCode()) + RandomUtil.createRandomString( 4 ) + "." + extname;
        InputStream input = null;
        FileOutputStream fos = null;
        try {
            input = file.getInputStream();
            fos = new FileOutputStream(filepath + "/" + filename);
            IOUtils.copy(input, fos);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(fos);
        }
        logger.info("文件名称:{}",filename);
        logger.info(filepath+"\\" + filename);
        return filepath+"\\" + filename;
    }
    
    public static void base64ToFile(String base64, String realPath, String fileName){
    	File file = null;
    	//创建文件夹
    	String filePath = realPath;
    	File dir = new File(filePath);
    	if(!dir.exists()&&!dir.isDirectory()){
    		dir.mkdirs();
    	}
    	BufferedOutputStream bos = null;
    	FileOutputStream fos = null;
    	try{
    		byte[] bytes = Base64.getDecoder().decode(base64);
    		file = new File(filePath+"/"+fileName);
    		fos = new FileOutputStream(file);
    		bos = new BufferedOutputStream(fos);
    		bos.write(bytes);
    	}catch (Exception e){
    		e.printStackTrace();
    	}finally{
    		if(bos != null){
    			try{
    				bos.close();
    			}catch(IOException e){
    				e.printStackTrace();
    			}
    		}
    		if(fos != null){
    			try{
    				fos.close();
    			}catch(IOException e){
    				e.printStackTrace();
    			}
    		}
    	}
    }
    
    public static void deleteFiles(List<String> filesUrl) {
    	List<File> files = new ArrayList<File>();
    	for(String fileUrl:filesUrl) {
    		files.add(new File(fileUrl));
    	}
    	for(File file:files) {
    		file.delete();
    	}
    }
    public static String convertPath(String oldPath){
    	String newPath="";
    	for(int i = 0; i <oldPath.length(); i++){
    		if(oldPath.charAt(i) == '\\'){
    			newPath += "\\\\";
    		}else {
				newPath += oldPath.charAt(i);
			}
    	}
    	return newPath;
    }

}
