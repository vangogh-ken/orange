package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightAuditRecord;

public interface FreightAuditRecordService {
	public List<FreightAuditRecord> getAll();

	public List<FreightAuditRecord> queryForList(
			FreightAuditRecord freightAuditRecord);

	public FreightAuditRecord queryForOne(FreightAuditRecord freightAuditRecord);

	public PageView query(PageView pageView,
			FreightAuditRecord freightAuditRecord);

	public void add(FreightAuditRecord freightAuditRecord);

	public void delete(String id);

	public void modify(FreightAuditRecord freightAuditRecord);

	public FreightAuditRecord getById(String id);
	/**
	 * 获取最近一条记录
	 * @param targetId
	 * @return
	 */
	public FreightAuditRecord getFreightAuditRecordProximate(String targetId);
}
