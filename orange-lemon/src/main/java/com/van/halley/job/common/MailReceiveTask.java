package com.van.halley.job.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.van.core.spring.ApplicationContextHelper;
import com.van.halley.core.mail.MailService;
import com.van.halley.db.persistence.entity.UserBase;
import com.van.service.UserBaseService;

@Component
public class MailReceiveTask {
	private Logger logger = LoggerFactory.getLogger(MailReceiveTask.class);
	private UserBaseService userBaseService;
	private MailService mailService;
	
	public UserBaseService getUserBaseService() {
		return userBaseService;
	}


	public void setUserBaseService(UserBaseService userBaseService) {
		this.userBaseService = userBaseService;
	}


	//@Scheduled(cron="*/30 * * ? * *")
	//@Scheduled(cron="*/60 */5 * ? * *")暂时取消流程自动同步。
	public void receive() {
		//logger.info("自动接收邮件任务开始进行...");
		if(userBaseService == null){
			userBaseService = ApplicationContextHelper.getBean("userBaseService");
		}
		/*if(mailFacade == null){
			mailFacade = ApplicationContextHelper.getBean(MailServiceFacade.class);
		}
		for(UserBase userBase : userBaseService.getAll()){
			if(userBase.getMailAsync() != null && userBase.getMailAsync().equals("T") 
					&& userBase.getMailAddress() != null && userBase.getMailPassword() != null){
				mailFacade.receiveMail(userBase.getMailAddress(), userBase.getMailPassword(), userBase.getUserId());
			}
			
		}*/
	}
}
