package com.van.halley.db.persistence.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightDataTemplateDao;
import com.van.halley.db.persistence.entity.FreightDataTemplate;
@Repository("freightDataTemplateDao")
public class FreightDataTemplateDaoImpl extends BaseDaoImpl<FreightDataTemplate> implements FreightDataTemplateDao {

	@Override
	public List<FreightDataTemplate> getByFreightActionId(String freightActionId, String userId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("freightActionId", freightActionId);
		map.put("userId", userId);
		return getSqlSession().selectList("freightdatatemplate.getByFreightActionId", map);
	}}
