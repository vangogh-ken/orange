package com.van.halley.core.mail;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.van.halley.db.persistence.entity.MailReceive;
import com.van.halley.db.persistence.entity.MailSend;
import com.van.halley.util.file.FileUtil;
import com.van.service.MailReceiveService;
import com.van.service.MailSendService;

public class MailService {
	private Logger logger =  LoggerFactory.getLogger(MailService.class);
	private MailHostInfo mailInfo;
	//private MailReceiveService mailReceiveService;
	//private MailSendService mailSendService;
	
	/**
	 * 接收邮件,只需要知道邮件账户信息,邮件服务器信息后台自动匹配
	 */
	public void receive(String mailAddress, String password, String userId) {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
		Session session = Session.getDefaultInstance(properties, null);
		URLName url = new URLName(mailInfo.getReceiveHost(), mailInfo.getReceiveAddress(), mailInfo.getSendPort(), null, mailAddress, password);
		Folder folder = null;
        Store store = null;
        try {
			store = session.getStore(url);
			store.connect();
			folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			int num = folder.getMessageCount();
			logger.error("Just find {} items", num);
			Message[] messages = folder.getMessages();
			for(Message message : messages){
				MimeMessage msg = (MimeMessage) message;
				if(!MailUtils.isNew(msg)){
					continue;
				}

				MailReceive mail = new MailReceive();
				String msgId = "";
				String massageId = msg.getMessageID();
				massageId = massageId.substring(1, massageId.length()-1);
				if(massageId.contains("@")){
					
					for(String s : massageId.split("@")){
						msgId += s;
					}
				}else{
					msgId = massageId;
				}
				mail.setMessageId(msgId);
				
				/*MailReceive filter = new MailReceive();
				filter.setMessageId(msgId);
				List<MailReceive> list = mailReceiveService.queryForList(filter);
				if(list == null || list.isEmpty()){
					mail.setAddressFrom(MailUtils.getFrom(msg));
					mail.setAddressTo(MailUtils.getMailAddress("to", msg));
					mail.setAddressCopy(MailUtils.getMailAddress("cc", msg));
					
					mail.setSubject(msg.getSubject());
					mail.setContent(MailUtils.getSimpleContent(msg));
					mail.setReceiveTime(msg.getReceivedDate());
					mail.setCreateTime(msg.getSentDate());
					mail.setStatus("未读");
					mail.setUserId(userId);
					mailReceiveService.add(mail);
				}*/
				
			}
		} catch (NoSuchProviderException e) {
			logger.error("Could not find the host: {}", e);
		} catch (MessagingException e) {
			logger.error("MessagingException: {}", e);
		}  
		
	}
	
	public boolean send(String mailAddress, String password, String to, String subject, String content, String userId){
		boolean flag = false;
        
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");
		
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailInfo.getSendAddress());
        javaMailSender.setUsername(mailAddress);
        javaMailSender.setPassword(password);
        javaMailSender.setDefaultEncoding("UTF-8");
        javaMailSender.setJavaMailProperties(props);
		try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
            helper.setFrom(mailAddress);
            helper.setSubject(subject);
            helper.setTo(to);
            helper.setText(content, true);

            logger.info("send mail from {} to {}", mailAddress, to);
            logger.info("send mail content {}", content);
            javaMailSender.send(msg);
            
            /*MailSend mail = new MailSend();
            mail.setAddressFrom(mailAddress);
            mail.setAddressTo(to);
            mail.setSubject(subject);
            mail.setContent(content);
            mail.setUserId(userId);
            mailSendService.add(mail);*/
            
            flag = true;
        } catch (Exception e) {
            logger.error("send mail error", e);
            flag = false;
        }
		
		return flag;
	}
	/**
	 * 发送邮件,只需要知道邮件账户信息和邮件信息,邮件服务器信息后台自动匹配
	 */
	public boolean send(String mailAddress, String password, String to, String subject, String content, List<Map<String, String>> attachments, String userId) {
		boolean flag = false;
		
		if(attachments == null || attachments.isEmpty()){
			flag = send(mailAddress, password, to, subject, content, userId);
		}else{
			Properties properties = new Properties();
	        properties.put("mail.smtp.auth", true);
	        properties.put("mail.smtp.starttls.enable", true);
	        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
	        javaMailSender = new JavaMailSenderImpl();
	        javaMailSender.setHost(mailInfo.getSendAddress());
	        javaMailSender.setUsername(mailAddress);
	        javaMailSender.setPassword(password);
	        javaMailSender.setDefaultEncoding("UTF-8");
	        javaMailSender.setJavaMailProperties(properties);
			try {
	            MimeMessage msg = javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
	            helper.setFrom(mailAddress);
	            helper.setSubject(subject);
	            helper.setTo(to);
	            helper.setText(content, true);
	            for(Map<String, String> attachment : attachments){
	            	helper.addAttachment(attachment.get("fileName"), FileUtil.getAttachment(attachment.get("fileData")));
	            }
	            
	            logger.info("send mail from {} to {}", mailAddress, to);
	            logger.info("send mail content {}", content);
	            javaMailSender.send(msg);
	            flag = true;
	        } catch (Exception e) {
	            logger.error("send mail error", e);
	            flag = false;
	        }
		}
		
		return flag;
	}
}	
