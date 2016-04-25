package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightOrderBox;


public interface FreightOrderBoxDao extends BaseDao<FreightOrderBox> {
	/**
	 * 根据订单ID获取箱封
	 * @param freightOrderId
	 * @return
	 */
	List<FreightOrderBox> getByFreightOrderId(String freightOrderId);
	/**
	 * 根据箱需ID获取箱封
	 * @param freightBoxRequireId
	 * @return
	 */
	List<FreightOrderBox> getByFreightBoxRequireId(String freightBoxRequireId);
	/**
	 * 获取费用关联的箱封
	 * @param freightExpenseId
	 * @return
	 */
	public List<FreightOrderBox> getByFreightExpenseId(String freightExpenseId);
	/**
	 * 删除相关关联
	 * @param freightExpenseId
	 */
	public void deleteExpenseBox(String freightExpenseId);
	/**
	 * 添加关联
	 * @param freightExpenseId
	 * @param freightOrderBoxId
	 */
	public void addExpenseBox(String freightExpenseId, String freightOrderBoxId);
	
	/**
	 * 获取动作关联的箱封
	 */
	public List<FreightOrderBox> getByFreightActionId(String freightActionId);
	/**
	 * 删除相关关联
	 */
	public void deleteActionBox(String freightActionId);
	/**
	 * 添加关联
	 */
	public void addActionBox(String freightActionId, String freightOrderBoxId);
}