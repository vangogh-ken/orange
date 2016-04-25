package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.ConstantAuthDao;
import com.van.halley.db.persistence.entity.ConstantAuth;
import com.van.halley.db.persistence.entity.User;
import com.van.service.ConstantAuthService;

@Transactional
@Service("constantAuthService")
public class ConstantAuthServiceImpl implements ConstantAuthService {
	@Autowired
	private ConstantAuthDao constantAuthDao;

	public List<ConstantAuth> getAll() {
		return constantAuthDao.getAll();
	}

	public List<ConstantAuth> queryForList(ConstantAuth constantAuth) {
		return constantAuthDao.queryForList(constantAuth);
	}

	public ConstantAuth queryForOne(ConstantAuth constantAuth) {
		return constantAuthDao.queryForOne(constantAuth);
	}

	public PageView query(PageView pageView, ConstantAuth constantAuth) {
		List<ConstantAuth> list = constantAuthDao.query(pageView, constantAuth);
		pageView.setResults(list);
		return pageView;
	}

	public void add(ConstantAuth constantAuth) {
		constantAuthDao.add(constantAuth);
	}

	public void delete(String id) {
		constantAuthDao.delete(id);
	}

	public void modify(ConstantAuth constantAuth) {
		constantAuthDao.modify(constantAuth);
	}

	public ConstantAuth getById(String id) {
		return constantAuthDao.getById(id);
	}

	@Override
	public List<User> getUserByConstantId(String constantId, String userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
