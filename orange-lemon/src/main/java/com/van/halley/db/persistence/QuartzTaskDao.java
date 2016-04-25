package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.QuartzTask;

public interface QuartzTaskDao extends BaseDao<QuartzTask> {
	public QuartzTask getByTaskKey(String taskKey);
	
	public List<QuartzTask> getByPositionId(String positionId);

	public List<QuartzTask> getByRoleId(String roleId);

	public void deleteRoleQuartz(String roleId);

	public void addRoleQuartz(String roleId, String quartzTaskId);
}