package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.LoginLogDao;
import com.van.halley.db.persistence.entity.LoginLog;
import com.van.service.LoginLogService;

@Transactional
@Service("loginLogService")
public class LoginLogServiceImpl implements LoginLogService {
	@Autowired
	private LoginLogDao loginLogDao;

	public PageView<LoginLog> query(PageView<LoginLog> pageView, LoginLog loginLog) {
		List<LoginLog> list = loginLogDao.query(pageView, loginLog);
		pageView.setResults(list);
		return pageView;
	}

	public void add(LoginLog loginLog) {
		loginLogDao.add(loginLog);
	}

	@Override
	public LoginLog getById(String id) {
		return loginLogDao.getById(id);
	}

	@Override
	public void modify(LoginLog loginLog) {
		loginLogDao.modify(loginLog);
	}

}
