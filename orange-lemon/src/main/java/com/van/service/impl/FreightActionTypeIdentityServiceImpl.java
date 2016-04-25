package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightActionTypeIdentityDao;
import com.van.halley.db.persistence.entity.FreightActionTypeIdentity;
import com.van.service.FreightActionTypeIdentityService;

@Transactional
@Service("freightActionTypeIdentityService")
public class FreightActionTypeIdentityServiceImpl implements
		FreightActionTypeIdentityService {
	@Autowired
	private FreightActionTypeIdentityDao freightActionTypeIdentityDao;

	public List<FreightActionTypeIdentity> getAll() {
		return freightActionTypeIdentityDao.getAll();
	}

	public List<FreightActionTypeIdentity> queryForList(
			FreightActionTypeIdentity freightActionTypeIdentity) {
		return freightActionTypeIdentityDao
				.queryForList(freightActionTypeIdentity);
	}

	public PageView query(PageView pageView,
			FreightActionTypeIdentity freightActionTypeIdentity) {
		List<FreightActionTypeIdentity> list = freightActionTypeIdentityDao
				.query(pageView, freightActionTypeIdentity);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightActionTypeIdentity freightActionTypeIdentity) {
		freightActionTypeIdentityDao.add(freightActionTypeIdentity);
	}

	public void delete(String id) {
		freightActionTypeIdentityDao.delete(id);
	}

	public void modify(FreightActionTypeIdentity freightActionTypeIdentity) {
		freightActionTypeIdentityDao.modify(freightActionTypeIdentity);
	}

	public FreightActionTypeIdentity getById(String id) {
		return freightActionTypeIdentityDao.getById(id);
	}

	@Override
	public void deleteByFreightActionTypeId(String freightActionTypeId) {
		freightActionTypeIdentityDao.deleteByFreightActionTypeId(freightActionTypeId);
	}

	@Override
	public FreightActionTypeIdentity getByFreightActionTypeId(String freightActionTypeId) {
		return freightActionTypeIdentityDao.getByFreightActionTypeId(freightActionTypeId);
	}
}
