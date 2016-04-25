package com.van.halley.db.persistence;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightPart;

public interface FreightPartDao extends BaseDao<FreightPart> {
	/**
	 * 获取公司部门映射到单位数据
	 * @param orgEntityId
	 * @return
	 */
	public FreightPart getByOrgEntityId(String orgEntityId);
	/**
	 * 通过名称获取单位
	 * @param partName
	 * @return
	 */
	public FreightPart getByPartName(String partName);
}