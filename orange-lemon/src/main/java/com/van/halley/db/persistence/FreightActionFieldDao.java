package com.van.halley.db.persistence;

import java.util.List;
import java.util.Map;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightActionField;

public interface FreightActionFieldDao extends BaseDao<FreightActionField> {
	/**
	 * 获取动作所有的字段
	 * @param freightActionTypeId
	 * @return
	 */
	public List<FreightActionField> getByFreightActionTypeId(String freightActionTypeId);
	/**
	 * 获取动作一般字段
	 * @param freightActionTypeId
	 * @return
	 */
	public List<FreightActionField> getNormalByFreightActionTypeId(String freightActionTypeId);
	/**
	 * 获取动作含箱字段
	 * @param freightActionTypeId
	 * @return
	 */
	public List<FreightActionField> getForBoxByFreightActionTypeId(String freightActionTypeId);
	/**
	 * 获取动作值，字段组合
	 * @param freightActionId
	 * @return
	 */
	public List<Map<String, Object>> getFieldAndValueByActionId(String freightActionId);
}