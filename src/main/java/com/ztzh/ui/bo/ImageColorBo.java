package com.ztzh.ui.bo;

public class ImageColorBo {
	public static String COLOR_WHITE_CODE = "1";
	public static String COLOR_BLACK_WHITE_CODE = "2";
	public static String COLOR_BLACK_CODE = "3";
	public static String COLOR_RED_CODE = "4";
	public static String COLOR_BLUE_CODE = "5";
	public static String COLOR_BROWN_CODE = "6";
	public static String COLOR_YELLOW_CODE = "7";
	public static String COLOR_ROSE_RED_CODE = "8";
	public static String COLOR_ORANGE_CODE = "9";
	public static String COLOR_SKY_BLUE_CODE = "10";
	public static String COLOR_GREEN_CODE = "11";
	public static String COLOR_PURPLE_CODE = "12";
	public String colorType;
	public float colorPercentage;
	public String getColorType() {
		return colorType;
	}
	public void setColorType(String colorType) {
		this.colorType = colorType;
	}
	public float getColorPercentage() {
		return colorPercentage;
	}
	public void setColorPercentage(float colorPercentage) {
		this.colorPercentage = colorPercentage;
	}
	@Override
	public String toString() {
		return "ImageColorBo [colorType=" + colorType + ", colorPercentage=" + colorPercentage + "]";
	}
	

}
