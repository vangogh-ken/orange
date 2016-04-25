package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BpmConfigAuthDao;
import com.van.halley.db.persistence.entity.BpmConfigAuth;
import com.van.service.BpmConfigAuthService;

@Transactional
@Service("bpmConfigAuthService")
public class BpmConfigAuthServiceImpl implements BpmConfigAuthService {
	@Autowired
	private BpmConfigAuthDao bpmConfigAuthDao;

	public List<BpmConfigAuth> getAll() {
		return bpmConfigAuthDao.getAll();
	}

	public List<BpmConfigAuth> queryForList(BpmConfigAuth bpmConfigAuth) {
		return bpmConfigAuthDao.queryForList(bpmConfigAuth);
	}

	public BpmConfigAuth queryForOne(BpmConfigAuth bpmConfigAuth) {
		return bpmConfigAuthDao.queryForOne(bpmConfigAuth);
	}

	public PageView query(PageView pageView, BpmConfigAuth bpmConfigAuth) {
		List<BpmConfigAuth> list = bpmConfigAuthDao.query(pageView,
				bpmConfigAuth);
		pageView.setResults(list);
		return pageView;
	}

	public void add(BpmConfigAuth bpmConfigAuth) {
		bpmConfigAuthDao.add(bpmConfigAuth);
	}

	public void delete(String id) {
		bpmConfigAuthDao.delete(id);
	}

	public void modify(BpmConfigAuth bpmConfigAuth) {
		bpmConfigAuthDao.modify(bpmConfigAuth);
	}

	public BpmConfigAuth getById(String id) {
		return bpmConfigAuthDao.getById(id);
	}
}
