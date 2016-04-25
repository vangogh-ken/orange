package com.van.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightActionType;

public interface FreightActionTypeService {
	/**
	 * 操作类型ID / 动作类型
	 */
	public static Map<String, List<FreightActionType>> cacheActionType = new HashMap<String, List<FreightActionType>>();
	
	public List<FreightActionType> getAll();

	public List<FreightActionType> queryForList(
			FreightActionType freightActionType);

	public PageView query(PageView pageView, FreightActionType freightActionType);

	public void add(FreightActionType freightActionType);

	public void delete(String id);

	public void modify(FreightActionType freightActionType);

	public FreightActionType getById(String id);
	
	public void deleteByMaintainTypeId(String freightMaintainTypeId);
	
	//刷新缓存
	public void refreshCache();
	
}
