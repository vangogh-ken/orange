package com.van.halley.db.persistence.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightActionFieldDao;
import com.van.halley.db.persistence.entity.FreightActionField;

@Repository("freightActionFieldDao")
public class FreightActionFieldDaoImpl extends BaseDaoImpl<FreightActionField>
		implements FreightActionFieldDao {

	@Override
	public List<FreightActionField> getByFreightActionTypeId(String freightActionTypeId) {
		return getSqlSession().selectList("freightactionfield.getByFreightActionTypeId", freightActionTypeId);
	}

	@Override
	public List<Map<String, Object>> getFieldAndValueByActionId(String freightActionId) {
		return getSqlSession().selectList("freightactionfield.getFieldAndValueByActionId", freightActionId);
	}

	@Override
	public List<FreightActionField> getNormalByFreightActionTypeId(
			String freightActionTypeId) {
		return getSqlSession().selectList("freightactionfield.getNormalByFreightActionTypeId", freightActionTypeId);
	}

	@Override
	public List<FreightActionField> getForBoxByFreightActionTypeId(
			String freightActionTypeId) {
		return getSqlSession().selectList("freightactionfield.getForBoxByFreightActionTypeId", freightActionTypeId);
	}
}
