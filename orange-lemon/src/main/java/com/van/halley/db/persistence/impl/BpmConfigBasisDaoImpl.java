package com.van.halley.db.persistence.impl;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BpmConfigBasisDao;
import com.van.halley.db.persistence.entity.BpmConfigBasis;
import com.van.halley.db.persistence.entity.RoleBpm;
@Repository("bpmConfigBasisDao")
public class BpmConfigBasisDaoImpl extends BaseDaoImpl<BpmConfigBasis> implements BpmConfigBasisDao {

	@Override
	public List<BpmConfigBasis> getByRoleId(String roleId) {
		return getSqlSession().selectList("bpmconfigbasis.getByRoleId", roleId);
	}

	@Override
	public List<BpmConfigBasis> getByUserId(String userId) {
		return getSqlSession().selectList("bpmconfigbasis.getByUserId", userId);
	}

	@Override
	public void addRoleBpm(String roleId, String bpmId) {
		RoleBpm roleBpm = new RoleBpm(roleId, bpmId);
		getSqlSession().insert("bpmconfigbasis.addRoleBpm", roleBpm);
	}

	@Override
	public void deleteRoleBpm(String roleId) {
		getSqlSession().delete("bpmconfigbasis.deleteRoleBpm", roleId);
	}

	@Override
	public BpmConfigBasis getByBpmKey(String bpmKey) {
		return getSqlSession().selectOne("bpmconfigbasis.getByBpmKey", bpmKey);
	}}
