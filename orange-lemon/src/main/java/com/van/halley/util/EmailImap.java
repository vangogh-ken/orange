package com.van.halley.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.imap.IMAPMessage;

/**
 * 使用IMAP协议接收邮件。POP3和IMAP协议的区别:
 * POP3协议允许电子邮件客户端下载服务器上的邮件,但是在客户端的操作(如移动邮件、标记已读等)，不会反馈到服务器上，
 * 比如通过客户端收取了邮箱中的3封邮件并移动到其它文件夹，邮箱服务器上的这些邮件是没有同时被移动的。
 * IMAP协议提供webmail与电子邮件客户端之间的双向通信，客户端的操作都会同步反应到服务器上，对邮件进行的操作，服务
 * 上的邮件也会做相应的动作。比如在客户端收取了邮箱中的3封邮件，并将其中一封标记为已读，将另外两封标记为删除，这些操作会
 * 即时反馈到服务器上。两种协议相比，IMAP 整体上为用户带来更为便捷和可靠的体验。POP3更易丢失邮件或多次下载相同的邮件，但IMAP通过邮件客户端
 * 与webmail之间的双向同步功能很好地避免了这些问题。
 */
public class EmailImap {
	public static void main1(String[] args) throws Exception {
		// 准备连接服务器的会话信息
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imap");
		props.setProperty("mail.imap.host", "imap.126.com");
		// props.setProperty("mail.imap.port", "143");

		Session session = Session.getInstance(props);
		Store store = session.getStore("imap");
		store.connect("","anxinxx@live.com", "********");
		System.out.println("连接成功");
		Folder inbox = store.getFolder("INBOX");
		// 以读写模式打开收件箱
		inbox.open(Folder.READ_WRITE);
		// Message[] messages = inbox.getMessages();
		int sumCount = inbox.getMessageCount();
		int unreadCount = inbox.getUnreadMessageCount();
		int newCount = inbox.getNewMessageCount();
		int delCount = inbox.getDeletedMessageCount();

		// 打印不同状态的邮件数量
		System.out.println("收件箱中共" + sumCount + "封邮件!");
		System.out.println("收件箱中共" + unreadCount + "封未读邮件!");
		System.out.println("收件箱中共" + newCount + "封新邮件!");
		System.out.println("收件箱中共" + delCount + "封已删除邮件!");

		System.out
				.println("------------------------开始读取未读邮件----------------------------------");

		Message[] msgs = inbox.getMessages(sumCount - unreadCount, sumCount);

		// 解析邮件
		for (Message message : msgs) {
			IMAPMessage msg = (IMAPMessage) message;
			// String subject = MimeUtility.decodeText(msg.getSubject());
			String subject = msg.getSubject();
			System.out.println("第 " + msg.getMessageNumber() + "封邮件 ["
					+ subject + "]" + msg.getMessageID());
			System.out.println(msg.getContentID() + msg.getContentMD5()
					+ msg.getContentType());
			System.out.println(msg.getDescription() + msg.getDisposition()
					+ msg.getEncoding());
			System.out.println(msg.getFileName() + msg.getInReplyTo()
					+ msg.getLineCount());
			System.out.println(msg.getFrom()[0].toString()
					+ msg.getFlags().hashCode() + msg.getReceivedDate());
			System.out.println();
			/**
			 * BufferedReader reader = new BufferedReader(new
			 * InputStreamReader(System.in)); String answer = reader.readLine();
			 * if ("yes".equalsIgnoreCase(answer)) {
			 * //POP3ReceiveMailTest.parseMessage(msg); // 解析邮件 //
			 * 第二个参数如果设置为true，则将修改反馈给服务器。false则不反馈给服务器 msg.setFlag(Flag.SEEN,
			 * true); //设置已读标志 }
			 **/

		}

		// 关闭资源
		// inbox.close(false);
		// store.close();
	}

	public static void main(String[] args) {
		new EmailImap().loadImapMail("anxinxx@live.com", "19870321");
	}

