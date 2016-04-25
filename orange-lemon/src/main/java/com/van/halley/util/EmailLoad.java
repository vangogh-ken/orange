package com.van.halley.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.event.ConnectionAdapter;
import javax.mail.event.ConnectionEvent;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

public class EmailLoad {

	public void loadEmail(List<Map<String, String>> emailInfos) {

	}

	public void getUserAllInfo() {

	}

	public void load() {
		DefaultAuthenticator authenticator = new DefaultAuthenticator(
				"xxx", "xxx");
	}

	public static void main(String[] args) throws Exception {
		EmailLoad e = new EmailLoad();
		// e.apacheSendMail();
		// e.getMailMsg();
		e.loadEmail("xxx", "xxxx");
	}

	public void apacheSendMail() throws Exception {
		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.live.com");
		// email.setSSL(true);
		// email.setSmtpPort(587);
		// email.setSslSmtpPort("25");
		email.setTLS(true);// gmail
		email.setAuthenticator(new DefaultAuthenticator("xxxx",
				"xxx"));
		email.setFrom("xxx");
		email.setSubject("TestCommonMail");
		email.setCharset("UTF-8");
		// 文本邮件
		email.setMsg("This is a test mail ... :-)");
		email.addTo("xxxx");
		email.send();
	}

	public void apacheSendHtmlMail() throws Exception {
		HtmlEmail email = new HtmlEmail();
		email.setHostName("smtp.gmail.com");
		// email.setSSL(true);
		// email.setSmtpPort(465);
		// email.setTLS(true);//gmail
		email.setAuthenticator(new DefaultAuthenticator("h***@gmail.com", "***"));
		email.setFrom("h***@gmail.com");
		email.addTo("**to@qq.com");
		email.setSubject("TestCommonMail");
		email.setCharset("gbk");
		// html邮件
		String cid = email.embed(new URL(
				"http://www.google.com.tw/intl/en_com/images/srpr/logo1w.png"),
				"google logo");
		email.setHtmlMsg("<html>The logo - <img src='cid:" + cid + "'></html>");

		email.send();
	}

	public void apacheSendAttachMail() throws Exception {
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.gmail.com");
		email.setSSL(true);
		email.setSmtpPort(465);
		email.setTLS(true);// gmail
		email.setAuthenticator(new DefaultAuthenticator("h***@gmail.com", "***"));
		email.setFrom("h***@gmail.com");
		email.addTo("**to@qq.com");
		email.setSubject("TestCommonMail");
		email.setMsg("This is a test mail ... :-)");
		email.setCharset("gbk");

		EmailAttachment attach = new EmailAttachment();
		attach.setName("attachFileName");
		attach.setPath("f:\\ok.txt");
		attach.setDescription(EmailAttachment.ATTACHMENT);

		email.attach(attach);

		email.send();
	}

