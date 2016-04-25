package com.van.halley.util.swf;

import java.io.File;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Office转换为PDF
 */
public class Office2PdfUtil {
	private static Logger logger = LoggerFactory.getLogger(Office2PdfUtil.class);
	private static OfficeManager officeManager;
	public static String OFFICE_PATH;
	private static int port[] = {8100, 8110, 8120};
	private static OfficeDocumentConverter converter = null;

	public static File convert2PDF(File sourceFile) {
		long startTime = System.currentTimeMillis();
		File targetFile = new File(PathUtil.getWebRootDirFilePath("pdf"), sourceFile.getName().split("\\.")[0] + ".pdf");
		try{
			if(converter == null){
				startService();
				logger.info("进行文档转换:" + sourceFile.getName());
				converter = new OfficeDocumentConverter(officeManager);
			}
			converter.convert(sourceFile, targetFile);
		}catch (Exception e) {
			return targetFile;
		}finally{
			//stopService();不关闭服务
		}
		long endTime = System.currentTimeMillis();
		if (targetFile.exists()) {
			logger.info("生成" + targetFile.getName() + "耗费："+ (endTime - startTime)/1000 + "秒");
			return targetFile;
		} else {
			logger.info("文档转换失败。");
			return null;
		}
	}

	public static void startService() {
		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
		try {
			configuration.setOfficeHome(OFFICE_PATH);// 设置OpenOffice.org安装目录
			configuration.setPortNumbers(port); // 设置转换端口，默认为8100
			configuration.setMaxTasksPerProcess(3);//
			configuration.setTaskExecutionTimeout(1000 * 60 * 3L);// 设置任务执行超时
			configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);// 设置任务队列超时
			officeManager = configuration.buildOfficeManager();
			officeManager.start(); //启动服务
			logger.info("office转换服务启动成功!");
		} catch (Exception e) {
			logger.info("office转换服务启动失败!详细信息:" + e);
		}
	}

	public static void stopService() {
		logger.info("正在关闭office转换服务。");
		if (officeManager != null) {
			officeManager.stop();
		}
		logger.info("关闭office转换成功!");
	}

	public static void main(String[] args) {
		convert2PDF(new File("C:\\logs\\attachment\\af56475f-eacd-4346-9926-9f4e03198cf7.xls"));
	}
}
