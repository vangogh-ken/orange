package com.van.halley.core.session;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.LoginLog;
import com.van.service.LoginLogService;

/**
 * 事件参考org.springframework.security.web.session.HttpSessionEventPublisher
 * @author anxin
 *
 */
@Component
public class HttpSessionDestroyed implements ApplicationListener<HttpSessionDestroyedEvent>{
	@Autowired
	private LoginLogService loginLogService;
	@Autowired
	private HttpSessionRegistry httpSessionRegistry;
	
	@Override
	public void onApplicationEvent(HttpSessionDestroyedEvent event) {
		LoginLog login = loginLogService.getById(event.getId());
		login.setLogoutTime(new Date());
		loginLogService.modify(login);
		
		httpSessionRegistry.cancel(event.getSession());
	}
	
	@PostConstruct
	public void init(){
		PageView<LoginLog> pageView = new PageView<LoginLog>(1);
		pageView.setFilterText("LOGOUT_TIME = 0");
		loginLogService.query(pageView, new LoginLog());
		List<LoginLog> logins = pageView.getResults();
		if(logins != null && !logins.isEmpty()){
			for(LoginLog login : logins){
				login.setLogoutTime(new Date());
				loginLogService.modify(login);
			}
		}
	}

}
