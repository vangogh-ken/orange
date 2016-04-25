package com.van.halley.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * 基础控制器
 * @author anxin
 *
 */
public class BasisController {

	private static Logger log = LoggerFactory.getLogger(BasisController.class);

	@Resource
	private MessageSource messageSource;

	@ExceptionHandler
	@ResponseBody
	public Map<String, Object> exp(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		Map<String, Object> map = new HashMap<String, Object>();

		int status = HttpServletResponse.SC_BAD_REQUEST;
		int errorCode = HttpServletResponse.SC_BAD_REQUEST;
		String msg = "";
		errorCode = HttpServletResponse.SC_NOT_FOUND;
		msg = ex.getMessage();
		// 其他系统异常
		log.error(ex.getMessage(), ex);
		status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		errorCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		msg = messageSource.getMessage("500", null, "server error", request.getLocale());
		response.setStatus(status);
		map.put("status", status);
		map.put("errorCode", errorCode);
		map.put("msg", msg);

		return map;
	}
}
