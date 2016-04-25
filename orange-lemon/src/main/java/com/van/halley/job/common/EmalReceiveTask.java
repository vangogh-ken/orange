package com.van.halley.job.common;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author Vangogh 邮件自动接收任务
 * 
 */
@Component
public class EmalReceiveTask {
	public List<Map<String, String>> getEmailList() {
		return null;
	}

	//@Scheduled(cron="*/60 * * ? * *")
	public void receive() {
		System.out.println("定时任务执行完成。。。。。。");
	}

}
