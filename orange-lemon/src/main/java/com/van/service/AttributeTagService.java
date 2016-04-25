package com.van.service;

import java.util.HashMap;
import java.util.Map;

public interface AttributeTagService {
	//////////////////////把数据缓存，提高运行速度//////////////////////////////
	///K CLSID > ATTRNAME > ATTR_ID
	/*public static Map<String, Map<String, String>> attributeCache = new HashMap<String, Map<String, String>>();
	///K CLSID > STATUS > TYPE
	public static Map<String, Map<String, String>> statusCache = new HashMap<String, Map<String, String>>();
	///K CLSID > ATTR+STATUS > TYPE
	
	;/*
	public boolean isReadonly(String status, String attribute, String clsId, String controllerId, String taskId);
	
	public boolean isReadonlyForTask(String attribute, String clsId, String controllerId, String taskId);
	//普通编辑或者查看使用
	public boolean isReadonlyForNormal(String status, String attribute, String clsId, String controllerId);
	//获取值类型
	
	public List<String> getTypeAndValue(String attribute, String clsId);*/
	//public static Map<String, String> attributeCache = new HashMap<String, String>();
	public static Map<String, String> attributeStatusCache = new HashMap<String, String>();
	//刷新缓存
	public void refreshCache();
	
	/**
	 * 获取当前属性是否只读，以及VALUE id
	 * @return
	 */
	public Map<String, String> getAttributeProperty(String basisSubstanceId, String attributeColumn, String taskId);
}
