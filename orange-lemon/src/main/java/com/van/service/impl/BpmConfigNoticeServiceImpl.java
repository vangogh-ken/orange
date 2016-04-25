package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BpmConfigNoticeDao;
import com.van.halley.db.persistence.entity.BpmConfigNotice;
import com.van.service.BpmConfigNoticeService;

@Transactional
@Service("bpmConfigNoticeService")
public class BpmConfigNoticeServiceImpl implements BpmConfigNoticeService {
	@Autowired
	private BpmConfigNoticeDao bpmConfigNoticeDao;

	public List<BpmConfigNotice> getAll() {
		return bpmConfigNoticeDao.getAll();
	}

	public List<BpmConfigNotice> queryForList(
			BpmConfigNotice bpmConfigNotice) {
		return bpmConfigNoticeDao.queryForList(bpmConfigNotice);
	}

	public PageView query(PageView pageView, BpmConfigNotice bpmConfigNotice) {
		List<BpmConfigNotice> list = bpmConfigNoticeDao.query(pageView,
				bpmConfigNotice);
		pageView.setResults(list);
		return pageView;
	}

	public void add(BpmConfigNotice bpmConfigNotice) {
		bpmConfigNoticeDao.add(bpmConfigNotice);
	}

	public void delete(String id) {
		bpmConfigNoticeDao.delete(id);
	}

	public void modify(BpmConfigNotice bpmConfigNotice) {
		bpmConfigNoticeDao.modify(bpmConfigNotice);
	}

	public BpmConfigNotice getById(String id) {
		return bpmConfigNoticeDao.getById(id);
	}

	@Override
	public List<BpmConfigNotice> getByNodeId(String nodeId) {
		return bpmConfigNoticeDao.getByNodeId(nodeId);
	}
}
