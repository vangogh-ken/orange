package com.van.util.converter;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

public class DecoderStringConverter implements Converter<String, String> {
	private static Logger logger = LoggerFactory.getLogger(DecoderStringConverter.class);
	@Override
	public String convert(String text) {
		logger.debug("decoder {}", text);
		if(text == null || "".equals(text)){
			return "";
		}else{
			String result = "";
			try {
				result = URLDecoder.decode(text, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return result;
		}
	}

}
