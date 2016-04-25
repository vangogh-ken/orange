package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.WorkCalInfo;

public interface WorkCalInfoDao extends BaseDao<WorkCalInfo> {
	public List<WorkCalInfo> getByOrgId(String orgId, String year, String month, String type);
	
	public List<WorkCalInfo> getByUserId(String userId, String year, String month, String type);
}