package com.zen.junit.test;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

public class TestMail {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Properties props = System.getProperties();  
        //props.put("mail.smtp.host", "smtp.qq.com");  
        props.put("mail.smtp.auth", "true");  
        Session session = Session.getDefaultInstance(props, null);  
        //Session session = Session.getInstance(props);
        
        //URLName urln = new URLName("smtp", "smtp.126.com", 25, null, "vandemo@126.com", "19870321");
        
        URLName urln = new URLName("pop3", "pop-mail.outlook.com", 995 ,null, "anxinxx@live.com", "19870321");
        //URLName urln = new URLName("pop3", "pop3.outlook.com", 995 ,null, "anxinxx@live.com", "19870321");
        //URLName urln = new URLName("imap", "imap.outlook.com", 995 ,null, "anxinxx@live.com", "19870321");
        //URLName urln = new URLName("imap", "imap-mail.outlook.com", 993 ,null, "anxinxx@live.com", "19870321");
        //URLName urln = new URLName("smtp", "smtp-mail.outlook.com", 25, null, "anxinxx@live.com", "19870321");
        //URLName urln = new URLName("imap", "imap.chengduchuangyuan.com", 143 ,null, "fli@chengduchuangyuan.com", "xx");
        //URLName urln = new URLName("pop3", "pop3.chengduchuangyuan.com", 110 ,null, "fli@chengduchuangyuan.com", "xx"); 
        //URLName urln = new URLName("imap", "imap.qq.com", 143 ,null, "570914511@qq.com", "xx"); 
        //URLName urln = new URLName("pop3", "pop.qq.com", 110 ,null, "570914511@qq.com", "xx");
        //URLName urln = new URLName("imap", "imap.126.com", 143, null, "vandemo@126.com", "xx");
        //URLName urln = new URLName("imap", "imap.163.com", 143, null, "puyixx@163.com", "XX");
        Folder folder = null;
        Store store = null;
        try {
        	store = session.getStore(urln);  
            store.connect(); 
            folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			int num = folder.getMessageCount();
			System.out.println(num);
			Message[]  msgs = folder.getMessages();
			for(Message msg : msgs){
				System.out.println(msg.getSubject());
				try {
					Object con = msg.getContent();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//for(int i = num; i>0 ; i--){
				//System.out.println(msgs[i-1].getSubject() + " : " 
						//+ msgs[i-1].getSentDate().toLocaleString() + " : " +  msgs[i-1].getFrom()[0].toString());
			//}
			//for(Message msg : msgs){
				//System.out.println(msg.getSubject() + " : " + msg.getSentDate().toLocaleString());
			//}
			//System.out.println(msgs[142].getSubject());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if(folder != null){
					folder.close(true);
				}
				
				if(store != null){
					store.close();
				}
				
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
