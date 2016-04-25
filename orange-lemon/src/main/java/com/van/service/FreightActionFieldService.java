package com.van.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightActionField;

public interface FreightActionFieldService {
	/**
	 * 缓存cacheActionField， key 为typeId, List为type所有的字段数据
	 */
	public static Map<String, List<FreightActionField>> cacheActionField = new HashMap<String, List<FreightActionField>>();
	
	public List<FreightActionField> getAll();

	public List<FreightActionField> queryForList(
			FreightActionField freightActionField);

	public PageView query(PageView pageView,
			FreightActionField freightActionField);

	public void add(FreightActionField freightActionField);

	public void delete(String id);

	public void modify(FreightActionField freightActionField);

	public FreightActionField getById(String id);
	
	public List<FreightActionField> getByActionTypeId(String actionTypeId);

	public List<Map<String, Object>> getFieldAndValueByActionId(String id);
	
	public void refreshCache();
	
}
