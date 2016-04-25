package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.BpmConfigBasis;

public interface BpmConfigBasisDao extends BaseDao<BpmConfigBasis> {
	/**
	 * 获取角色关联的配置
	 * @param roleId
	 * @return
	 */
	public List<BpmConfigBasis> getByRoleId(String roleId);
	/**
	 * 获取用户拥有的流程权限
	 * @param userId
	 * @return
	 */
	public List<BpmConfigBasis> getByUserId(String userId);
	/**
	 * 增加角色关联
	 * @param roleBpm
	 */
	public void addRoleBpm(String roleId, String bpmId);
	/**
	 * 删除角色关联
	 * @param roleId
	 */
	public void deleteRoleBpm(String roleId);
	/**
	 * 根据流程KEY获取相关配置
	 * @param bpmKey
	 * @return
	 */
	public BpmConfigBasis getByBpmKey(String bpmKey);
}