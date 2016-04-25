package com.van.halley.core.mail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.van.halley.util.file.FileUtil;

public class MailUtils {
	private static Logger logger = LoggerFactory.getLogger(MailUtils.class);
	
	//////////////////////    接收邮件部分               ////////////////
	/**
	 * 获取邮件的文本内容
	 */
	public static String getSimpleContent(Part part){
		StringBuilder content = new StringBuilder();
		try {
			String contentType = part.getContentType();
			boolean isName = contentType.contains("name");
			
			if(part.isMimeType("text/plain") && !isName){
				content.append((String)part.getContent());
			}else if (part.isMimeType("text/html") && !isName){
				content.append((String)part.getContent());
			}else if(part.isMimeType("multipart/*")){
				Multipart mpart = (Multipart) part.getContent();
				for(int i=0, len = mpart.getCount(); i<len; i++){
					content.append(getSimpleContent(mpart.getBodyPart(i)));
				}
			}else if(part.isMimeType("message/rc822")){
				content.append(getSimpleContent((Part)part.getContent()));
			}
			
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString();
	}
	
	
	/**
	 * 检查邮件是否包含附件
	 */
	public static boolean isContainsAttachment(Part part){
		boolean flag = false;
		try {
			if(part.isMimeType("muiltpart/*")){
				Multipart mpart = (Multipart) part.getContent();
				for(int i=0, len = mpart.getCount(); i<len; i++){
					BodyPart bodyPart = mpart.getBodyPart(i);
					String disposition = bodyPart.getDisposition();
					if(disposition != null && 
							(disposition.equals(Part.ATTACHMENT) ||
									disposition.equals(Part.INLINE))){
						flag = true;
						break;
						
					}else if(bodyPart.isMimeType("muiltpart/*")){
						flag = isContainsAttachment(bodyPart);
					}else{
						String contentType = bodyPart.getContentType();
						if(contentType.toLowerCase().indexOf("application") != -1){
							flag = true;
							break;
						}else if(contentType.toLowerCase().indexOf("name") != -1){
							flag = true;
							break;
						}
					}
				}
			}else if(part.isMimeType("message/frc822")){
				flag = isContainsAttachment((Part)part.getContent());
			}
				
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	/**
	 * 保存邮件附件, 返回文件名,文件数据的Map的集合
	 */
	public static List<Map<String, String>> saveAttachment(Part part){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			if(part.isMimeType("muiltpart/*")){
				Multipart mpart = (Multipart) part.getContent();
				for(int i=0, len = mpart.getCount(); i<len; i++){
					BodyPart bodyPart = mpart.getBodyPart(i);
					String disposition = bodyPart.getDisposition();
					if(disposition != null && 
							(disposition.equals(Part.ATTACHMENT) ||
									disposition.equals(Part.INLINE))){
						String fileName = bodyPart.getFileName();
						if(fileName.toLowerCase().indexOf("gb2312") != -1){
							fileName = MimeUtility.decodeText(fileName);
						}
						
						list.add(FileUtil.saveAttachemnt(fileName, bodyPart.getInputStream()));
					}else if(bodyPart.isMimeType("muiltpart/*")){
						list.addAll(saveAttachment(bodyPart));
					}else{
						String fileName = bodyPart.getFileName();
						if(fileName != null){
							if(fileName.toLowerCase().indexOf("gb2312") != -1){
								fileName = MimeUtility.decodeText(fileName);
							}
							
							list.add(FileUtil.saveAttachemnt(fileName, bodyPart.getInputStream()));
						}
					}
				}
			}else if(part.isMimeType("message/frc822")){
				list.addAll(saveAttachment((Part)part.getContent()));
			}
				
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 判断邮件是否需要回执。
	 */
	public static boolean isNeedReplySign(MimeMessage msg){
		boolean flag = false;
		try {
			String[] needReply = msg.getHeader("Disposition-Notification-To");
			if(needReply != null){
				flag = true;
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	/**
	 * 查看邮件是否是最新的
	 */
	public static boolean isNew(MimeMessage msg){
		boolean result = true;
		try {
			Flags flags = msg.getFlags();
			Flags.Flag[] flag = flags.getSystemFlags();
			for(Flags.Flag f : flag){
				if(f == Flags.Flag.SEEN){
					result = false;
					break;
				}
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
		//////////////////////  发送邮件部分              ////////////////
	
	/**
	 * 附件内容
	 */
	public MimeBodyPart createAttachmentBody(Map<String, String> map){
		String fileName = map.get("fileName");
		String fileData = map.get("fileData");
		MimeBodyPart body = new MimeBodyPart();
		FileDataSource dataSource = new FileDataSource(FileUtil.getAttachment(fileData));
		try {
			body.setDataHandler(new DataHandler(dataSource));
			body.setFileName(fileName);
		} catch (MessagingException e) {
			logger.error("Create mail attachment error {}", e);
		}
		return body;
	}
	
	/**
	 * 正文部分
	 */
	public MimeBodyPart createTextBody(String text){
		MimeBodyPart body = new MimeBodyPart();
		try {
			body.setText(text);
		} catch (MessagingException e) {
			logger.error("Create mail text error {}", e);
		}
		return body;
	}
	
	/**
	 * 返回邮件发送人
	 */
	public static String getFrom(MimeMessage msg){
		String from = null;
		InternetAddress ia = null;
		try {
			from = MailUtils.decodeText(msg.getFrom()[0].toString());
			ia = new InternetAddress(from);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		//return ia.getPersonal(); 返回邮件发送人的名称
		return ia.getAddress();
	}
	
	/**
	 * 获取邮件的收件人 TO CC BCC
	 */
	public static String getMailAddress(String type,  MimeMessage msg){   
        String mailaddr = "";   
        String addtype = type.toUpperCase();   
        InternetAddress[] address = null;   
        if (addtype.equals("TO") || addtype.equals("CC")|| addtype.equals("BCC")) {
        	try{
        		if (addtype.equals("TO")) {   
                    address = (InternetAddress[]) msg.getRecipients(Message.RecipientType.TO);   
                } else if (addtype.equals("CC")) {   
                    address = (InternetAddress[]) msg.getRecipients(Message.RecipientType.CC);   
                } else {   
                    address = (InternetAddress[]) msg.getRecipients(Message.RecipientType.BCC);   
                }   
                if (address != null) {
                    for (int i = 0; i < address.length; i++) {   
                        String email = address[i].getAddress();   
                        if (email == null)   
                            email = "";   
                        else {   
                            email = MimeUtility.decodeText(email);   
                        }   
                        String personal = address[i].getPersonal();   
                        if (personal == null)   
                            personal = "";   
                        else {   
                            personal = MimeUtility.decodeText(personal);   
                        }   
                        //String compositeto = personal + "<" + email + ">"; 暂时不用personal
                        //mailaddr += "," + compositeto;  
                        mailaddr += ","  + email;
                    }   
                    mailaddr = mailaddr.substring(1);   
                }   
        	}catch(MessagingException e){
        		logger.error("MessagingException", e);
        	}catch(UnsupportedEncodingException e){
        		logger.error("UnsupportedEncodingException", e);
        	}
        }
        
        return mailaddr;   
    }   
	
	/**
	 * 解码字符串
	 */
	public static String decodeText(String text) {
		if (text == null) {
			return null;
		}
		if (text.startsWith("=?GB") || text.startsWith("=?gb")) {
			try {
				text = MimeUtility.decodeText(text);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {

			try {
				text = new String(text.getBytes("ISO8859_1"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return text;
	}
}
