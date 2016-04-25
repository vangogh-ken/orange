package com.van.halley.util;

import java.security.MessageDigest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class Md5Tool {

	/**
	 * Md5的加密算法是单向不可逆的，只能将现有的字符串加密之后再与之前的加密字符串进行对比。
	 * 
	 * @param password
	 * @return
	 */
	public static String encodeMd5(String text) {
		String str = "";
		if (text != null && !text.equals("")) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				BASE64Encoder base = new BASE64Encoder();
				// 加密后的字符串
				str = base.encode(md.digest(text.getBytes("utf-8")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	
	 public static String decodeMd5(String encodPpassword){ 
		 String str = "";
		 if(encodPpassword !=null && !encodPpassword.equals("")){ 
			 try {
				 MessageDigest md = MessageDigest.getInstance("MD5"); 
				 BASE64Decoder base = new BASE64Decoder(); 
				 String s = new String(md.digest(base.decodeBuffer(encodPpassword)),"utf-8");
				 //md.digest(encodPpassword.getBytes(("utf-8"))); //base. 
				 //String s = new String(base.decodeBuffer(encodPpassword),"utf-8"); 
				 System.out.println(s);
				 //加密后的字符串 //str =
				 //base...encode(md.digest(encodPpassword.getBytes("utf-8"))); 
			  } catch(Exception e) { 
				 e.printStackTrace(); 
			  } 
	 	}
	 
	 	return str; 
	 }
	

	// 可加密多次
	public static void main(String[] args) {
		System.out.println(encodeMd5("1"));
		// decodeMd5("xMpCOKC5I4INzFCab3WEmw==");
		//System.out.println(decodeMd5("xMpCOKC5I4INzFCab3WEmw=="));
	}

}
