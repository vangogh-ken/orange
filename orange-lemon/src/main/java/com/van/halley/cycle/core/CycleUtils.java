package com.van.halley.cycle.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CycleUtils {
	private static DateFormat format;
	
	static{
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S");
	}
	public static String format(Date date){
		return format.format(date);
	}
	
	public static void main(String[] args) {
		System.out.println(CycleUtils.format(new Date()));
	}
}
