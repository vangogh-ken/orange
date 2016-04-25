package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightAction;

public interface FreightActionDao extends BaseDao<FreightAction> {
	/**
	 * 通过订单获取所有的Action
	 * @param freightOrderId
	 * @return
	 */
	public List<FreightAction> getByFreightOrderId(String freightOrderId);
	
	/**
	 * 通过订单获取已填报过内容的action
	 * @param freightOrderId
	 * @return
	 */
	public List<FreightAction> getActionHasPrimeByFreightOrderId(String freightOrderId);

	/**
	 * 删除操作下面的所有动作
	 * @param freightMaintainId
	 
	public void deleteByFreightMaintainId(String freightMaintainId);
	*/
}