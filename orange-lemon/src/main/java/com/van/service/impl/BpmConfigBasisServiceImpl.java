package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BpmConfigBasisDao;
import com.van.halley.db.persistence.entity.BpmConfigBasis;
import com.van.service.BpmConfigBasisService;

@Transactional
@Service("bpmConfigBasisService")
public class BpmConfigBasisServiceImpl implements BpmConfigBasisService {
	@Autowired
	private BpmConfigBasisDao bpmConfigBasisDao;

	public List<BpmConfigBasis> getAll() {
		return bpmConfigBasisDao.getAll();
	}

	public List<BpmConfigBasis> queryForList(BpmConfigBasis bpmConfigBasis) {
		return bpmConfigBasisDao.queryForList(bpmConfigBasis);
	}

	public BpmConfigBasis queryForOne(BpmConfigBasis bpmConfigBasis) {
		return bpmConfigBasisDao.queryForOne(bpmConfigBasis);
	}

	public PageView query(PageView pageView, BpmConfigBasis bpmConfigBasis) {
		List<BpmConfigBasis> list = bpmConfigBasisDao.query(pageView,
				bpmConfigBasis);
		pageView.setResults(list);
		return pageView;
	}

	public void add(BpmConfigBasis bpmConfigBasis) {
		bpmConfigBasisDao.add(bpmConfigBasis);
	}

	public void delete(String id) {
		bpmConfigBasisDao.delete(id);
	}

	public void modify(BpmConfigBasis bpmConfigBasis) {
		bpmConfigBasisDao.modify(bpmConfigBasis);
	}

	public BpmConfigBasis getById(String id) {
		return bpmConfigBasisDao.getById(id);
	}

	@Override
	public BpmConfigBasis getByBpmKey(String bpmKey) {
		BpmConfigBasis filter = new BpmConfigBasis();
		filter.setBpmKey(bpmKey);
		return bpmConfigBasisDao.queryForOne(filter);
	}

	@Override
	public List<BpmConfigBasis> getByRoleId(String roleId) {
		return bpmConfigBasisDao.getByRoleId(roleId);
	}

	@Override
	public List<BpmConfigBasis> getByUserId(String userId) {
		return bpmConfigBasisDao.getByUserId(userId);
	}
}
