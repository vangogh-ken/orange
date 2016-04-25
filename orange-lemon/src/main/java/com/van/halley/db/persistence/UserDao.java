package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.Role;
import com.van.halley.db.persistence.entity.User;

public interface UserDao extends BaseDao<User> {
	public int countUser(String userName, String userPassword);

	public int countUserByUserName(String userName);

	public int countUserByDisplayName(String displayName);

	public User getByUserName(String userName);

	public Role findbyUserRole(String userId);
	
	public List<User> getUnderling(String userId);
	
	public List<User> getByOrgEntityId(String orgEntityId);
	
	public User getByDisplayName(String displayName);
	/**
	 * 动作委派
	 */
	public List<User> getActionAssignByFreightActionTypeId(String freightActionTypeId);
	public void addActionAssign(String freightActionTypeId, String userId);
	public void deleteActionAssign(String freightActionTypeId);
	
	/**
	 * 组织机构负责人
	 */
	public List<User> getGafferByOrgEntityId(String orgEntityId);
	public void addGaffer(String orgEntityId, String userId);
	public void deleteGaffer(String orgEntityId);
	
	/**
	 * 获取组织机构下职位等级最高的人
	 * @param orgEntityId
	 * @return
	 */
	public List<User> getTopByOrgEntityId(String orgEntityId);
	/**
	 * 获取组织机构内某职级的人
	 * @param orgEntityId
	 * @param grade
	 * @return
	 */
	public List<User> getNextByOrgEntityId(String orgEntityId, int grade);
	/**
	 * 获取分配好的用户
	 * @param bpmConfigNodeId
	 * @return
	 */
	public List<User> getByBpmConfigNodeId(String bpmConfigNodeId);
	/**
	 * 获取所有拥有该职位的用户
	 * @param positionId
	 * @return
	 */
	public List<User> getByPositionId(String positionId);
	/**
	 * 根据角色获取用户，暂不使用
	 * @param roleId
	 * @return
	 */
	public List<User> getByRoleId(String roleId);
	/**
	 * 获取用户直接上级
	 * @param userId
	 * @return
	 */
	public List<User> getDirectSuperior(String userId);
	/**
	 * 获取用户直接领导
	 * @param userId
	 * @return
	 */
	public List<User> getDirectShepherd(String userId);
	
}
