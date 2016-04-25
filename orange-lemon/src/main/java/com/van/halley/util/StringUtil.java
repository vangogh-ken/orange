package com.van.halley.util;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.van.halley.core.page.PageView;

import sun.misc.BASE64Encoder;

public class StringUtil {
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	private static List<String> patterns = new ArrayList<String>();
	static{
		patterns.add("yyyy-MM-dd HH:mm:ss");
        patterns.add("yyyy-MM-dd");
        patterns.add("yyyy/MM/dd HH:mm:ss");
        patterns.add("yyyy/MM/dd");
	}
	//转换到Date
	public static Date convert(String text) {
        if (text == null) {
            return null;
        }

        for (String pattern : patterns) {
            Date date = tryConvert(text, pattern);
            if (date != null) {
                return date;
            }
        }

        return null;
    }
	
	public static String parseDateTime(Date date){
		if(date == null){
			return null;
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	public static String parseTimeStamp(Date date){
		if(date == null){
			return null;
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

    public static Date tryConvert(String text, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setLenient(false);
        try {
            return dateFormat.parse(text);
        } catch (ParseException ex) {
            logger.debug(ex.getMessage(), ex);
        }

        return null;
    }
    
    /**
     * 按照指定格式转换
     * @param date
     * @param pattern
     * @return
     */
    public static String customFormat(Date date, String pattern){
    	return new SimpleDateFormat(pattern).format(date);
    }
    
	public static String getUUID() {
		UUID id = UUID.randomUUID();
		return id.toString();
	}

	public static boolean hasLength(String source) {
		if (source == null || source.trim().equals("")) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean isNullOrEmpty(String text){
		boolean flag = true;
		if(text != null && !"".equals(text) && !"".equals(text.trim())){
			flag = false;
		}
		
		return flag;
	}
	
	public static String getTimeString(Date date){
		return timeFormat.format(date);
	}
	
	public static String getDateString(Date date){
		return dateFormat.format(date);
	}
	
	/**
	 * 报表中数据源Sql使用当前用户替换
	 */
	public static String replaceCurrentUserId(String text, String value){
		if(text.contains(":CURRENT_USER_ID")){
			text = text.replaceAll("CURRENT_USER_ID", value);
			if(text.contains(":")){
				String[] subtext = text.split(":");
				String newText = "";
				for(String t : subtext){
					newText += t;
				}
				return newText;
			}else{
				return text;
			}
		}else{
			return text;
		}
	}
	
	/**
	 * 获取请求的IP地址
	 */
	public static String getIPAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * Md5的加密算法是单向不可逆的，只能将现有的字符串加密之后再与之前的加密字符串进行对比。
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
	
	/**
	 * 判断数据中是否有重复的元素
	 * @return
	 */
	public static boolean hasDuplicate(String[] ss){
		boolean flag = false;
		if(ss != null && ss.length > 0){
			Set<String> set = new HashSet<String>();
			for(String s : ss){
				if(!set.contains(s)){
					set.add(s);
				}
			}
			
			if(ss.length > set.size()){
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 判断集合中是否有重复数据
	 * @param ss
	 * @return
	 */
	public static boolean hasDuplicate(List<String> ss){
		boolean flag = false;
		if(ss != null && ss.size() > 0){
			Set<String> set = new HashSet<String>();
			for(String s : ss){
				if(!set.contains(s)){
					set.add(s);
				}
			}
			
			if(ss.size() > set.size()){
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 拼接分页信息
	 * @param text
	 * @param pageView
	 * @return
	 */
	public static String appendPageView(StringBuilder text, PageView pageView){
		if(pageView.getPageSize() != 0){
			text.append(" LIMIT ");
			text.append((pageView.getPageNo() - 1) * 10 + ", ");
			text.append(pageView.getPageSize());
		}
		
		if(pageView.getOrderBy() != null){
			text.append(" ORDER BY ");
			text.append(pageView.getOrderBy() + " " + pageView.getOrder());
		}
		
		return text.toString();
	}
	
	/**
	 * 将REQUEST的键值对抓换为单值的
	 * @param mapOld
	 * @return
	 */
	public static Map<String, Object> toSingleValueMap(Map<String, String[]> mapOld){
		Map<String, Object> mapNew = new HashMap<String, Object>();
		for(String key : mapOld.keySet()){
			if(mapOld.get(key) == null){
				continue;
				//mapNew.put(key, null);
			}else{
				if(isNullOrEmpty(mapOld.get(key)[0])){
					continue;
				}else{
					mapNew.put(key, mapOld.get(key)[0]);
				}
			}
		}
		return mapNew;
	}
	
	 /**
	  * 去除换行
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
	        String dest = "";
	        if (str!=null) {
	            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
	            Matcher m = p.matcher(str);
	            dest = m.replaceAll("");
	        }
	        return dest;
	    }
	    /*-----------------------------------
	 
	    笨方法：String s = "你要去除的字符串";
	 
	            1.去除空格：s = s.replace('\\s','');
	 
	            2.去除回车：s = s.replace('\n','');
	 
	    这样也可以把空格和回车去掉，其他也可以照这样做。
	 
	    注：\n 回车(\u000a) 
	    \t 水平制表符(\u0009) 
	    \s 空格(\u0008) 
	    \r 换行(\u000d)*/
	/**
	 * 判断字符串可转换为数字
	 * @param text
	 * @return
	 */
	public static boolean isDouble(String text){
		try{
			Double.parseDouble(text);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
	
	/**
	 * 使用正则表达式判断
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
	   Pattern pattern = Pattern.compile("^[+-]?[\\d\\.]+$");
	   Matcher isNum = pattern.matcher(str);
	   if( !isNum.matches() ){
	       return false; 
	   } 
	   return true; 
	}
	
	
	public static void main(String[] args) {
		System.out.println(isNumeric("9a"));
	}

}
