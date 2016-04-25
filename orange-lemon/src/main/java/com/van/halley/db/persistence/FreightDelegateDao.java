package com.van.halley.db.persistence;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightDelegate;

public interface FreightDelegateDao extends BaseDao<FreightDelegate> {
	/**
	 * 重复提交导致重复生成委托，需要此处处理
	 * 获取委托对应的动作
	 * @param freightActionId
	 * @return
	 */
	public FreightDelegate getByFreightActionId(String freightActionId);
}