package com.van.halley.mybatis.mapper.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileCreation {
	/*
	 * @param filePath
	 * @param content 对时间、附件、textArea 做特殊处理。其他都使用input text
	 */
	public static void doCreate(String fileName, String content) {
		// 创建目录
		File dirsFile = new File("C:\\T\\");
		// 创建文件
		File file = new File("C:\\T\\" + fileName);

		try {
			if (!dirsFile.exists()) {
				dirsFile.mkdir();
			}
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter writer = new FileWriter(file);
			writer.write(content, 0, content.length());
			writer.flush();
			writer.close();
			System.out.println(fileName + "文件被写入数据！");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
