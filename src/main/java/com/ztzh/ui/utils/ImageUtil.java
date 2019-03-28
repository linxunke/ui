package com.ztzh.ui.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ztzh.ui.bo.ImageColorBo;
import com.ztzh.ui.po.MaterialInfoDomain;

public class ImageUtil {
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	
	public static ImageColorBo getColorPercentage(File file) {
		logger.info("进入获取图片颜色方法");
		List<Integer> white = new ArrayList<Integer>();
		List<Integer> black = new ArrayList<Integer>();
		List<Integer> red = new ArrayList<Integer>();
		List<Integer> blue = new ArrayList<Integer>();
		List<Integer> brown = new ArrayList<Integer>();
		List<Integer> yellow = new ArrayList<Integer>();
		List<Integer> rose = new ArrayList<Integer>();
		List<Integer> orange = new ArrayList<Integer>();
		List<Integer> skyBlue = new ArrayList<Integer>();
		List<Integer> green = new ArrayList<Integer>();
		List<Integer> purple = new ArrayList<Integer>();
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int width = bi.getWidth();
		int height = bi.getHeight();
		int minx = bi.getMinX();
		int miny = bi.getMinY();
		logger.info("图片的宽度为：{}，图片的高度为：{}",width,height);
		for (int i = minx; i < width; i++) {
			for (int j = miny; j < height; j++) {
				int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
				int rgbR = (pixel & 0xff0000) >> 16;
				int rgbG = (pixel & 0xff00) >> 8;
				int rgbB = (pixel & 0xff);
				if(217<rgbR&&217<rgbG&&217<rgbB) {
					//判断为白色
					white.add(pixel);
				}else if(rgbR<80&&rgbG<80&&rgbB<80) {
					//判断为黑色
					black.add(pixel);
				}else if(188<rgbR&&rgbG>20&&rgbG<35&&rgbB>20&&rgbB<35) {
					//判断为红色
					red.add(pixel);
				}else if(10>rgbR&&115>rgbG&&85<rgbG&&178<rgbB) {
					//判断为蓝色
					blue.add(pixel);
				}else if(98<rgbR&&rgbR<132&&60>rgbG&&25<rgbG&&60>rgbB&&25<rgbB) {
					//判断为褐色
					brown.add(pixel);
				}else if(178<rgbR&&163<rgbG&&rgbG<220&&38>rgbB) {
					//判断为黄色
					yellow.add(pixel);
				}else if(182<rgbR&&rgbR<247&&58<rgbG&&78>rgbG&&158<rgbB&&213>rgbB) {
					//判断为玫红
					rose.add(pixel);		
				}else if(180<rgbR&&115>rgbG&&rgbG>85&&rgbB<60) {
					//判断为橙色
					orange.add(pixel);
				}else if(60>rgbR&&155<rgbG&&rgbG<210&&rgbB>170) {
					//判断为天蓝
					skyBlue.add(pixel);
				}else if(rgbR>75&&rgbR<102&&rgbG>141&&rgbG<192&&rgbB<94&&rgbB>69) {
					//判断为绿色
					green.add(pixel);
				}else if(75<rgbR&&170>rgbR&&70<rgbG&&130>rgbG&&140<rgbB) {
					//判断为紫色
					purple.add(pixel);
				}
			}
		}
		Integer total = white.size()+black.size()+red.size()+blue.size()+
				brown.size()+yellow.size()+rose.size()+orange.size()+
				skyBlue.size()+green.size()+purple.size();
		float whitePercentage = (float)white.size()/total;
		float blackPercentage = (float)black.size()/total;
		float redPercentage = (float)red.size()/total;
		float bluePercentage = (float)blue.size()/total;
		float brownPercentage = (float)brown.size()/total;
		float yellowPercentage = (float)yellow.size()/total;
		float rosePercentage = (float)rose.size()/total;
		float orangePercentage = (float)orange.size()/total;
		float skyBluePercentage = (float)skyBlue.size()/total;
		float greenPercentage = (float)green.size()/total;
		float purplePercentage = (float)purple.size()/total;
		float blackWhitePercentage = whitePercentage+blackPercentage;
		float[] colors = {whitePercentage,blackPercentage,redPercentage,
				bluePercentage,brownPercentage,yellowPercentage,rosePercentage,
				orangePercentage,skyBluePercentage,greenPercentage,purplePercentage};
		int maxArrary = getMaxArrary(colors);
		float maxPercentage = colors[maxArrary];
		String colorType = getColorType(colors,maxArrary);
		if(blackWhitePercentage>0.98) {
			//判断为黑白
			maxPercentage = blackWhitePercentage;
			colorType = ImageColorBo.COLOR_BLACK_WHITE_CODE;
		}
		ImageColorBo imageColorBo = new ImageColorBo();
		imageColorBo.setColorPercentage(maxPercentage);
		imageColorBo.setColorType(colorType);
		logger.info("颜色百分比:{}",imageColorBo);
		return imageColorBo;
	}
	private static String getColorType(float[] colors,int count) {
		String colorType = "";
		if(colors[0]+colors[1]==1) {
			colorType = ImageColorBo.COLOR_BLACK_WHITE_CODE;
		}
		if(count==0) {
			colorType = ImageColorBo.COLOR_WHITE_CODE;
		}else if(count==1) {
			colorType = ImageColorBo.COLOR_BLACK_CODE;
		}else if(count==2) {
			colorType = ImageColorBo.COLOR_RED_CODE;
		}else if(count==3) {
			colorType = ImageColorBo.COLOR_BLUE_CODE;
		}else if(count==4) {
			colorType = ImageColorBo.COLOR_BROWN_CODE;
		}else if(count==5) {
			colorType = ImageColorBo.COLOR_YELLOW_CODE;
		}else if(count==6) {
			colorType = ImageColorBo.COLOR_ROSE_RED_CODE;
		}else if(count==7) {
			colorType = ImageColorBo.COLOR_ORANGE_CODE;
		}else if(count==8) {
			colorType = ImageColorBo.COLOR_SKY_BLUE_CODE;
		}else if(count==9) {
			colorType = ImageColorBo.COLOR_GREEN_CODE;
		}else if(count==10) {
			colorType = ImageColorBo.COLOR_PURPLE_CODE;
		}
		return colorType;
	}
	
	private static int getMaxArrary (float[] colors) {
		float max = colors[0];
		int count = 0;
		for(int n=0;n<colors.length;n++) {
			if(max<colors[n]) {
				max = colors[n];
				count = n;
			}
		}
		return count;
	}

}
