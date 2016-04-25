package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.OrgEntityIdentityDao;
import com.van.halley.db.persistence.entity.OrgEntityIdentity;
import com.van.service.OrgEntityIdentityService;

@Transactional
@Service("orgEntityIdentityService")
public class OrgEntityIdentityServiceImpl implements OrgEntityIdentityService {
	@Autowired
	private OrgEntityIdentityDao orgEntityIdentityDao;

	public List<OrgEntityIdentity> getAll() {
		return orgEntityIdentityDao.getAll();
	}

	public List<OrgEntityIdentity> queryForList(OrgEntityIdentity orgEntityIdentity) {
		return orgEntityIdentityDao.queryForList(orgEntityIdentity);
	}

	public OrgEntityIdentity queryForOne(OrgEntityIdentity orgEntityIdentity) {
		return orgEntityIdentityDao.queryForOne(orgEntityIdentity);
	}

	public PageView query(PageView pageView, OrgEntityIdentity orgEntityIdentity) {
		List<OrgEntityIdentity> list = orgEntityIdentityDao.query(pageView, orgEntityIdentity);
		pageView.setResults(list);
		return pageView;
	}

	public void add(OrgEntityIdentity orgEntityIdentity) {
		orgEntityIdentityDao.add(orgEntityIdentity);
	}

	public void delete(String id) {
		orgEntityIdentityDao.delete(id);
	}

	public void modify(OrgEntityIdentity orgEntityIdentity) {
		orgEntityIdentityDao.modify(orgEntityIdentity);
	}

	public OrgEntityIdentity getById(String id) {
		return orgEntityIdentityDao.getById(id);
	}
}
