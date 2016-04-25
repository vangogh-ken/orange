package com.zen.junit.test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.van.halley.core.util.PackageUtils;
import com.van.halley.util.DoubleUtil;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.XlsUtil;

import me.chanjar.weixin.cp.api.WxCpConfigStorage;

public class TestFor {
	

	public static void main(String[] args) {
		t44();
	}
	
	public static void t44(){
		File f = new File("D:\\用户目录\\我的文档\\Tencent Files\\570914511\\FileRecv");
		//File f = new File("D:\\用户目录\\我的文档\\Tencent Files\\570914511\\FileRecv\\开发_测试环境 (1).xlsx");
		System.out.println(f.getAbsolutePath());
		String zipFile = System.getProperty("java.io.tmpdir") + StringUtil.getUUID() + ".zip";
		System.out.println(zipFile);
	}
	
	public static void t43(){
		System.out.println(String.format("系统已接收到消息，类型：%s", "A"));
	}
	
	public static void t42(){
		String s = "";
		String s2 = "abcd";
		System.out.println(s2.substring(s2.indexOf(s), s2.length()));
	}
	
	public static void t41(){
		try {
			System.out.println(TestFor.class.getMethod("t40").getParameterCount());
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void t40(){
		String s = "getId";
		System.out.println(s.substring(s.indexOf("get") + 3, s.length()));
	}
	
	public static void t39(){
		//~ only support single column to merge
		HSSFWorkbook workbook = XlsUtil.getWorkbook(new File("C:\\Users\\Administrator\\Desktop\\test.xls"));
		
		HSSFCellStyle style = workbook.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();
		int rowCount = sheet.getPhysicalNumberOfRows();
		Map<Integer, List<int[]>> map = new HashMap<Integer, List<int[]>>();
		for(int i=0; i<columnCount; i++){
			List<int[]> items = new ArrayList<int[]>();
			int[] item = new int[2];
			String value = null;
			for(int j=0; j<rowCount; j++){
				
				sheet.getRow(j).getCell(i).setCellStyle(style);
				if(value == null){
					String temp = XlsUtil.getValueToStringFromCell(sheet.getRow(j).getCell(i));
					if(NumberUtils.isNumber(temp) || StringUtils.isBlank(temp)){//except numbers and blank
						if(NumberUtils.isNumber(temp)){
							sheet.getRow(j).getCell(i).setCellValue(NumberUtils.toDouble(temp));
							sheet.getRow(j).getCell(i).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						}
						
						continue;
					}
					value = XlsUtil.getValueToStringFromCell(sheet.getRow(j).getCell(i));
					item[0] = j;
				}else{
					String temp = XlsUtil.getValueToStringFromCell(sheet.getRow(j).getCell(i));
					if(NumberUtils.isNumber(temp) || StringUtils.isBlank(temp)){//except numbers and blank
						if(NumberUtils.isNumber(temp)){
							sheet.getRow(j).getCell(i).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						}
						
						if(item[1] != 0){//~ catch
							items.add(item);
						}
						value = XlsUtil.getValueToStringFromCell(sheet.getRow(j).getCell(i));
						item = new int[2];
						item[0] = j;
						item[1] = 0;
					}
					
					if(value.equals(XlsUtil.getValueToStringFromCell(sheet.getRow(j).getCell(i)))){
						item[1] = j;
					}else{
						if(item[1] != 0){//~ catch
							items.add(item);
						}
						value = XlsUtil.getValueToStringFromCell(sheet.getRow(j).getCell(i));
						item = new int[2];
						item[0] = j;
						item[1] = 0;
					}
				}
			}
			
			if(item[1] != 0){//~ catch
				items.add(item);
			}
			
			map.put(i, items);
		}
		
		for(Entry<Integer, List<int[]>> entry : map.entrySet()){
			if(entry.getValue() != null && !entry.getValue().isEmpty()){
				for(int[] item : entry.getValue()){
					sheet.addMergedRegion(new CellRangeAddress(item[0], item[1], 
							entry.getKey(), entry.getKey()));
					//只设置合并单元格
					/*for(int k=item[0]; k<item[1] + 1; k++){
						String temp = XlsUtil.getValueToStringFromCell(sheet.getRow(k).getCell(entry.getKey()));
						Cell cell = sheet.getRow(k).getCell(entry.getKey());
						cell.setCellValue(temp);
						cell.setCellStyle(style);
					}*/
				}
			}
		}
		
		try {
			FileUtils.copyInputStreamToFile(XlsUtil.getInputStreamByXls(workbook), new File("C:\\Users\\Administrator\\Desktop\\test2.xls"));
		} catch (IOException e) {
			e.printStackTrace();
		};
		System.out.println(1);
	}
	
	public static void t38(){
		long l1 = 1453731118016L;
		long l2 = 1429112041000L;
		
		System.out.println((l1 - l2)/(1000*60*60*24));
		
		WxCpConfigStorage wxCpConfigStorage;
	}
	
	
	public static void t37(){
		System.out.println(new StringBuilder() instanceof Object);
	}
	
	public static void t36(){
		Date placeTime = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			Date divide = format.parse("20160201");
			if(placeTime.after(divide)){
				System.out.println(1);
			}else{
				System.out.println(2);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void t35(){
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 1, 1);
		System.out.println(cal.getTime());
		System.out.println(cal.after(new Date()));
		System.out.println(new Date().after(cal.getTime()));
	}
	
	public static void t34(){
		System.out.println("已".equalsIgnoreCase("已"));
		
	}
	
	public static void t33(){
		String s = "ABCD";
		System.out.println(s.substring(s.length() - 1));
	}
	public static void t32(){
		double d1 = 19164.35;
		double d2 = 8790;
		//System.out.println((d1 - d2));
		
		System.out.println(DoubleUtil.div(DoubleUtil.sub(d1, d2), 1));
	}
	
	public static void t31(){
		System.out.println(new SimpleDateFormat("yyMMww").format(new Date()));
		System.out.println(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
	}
	
	public static void t30(){
		try {
			FileUtils.copyDirectory(new File("C:\\T", "t1"), new File("C:\\T", "t2"));
			FileUtils.copyDirectory(new File("C:\\T", "t2"), new File("C:\\T", "t1"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void t29(){
		String a = "a256" + "_";
		String b = "a256_";
		System.out.println(a==b);
		System.out.println(a.equals(b));
		
		Integer n = new Integer(12);
		Integer m = new Integer(12);
		System.out.println(n==m);
		System.out.println(n.equals(m));
		
		System.out.println(3<<1);
		System.out.println(5>>1);
		
		int i = 3;
		int j = 5;
		i = j-i + i;
		j = j - (j-i);
		//System.out.println(i);
		//System.out.println(j);
	}
	public static void t28(){
		List<String> list = null;
		for(String s : list){
			System.out.println(s);
		}
	}
	public static void t27(){
		System.out.println("		FROM OUT_FORUM_TOPIC".indexOf("\tFROM "));
	}
	public static void t26(){
		System.out.println("ABDBCD".indexOf("B"));
	}
	
	public static void t25(){
		System.out.println("TEMU5905023".length());
		System.out.println("A，B".length());
	}
	
	public static void t24(){
		List<String> l = new ArrayList<String>();
		l.add("a");
		l.add("c");
		l.add("d");
		
		System.out.println(l.toArray(new String[l.size()]));
	}
	
	public static void t23(){
		StringBuilder s = new StringBuilder(" WHERE AND");
		System.out.println(s.substring(0, s.lastIndexOf("AND")));
	}
	public static void t22(){
		String s = "1504";
		SimpleDateFormat sd = new SimpleDateFormat("yyMM");
		try {
			System.out.println(sd.parse(s).toLocaleString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void t21(){
		for(int i=0; i<100; i++){
			if(i == 50){
				break;
			}
			System.out.println("i: " + i);
		}
	}
	public static void t20(){
		for(int i=0; i<100; i++){
			for(int n=200; n<300; n++){
				if(n == 205){
					break;
				}
				System.out.println("i: " + i + " n: " + n);
			}
		}
		System.out.println("AAA");
	}
	public static void t19(){
		String s = "c:/logs/vanbpmrp-access.log";
		String[] ss = s.split("/");
		System.out.println(ss[ss.length - 1]);
	}
	
	
	public static void t18(){
		List<String> list = new ArrayList<String>();
		System.out.println(list.isEmpty());
		System.out.println("T".equals(null));
		try {
			Thread.currentThread().sleep(10 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void t17(){
		String s = " A B C ";
		System.out.println(s.trim());
	}
	
	public static void t16(){
		SimpleDateFormat f = new SimpleDateFormat("yyMMww");
		System.out.println(f.format(new Date()));
	}
	
	public static void t15(){
		String s = "SELECT * FROM FRE_ORDER WHERE ID =:ID";
		String[] ts = s.split("\\s+");
		for(String tss : ts){
			System.out.println(tss);
		}
		
	}
	public static void t14(){
		Calendar cal = Calendar.getInstance();
		//cal.add(Calendar.MONTH, 1);
		//cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 20);
		System.out.println(cal.getTime().toLocaleString());
		
		cal.add(Calendar.DAY_OF_YEAR, 60);
		System.out.println(cal.getTime().toLocaleString());
	}
	
	public static void t13(){
		String s = "content/fre/fre-box-list.jsp";
		System.out.println(s.endsWith("500.jsp"));
	}
	public static void t12(){
		double d = 3.1415925;
		System.out.println(String.format("%.2f", d));
	}
	public static void t11(){
		System.out.println("AD" + new SimpleDateFormat("yyyyMM").format(new Date()) + "0001");
	}
	public static void t10(){
		String s = "WT.xls";
		System.out.println(s.split("\\.")[1]);
	}
	
	public static void t9(){
		StringBuilder s = new StringBuilder("XXXXXXXX");
		System.out.println(s.insert(0, "Y"));
		System.out.println(s.insert(0, "W"));
		
	}
	
	public static void t8(){
		StringBuilder value = new StringBuilder("TO:$$(FRE_ORDER,ORDER_NUMBER)$$");
		int beginIndex = value.lastIndexOf("$$(");
		int endIndex = value.lastIndexOf(")$$");
		String $value = value.substring(beginIndex + 3, endIndex);
		System.out.println($value);
	}
	
	//@Test
	public void t2(){
		for(int i=0; i<1000000001; i++){
			if(i%3==0){
				if(i%5==0){
					//System.out.println("*#" + i);
				}else{
					//System.out.println("*" + i);
				}
			}else if(i%5==0){
				//System.out.println("#" + i);
			}
		}
	}
	//@Test
	public void t3(){
		for(int i=0; i<1000000001; i++){
			if(i%15==0){
				//System.out.println("*#" + i);
			}else if(i%3==0){
				//System.out.println("*" + i);
			}else if(i%5==0){
				//System.out.println("#" + i);
			}
		}
	}
	
	public static void scanPackage(){
		PackageUtils.getAllClassesInPackage("com.van.db.persistence.entity");
	}
	
	public static void t4(){
		String a = "12345678901234567890";
		System.out.println(a.substring(0, a.length()-10));
		System.out.println("SQL-cc97f132ea874cf298385e7b51d42d8e-307963.xml".length());
	}
	
	public static void t5(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "b");
		map.put("a", "c");
		
		System.out.println(map.get("a"));
	}
	
	public static void t6(){
		try {
			System.out.println(URLDecoder.decode("%E8%8F%9C%E5%8D%95", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void t7(){
		String d = "2014-08-09 12:11:00";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			System.out.println(dateFormat.format(dateFormat.parse(d)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
