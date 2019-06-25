package com.ztzh.ui.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ztzh.ui.utils.FileUpload;

@Component
public class CatchTimer {
	
	@Value("${material.catch.png.url}")
	private String catchPngUrl;
	
	
	@Scheduled(cron = "00 00 03 * * ?")  //cron = (秒, 分, 时, 日, 月 ....)
	public void cleanPngCatchOnTime() {
        List<String> urlList = this.getChildFileUrls(catchPngUrl);
        FileUpload.deleteFiles(urlList);
    }
	
	/*获取一个文件夹下的所有文件（非文件夹）的绝对路径*/
	public List<String> getChildFileUrls(String directoryPath){
		List<String> urlList = new ArrayList<String>();
		File[] fileList = new File(directoryPath).listFiles();
		for(File tempFile : fileList){
			if(tempFile.isFile()){
				urlList.add(tempFile.getAbsolutePath());
			}
		}
		return urlList;
	}
}