	public void loadImapMail(String emailAddr, String password) {
		// 准备连接服务器的会话信息
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imap");
		props.setProperty("mail.imap.host", getImapHostByAddr(emailAddr));
		// props.setProperty("mail.store.protocol", "imaps");
		// props.setProperty("mail.imap.starttls.enable", "true");
		// props.setProperty("mail.imap.ssl.enable", "true");
		// props.setProperty("mail.imap.host", "imap.126.com");
		// props.setProperty("mail.imap.port", "143");
		Session session = Session.getInstance(props);

		Store store = null;
		Folder inbox = null;

		int sumCount = 0;
		int unreadCount = 0;
		int newCount = 0;
		int delCount = 0;
		try {
			store = session.getStore("imap");
			store.connect("xx", emailAddr, password);
			System.out.println("连接成功");
			inbox = store.getFolder("INBOX");
			// 以读写模式打开收件箱
			inbox.open(Folder.READ_WRITE);

			sumCount = inbox.getMessageCount();
			unreadCount = inbox.getUnreadMessageCount();
			newCount = inbox.getNewMessageCount();
			delCount = inbox.getDeletedMessageCount();

			System.out.println("收件箱中共" + sumCount + "封邮件!");
			System.out.println("收件箱中共" + unreadCount + "封未读邮件!");
			System.out.println("收件箱中共" + newCount + "封新邮件!");
			System.out.println("收件箱中共" + delCount + "封已删除邮件!");

			System.out
					.println("------------------------开始读取未读邮件----------------------------------");
			Message[] msgs = inbox
					.getMessages(sumCount - unreadCount, sumCount);

			for (Message message : msgs) {
				IMAPMessage msg = (IMAPMessage) message;
				String subject = msg.getSubject();
				System.out.println("第 " + msg.getMessageNumber() + "封邮件 ["
						+ subject + "]" + msg.getMessageID());

				System.out.println("发送时间：" + msg.getSentDate());
				System.out.println("主题：" + msg.getSubject());
				// System.out.println("内容：" + msg.getContent());
				Flags flags = msg.getFlags();
				if (flags.contains(Flags.Flag.SEEN))
					System.out.println("这是一封已读邮件");
				else {
					System.out.println("未读邮件");
				}
				// System.out.println(msg.getContent() +
				// message.getContentType());
				System.out
						.println("========================================================");
				// msg.isMimeType(mimeType);
				Object content = msg.getContent();
				if (content instanceof MimeMultipart) {
					// MimeMultipart multipart = (MimeMultipart) content;
					// parseMultipart(multipart);
				}
			}
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				inbox.close(false);
				store.close();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 通过邮箱地址获取协议主机
	 * 
	 * @param emailAddr
	 * @return
	 */
	public String getImapHostByAddr(String emailAddr) {
		return "imap." + emailAddr.split("@")[1];
	}

	public void parseMultipart(Multipart multipart) throws MessagingException,
			IOException {
		int count = multipart.getCount();
		System.out.println("couont =  " + count);
		for (int idx = 0; idx < count; idx++) {
			BodyPart bodyPart = multipart.getBodyPart(idx);
			System.out.println(bodyPart.getContentType());
			if (bodyPart.isMimeType("text/plain")) {
				System.out.println("plain................."
						+ bodyPart.getContent());
			} else if (bodyPart.isMimeType("text/html")) {
				System.out.println("html..................."
						+ bodyPart.getContent());
			} else if (bodyPart.isMimeType("multipart/*")) {
				Multipart mpart = (Multipart) bodyPart.getContent();
				parseMultipart(mpart);

			} else if (bodyPart.isMimeType("application/octet-stream")) {
				String disposition = bodyPart.getDisposition();
				System.out.println(disposition);
				if (disposition.equalsIgnoreCase(BodyPart.ATTACHMENT)) {
					String fileName = bodyPart.getFileName();
					InputStream is = bodyPart.getInputStream();
					copy(is, new FileOutputStream("D:\\" + fileName));
				}
			}
		}
	}

	/**
	 * 文件拷贝，在用户进行附件下载的时候，可以把附件的InputStream传给用户进行下载
	 * 
	 * @param is
	 * @param os
	 * @throws IOException
	 */
	public void copy(InputStream is, OutputStream os) throws IOException {
		byte[] bytes = new byte[1024];
		int len = 0;
		while ((len = is.read(bytes)) != -1) {
			os.write(bytes, 0, len);
		}
		if (os != null)
			os.close();
		if (is != null)
			is.close();
	}

}