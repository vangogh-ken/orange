package com.zen.junit.test;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class TestMailTwo {
	private static Logger logger = LoggerFactory.getLogger(TestMailTwo.class);

	public static void main(String[] args) {
		//fetchMailWithSSL();
		//sendMailWithSSL();
		send("anxinxx@live.com", "19870321", "vandemo@126.com", "subject", "content");
	}

	public static void fetchMailWithSSL() {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		// Get a Properties object
		Properties props = System.getProperties();
		props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.pop3.socketFactory.fallback", "false");
		props.setProperty("mail.pop3.port", "995");
		props.setProperty("mail.pop3.socketFactory.port", "995");
		// 以下步骤跟一般的JavaMail操作相同
		Session session = Session.getDefaultInstance(props, null);
		// 请将红色部分对应替换成你的邮箱帐号和密码
		URLName urln = new URLName("pop3", "pop-mail.outlook.com", 995, null,
				"anxinxx@live.com", "19870321");

		Store store = null;
		Folder inbox = null;
		try {
			store = session.getStore(urln);
			store.connect();
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			FetchProfile profile = new FetchProfile();
			profile.add(FetchProfile.Item.ENVELOPE);

			Message[] messages = inbox.getMessages();
			inbox.fetch(messages, profile);
			System.out.println("收件箱的邮件数：" + messages.length);

			for (int i = 0; i < messages.length; i++) {
				String from = decodeText(messages[i].getFrom()[0].toString());
				InternetAddress ia = new InternetAddress(from);
				System.out.println("FROM:" + ia.getPersonal() + '('
						+ ia.getAddress() + ')');
				System.out.println("TITLE:" + messages[i].getSubject());
				System.out.println("SIZE:" + messages[i].getSize());
				System.out.println("DATE:" + messages[i].getSentDate());
			}

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {

			try {
				if (inbox != null) {
					inbox.close(false);
				}

				if (store != null) {
					store.close();
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

	}

	public static void sendMailWithSSL() {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		//final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.host", "smtp-mail.outlook.com");
		props.setProperty("mail.smtp.port", "25");
		//props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
				//props.setProperty("mail.smtp.socketFactory.fallback", "false");
		//props.setProperty("mail.smtp.socketFactory.port", "25");
		//props.put("mail.smtp.auth", "true");
		final String username = "anxinxx@live.com";
		final String password = "19870321";
		Session session = Session.getDefaultInstance(props,
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
		// -- Create a new message --
		Message msg = new MimeMessage(session);
		// -- Set the FROM and TO fields --
		try {
			msg.setFrom(new InternetAddress(username));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("vandemo@126.com", false));
			msg.setSubject("Hello");
			msg.setText("How are you");
			msg.setSentDate(new Date());
			Transport.send(msg);
			System.out.println("Message sent.");
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

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
	
	public static void send(String mailAddress, String password, String to, String subject, String content){
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");
		
		//props.setProperty("mail.smtp.port", "25");
		//props.setProperty("mail.smtp.host", "smtp-mail.outlook.com");
		//Properties properties = new Properties();
        //properties.put("mail.smtp.auth", true);
        //properties.put("mail.smtp.starttls.enable", true);
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp-mail.outlook.com");
        //javaMailSender.setHost("smtp.qq.com");
        javaMailSender.setPort(25);
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
            
        } catch (Exception e) {
           logger.error("send mail error", e);
        }
	}

}
