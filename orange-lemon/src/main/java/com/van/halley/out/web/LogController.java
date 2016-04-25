package com.van.halley.out.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Context;
import org.apache.catalina.Manager;
import org.apache.catalina.Session;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.van.core.spring.ApplicationContextHelper;
import com.van.core.spring.ApplicationPropertiesFactoryBean;
import com.van.halley.core.page.PageView;
import com.van.halley.core.session.HttpSessionRegistry;
import com.van.halley.db.persistence.entity.LoginLog;
import com.van.halley.db.persistence.entity.OperationLog;
import com.van.halley.util.StringUtil;
import com.van.service.LoginLogService;
import com.van.service.OperationLogService;

@Controller
@RequestMapping(value = "/log/")
public class LogController {
	@Autowired
	private LoginLogService loginLogService;
	@Autowired
	private OperationLogService operationLogService;
	@Autowired
	private HttpSessionRegistry httpSessionRegistry;

	/**
	 * 查询客户登陆信息
	 */
	@RequestMapping(value = "log-login-list")
	public String queryUserLogin(Model model, LoginLog loginLog,
			String displayName, @ModelAttribute PageView<LoginLog> pageView) {
		if (pageView == null) {
			pageView = new PageView<LoginLog>(1);
		}
		
		if(pageView.getOrderBy() == null){
			pageView.setOrderBy("LOGIN_TIME");
			pageView.setOrder("DESC");
		}
		
		if(!StringUtil.isNullOrEmpty(displayName)){
			String filterText = " USER_ID IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + displayName + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		loginLogService.query(pageView, loginLog);
		model.addAttribute("pageView", pageView);
		return "/content/log/log-login-list";
	}
	
	@RequestMapping(value = "sessions-list")
	public String activeSessions(Model model, RequestFacade request, HttpServletResponse response) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		List<Session> sessions = new ArrayList<Session>();
		if(request instanceof RequestFacade){
			Field requestField = request.getClass().getDeclaredField("request");
			requestField.setAccessible(true);
			Request req = (Request)requestField.get(request);
			Context ctx = req.getContext();
			Manager manager = ctx.getManager();
		    sessions.addAll(Arrays.asList(manager.findSessions()));
		}
		model.addAttribute("sessions", httpSessionRegistry.getSessions());
		return "/content/log/sessions-list";
	}
	
	@RequestMapping(value = "log-operation-list")
	public String queryUserLogin(Model model, OperationLog operationLog,
			String displayName, @ModelAttribute PageView<OperationLog> pageView) {
		if (pageView == null) {
			pageView = new PageView<OperationLog>(1);
		}
		if(pageView.getOrderBy() == null){
			pageView.setOrderBy("CREATE_TIME");
			pageView.setOrder("DESC");
		}
		if(!StringUtil.isNullOrEmpty(displayName)){
			String filterText = " USER_ID IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + displayName + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		operationLogService.query(pageView, operationLog);
		model.addAttribute("pageView", pageView);
		return "/content/log/log-operation-list";
	}
	
	@RequestMapping(value = "down-load-admin-log")
	public void down(HttpServletRequest request, HttpServletResponse response) throws IOException{
		ApplicationPropertiesFactoryBean applicationProperties = 
				ApplicationContextHelper.getBean(ApplicationPropertiesFactoryBean.class);
		String path = applicationProperties.getProperties().getProperty("log.base");
		String fileName = "halley." + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log";
		File file = new File(path, fileName);
		if(file.exists()){
			FileInputStream in = FileUtils.openInputStream(file);
			int len = in.available();
			response.reset();
			response.setHeader("Connection", "close");
			response.setContentType("application/x-msdownload");
			response.addHeader("Content-Type;charset=UTF-8","application/octet-stream");
			response.addHeader("Content-Length", "" + len);
			response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));

			byte[] cache = new byte[1024 * 1024];
			ServletOutputStream servletOS = response.getOutputStream();
			while (in.read(cache) > -1) {
				servletOS.write(cache);
			}
			servletOS.flush();
			servletOS.close();
		}else{
			response.getWriter().println("<script>alert('Can not find any log file !');</script>");
		}
	}

}
