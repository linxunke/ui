package com.ztzh.ui.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.core.ImageCommand;
import org.im4java.core.ImageMagickCmd;
import org.im4java.core.MontageCmd;
import org.im4java.process.ArrayListOutputConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;








@Component
public class ImageMagickUtil {
	 /** * ImageMagick的路径 */
	
	@Value("${imagemagickpath.address}")
    private  String imageMagickPath;
	
	@Value("${material.catch.png.url}")
    private  String catchOperationUrl;
	
	@Value("${imagemagickpath.running.system}")
    private  String runningSystem;
	
	
	private static final Logger logger = LoggerFactory.getLogger(ImageMagickUtil.class);
	public int getSize(String imagePath) {
		int size = 0;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(imagePath);
			size = inputStream.available();
			inputStream.close();
			inputStream = null;
		} catch (FileNotFoundException e) {
			size = 0;
			System.out.println("文件未找到!");
		} catch (IOException e) {
			size = 0;
			System.out.println("读取文件大小错误!");
		} finally {
			// 可能异常为关闭输入流,所以需要关闭输入流
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					System.out.println("关闭文件读入流异常");
				}
				inputStream = null;

			}
		}
		return size;
	}

	/**
	 * 获得图片的宽度
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 图片宽度
	 */
	public int getWidth(String imagePath) {
		int line = 0;
		try {
			IMOperation op = new IMOperation();
			op.format("%w"); // 设置获取宽度参数
			op.addImage(1);
			IdentifyCmd identifyCmd = new IdentifyCmd(true);
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			identifyCmd.setOutputConsumer(output);
			identifyCmd.run(op, imagePath);
			ArrayList<String> cmdOutput = output.getOutput();
			assert cmdOutput.size() == 1;
			line = Integer.parseInt(cmdOutput.get(0));
		} catch (Exception e) {
			line = 0;
			System.out.println(e.getMessage());
		}
		return line;
	}

	/**
	 * 获得图片的高度
	 * 
	 * @param imagePath
	 *            文件路径
	 * @return 图片高度
	 */
	public int getHeight(String imagePath) {
		int line = 0;
		try {
			IMOperation op = new IMOperation();
			op.format("%h"); // 设置获取高度参数
			op.addImage(1);
			IdentifyCmd identifyCmd = new IdentifyCmd(true);
			identifyCmd.setSearchPath(imageMagickPath);
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			identifyCmd.setOutputConsumer(output);
			identifyCmd.run(op, imagePath);
			ArrayList<String> cmdOutput = output.getOutput();
			assert cmdOutput.size() == 1;
			line = Integer.parseInt(cmdOutput.get(0));
		} catch (Exception e) {
			line = 0;
			System.out.println("运行指令出错!"+e.toString());
		}
		return line;
	}

	/**
	 * 图片信息
	 * 
	 * @param imagePath
	 * @return
	 */
	public static String getImageInfo(String imagePath) {
		String line = null;
		try {
			IMOperation op = new IMOperation();
			op.format("width:%w,height:%h,path:%d%f,size:%b%[EXIF:DateTimeOriginal]");
			op.addImage(1);
			IdentifyCmd identifyCmd = new IdentifyCmd(true);
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			identifyCmd.setOutputConsumer(output);
			identifyCmd.run(op, imagePath);
			ArrayList<String> cmdOutput = output.getOutput();
			assert cmdOutput.size() == 1;
			line = cmdOutput.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * 裁剪图片
	 * 
	 * @param imagePath
	 *            源图片路径
	 * @param newPath
	 *            处理后图片路径
	 * @param x
	 *            起始X坐标
	 * @param y
	 *            起始Y坐标
	 * @param width
	 *            裁剪宽度
	 * @param height
	 *            裁剪高度
	 * @return 返回true说明裁剪成功,否则失败
	 */
	public boolean cutImage(String imagePath, String newPath, int x, int y,
			int width, int height) {
		boolean flag = false;
		try {
			IMOperation op = new IMOperation();
			op.addImage(imagePath);
			/** width：裁剪的宽度 * height：裁剪的高度 * x：裁剪的横坐标 * y：裁剪纵坐标 */
			op.crop(width, height, x, y);
			op.addImage(newPath);
			ConvertCmd convert = new ConvertCmd(true);
			convert.setSearchPath(imageMagickPath);
			convert.run(op);
			flag = true;
		} catch (IOException e) {
			System.out.println("文件读取错误!");
			flag = false;
		} catch (InterruptedException e) {
			flag = false;
		} catch (IM4JavaException e) {
			flag = false;
		} finally {

		}
		return flag;
	}

	/**
	 * 根据尺寸缩放图片[等比例缩放:参数height为null,按宽度缩放比例缩放;参数width为null,按高度缩放比例缩放]
	 * 
	 * @param imagePath
	 *            源图片路径
	 * @param newPath
	 *            处理后图片路径
	 * @param width
	 *            缩放后的图片宽度
	 * @param height
	 *            缩放后的图片高度
	 * @return 返回true说明缩放成功,否则失败
	 */
	public boolean zoomImage(String imagePath, String newPath, Integer width,
			Integer height) {

		boolean flag = false;
		try {
			IMOperation op = new IMOperation();
			op.addImage(imagePath);
			if (width == null) {// 根据高度缩放图片
				op.resize(null, height);
			} else if (height == null) {// 根据宽度缩放图片
				op.resize(width);
			} else {
				op.resize(width, height);
			}
			op.addImage(newPath);
			ConvertCmd convert = new ConvertCmd();
			convert.setSearchPath(imageMagickPath);
			convert.run(op);
			flag = true;
		} catch (IOException e) {
			System.out.println("文件读取错误!");
			flag = false;
		} catch (InterruptedException e) {
			flag = false;
		} catch (IM4JavaException e) {
			System.out.println(e.toString());
			flag = false;
		} finally {

		}
		return flag;
	}

	/**
	 * 图片旋转
	 * 
	 * @param imagePath
	 *            源图片路径
	 * @param newPath
	 *            处理后图片路径
	 * @param degree
	 *            旋转角度
	 */
	public boolean rotate(String imagePath, String newPath, double degree) {
		boolean flag = false;
		try {
			// 1.将角度转换到0-360度之间
			degree = degree % 360;
			if (degree <= 0) {
				degree = 360 + degree;
			}
			IMOperation op = new IMOperation();
			op.addImage(imagePath);
			op.rotate(degree);
			op.addImage(newPath);
			ConvertCmd cmd = new ConvertCmd();
			cmd.run(op);
			flag = true;
		} catch (Exception e) {
			logger.info(e.getMessage());
			flag = false;
			System.out.println("图片旋转失败!");
		}
		return flag;
	}
	
	
	/**
	 * 转换格式
	 * @param imagePath
	 * @param newPath
	 * @return
	 * @throws IM4JavaException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public boolean convertType(String[] imagePath, String newPath) throws IOException, InterruptedException, IM4JavaException {
		boolean flag = false;
		IMOperation op = new IMOperation();
		op.addImage(imagePath);
		op.addImage(newPath);
		ConvertCmd cmd = new ConvertCmd();
		if(runningSystem.equals("windows")) {
			cmd.setSearchPath(imageMagickPath);
		}
		cmd.run(op);
		flag = true;
		return flag;
	}
	
	public String iconDisplay(List<String> urls) throws IOException, InterruptedException, IM4JavaException {
		IMOperation op = new IMOperation();
		op.addImage(this.getClass().getResource("/resources/img/iconBackground.png").getPath().substring(1).replace("/", "\\"));
		op.compose("atop");
		
		/*
		 * op.addImage("E:\\catch\\png\\a51155d627054fc885e6ce1a0ca0b0f2.png");
		 * op.geometry(15,15,10,10).composite();
		 * op.addImage("E:\\catch\\png\\a51155d627054fc885e6ce1a0ca0b0f2.png");
		 * op.geometry(15,15,55,10).composite();
		 * op.addImage("E:\\catch\\png\\a51155d627054fc885e6ce1a0ca0b0f2.png");
		 * op.geometry(15,15,100,10).composite();
		 * op.addImage("E:\\catch\\png\\a51155d627054fc885e6ce1a0ca0b0f2.png");
		 * op.geometry(15,15,145,10).composite();
		 * op.addImage("E:\\catch\\png\\a51155d627054fc885e6ce1a0ca0b0f2.png");
		 * op.geometry(15,15,190,10).composite();
		 * op.addImage("E:\\catch\\png\\a51155d627054fc885e6ce1a0ca0b0f2.png");
		 * op.geometry(15,15,10,50).composite();
		 * op.addImage("E:\\catch\\png\\a51155d627054fc885e6ce1a0ca0b0f2.png");
		 * op.geometry(15,15,10,90).composite();
		 * op.addImage("E:\\catch\\png\\a51155d627054fc885e6ce1a0ca0b0f2.png");
		 * op.geometry(15,15,10,130).composite();
		 */
		 
		
		
		int count = 0;
		for(int i=0;i<urls.size();i++) { 
			op.addImage(urls.get(i));
			op.geometry(15,15,10+(count%5*45),10+(count/5*40)).composite();
			count++;
		}
		 
		String resultImageUrl = catchOperationUrl+UUID.randomUUID().toString().replace("-", "")+".png";
		op.addImage(resultImageUrl);
		ConvertCmd cmd = new ConvertCmd();
		/* MontageCmd cmd = new MontageCmd(); */
		if(runningSystem.equals("windows")) {
			cmd.setSearchPath(imageMagickPath);
		}
		cmd.run(op);
		return resultImageUrl;
		
	}

}


