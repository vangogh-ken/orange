package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.UserBaseDao;
import com.van.halley.db.persistence.entity.UserBase;
import com.van.service.UserBaseService;

@Transactional
@Service("userBaseService")
public class UserBaseServiceImpl implements UserBaseService {
	@Autowired
	private UserBaseDao userBaseDao;

	public List<UserBase> getAll() {
		return userBaseDao.getAll();
	}

	public List<UserBase> queryForList(UserBase userBase) {
		return userBaseDao.queryForList(userBase);
	}

	public PageView query(PageView pageView, UserBase userBase) {
		List<UserBase> list = userBaseDao.query(pageView, userBase);
		pageView.setResults(list);
		return pageView;
	}

	public void add(UserBase userBase) {
		userBaseDao.deleteByUserId(userBase.getUserId());
		userBaseDao.add(userBase);
	}

	public void delete(String id) {
		userBaseDao.delete(id);
	}

	public void modify(UserBase userBase) {
		userBaseDao.modify(userBase);
	}

	public UserBase getById(String id) {
		return userBaseDao.getById(id);
	}

	@Override
	public UserBase getByUserId(String userId) {
		return userBaseDao.getByUserId(userId);
	}

	@Override
	public UserBase getByWeixinName(String weixinName) {
		return userBaseDao.getByWeixinName(weixinName);
	}
}
