package com.van.halley.fre.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 数据统计按照订单下单时间进行统计。
 * @author Administrator
 *
 */
public class FreightVersionUtil {
	private static DateFormat format;
	/**
	 * 20160201之前的数据为V1
	 */
	private static Date divideV1;

	static {
		format = new SimpleDateFormat("yyyyMMdd");
		try {
			divideV1 = format.parse("20160201");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否为V1的数据
	 * @param placeTime
	 * @return
	 */
	public static boolean assertV1(Date placeTime) {
		if (placeTime.before(divideV1)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(assertV1(format.parse("20160301")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
