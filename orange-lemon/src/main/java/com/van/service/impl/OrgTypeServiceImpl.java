package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.OrgTypeDao;
import com.van.halley.db.persistence.entity.OrgType;
import com.van.service.OrgTypeService;

@Transactional
@Service("orgTypeService")
public class OrgTypeServiceImpl implements OrgTypeService {
	@Autowired
	private OrgTypeDao orgTypeDao;

	public List<OrgType> getAll() {
		return orgTypeDao.getAll();
	}

	public List<OrgType> queryForList(OrgType orgType) {
		return orgTypeDao.queryForList(orgType);
	}

	public PageView query(PageView pageView, OrgType orgType) {
		List<OrgType> list = orgTypeDao.query(pageView, orgType);
		pageView.setResults(list);
		return pageView;
	}

	public void add(OrgType orgType) {
		orgTypeDao.add(orgType);
	}

	public void delete(String id) {
		orgTypeDao.delete(id);
	}

	public void modify(OrgType orgType) {
		orgTypeDao.modify(orgType);
	}

	public OrgType getById(String id) {
		return orgTypeDao.getById(id);
	}
}
