package com.van.ext.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.van.halley.core.util.ReflectUtils;


/**
 * @author Think
 * 基本数据的导出模板
 */
public class ExportDataModel {
	private static Logger logger = LoggerFactory.getLogger(ExportDataModel.class);
    private String fileName;
    private List<String> headers = new ArrayList<String>();
    private List<String> getValueMethods = new ArrayList<String>();
    private List<?> list;
    
    public ExportDataModel(String fileName, List<?> list){
    	this.fileName = fileName;
    	this.list = list;
    }
    
    public void addHeaders(String... array){
    	for(int i=0, len = array.length; i<len; i++){
    		headers.add(array[i]);
    	}
    }
    
    public void addGetValueMethods(String... array){
    	for(int i=0, len = array.length; i<len; i++){
    		getValueMethods.add(array[i]);
    	}
    }
    
    public String getValue(int i, String getValueMethod) {
        try {
            Object object = list.get(i);
            Object value = null;
            //如果是直接通过jdbcTemplate查询出来的List<Map<String, Object>>, 则getValueMethod就是对应查出的字段
            if(object instanceof Map){
            	value = ((Map) object).get(getValueMethod);
            }else{
            	value = ReflectUtils.getMethodValue(object, getValueMethod);
            }
            
            if(value instanceof Date){
            	value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
            }
            
            return (value == null) ? "" : value.toString();
        } catch (Exception ex) {
            logger.info("error", ex);
            return "";
        }
    }
    
    public String toCsv(){
    	if(headers.size() != getValueMethods.size()){
    		throw new IllegalAccessError("Header does not match with value");
    	}
    	StringBuilder content = new StringBuilder();
    	for(int i=0, len = headers.size(); i < len; i++){
    		content.append(headers.get(i));
    		if(i != (len -1)){
    			content.append(",");
    		}
    	}
    	content.append("\n");
    	
    	for(int i=0, len = list.size(); i < len; i++){
    		for(int j=0, siz = getValueMethods.size(); j < siz; j++){
    			content.append(getValue(i, getValueMethods.get(j)));
    			if(j != (siz -1)){
        			content.append(",");
        		}
    		}
    		content.append("\n");
    	}
    	
    	return content.toString();
    }

	public String getFileName() {
		return fileName;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public List<String> getGetValueMethods() {
		return getValueMethods;
	}

	public List<?> getList() {
		return list;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public void setGetValueMethods(List<String> getValueMethods) {
		this.getValueMethods = getValueMethods;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
}
