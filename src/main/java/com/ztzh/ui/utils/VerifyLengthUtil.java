package com.ztzh.ui.utils;

public class VerifyLengthUtil {

	public static int objectLengthForPassword(String str){
		int aLenth = str.length();
		if(aLenth<6 || aLenth>50 || str == null){
			return 1;
		}else{
			return 0;
		}
	}
	
	public static int objectLengthShortThan20(String str){
		int aLenth = str.length();
		if(aLenth>20 || str == null){
			return 1;
		}else{
			return 0;
		}
	}
}
