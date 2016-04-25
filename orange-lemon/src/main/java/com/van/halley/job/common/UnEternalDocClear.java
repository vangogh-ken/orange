package com.van.halley.job.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.van.halley.db.persistence.entity.DocInfo;
import com.van.service.DocInfoService;

/**
 * @author Think
 * 删除临时文档
 */
@Component
public class UnEternalDocClear {
	private static Logger logger = LoggerFactory.getLogger(UnEternalDocClear.class);
	@Autowired
	private DocInfoService docInfoService;

	
	//@Scheduled(cron = "0 0 1,12 * * ?")
	//@Scheduled(cron = "0 * * * * ?")
	// 每天1、12点都会处理
	//@Scheduled(cron = "0 0 1,12 * * ?")
	@Scheduled(cron="*/60 */5 * ? * *")
	public void execute() {
		List<DocInfo> docInfos = docInfoService.getUnEternalDoc();
		if(docInfos != null){
			//logger.info("开始清理临时文档, 临时文档数{}", docInfos.size());
			for (DocInfo docInfo : docInfos) {
				docInfoService.delete(docInfo.getId() + "");
			}
			//logger.info("临时文档清理完毕, 临时文档数{}", docInfos.size());
		}else{
			//logger.info("临时文档清理跳过, 临时文档数{}", 0);
		}
	}
}
