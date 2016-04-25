package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BpmConfigDelegateDao;
import com.van.halley.db.persistence.entity.BpmConfigDelegate;
import com.van.service.BpmConfigDelegateService;

@Transactional
@Service("bpmConfigDelegateService")
public class BpmConfigDelegateServiceImpl implements BpmConfigDelegateService {
	@Autowired
	private BpmConfigDelegateDao bpmConfigDelegateDao;

	public List<BpmConfigDelegate> getAll() {
		return bpmConfigDelegateDao.getAll();
	}

	public List<BpmConfigDelegate> queryForList(
			BpmConfigDelegate bpmConfigDelegate) {
		return bpmConfigDelegateDao.queryForList(bpmConfigDelegate);
	}

	public PageView query(PageView pageView, BpmConfigDelegate bpmConfigDelegate) {
		List<BpmConfigDelegate> list = bpmConfigDelegateDao.query(pageView,
				bpmConfigDelegate);
		pageView.setResults(list);
		return pageView;
	}

	public void add(BpmConfigDelegate bpmConfigDelegate) {
		bpmConfigDelegateDao.add(bpmConfigDelegate);
	}

	public void delete(String id) {
		bpmConfigDelegateDao.delete(id);
	}

	public void modify(BpmConfigDelegate bpmConfigDelegate) {
		bpmConfigDelegateDao.modify(bpmConfigDelegate);
	}

	public BpmConfigDelegate getById(String id) {
		return bpmConfigDelegateDao.getById(id);
	}

	@Override
	public BpmConfigDelegate queryForOne(BpmConfigDelegate filter) {
		return bpmConfigDelegateDao.queryForOne(filter);
	}
}
