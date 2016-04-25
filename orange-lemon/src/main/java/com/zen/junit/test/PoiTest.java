package com.zen.junit.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class PoiTest {
	public static void main(String[] args) {
		t2();
	}
	//word 2003
	public static void t1(){
		String path = "C:\\T\\投保-人保（10-1）.doc";
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(path);
			HWPFDocument doc = new HWPFDocument(in);
			Range range = doc.getRange();
			range.replaceText("${FPJE}", "2000");
			range.replaceText("${HWMC}", "货物名称");
			out = new FileOutputStream("C:\\T\\投保-人保（10-1）2.doc");
			doc.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				if(in != null){
					in.close();
				}
				if(out != null){
					out.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	//word 2007~
	public static void t2(){
		String path = "C:\\T\\投保-人保（10-1）.docx";
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(path);
			XWPFDocument doc = new XWPFDocument(in);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("HWMC", "货物名称");
			params.put("FPJE", "2000");
			params.put("TDH", "提单号");
			params.put("BZJSL", "包装及数量");
			replaceParamsInDocument(doc, params);
			replaceParamsInTable(doc, params);
			out = new FileOutputStream("C:\\T\\投保-人保（10-1）2.docx");
			doc.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				if(in != null){
					in.close();
				}
				if(out != null){
					out.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 对参数进行替换
	 * @param doc
	 * @param params
	 */
	public static void replaceParamsInDocument(XWPFDocument doc, Map<String, Object> params){
		Iterator<XWPFParagraph> it = doc.getParagraphsIterator();
		XWPFParagraph paragraph;
		while(it.hasNext()){
			paragraph = it.next();
			replaceParamsInParagraph(paragraph, params);
		}
	} 
	
	/**
	 * 在段落中进行替换
	 * @param paragraph
	 * @param params
	 */
	public static void replaceParamsInParagraph(XWPFParagraph paragraph, Map<String, Object> params){
		List<XWPFRun> runs;
		Matcher matcher;
		if(getMatcher(paragraph.getParagraphText()).find()){
			runs = paragraph.getRuns();
			for(int i=0, len=runs.size(); i<len; i++){
				String text = runs.get(i).toString();
				System.out.println(text);
				matcher = getMatcher(text);
				if(matcher.find()){
					while((matcher = getMatcher(text)).find()){
						Object object = params.get(matcher.group(1)) == null ? "" : params.get(matcher.group(1));
						System.out.println(matcher.group(1) + " : " + object);
						text = matcher.replaceFirst((String)object);
					}
				}
				/*
				 * 直接调用setText方法设置文本时候，在底层会重新创建一个XWFRun，会把文本附加在当前文本之后。
				 * 所以此处不能直接设置，而是首先删除当前Run，然后再插入一个新的Run，再进行设值。
				 */
				UnderlinePatterns underlinePattern = runs.get(i).getUnderline();
				paragraph.removeRun(i);
				XWPFRun newRun = paragraph.insertNewRun(i);
				newRun.setText(text);
				newRun.setUnderline(underlinePattern);
			}
		}
	} 
	
	public static void replaceParamsInTable(XWPFDocument doc, Map<String, Object> params){
		Iterator<XWPFTable> it = doc.getTablesIterator();
		XWPFTable table;
		List<XWPFTableRow> rows;
		List<XWPFTableCell> cells;
		List<XWPFParagraph> paragraphs;
		while(it.hasNext()){
			table = it.next();
			rows = table.getRows();
			for(XWPFTableRow row : rows){
				cells = row.getTableCells();
				for(XWPFTableCell cell : cells){
					paragraphs = cell.getParagraphs();
					for(XWPFParagraph paragraph : paragraphs){
						replaceParamsInParagraph(paragraph, params);
					}
				}
			}
		}
	}
	/**
	 * 正则表达式匹配字符串
	 * @param text
	 * @return
	 */
	public static Matcher getMatcher(String text){
		Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);
		return matcher;
	}
	
	
}
