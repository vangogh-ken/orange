package com.van.halley.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.van.halley.db.persistence.entity.PersistenceObject;

public class DynamicCompileCodeUtils {
	private static String realPath = null;
	private static Logger logger = LoggerFactory.getLogger(DynamicCompileCodeUtils.class);

	static {
		realPath = PersistenceObject.class.getClassLoader().getResource("")
				.getPath();
		if(realPath.endsWith("/")){
			realPath = realPath.substring(1, realPath.length()-1);
		}
	}

	public static void createFileToCompile(String classString, String text) {
		String className = classString.substring(
				classString.lastIndexOf(".") + 1, classString.length());
		String[] strs = classString.split("\\.");
		
		String targetPath = realPath;
		String sourcePath = realPath;
		for(int i=0, len = strs.length; i<len-1; i++){
			sourcePath += "/" + strs[i];
		}
		
		File file = new File(sourcePath, className + ".java");
		PrintWriter writer = null;
		try {
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			writer = new PrintWriter(new FileWriter(file));
			writer.write(text);
			writer.close();
			
			JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
			int status = javac.run(null, null, null, "-d", targetPath + File.separator, "-classpath", targetPath, sourcePath + File.separator + className + ".java");
			if (status != 0) {
				logger.info("编译失败   类名:{}  路径:{}", className, sourcePath);
			} else {
				logger.info("编译成功   类名:{}  路径:{}", className, sourcePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
