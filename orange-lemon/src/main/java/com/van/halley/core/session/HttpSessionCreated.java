package com.van.halley.core.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.stereotype.Component;

import com.van.halley.db.persistence.entity.LoginLog;
import com.van.service.LoginLogService;

/**
 * 事件参考org.springframework.security.web.session.HttpSessionEventPublisher
 * @author anxin
 *
 */
@Component
public class HttpSessionCreated implements ApplicationListener<HttpSessionCreatedEvent>{
	@Autowired
	private LoginLogService loginLogService;
	@Autowired
	private HttpSessionRegistry httpSessionRegistry;
	
	@Override
	public void onApplicationEvent(HttpSessionCreatedEvent event) {
		LoginLog login = new LoginLog();
		login.setId(event.getSession().getId());
		loginLogService.add(login);
		httpSessionRegistry.regist(event.getSession());
	}

}
