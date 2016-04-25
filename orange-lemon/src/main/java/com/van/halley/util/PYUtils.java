package com.van.halley.util;

import java.io.UnsupportedEncodingException;


public class PYUtils {
	public static String getLetter(String text){
		char[] chs = text.toCharArray();
		StringBuilder s = new StringBuilder();
		for(int i=0, len = chs.length; i<len; i++){
			s.append(ch(chs[i]));
		}
		
		return s.toString();
	}
	
	public static char ch(char c){
		int begin = 45217;
		int end = 63486;
		int[] index = new int[27];
		char[] CH = {'啊','芭','擦','搭','蛾','发','噶','哈','哈','击','喀','垃','妈','拿','哦','啪','期','然','撒','塌','塌','塌','挖','昔','压','匝'};
		char[] EN = {'A','B','C','D','E','F','G','H','H','J','K','L','M','N','O','P','Q','R','S','T','T','T','W','X','Y','Z'};
		
		for(int i=0; i<26; i++){
			index[i] = getNum(CH[i]);
		}
		index[26] = end;
		int count =  getNum(c);
		if(count < begin || count > end){
			return 'A';
		}else{
			for(int i=0; i<26; i++){
				if(count >= index[i] && count < index[i+1]){
					return EN[i];
				}
			}
			
			return 'A';
		}
	}
	
	public static int getNum(char c){
		byte[] b = null;
		try {
			b = (""+c).getBytes("GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return (b[0]<<8&0xff00)+(b[1]&0xff);
	}
}
