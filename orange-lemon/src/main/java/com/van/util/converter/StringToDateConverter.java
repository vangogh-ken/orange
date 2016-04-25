package com.van.util.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import com.van.halley.util.StringUtil;

/**
 * @author Vangogh 字符串对日期的转换
 */
public class StringToDateConverter implements Converter<String, Date> {
	private String source;

	public StringToDateConverter() {
		
	}

	public StringToDateConverter(String source) {
		this.source = source;
	}
	
	public Date convert(String source) {
		if (!StringUtil.hasLength(source)) {
			return null;
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
