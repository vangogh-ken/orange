package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightAuditRecordDao;
import com.van.halley.db.persistence.entity.FreightAuditRecord;
@Repository("freightAuditRecordDao")
public class FreightAuditRecordDaoImpl extends BaseDaoImpl<FreightAuditRecord> implements FreightAuditRecordDao {

	@Override
	public FreightAuditRecord getFreightAuditRecordProximate(String targetId) {
		return getSqlSession().selectOne("freightauditrecord.getFreightAuditRecordProximate", targetId);
	}}
