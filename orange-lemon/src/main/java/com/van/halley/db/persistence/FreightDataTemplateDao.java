package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightDataTemplate;

public interface FreightDataTemplateDao extends BaseDao<FreightDataTemplate> {
	/**
	 * 根据动作ID获取相关数据模板
	 * @param freightActionId
	 * @param userId
	 * @return
	 */
	public List<FreightDataTemplate> getByFreightActionId(String freightActionId, String userId);
}