package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightAuditRecordDao;
import com.van.halley.db.persistence.entity.FreightAuditRecord;
import com.van.service.FreightAuditRecordService;

@Transactional
@Service("freightAuditRecordService")
public class FreightAuditRecordServiceImpl implements FreightAuditRecordService {
	@Autowired
	private FreightAuditRecordDao freightAuditRecordDao;

	public List<FreightAuditRecord> getAll() {
		return freightAuditRecordDao.getAll();
	}

	public List<FreightAuditRecord> queryForList(
			FreightAuditRecord freightAuditRecord) {
		return freightAuditRecordDao.queryForList(freightAuditRecord);
	}

	public FreightAuditRecord queryForOne(FreightAuditRecord freightAuditRecord) {
		return freightAuditRecordDao.queryForOne(freightAuditRecord);
	}

	public PageView query(PageView pageView,
			FreightAuditRecord freightAuditRecord) {
		List<FreightAuditRecord> list = freightAuditRecordDao.query(pageView,
				freightAuditRecord);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightAuditRecord freightAuditRecord) {
		freightAuditRecordDao.add(freightAuditRecord);
	}

	public void delete(String id) {
		freightAuditRecordDao.delete(id);
	}

	public void modify(FreightAuditRecord freightAuditRecord) {
		freightAuditRecordDao.modify(freightAuditRecord);
	}

	public FreightAuditRecord getById(String id) {
		return freightAuditRecordDao.getById(id);
	}

	@Override
	public FreightAuditRecord getFreightAuditRecordProximate(String targetId) {
		return freightAuditRecordDao.getFreightAuditRecordProximate(targetId);
	}
}
