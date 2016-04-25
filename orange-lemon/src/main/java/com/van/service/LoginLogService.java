package com.van.service;


import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.LoginLog;

public interface LoginLogService {
	public PageView<LoginLog> query(PageView<LoginLog> pageView, LoginLog loginLog);

	public void add(LoginLog loginLog);

	public LoginLog getById(String id);

	public void modify(LoginLog loginLog);

}
