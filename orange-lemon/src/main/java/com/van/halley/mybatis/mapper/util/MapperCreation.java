package com.van.halley.mybatis.mapper.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MapperCreation<T> {
	public String createXml(Class<T> clazz, Map<String, String> columnRelation, String tableName) {
		StringBuilder content = new StringBuilder();
		//List<String> fieldNames = getFieldNames(clazz);
		Set<String> fieldNames = columnRelation.keySet();
		content.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n");
		content.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \n");
		content.append("\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\"> \n");
		content.append("<mapper namespace=\"" + getClassNameLower(clazz)
				+ "\"> \n");
		content.append("<cache type=\"org.mybatis.caches.ehcache.LoggingEhcache\" />\n");
		content.append("<sql id=\"selectId\"> \n");
		for (String fieldName : fieldNames) {
			content.append(columnRelation.get(fieldName) + " AS " + fieldName + ",\n");
		}
		content.deleteCharAt(content.lastIndexOf(","));
		content.append("</sql>\r\n");
		
		//query
		content.append("\r\n");
		content.append("<select id=\"query\" parameterType=\"java.util.HashMap\" useCache=\"false\" resultType=\"" + getClassName(clazz) + "\" > \n");
		content.append("select \n");
		content.append("<include refid=\"selectId\" /> \n");
		content.append(" from " + tableName + "\n");
		content.append("<where>\n");
		for (String fieldName : fieldNames) {
			content.append("<if test=\"t." + fieldName + " != null and t." + fieldName + " != ''\">\n");
			content.append("and " + columnRelation.get(fieldName) + " = #{t." + fieldName + "}\n");
			content.append("</if>\n");
		}
		
		//pageView的过滤字符串
		content.append("<if test=\"pageView.filterText != null and pageView.filterText != ''\">\n");
		content.append("and ${pageView.filterText}\n");
		content.append("</if>\n");
		
		content.append("</where>\n");
		
		//排序代码
		content.append("<!-- 排序的时候不要使用预处理，不然排序无效，因此此处使用$取值，拼接SQL -->\n");
		content.append("<if test=\"pageView.orderBy != null and pageView.orderBy != ''\">\n");
		content.append("order by ${pageView.orderBy}\n");
		content.append("<if test=\"pageView.order != null and pageView.order != ''\">\n");
		content.append("<if test=\"pageView.order == 'ASC'\">\n");
		content.append("ASC\n");
		content.append("</if>\n");
		content.append("<if test=\"pageView.order == 'DESC'\">\n");
		content.append("DESC\n");
		content.append("</if>\n");
		content.append("</if>\n");
		content.append("</if>\n");
		content.append("</select>\r\n");
		//queryForList
		content.append("\r\n");
		content.append("<select id=\"queryForList\" parameterType=\"" + getClassName(clazz) + "\" resultType=\"" + getClassName(clazz) + "\">\n");
		content.append("select\r\n");
		content.append("<include refid=\"selectId\" />\n");
		content.append("from " + tableName + "\n");
		content.append("<where>\n");
		for (String fieldName : fieldNames) {
			content.append("<if test=\"" + fieldName + " != null and " + fieldName + " != ''\">\n");
			content.append("and " + columnRelation.get(fieldName) + " = #{" + fieldName + "}\n");
			content.append("</if>\n");
		}
		content.append("</where>\n");
		content.append("</select>\r\n");
		content.append("\r\n");
		//getAll
		content.append("<select id=\"getAll\" resultType=\"" + getClassName(clazz) + "\">\n");
		content.append("select\n");
		content.append("<include refid=\"selectId\" />\n");
		content.append("from " + tableName + "\n");
		content.append("</select>\r\n");
		content.append("\r\n");
		//count
		content.append("<select id=\"count\" parameterType=\"" + getClassName(clazz) + "\" resultType=\"int\">\n");
		content.append("select count(0) from " + tableName + "\n");
		content.append("<where>\n");
		for (String fieldName : fieldNames) {
			content.append("<if test=\"" + fieldName + " != null and " + fieldName + " != ''\">\n");
			content.append("and " + columnRelation.get(fieldName) + " = #{" + fieldName + "}\n");
			content.append("</if>\n");
		}
		content.append("</where>\n");
		content.append("</select>\r\n");
		
		//insert
		content.append("\r\n");
		content.append("<insert id=\"add\" parameterType=\"" + getClassName(clazz) + "\">\n");
		content.append("insert into " + tableName + "(\n");
		for (String fieldName : fieldNames) {
			content.append(columnRelation.get(fieldName) + ",\n");
		}
		content.deleteCharAt(content.lastIndexOf(",\n"));
		content.append(")values (\n");
		for (String fieldName : fieldNames) {
			if(!fieldName.equalsIgnoreCase("id")){
				content.append("#{" + fieldName + "},\n");
			}else{
				content.append("<if test=\"id != null and id != '' \">\n");
				content.append("#{" + fieldName + "},\n");
				content.append("</if>\n");
				
				content.append("<if test=\"id == null or id == '' \">\n");
				content.append("UUID(),\n");
				content.append("</if>\n");
			}
			
		}
		content.deleteCharAt(content.lastIndexOf(","));
		content.append(")\n");
		content.append("</insert>\r\n");
		
		//deleteById
		content.append("\r\n");
		content.append("<delete id=\"deleteById\" parameterType=\"String\">\r\n");
		content.append("delete from " + tableName + " where id=#{id}\r\n");
		content.append("</delete>\r\n");
		
		//getById
		content.append("\r\n");
		content.append("<select id=\"getById\" parameterType=\"String\" resultType=\"" + getClassName(clazz) + "\">\r\n");
		content.append("select <include refid=\"selectId\" />\n");
		content.append("from " + tableName + " where id=#{id}\n");
		content.append("</select>\r\n");
		
		//update
		content.append("\r\n");
		content.append("<update id=\"update\" parameterType=\"" + getClassName(clazz) + "\">\r\n");
		content.append("update " + tableName + "\n");
		content.append("<set>\n");
		for (String fieldName : fieldNames) {
			if("ID".equals(fieldName.toUpperCase())){
				continue;
			}else if("MODIFYTIME".equals(fieldName.toUpperCase())){
				content.append("MODIFY_TIME=SYSDATE(),\n");
			}else{
				content.append("<if test=\"" + fieldName + " != null and " + fieldName + " != ''\">\n");
				content.append(columnRelation.get(fieldName) + "=#{" + fieldName + "},\n");
				content.append("</if>\n");
			}
		}
		content.append("</set>\n");
		content.append("WHERE ID=#{id} AND MODIFY_TIME=#{modifyTime} \r\n");//更新数据并发或重复提交处理
		content.append("</update>\r\n");
		
		
		//deleteBatch
		content.append("\r\n");
		content.append("<delete id=\"deleteBatch\" parameterType=\"java.util.List\">\r\n");
		content.append("delete from " + tableName + " where ID IN \r\n");
		content.append("<foreach collection=\"list\"  item=\"t\" index=\"index\" open=\"(\" separator=\",\" close=\")\" >\r\n");
		content.append("#{t.id}\r\n");
		content.append("</foreach>\r\n");
		content.append("</delete>\r\n");
		
		//updateBatch
		content.append("\r\n");
		content.append("<update id=\"updateBatch\" parameterType=\"java.util.List\">\r\n");
		content.append("<foreach collection=\"list\" item=\"t\" index=\"index\" open=\"\" close=\"\" separator=\";\">\r\n");
		content.append("update " + tableName + "\n");
		content.append("<set>\n");
		for (String fieldName : fieldNames) {
			if("ID".equals(fieldName.toUpperCase())){
				continue;
			}else if("MODIFYTIME".equals(fieldName.toUpperCase())){
				content.append("MODIFY_TIME=SYSDATE(),\n");
			}else{
				content.append("<if test=\"t." + fieldName + " != null and t." + fieldName + " != ''\">\n");
				content.append(columnRelation.get(fieldName) + "=#{t." + fieldName + "},\n");
				content.append("</if>\n");
			}
		}
		content.append("</set>\n");
		content.append("WHERE ID=#{t.id} AND MODIFY_TIME=#{modifyTime} \r\n");//更新数据并发或重复提交处理
		content.append("</foreach>\r\n");
		content.append("</update>\r\n");
		
		//insertBatch
		content.append("\r\n");
		content.append("<insert id=\"insertBatch\" parameterType=\"java.util.List\">\n");
		content.append("insert into " + tableName + "(\n");
		for (String fieldName : fieldNames) {
			content.append(columnRelation.get(fieldName) + ",\n");
		}
		content.deleteCharAt(content.lastIndexOf(",\n"));
		content.append(")values");
		content.append("<foreach collection=\"list\" item=\"t\" index=\"index\" separator=\",\">\r\n");
		content.append("(\n");
		for (String fieldName : fieldNames) {
			if(!fieldName.equalsIgnoreCase("id")){
				content.append("#{t." + fieldName + "},\n");
			}else{
				content.append("<if test=\"t.id != null and t.id != '' \">\n");
				content.append("#{t." + fieldName + "},\n");
				content.append("</if>\n");
				
				content.append("<if test=\"t.id == null or t.id == '' \">\n");
				content.append("UUID(),\n");
				content.append("</if>\n");
			}
			
		}
		content.deleteCharAt(content.lastIndexOf(","));
		content.append(")\n");
		content.append("</foreach>\r\n");
		content.append("</insert>\r\n");
				
		content.append("</mapper>\r\n");
		return content.toString();
	}

	public String getClassNameLower(Class<T> clazz) {
		String s = clazz.getName();
		return s.substring(s.lastIndexOf(".") + 1, s.length()).toLowerCase();
	}

	public String getClassName(Class<T> clazz) {
		String s = clazz.getName();
		return s.substring(s.lastIndexOf(".") + 1, s.length());
	}

	// 获取字段明和类型
	public List<String[]> getFieldNameAndTypeLower(Class<T> clazz) {
		List<String[]> fields = new ArrayList<String[]>();
		Field[] fieldsObject = clazz.getDeclaredFields();
		String[] fieldInfo = new String[2];
		for (Field f : fieldsObject) {
			String[] typeString = f.getType().getName().split("\\.");
			fieldInfo[0] = f.getName();
			fieldInfo[1] = typeString[typeString.length - 1];
			fields.add(fieldInfo);
		}
		return fields;
	}

	// 获取字段名
	public List<String> getFieldNames(Class<T> clazz) {
		List<String> fields = new ArrayList<String>();
		Field[] fieldsObject = clazz.getDeclaredFields();
		for (Field f : fieldsObject) {
			fields.add(f.getName());
		}

		return fields;
	}

	public String javaTypeToSQLType(String javaType) {
		if ("int".equalsIgnoreCase(javaType)) {
			return "int";
		} else if ("double".equalsIgnoreCase(javaType)) {
			return "double";
		} else if ("Date".equalsIgnoreCase(javaType)) {
			return "timestamp";
		} else if ("String".equalsIgnoreCase(javaType)) {
			return "varchar(64)";
		} else {
			return "varchar(64)";
		}
	}

	public static void main(String[] args) {
	}
}
