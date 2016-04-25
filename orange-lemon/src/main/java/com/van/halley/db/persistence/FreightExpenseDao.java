package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightExpense;

public interface FreightExpenseDao extends BaseDao<FreightExpense> {
	/**
	 * 获取与业务关联的费用（仅业务添加）
	 * @param freightOrderId
	 * @return
	 */
	public List<FreightExpense> getByFreightOrderId(String freightOrderId);
	/**
	 * 账单关联费用
	 * @param freightStatementId
	 * @return
	 */
	public List<FreightExpense> getByFreightStatementId(String freightStatementId);

	/**
	 * 获取内部费用 订单，费用类型，收付对象不可缺
	 * @param freightExpense
	 * @return
	 */
	public List<FreightExpense> getInternalExpense(FreightExpense freightExpense);

	/**
	 * 获取内部费用 订单，费用类型，收付对象不可缺
	 * @param freightExpense
	 * @return
	 */
	public List<FreightExpense> getNormalExpense(FreightExpense freightExpense);
	/**
	 * 获取与动作相关的普通费用
	 * @param freightActionId
	 * @return
	 */
	public List<FreightExpense> getNormalByFreightActionId(String freightActionId);
	
	/**
	 * 获取具体动作的内部费用 
	 * @param freightActionId
	 * @return
	 */
	public List<FreightExpense> getInternalByFreightActionId(String freightActionId);
	/**
	 * 删除具体动作的内部费用 
	 * @param freightActionId
	 */
	public void deleteInternalByFreightActionId(String freightActionId);
	
}