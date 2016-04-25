package com.van.halley.db.persistence.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.WorkCalInfoDao;
import com.van.halley.db.persistence.entity.WorkCalInfo;

@Repository("workCalInfoDao")
public class WorkCalInfoDaoImpl extends BaseDaoImpl<WorkCalInfo> implements
		WorkCalInfoDao {

	@Override
	public List<WorkCalInfo> getByOrgId(String orgId, String year, String month, String type) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgId", orgId);
		map.put("year", year);
		map.put("month", month);
		map.put("type", type);
		return getSqlSession().selectList("workcalinfo.getByOrgId", map);
	}

	@Override
	public List<WorkCalInfo> getByUserId(String userId, String year,
			String month, String type) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgId", userId);
		map.put("year", year);
		map.put("month", month);
		map.put("type", type);
		return getSqlSession().selectList("workcalinfo.getByUserId", map);
	}
}
