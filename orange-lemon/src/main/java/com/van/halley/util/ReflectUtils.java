package com.van.halley.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectUtils {
	private static Logger logger = LoggerFactory.getLogger(ReflectUtils.class);
	
	/**
	 * 获取通用对象的变量
	 */
	public static List<String> getAttributes(String className){
		List<String> list = new ArrayList<String>();
		try {
			Class<?>  clazz = Class.forName(className);
			Method[] methods = clazz.getDeclaredMethods();
			for(Method method : methods){
				String methodName = method.getName();
				if(methodName.startsWith("get")){
					list.add(lowerFirstChar(deleteGetPreffix(methodName)));
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 首字母小写
	 */
	public static String lowerFirstChar(String text){
		return text.substring(0, 1).toLowerCase() + text.substring(1);
	}
	
	/**
	 * 去除get方法的get
	 */
	public static String deleteGetPreffix(String text){
		if(text.startsWith("get") && text.length() > 3){
			return text.substring(3, text.length());
		}else{
			return text;
		}
	}
	
	
	/**
	 * @param args
	 * 返回对象的ID
	 */
	public static String getIdOfObject(Object args){
		Class<?> clazz = args.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		String id = "";
		for(Method method : methods){
			if(method.getName().equalsIgnoreCase("getid")){
				try {
					id = (String) method.invoke(args);
					break;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
		return id;
	}
	
	/**
	 * @param args
	 * 返回对象的所有属性的值
	 */
	public static String getToStringOfObject(Object args){
		Class<?> clazz = args.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		StringBuilder toString = new StringBuilder(clazz.getName() + " ,");
		
		List<String> fieldNames = new ArrayList<String>();
		List<String> setMethodsName = new ArrayList<String>();
		List<String> getMethodsName = new ArrayList<String>();
		for(Method method : methods){
			String methodName = method.getName();
			if(!"getCallback".equals(methodName) && methodName.startsWith("get")){
				getMethodsName.add(methodName);
				
				String field = methodName.substring(3);
				if(!fieldNames.contains(field)){
					fieldNames.add(field);
				}
				
			}else if(!"setCallback".equals(methodName) && methodName.startsWith("set")){
				setMethodsName.add(methodName);
			}
		}
		
		for(String fieldName : fieldNames){
			if(!setMethodsName.contains("set" + fieldName) || !getMethodsName.contains("get" + fieldName)){
				fieldNames.remove(fieldName);
			}
		}
		
		
		for(Method method : methods){
			String methodName = method.getName();
			if(methodName.startsWith("get") && fieldNames.contains(methodName.substring(3))){
				try {
					toString.append(methodName.substring(3));
					toString.append(": ");
					toString.append(method.invoke(args) == null ? "null" : method.invoke(args).toString());
					toString.append(",");
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					logger.error("参数出错, 方法 {}, 参数 {}, Exception {}", method.getName(), args, e);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					logger.error("数据访问出错, 方法 {}, 参数 {}, Exception {}", method.getName(), args, e);
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					logger.error("方法调用出错, 方法 {}, 参数 {}, Exception {}", method.getName(), args, e);
				}
			}
		}
		toString.deleteCharAt(toString.lastIndexOf(","));
		return toString.toString();
	}
	
	public static String getToStringOfMapOrRequest(Object o){
		StringBuilder toString = new StringBuilder(); 
		if(o instanceof Map){
			Map<String, Object> map = (Map<String, Object>) o;
			for(String key : map.keySet()){
				String value = map.get(key) == null ? "" : map.get(key).toString();
				toString.append(key + " : " + value + ",");
			}
			
		}else if(o instanceof HttpServletRequest){
			HttpServletRequest request = (HttpServletRequest) o;
			Enumeration<String> keys = request.getParameterNames();
			while(keys.hasMoreElements()){
				String key = keys.nextElement();
				toString.append(key + " : " + (String)request.getParameter(key) + ",");
			}
		}
		
		return toString.toString();
	}
	
	/**
	 * 获取类的属性和对应set方法
	 */
	public static Object bindEntityByHttpRequest(Class<?> clazz, HttpServletRequest request){
		Method[] methods = clazz.getDeclaredMethods();
		List<String> list = new ArrayList<String>();
		Object instance = null;
		try {
			instance = clazz.newInstance();
			for(Method method : methods){
				String methodName = method.getName();
				Class<?>[] types = method.getParameterTypes();
				if(methodName.startsWith("set")){
					String field = methodName.substring(3);
					field = lowerFirstChar(field);
					if(!list.contains(field)){
						list.add(field);
						System.out.println(field);
						if(request.getParameter(field) == null){
							continue;
						}else{
							method.invoke(instance, request.getParameter(field));
						}
					}
				}
			}
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		}  catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
		
		return instance;
	}
}
