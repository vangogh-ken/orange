package com.van.halley.util;

import java.io.IOException;
import java.io.InputStream;

import javax.activation.DataSource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.van.halley.db.persistence.entity.OrgEntity;
import com.van.halley.db.persistence.entity.Position;
import com.van.halley.db.persistence.entity.User;

public class Object2JsonUtil {
	private static Logger LOG = LoggerFactory.getLogger(Object2JsonUtil.class);
	private static ObjectMapper mapper = null;
	static{
		mapper = new ObjectMapper();
		mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>(){
			@Override
			public void serialize(Object value, JsonGenerator gernerator,
					SerializerProvider provider) throws IOException,
					JsonProcessingException {
				gernerator.writeString("");
			}
			
		});
		mapper.configure(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}
	/**
	 * 转换为json字符串
	 * @param t
	 * @return
	 */
	public static String toJsonString(Object t){
		if(t == null){
			return null;
		}
		if(t instanceof ServletRequest || t instanceof ServletResponse || t instanceof DataSource || t instanceof InputStream){
			return "HttpServletRequest, ServletResponse请求内容不可查看";
		}
		try {
			if(mapper.writeValueAsString(t).length() > 60000){
				LOG.error("对象转换为jsong字符串长度太大: {}", mapper.writeValueAsString(t));
				return null;
			}
			return t.getClass().getName() + " : " + mapper.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			LOG.error("被转换类型：{}, 对象转换为jsong字符串出错1: {}", t.getClass().getName(), e);
			return null;
		} catch(Exception e){
			LOG.error("被转换类型：{}, 对象转换为jsong字符串出错2: {}", t.getClass().getName(), e);
			return null;
		}
	}
	
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?> ...elementClasses){
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}
	
	/**
	 * 转换为对象
	 * @param jsonString
	 * @param requiredType
	 * @return
	 */
	public static <T> T toSerialObject(String jsonString, Class<T> requiredType){
		if(!StringUtils.isNotEmpty(jsonString)){
			return null;
		}
		try {
			return mapper.readValue(jsonString, requiredType);
		} catch (JsonParseException e) {
			LOG.error("被转换类型：{}, 对象转换为jsong字符串出错1: {}", requiredType.getName(), e);
		} catch (JsonMappingException e) {
			LOG.error("被转换类型：{}, 对象转换为jsong字符串出错2: {}", requiredType.getName(), e);
		} catch (IOException e) {
			LOG.error("被转换类型：{}, 对象转换为jsong字符串出错3: {}", requiredType.getName(), e);
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		User user = new User();
		user.setId("xxxx");
		OrgEntity orgEntity = new OrgEntity();
		orgEntity.setOrgEntityName("1");
		orgEntity.setParentOrg(orgEntity);
		Position position = new Position();
		position.setDescn("职位");
		position.setOrgEntity(orgEntity);
		user.setPosition(position);
		user.setDisplayName("yyy");
		System.out.println(toJsonString(user));
	}
}
