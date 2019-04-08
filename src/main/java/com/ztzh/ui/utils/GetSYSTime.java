package com.ztzh.ui.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GetSYSTime {
	static Logger logger = LoggerFactory.getLogger(GetSYSTime.class);
	public static Date systemTime(){
	    return new Date();
	}
}
