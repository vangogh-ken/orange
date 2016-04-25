package com.van.halley.job.common;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;

/**
 * 用于转移用户头像数据
 * @author Think
 *
 */
@Component
public class UserIconTransferTask {
	public static Logger LOG = LoggerFactory.getLogger(UserIconTransferTask.class);
	
	@Scheduled(cron="59 * * * * ?")//每分钟执行
	public void execute(){
		if(!StringUtil.isNullOrEmpty(FileUtil.attachmentPath) &&
				!StringUtil.isNullOrEmpty(FileUtil.projectPath)){
			File target = new File(FileUtil.attachmentPath, "icon");
			File source = new File(FileUtil.projectPath, "icon");
			try {
				if(!source.exists()){
					source.mkdirs();
				}
				FileUtils.copyDirectory(source, target);
			} catch (IOException e) {
				LOG.error("icon 文件移动失败！source {} target {} 错误: {}", source, target, e);
			}
			
			try {
				if(!target.exists()){
					target.mkdirs();
				}
				FileUtils.copyDirectory(target, source);
			} catch (IOException e) {
				LOG.error("icon 文件移动失败！source {} target {} 错误: {}", source, target, e);
			}
		}
	}
}
