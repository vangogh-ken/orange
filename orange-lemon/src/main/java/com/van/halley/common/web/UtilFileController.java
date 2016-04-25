package com.van.halley.common.web;


import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.van.halley.util.file.FileUtil;

@Controller
@RequestMapping("/util/")
public class UtilFileController {
	@RequestMapping("down-file")
	public void convertToSwf(@RequestParam(value="fileData", required=true)String fileData, 
			@RequestParam(value="fileName", required=true)String fileName, HttpServletResponse response){
		FileUtil.download(fileData, fileName, response);
	}
	
}