	public void loadEmailIMAP(String emailAddr, String password) {
		/**
		 * Properties props = PropertyManager.getReceiveProperty(mail.getMail(),
		 * mail.getHost()); props.put("mail.imap.connectiontimeout",
		 * ConfigKeys.IMAP_CONNECTIONTIMEOUT); session =
		 * Session.getInstance(props, null); store = (IMAPStore)
		 * this.session.getStore(IMAP); store.connect(mail.getHost(),
		 * mail.getMail(), mail.getPassword()); session.setDebug(debug); folder
		 * = (IMAPFolder) this.store.getFolder("INBOX");
		 **/

		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		// Get a Properties object
		Properties props = System.getProperties();
		props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.pop3.socketFactory.fallback", "false");
		props.setProperty("mail.pop3.port", "995");
		props.setProperty("mail.pop3.socketFactory.port", "995");
		Session session = Session.getDefaultInstance(props, null);
		URLName urln = new URLName("imap", "imap." + emailAddr.split("@")[1],
				995, null, emailAddr, password);

		Store store = null;
		Folder inbox = null;
		FetchProfile profile = new FetchProfile();

		InputStream in = null;
		try {
			store = session.getStore(urln);
			store.connect();
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.HOLDS_MESSAGES);
			profile.add(FetchProfile.Item.ENVELOPE);

			inbox.addConnectionListener(new ConnectionAdapter() {
				@Override
				public void closed(ConnectionEvent e) {
					// TODO Auto-generated method stub
					super.closed(e);
				}
			});

			inbox.close(false);
			inbox.open(Folder.READ_ONLY);
			int allMailCount = inbox.getMessageCount();
			int unreadMailCount = inbox.getNewMessageCount();
			// inbox.addConnectionListener(l)
			if (inbox.hasNewMessages()) {
				System.out.println("有新邮件");
			}

			// Message[] messages = inbox.getMessages();
			System.out.println(unreadMailCount + " 封邮件未读,总共 " + allMailCount
					+ " 封邮件");

			Message msg = inbox.getMessage(304);
			System.out.println(msg.getSubject());
			/****/
			/**
			 * for(Message msg : messages){ //Address[] addrs = msg.getFrom();
			 * in = msg.getInputStream();
			 * 
			 * //int size = msg.getSize(); int msgNo = msg.getMessageNumber();
			 * String subject = msg.getSubject(); System.out.println("第" + msgNo
			 * + "  封邮件: " + subject); }
			 **/

		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			/**
			 * }catch (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace();
			 **/
		} finally {
			try {
				in.close();
				inbox.close(false);
				store.close();
			} catch (Exception e) {
			}
		}

	}

	// POP3不支持推送，IMAP支持因此。。。
	public void loadEmail(String emailAddr, String password) {
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		// Get a Properties object
		Properties props = System.getProperties();
		props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.pop3.socketFactory.fallback", "false");
		props.setProperty("mail.pop3.port", "995");
		props.setProperty("mail.pop3.socketFactory.port", "995");
		Session session = Session.getDefaultInstance(props, null);
		URLName urln = new URLName("pop3", "pop." + emailAddr.split("@")[1],
				995, null, emailAddr, password);

		Store store = null;
		Folder inbox = null;
		FetchProfile profile = new FetchProfile();

		InputStream in = null;
		try {
			store = session.getStore(urln);
			store.connect();
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.HOLDS_MESSAGES);
			profile.add(FetchProfile.Item.ENVELOPE);

			inbox.addConnectionListener(new ConnectionAdapter() {
				@Override
				public void closed(ConnectionEvent e) {
					// TODO Auto-generated method stub
					super.closed(e);
				}
			});

			inbox.close(false);
			inbox.open(Folder.READ_ONLY);
			int allMailCount = inbox.getMessageCount();
			int unreadMailCount = inbox.getNewMessageCount();
			// inbox.addConnectionListener(l)
			if (inbox.hasNewMessages()) {
				System.out.println("有新邮件");
			}

			// Message[] messages = inbox.getMessages();
			System.out.println(unreadMailCount + " 封邮件未读,总共 " + allMailCount
					+ " 封邮件");

			Message msg = inbox.getMessage(304);
			System.out.println(msg.getSubject());

			/**
			 * for(Message msg : messages){ //Address[] addrs = msg.getFrom();
			 * in = msg.getInputStream();
			 * 
			 * //int size = msg.getSize(); int msgNo = msg.getMessageNumber();
			 * String subject = msg.getSubject(); System.out.println("第" + msgNo
			 * + "  封邮件: " + subject); }
			 **/

		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			/**
			 * }catch (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace();
			 **/
		} finally {
			try {
				in.close();
				inbox.close(false);
				store.close();
			} catch (Exception e) {
			}
		}
	}

	public void getMailMsg() throws Exception {
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
		URLName urln = new URLName("pop3", "pop.126.com", 995, null,
				"xxxx", "xxxx");
		// URLName urln = new URLName("imap", "imap.126.com", 993,
		// null,"anxinxx@126.com", "THDQDOU19870321");
		Store store = session.getStore(urln);
		Folder inbox = null;

		try {
			store.connect();
			inbox = store.getFolder("INBOX");

			inbox.open(Folder.READ_ONLY);
			FetchProfile profile = new FetchProfile();
			profile.add(FetchProfile.Item.ENVELOPE);
			System.out.println(inbox.getNewMessageCount() + " 封邮件未读");
			Message[] messages = inbox.getMessages();
			inbox.fetch(messages, profile);
			System.out.println("收件箱的邮件数：" + messages.length);
			Map<String, String> mailMap = new HashMap<String, String>();// 存放邮件的数据
			for (int i = 0; i < messages.length; i++) {
				// 邮件发送者
				String from = decodeText(messages[i].getFrom()[0].toString());
				InternetAddress ia = new InternetAddress(from);
				System.out.println("FROM:" + ia.getPersonal() + '('
						+ ia.getAddress() + ')');
				// mailMap.put(MailMSG.FROM, ia.getAddress());

				// 邮件标题

				System.out.println("TITLE:" + messages[i].getSubject());
				// mailMap.put(MailMSG.TITLE, messages[i].getSubject());
				// 邮件大小

				System.out.println("SIZE:" + messages[i].getSize());
				mailMap.put("SIZE", String.valueOf(messages[i].getSize()));
				// 邮件发送时间

				System.out.println("DATE:" + messages[i].getSentDate());
				mailMap.put("DATE", messages[i].getSentDate().toString());
				// MailJobs.executeJob(mailMap );//根据发送的指令执行某个动做
			}
		} catch (Exception e) {

		} finally {
			try {
				inbox.close(false);
			} catch (Exception e) {
			}
			try {
				store.close();
			} catch (Exception e) {
			}
		}
	}

	protected static String decodeText(String text)
			throws UnsupportedEncodingException {

		if (text == null) {
			return null;
		} else if (text.startsWith("=?GB") || text.startsWith("=?gb")) {
			text = MimeUtility.decodeText(text);
		} else {
			text = new String(text.getBytes("GBK"));
		}

		return text;
	}

}
