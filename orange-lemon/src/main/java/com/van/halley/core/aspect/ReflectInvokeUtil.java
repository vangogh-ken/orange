package com.van.halley.core.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectInvokeUtil {
	private static Logger LOG = LoggerFactory.getLogger(ReflectInvokeUtil.class);
	
	/**
	 * 通过反射调用并返回值
	 * @param beanClass
	 * @param methodName
	 * @param arguments
	 * @return
	 */
	public static Object invokeReturnObject(String beanClass, String methodName, Object... arguments){
		Object object = null;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(beanClass);
			object = clazz.newInstance();
			AutowiredAspectSupport.autowired(clazz, object);
			
			Method[] methods = clazz.getDeclaredMethods();
			if(methods != null && methods.length != 0){
				for(Method method : methods){
					if(method.getName().equals(methodName)){
						if(arguments == null){
							return method.invoke(object, new Object[method.getParameterCount()]);
						}else{
							return method.invoke(object, arguments);
						}
					}
				}
			}
			
			/*
			 * Method method = clazz.getDeclaredMethod(methodName);//只能获取其无参方法
			 if (method != null) {
				return method.invoke(object, arguments);
			}*/
		} catch (ClassNotFoundException e) {
			LOG.error("类型 {}， 调用方法 {}，  出错 {}", beanClass, methodName, e);
		}catch (InstantiationException e) {
			LOG.error("类型 {}， 调用方法 {}，  出错 {}", beanClass, methodName, e);
		} catch (IllegalAccessException e) {
			LOG.error("类型 {}， 调用方法 {}，  出错 {}", beanClass, methodName, e);
		} catch (SecurityException e) {
			LOG.error("类型 {}， 调用方法 {}，  出错 {}", beanClass, methodName, e);
		} catch (IllegalArgumentException e) {
			LOG.error("类型 {}， 调用方法 {}，  出错 {}", beanClass, methodName, e);
		} catch (InvocationTargetException e) {
			LOG.error("类型 {}， 调用方法 {}，  出错 {}", beanClass, methodName, e);
		}
		
		return null;
	}
	
	/**
	 * 通过反射调用并返回值
	 * @param beanClass
	 * @param methodName
	 * @param arguments
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> invokeReturnMap(String beanClass, String methodName, Object... arguments){
		Object object = null;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(beanClass);
			object = clazz.newInstance();
			AutowiredAspectSupport.autowired(clazz, object);
			
			Method[] methods = clazz.getDeclaredMethods();
			if(methods != null && methods.length != 0){
				for(Method method : methods){
					if(method.getName().equals(methodName)){
						if(method.getReturnType().equals(Map.class)){
							if(arguments == null){
								return (Map<String, Object>)method.invoke(object, new Object[method.getParameterCount()]);
							}else{
								return (Map<String, Object>)method.invoke(object, arguments);
							}
						}else{
							LOG.error("类型 {}， 调用方法 {}，  出错 {}", beanClass, methodName, "返回值类型不一致");
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			LOG.error("类型 {}， 调用方法 {}，  出错 {}", beanClass, methodName, e);
		}catch (InstantiationException e) {
			LOG.error("类型 {}， 调用方法 {}，  出错 {}", beanClass, methodName, e);
		} catch (IllegalAccessException e) {
			LOG.error("类型 {}， 调用方法 {}，  出错 {}", beanClass, methodName, e);
		}catch (SecurityException e) {
			LOG.error("类型 {}， 调用方法 {}，  出错 {}", beanClass, methodName, e);
		} catch (IllegalArgumentException e) {
			LOG.error("类型 {}， 调用方法 {}，  出错 {}", beanClass, methodName, e);
		} catch (InvocationTargetException e) {
			LOG.error("类型 {}， 调用方法 {}，  出错 {}", beanClass, methodName, e);
		}
		
		return null;
	}
	
	/**
	 * 对对象进行分组
	 * @param list item with order !!!
	 * @param groupBys
	 * @return
	 */
	public static List<Map<String, Object>> groupObject(Class<?> clazz, List<?> list, String... groupBys){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> group = null;
		List<String> groupFields = Arrays.asList(groupBys);
		Map<String, Object> item = new HashMap<String, Object>();
		for(Object object : list){
			Map<String, Object> values = getValueOfObject(clazz, object);
			if(group == null){
				item = values;
				group = new HashMap<String, Object>();
				for(String groupField : groupFields){
					group.put(groupField, values.get(groupField));
				}
			}else{
				boolean flag = true;
				for(String groupField : groupFields){
					if((group.get(groupField) == null && values.get(groupField) != null) 
							|| (group.get(groupField) != null &&  values.get(groupField) == null)
							|| (isBaseType(group.get(groupField)) && group.get(groupField) != values.get(groupField))
							|| (!isBaseType(group.get(groupField)) && !group.get(groupField).equals(values.get(groupField)))){
						flag = false;
						break;
					}
				}
				
				if(flag){
					item = mergeValueOfObject(groupFields, item, values);
				}else{
					result.add(item);
					
					item = values;
					//~  reset group
					group = new HashMap<String, Object>();
					for(String groupField : groupFields){
						group.put(groupField, values.get(groupField));
					}
				}
			}
			
		}
		
		result.add(item);
		return result;
	}
	
	/**
	 * 需要将分组的字段排除
	 * 将解析出的对象值合并到map。当前只支持基础数据类型的相加，以及List合并，若为其他类型的数据则只能toString()
	 * @param map
	 * @param mergeObject
	 */
	public static Map<String, Object> mergeValueOfObject(List<String> groupFields, 
			Map<String, Object> map, Map<String, Object> mergeObject){
		for(Entry<String, Object> entry : mergeObject.entrySet()){
			if(groupFields.contains(entry.getKey())){
				continue;
			}else{
				if(entry.getValue() == null){
					continue;
				}else{
					 if(isArithmetic(entry.getValue())){
						 map.put(entry.getKey(), arithmetic(map.get(entry.getKey()), entry.getValue()));
					 }else{
						 List<Object> list = new ArrayList<Object>();
						 if(isListType(entry.getKey())){
							 list.addAll((List<Object>)(entry.getValue()));
							 
							 if(map.get(entry.getKey()) != null){
								 list.addAll((List<Object>)(map.get(entry.getKey())));
							 }
							 
							 map.put(entry.getKey(), list);
						 }else{
							 list.add(map.get(entry.getKey()));
							 list.add(entry.getValue());
							 map.put(entry.getKey(), list);
						 }
					 }
				}
			}
		}
		
		return map;
	}
	
	/**
	 * 获取对象属性的值
	 * @param clazz
	 * @param object
	 * @return
	 */
	public static Map<String, Object> getValueOfObject(Class<?> clazz, Object object){
		Field[] fields = clazz.getDeclaredFields();
		Method[] methods = clazz.getMethods();//~ get values only by getter
		
		Map<String, Method> methodMap = new HashMap<String, Method>();
		for(Method method : methods){
			String methodName = method.getName();
			if(methodName.startsWith("get") && method.getParameterCount() == 0){
				methodMap.put(lowerFirstChar(methodName.substring(methodName.indexOf("get") + 3, methodName.length())), method);
			}
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		for(Field field : fields){
			Method method = methodMap.get(field.getName());
			if(method != null){
				try {
					result.put(field.getName(), method.invoke(object, new Object[]{}));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	public static String lowerFirstChar(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}
	
	public static boolean isListType(Object object){
		if(object instanceof List){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断是否为基础类型
	 * @param object
	 * @return
	 */
	public static boolean isBaseType(Object object){
		//if(object instanceof Byte){
			//return true;
		//}else 
		if(object instanceof Short){
			return true;
		}else if(object instanceof Integer){
			return true;
		}else if(object instanceof Long){
			return true;
		}else if(object instanceof Double){
			return true;
		}else if(object instanceof Float){
			return true;
		}
		//else if(object instanceof Boolean){
			//return true;
		//}
		else if(object instanceof Character){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 是否可直接运算
	 * @param object
	 * @return
	 */
	public static boolean isArithmetic(Object object){
		if(object instanceof Short){
			return true;
		}else if(object instanceof Integer){
			return true;
		}else if(object instanceof Long){
			return true;
		}else if(object instanceof Double){
			return true;
		}else if(object instanceof Float){
			return true;
		}else if(object instanceof Character){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 对基础数据进行运算
	 * @param source
	 * @param target
	 * @return
	 */
	public static Object arithmetic(Object source, Object target){
		if(source == null){
			return target;
		}
		
		if(target instanceof Short){
			return (Short)source + (Short)target;
		}else if(target instanceof Integer){
			return (Integer)source + (Integer)target;
		}else if(target instanceof Long){
			return (Long)source + (Long)target;
		}else if(target instanceof Double){
			return (Double)source + (Double)target;
		}else if(target instanceof Float){
			return (Float)source + (Float)target;
		}else if(target instanceof Character){
			return (Character)source + (Character)target;
		}else{
			LOG.error("NOT arithmetic type, current type {}", target.getClass().getName());
			return null;
		}
	}
}
