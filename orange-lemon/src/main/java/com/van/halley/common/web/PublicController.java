package com.van.halley.common.web;


import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.van.halley.core.util.ServletUtils;
import com.van.halley.db.persistence.entity.DiskInfo;
import com.van.service.DiskInfoService;

/**
 * 无验证controller
 * @author anxinxx
 *
 */
@Controller
@RequestMapping("/public/")
public class PublicController {
	@Autowired
	private DiskInfoService diskInfoService;
	
	@RequestMapping("disk-info-done-download-disk")
    public void download(@RequestParam("diskInfoId") String[] diskInfoId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DiskInfo diskInfo = diskInfoService.getById(diskInfoId[0]);
		if(diskInfoId.length == 1){
			ServletUtils.setFileDownloadHeader(request, response, "dir".equals(diskInfo.getFileSuffix()) ? (diskInfo.getFileName() + ".zip") : diskInfo.getFileName());
		}else{
			ServletUtils.setFileDownloadHeader(request, response, (StringUtils.isNotBlank(diskInfo.getFileDir()) ? diskInfo.getFileDir() : "压缩文件") + ".zip");
		}
		
		InputStream in = null;
		OutputStream out = null;
		try{
			in = diskInfoService.doneLoadDisk(diskInfoId);
			out = response.getOutputStream();
			IOUtils.copy(in, out);
			out.flush();
		}finally{
			if(in != null){
				in.close();
			}
		}
    }
}
