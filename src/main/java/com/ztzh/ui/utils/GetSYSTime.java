package com.ztzh.ui.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GetSYSTime {
	static Logger logger = LoggerFactory.getLogger(GetSYSTime.class);
	public static Date systemTime(){
		/*Date systemTime = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");//可以方便地修改日期格式
		String nowTime = dateFormat.format(systemTime);
		logger.info("系统时间{}",systemTime);
		return nowTime;*/
		Date nowTime = new java.sql.Date(new Date().getTime());
	    return nowTime;
	}
}
