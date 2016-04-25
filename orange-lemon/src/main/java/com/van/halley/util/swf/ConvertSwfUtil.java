package com.van.halley.util.swf;

import java.io.File;
import java.util.Date;

import com.van.halley.util.file.FileUtil;

public class ConvertSwfUtil {
	public static String convert(String sourceFileName){
		File sourceFile = new File(FileUtil.attachmentPath, sourceFileName);
		if(sourceFile.exists()){
			//如果文件未10秒以内未更改，可尝试从已生成的文件中获取转换好的SFW文件
			if((new Date().getTime() - sourceFile.lastModified())/1000 - 10 > 0){
				File targetFile = new File(PathUtil.getWebRootDirFilePath("swf"), sourceFileName.split("\\.")[0] + ".swf");
				if(targetFile.exists()){
					return targetFile.getName();
				}else{
					targetFile = Pdf2SwfUtil.convert2SWF(Office2PdfUtil.convert2PDF(sourceFile));
					if(targetFile.exists()){
						return targetFile.getName();
					}else{
						return null;
					}
				}
			}else{
				File targetFile = Pdf2SwfUtil.convert2SWF(Office2PdfUtil.convert2PDF(sourceFile));
				if(targetFile.exists()){
					return targetFile.getName();
				}else{
					return null;
				}
			}
		}else{
			return null;
		}
	}
}
