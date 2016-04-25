package com.van.halley.db.persistence.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.QuartzTaskDao;
import com.van.halley.db.persistence.entity.QuartzTask;

@Repository("quartzTaskDao")
public class QuartzTaskDaoImpl extends BaseDaoImpl<QuartzTask>implements QuartzTaskDao {
	
	@Override
	public QuartzTask getByTaskKey(String taskKey) {
		return getSqlSession().selectOne("quartztask.getByTaskKey", taskKey);
	}

	@Override
	public List<QuartzTask> getByPositionId(String positionId) {
		return getSqlSession().selectList("quartztask.getByPositionId", positionId);
	}
	
	@Override
	public List<QuartzTask> getByRoleId(String roleId) {
		return getSqlSession().selectList("quartztask.getByRoleId", roleId);
	}

	@Override
	public void deleteRoleQuartz(String roleId) {
		getSqlSession().delete("quartztask.deleteRoleQuartz", roleId);
	}

	@Override
	public void addRoleQuartz(String roleId, String quartzTaskId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("quartzTaskId", quartzTaskId);
		getSqlSession().delete("quartztask.addRoleQuartz", roleId);
	}

	
}
