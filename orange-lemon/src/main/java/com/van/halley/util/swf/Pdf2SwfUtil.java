package com.van.halley.util.swf;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * PDF转换为SWF
 * @author Think
 */
public class Pdf2SwfUtil {
	private static Logger logger = LoggerFactory.getLogger(Pdf2SwfUtil.class);
	public static String PDF2SWF_PATH;
	
	@SuppressWarnings("static-access")
	public static File convert2SWF(File sourceFile){
		File targetFile = new File(PathUtil.getWebRootDirFilePath("swf"), sourceFile.getName().split("\\.")[0] + ".swf");
		if(!sourceFile.getName().endsWith(".pdf")){
			logger.info("文件格式非PDF！");
			return null;
		}
		if(!sourceFile.exists()){
			logger.info("PDF文件不存在！");
			return null;
		}
		if(targetFile.exists()){
			logger.info("SWF文件已存在！");
			return targetFile;
		}
		String command = PDF2SWF_PATH +" \"" + sourceFile.getAbsolutePath() + "\" -s flashversion=9 -o " + targetFile.getAbsolutePath()+" -T 9 -f";
		try {
			long startTime = System.currentTimeMillis();
			logger.info("开始转换文档: "+ sourceFile.getName());
			Runtime.getRuntime().exec(command);
			while(!targetFile.exists()){
				try {
					Thread.currentThread().sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			long endTime = System.currentTimeMillis();
			logger.info("成功转换为SWF文件！耗时：" + (endTime - startTime)/1000 + "秒");
			return targetFile;
		} catch (IOException e) {
			logger.info("转换为swf文件失败！错误信息：{}", e);
			return null;
		}
	}
	

	/**
	public static boolean convert2SWF(String inputFile, String swfFile) {
		File pdfFile = new File(inputFile);
		File outFile = new File(swfFile);
		if(!inputFile.endsWith(".pdf")){
			logger.error("文件格式非PDF！");
			return false;
		}
		if(!pdfFile.exists()){
			logger.error("PDF文件不存在！");
			return false;
		}
		if(outFile.exists()){
			logger.error("SWF文件已存在！");
			return false;
		}
		String command = PDF2SWF_PATH +" \"" + inputFile + "\" -o " + swfFile+" -T 9 -f";
		try {
			System.out.println("开始转换文档: "+inputFile);
			Runtime.getRuntime().exec(command);
			System.out.println("成功转换为SWF文件！");
			return true;
		} catch (IOException e) {
			logger.info("转换为swf文件失败！错误信息：{}", e);
			return false;
		}
		
	}
	**/
	
}
