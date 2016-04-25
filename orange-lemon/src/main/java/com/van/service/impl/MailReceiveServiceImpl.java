package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.MailReceiveDao;
import com.van.halley.db.persistence.entity.MailReceive;
import com.van.service.MailReceiveService;

@Transactional
@Service("mailReceiveService")
public class MailReceiveServiceImpl implements MailReceiveService {
	@Autowired
	private MailReceiveDao mailReceiveDao;

	public List<MailReceive> getAll() {
		return mailReceiveDao.getAll();
	}

	public List<MailReceive> queryForList(MailReceive mailReceive) {
		return mailReceiveDao.queryForList(mailReceive);
	}

	public PageView query(PageView pageView, MailReceive mailReceive) {
		List<MailReceive> list = mailReceiveDao.query(pageView, mailReceive);
		pageView.setResults(list);
		return pageView;
	}

	public void add(MailReceive mailReceive) {
		mailReceiveDao.add(mailReceive);
	}

	public void delete(String id) {
		mailReceiveDao.delete(id);
	}

	public void modify(MailReceive mailReceive) {
		mailReceiveDao.modify(mailReceive);
	}

	public MailReceive getById(String id) {
		return mailReceiveDao.getById(id);
	}
}
