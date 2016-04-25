package com.van.halley.util;

import java.math.BigDecimal;

/**
 * 解决double数据进行运算之后精度丢失的问题
 * @author Think
 *
 */
public class DoubleUtil {
	private static final int DEF_DIV_SCALE = 10;
	
	public static void main(String[] args) {
		System.out.println(sub(10, 3, 3));
	}
	/**
	 * 加法
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double sum(double d1, double d2){
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal b2 = new BigDecimal(String.valueOf(d2));
		return b1.add(b2).doubleValue();
	}
	
	/**
	 * 减法
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double sub(double d1, double d2){
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal b2 = new BigDecimal(String.valueOf(d2));
		return b1.subtract(b2).doubleValue();
	}
	
	/**
	 * 第一个数减去多个数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double sub(double d1, double... d2){
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		for(double d : d2){
			BigDecimal b2 = new BigDecimal(String.valueOf(d));
			b1 = b1.subtract(b2);
		}
		return b1.doubleValue();
	}
	
	/**
	 * 乘法
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double mul(double d1, double d2){
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal b2 = new BigDecimal(String.valueOf(d2));
		return b1.multiply(b2).doubleValue();
	}
	
	/**
	 * 除法
	 * @param d1
	 * @param d2
	 * @param scale
	 * @return
	 */
	public static double div(double d1, double d2, int scale){
		if(scale < 0){
			throw new IllegalArgumentException("The scale must be positive integer or zero.");
		}
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal b2 = new BigDecimal(String.valueOf(d2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 默认除法
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double div(double d1, double d2){
		return div(d1, d2, DEF_DIV_SCALE);
	}
	
	/**
	 * 四舍五入
	 * @param d1
	 * @param scale
	 * @return
	 */
	public static double round(double d1, int scale){
		if(scale < 0){
			throw new IllegalArgumentException("The scale must be positive integer or zero.");
		}
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal one = new BigDecimal("1");
		return b1.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
