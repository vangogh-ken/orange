package com.van.halley.db.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightExpenseDao;
import com.van.halley.db.persistence.entity.FreightExpense;

@Repository("freightExpenseDao")
public class FreightExpenseDaoImpl extends BaseDaoImpl<FreightExpense>
		implements FreightExpenseDao {
	@Override
	public List<FreightExpense> getInternalExpense(FreightExpense freightExpense) {
		if(freightExpense.getFreightOrder() == null 
				|| freightExpense.getFreightExpenseType() == null 
				|| freightExpense.getFreightPartB() == null){
			return null;
		}
		return getSqlSession().selectList("freightexpense.getInternalExpense", freightExpense);
	}

	@Override
	public List<FreightExpense> getNormalExpense(FreightExpense freightExpense) {
		if(freightExpense.getFreightOrder() == null 
				|| freightExpense.getFreightExpenseType() == null 
				|| freightExpense.getFreightPartB() == null){
			return null;
		}
		return getSqlSession().selectList("freightexpense.getNormalExpense", freightExpense);
	}
	
	@Override
	public List<FreightExpense> getNormalByFreightActionId(String freightActionId) {
		return getSqlSession().selectList("freightexpense.getNormalByFreightActionId", freightActionId);
	}

	@Override
	public List<FreightExpense> getByFreightOrderId(String freightOrderId) {
		return getSqlSession().selectList("freightexpense.getByFreightOrderId", freightOrderId);
	}

	@Override
	public List<FreightExpense> getByFreightStatementId(String freightStatementId) {
		return getSqlSession().selectList("freightexpense.getByFreightStatementId", freightStatementId);
	}

	@Override
	public List<FreightExpense> getInternalByFreightActionId(
			String freightActionId) {
		return getSqlSession().selectList("freightexpense.getInternalByFreightActionId", freightActionId);
	}

	@Override
	public void deleteInternalByFreightActionId(String freightActionId) {
		getSqlSession().delete("freightexpense.deleteInternalByFreightActionId", freightActionId);
	}

	
}
