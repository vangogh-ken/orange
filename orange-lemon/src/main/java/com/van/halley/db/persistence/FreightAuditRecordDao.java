package com.van.halley.db.persistence;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightAuditRecord;

public interface FreightAuditRecordDao extends BaseDao<FreightAuditRecord> {
	/**
	 * 获取最近的一条审核记录
	 * @param targetId
	 * @return
	 */
	public FreightAuditRecord getFreightAuditRecordProximate(String targetId);
}