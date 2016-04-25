package com.van.halley.core.dbmigrate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DataMigrateUtil {
	private static Logger LOG = LoggerFactory.getLogger(DataMigrateUtil.class);

	/**
	 * 获取被跳过的表名
	 * @param xmlPath
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getSkipTables(String xmlPath) {
		List<String> skipTables = new ArrayList<String>();
		InputStream is = DataMigrateUtil.class.getResourceAsStream(xmlPath);
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(is);
			Element root = document.getRootElement();
			Element skipTableElements = root.element("skipTables");
			for(Iterator<Element> it = skipTableElements.elementIterator(); it.hasNext(); ){
				Element skipTableElement = it.next();
				skipTables.add(skipTableElement.getText().toUpperCase());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			LOG.error("parse xml error: {}", e);
		}
		return skipTables;
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getStrictTables(String xmlPath) {
		List<String> skipTables = new ArrayList<String>();
		InputStream is = DataMigrateUtil.class.getResourceAsStream(xmlPath);
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(is);
			Element root = document.getRootElement();
			Element skipTableElements = root.element("strictTables");
			for(Iterator<Element> it = skipTableElements.elementIterator(); it.hasNext(); ){
				Element skipTableElement = it.next();
				skipTables.add(skipTableElement.getText().toUpperCase());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			LOG.error("parse xml error: {}", e);
		}
		return skipTables;
	}
	
	/**
	 * 获取被跳过的字段名
	 * @param xmlPath
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, List<String>> getSkipColumns(String xmlPath) {
		Map<String, List<String>> skipColumns = new HashMap<String, List<String>>();
		InputStream is = DataMigrateUtil.class.getResourceAsStream(xmlPath);
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(is);
			Element root = document.getRootElement();
			Element skipColumnElements = root.element("skipColumns");
			for(Iterator<Element> it = skipColumnElements.elementIterator(); it.hasNext(); ){
				Element skipColumnElement = it.next();
				String tableName = skipColumnElement.attributeValue("value").toUpperCase();
				
				List<String> skipCols = new ArrayList<String>();
				for(Iterator<Element> i = skipColumnElement.elementIterator(); i.hasNext(); ){
					Element skipColElement = i.next();
					skipCols.add(skipColElement.getText().toUpperCase());
				}
				
				skipColumns.put(tableName, skipCols);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			LOG.error("parse xml error: {}", e);
		}
		return skipColumns;
	}
	
	public static String buildSelect(String tableName, List<String> columns){
		Assert.assertTrue(tableName != null && columns != null && !columns.isEmpty());
		
		StringBuilder sql = new StringBuilder("SELECT ");
		for(String column : columns){
			sql.append(column + ",");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(" FROM " + tableName);
		
		return sql.toString();
	}
	
	public static String buildCount(String tableName, List<String> primaries){
		Assert.assertTrue(tableName != null && primaries != null && !primaries.isEmpty());
		
		StringBuilder sql = new StringBuilder("SELECT COUNT(1) FROM " + tableName + " WHERE ");
		for(String primary : primaries){
			sql.append(" " + primary + "= ? AND");
		}
		return sql.substring(0, sql.lastIndexOf("AND"));
	}
	
	
	@SuppressWarnings("unused")
	public static String buildInsert(String tableName, List<String> columns){
		Assert.assertTrue(tableName != null && columns != null && !columns.isEmpty());
		
		StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + "(");
		for(String column : columns){
			sql.append(column + " ,");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(") VALUES (");
		for(String column : columns){
			sql.append("? ,");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(")");
		return sql.toString();
	}
	
	public static String buildUpdate(String tableName, List<String> primaries, List<String> columns){
		Assert.assertTrue(tableName != null && primaries != null && !primaries.isEmpty());
		
		StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
		for(String column : columns){
			sql.append(column + "=? ,");
		}
		
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(" WHERE ");
		for(String primary : primaries){
			sql.append(" " + primary + "= ? AND");
		}
		return sql.substring(0, sql.lastIndexOf("AND"));
	}
	
	public static String buildDelete(String tableName, List<String> primaries){
		Assert.assertTrue(tableName != null && primaries != null && !primaries.isEmpty());
		
		StringBuilder sql = new StringBuilder("DELETE FROM " + tableName + " WHERE ");
		for(String primary : primaries){
			sql.append(" " + primary + "= ? AND");
		}
		return sql.substring(0, sql.lastIndexOf("AND"));
	}
	
	
	public static void main(String[] args) {
		System.out.println(getSkipTables("/datamigrate.xml").size());
		getSkipColumns("/datamigrate.xml");
	}
}
