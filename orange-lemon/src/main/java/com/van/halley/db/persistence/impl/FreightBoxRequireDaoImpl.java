package com.van.halley.db.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightBoxRequireDao;
import com.van.halley.db.persistence.entity.FreightBoxRequire;

@Repository("freightBoxRequireDao")
public class FreightBoxRequireDaoImpl extends BaseDaoImpl<FreightBoxRequire>
		implements FreightBoxRequireDao {

	@Override
	public List<FreightBoxRequire> getByFreightOrderId(String freightOrderId) {
		return getSqlSession().selectList("freightboxrequire.getByFreightOrderId", freightOrderId);
	}
}
