package com.van.halley.util.swf;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;


public class PathUtil {
	public static String getWEBINFDir() {
		String path = null;
		try {
			path = PathUtil.class.getResource("").toURI().getPath();
			path = path.substring(0, path.indexOf("classes"));
			return path;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @return WebRootĿ¼�ľ��·��
	 */
	public static String getWebRootDir() {
		String path = null;
		String folderPath = PathUtil.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		if (folderPath.indexOf("WEB-INF") > 0) {
			path = folderPath.substring(0, folderPath
					.indexOf("WEB-INF/classes"));
		}
		path = path.replaceAll("%20", " ");
		return path;
	}
	
	/**
	 * @param args
	 */
	public static String getWebRootDirFilePath(String dir){
		File file = new File(getWebRootDir(), dir);
		if(! file.exists()){
			file.mkdirs();
		}
		return file.getAbsolutePath();
	}
	
	/**
	 * @param args
	 */
	public static String getWebInfoDirFilePath(String dir){
		File file = new File(getWebRootDir(), dir);
		if(!file.exists()){
			file.mkdirs();
		}
		try {
			return file.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
