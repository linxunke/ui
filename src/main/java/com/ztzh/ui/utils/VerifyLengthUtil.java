package com.ztzh.ui.utils;

public class VerifyLengthUtil {

	public static int objectLength(String str){
		int aLenth = str.length();
		if(aLenth<6 || aLenth>50 || str == null){
			return 1;
		}else{
			return 0;
		}
	}
}
