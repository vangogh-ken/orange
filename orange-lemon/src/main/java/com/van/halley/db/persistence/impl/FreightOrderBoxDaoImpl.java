package com.van.halley.db.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightOrderBoxDao;
import com.van.halley.db.persistence.entity.FreightActionBox;
import com.van.halley.db.persistence.entity.FreightExpenseBox;
import com.van.halley.db.persistence.entity.FreightOrderBox;

@Repository("freightOrderBoxDao")
public class FreightOrderBoxDaoImpl extends BaseDaoImpl<FreightOrderBox>
		implements FreightOrderBoxDao {

	@Override
	public List<FreightOrderBox> getByFreightOrderId(String freightOrderId) {
		return getSqlSession().selectList("freightorderbox.getByFreightOrderId", freightOrderId);
	}

	@Override
	public List<FreightOrderBox> getByFreightBoxRequireId(
			String freightBoxRequireId) {
		return getSqlSession().selectList("freightorderbox.getByFreightBoxRequireId", freightBoxRequireId);
	}

	@Override
	public List<FreightOrderBox> getByFreightExpenseId(String freightExpenseId) {
		return getSqlSession().selectList("freightorderbox.getByFreightExpenseId", freightExpenseId);
	}

	@Override
	public void deleteExpenseBox(String freightExpenseId) {
		getSqlSession().delete("freightorderbox.deleteExpenseBox", freightExpenseId);
	}

	@Override
	public void addExpenseBox(String freightExpenseId, String freightOrderBoxId) {
		getSqlSession().insert("freightorderbox.addExpenseBox", new FreightExpenseBox(freightExpenseId, freightOrderBoxId));
	}
	
	@Override
	public List<FreightOrderBox> getByFreightActionId(String freightActionId) {
		return getSqlSession().selectList("freightorderbox.getByFreightActionId", freightActionId);
	}

	@Override
	public void deleteActionBox(String freightActionId) {
		getSqlSession().delete("freightorderbox.deleteActionBox", freightActionId);
	}

	@Override
	public void addActionBox(String freightActionId, String freightOrderBoxId) {
		getSqlSession().insert("freightorderbox.addActionBox", new FreightActionBox(freightActionId, freightOrderBoxId));
	}
}
