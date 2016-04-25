package com.van.halley.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hwpf.HWPFDocument;

public class DoxUtil {
	public static HWPFDocument getDocument(File file){
		HWPFDocument document = null;
		try {
			document = new HWPFDocument(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(document.getDocumentText());
		//System.out.println(document.getTextTable().);
		
		return document;
	}
	
	public static void main(String[] args) {
		getDocument(new File("F:\\系统资料\\创源操作委托模板_ - 副本\\熏蒸委托书-嘉林科技.doc"));
	}
}
