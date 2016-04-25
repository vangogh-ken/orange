package com.van.halley.db.persistence.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.van.halley.auth.support.CustomManagementLine;
import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.FreightActionTypeIdentity;
import com.van.halley.db.persistence.entity.OrgEntityIdentity;
import com.van.halley.db.persistence.entity.Role;
import com.van.halley.db.persistence.entity.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	public int countUser(String userName, String userPassword) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("userName", userName);
		map.put("userPassword", userPassword);
		return (Integer) getSqlSession().selectOne("user.countUser", map);
	}

	@Override
	public User getByUserName(String userName) {
		return (User) getSqlSession().selectOne("user.getByUserName", userName);
	}

	public Role findbyUserRole(String userId) {
		return (Role) getSqlSession().selectOne("roles.findbyUserRole", userId);
	}

	@Override
	public User getByDisplayName(String displayName) {
		return getSqlSession().selectOne("user.getByDisplayName", displayName);
	}

	@Override
	public List<User> getUnderling(String userId) {
		return getSqlSession().selectList("user.getUnderling", userId);
	}

	@Override
	public int countUserByUserName(String userName) {
		return (Integer) getSqlSession().selectOne("user.countByUserName",
				userName);
	}

	@Override
	public int countUserByDisplayName(String displayName) {
		return (Integer) getSqlSession().selectOne("user.countByDisplayName",
				displayName);
	}

	@Override
	public List<User> getByOrgEntityId(String orgEntityId) {
		return getSqlSession().selectList("user.getByOrgEntityId", orgEntityId);
	}

	@Override
	public List<User> getActionAssignByFreightActionTypeId(
			String freightActionTypeId) {
		return getSqlSession().selectList("user.getActionAssignByFreightActionTypeId", freightActionTypeId);
	}

	@Override
	public void addActionAssign(String freightActionTypeId, String userId) {
		FreightActionTypeIdentity freightActionTypeIdentity = new FreightActionTypeIdentity(freightActionTypeId, userId);
		getSqlSession().insert("user.addActionAssign", freightActionTypeIdentity);
	}

	@Override
	public void deleteActionAssign(String freightActionTypeId) {
		getSqlSession().delete("user.deleteActionAssign", freightActionTypeId);
	}

	@Override
	public List<User> getGafferByOrgEntityId(String orgEntityId) {
		return getSqlSession().selectList("user.getGafferByOrgEntityId", orgEntityId);
	}

	@Override
	public void addGaffer(String orgEntityId, String userId) {
		OrgEntityIdentity orgEntityIdentity = new OrgEntityIdentity(orgEntityId, userId);
		getSqlSession().insert("user.addGaffer", orgEntityIdentity);
	}

	@Override
	public void deleteGaffer(String orgEntityId) {
		getSqlSession().delete("user.deleteGaffer", orgEntityId);
	}

	@Override
	public List<User> getTopByOrgEntityId(String orgEntityId) {
		return getSqlSession().selectList("user.getTopByOrgEntityId", orgEntityId);
	}

	@Override
	public List<User> getByBpmConfigNodeId(String bpmConfigNodeId) {
		return getSqlSession().selectList("user.getByBpmConfigNodeId", bpmConfigNodeId);
	}

	
	@Override
	public List<User> getByPositionId(String positionId) {
		return getSqlSession().selectList("user.getByPositionId", positionId);
	}

	@Override
	public List<User> getByRoleId(String roleId) {
		return getSqlSession().selectList("user.getByRoleId", roleId);
	}


	/* 
	 * 根据刘总要求，对于财务部单独处理；普通员工的直接上级是副经理，直接领导是经理
	 */
	@Override
	public List<User> getDirectSuperior(String userId) {
		User current = getById(userId);
		
		/***********财务部特殊处理****************/
		/*if(current.getPosition() != null && current.getPosition().getGrade() == 1 
				&& "a23c4850-18c1-11e4-bce4-b870f47f73d5".equals(current.getOrgEntity().getId())){//财务部
			List<User> users = new ArrayList<User>();
			users.add(getById("de1608ab-86bd-11e4-91d6-b870f47f73d5"));//杜容
			return users;
		}*/
		/***************************/
		
		/******自定义上下级关系********/
		List<User> usersDirectGet = CustomManagementLine.getDirectSuperior(current);
		if(usersDirectGet != null){
			return usersDirectGet;
		}
		/**********************/
		
		if(current.getPosition() != null){
			List<User> users = getTopByOrgEntityId(current.getOrgEntity().getId());
			boolean containsSelf = false;
			if(users != null && !users.isEmpty()){
				for(User user : users){
					if(userId.equals(user.getId())){
						containsSelf = true;
						break;
					}
				}
			}else{//最高职级没有配备人员
				logger.error("该用户所在组织机构的最高职级无分配用户，取其次职级用户，用户：{}", current.getDisplayName());
				for(int i=6; i>0; i--){
					if(containsSelf){
						break;
					}
					List<User> gradeUsers = getNextByOrgEntityId(current.getOrgEntity().getId(), i);
					if(gradeUsers != null && !gradeUsers.isEmpty()){
						for(User user : gradeUsers){
							if(userId.equals(user.getId())){
								containsSelf = true;
								break;
							}
						}
						if(!containsSelf){
							return gradeUsers;
						}
					}else{
						continue;
					}
				}
			}
			
			if(!containsSelf){
				return users;
			}else{
				logger.error("该用户已是该所在组织机构的最高职级，则取其分管领导，用户：{}", current.getDisplayName());
				return getGafferByOrgEntityId(current.getOrgEntity().getId());
			}
		}else{
			logger.error("该用户尚未分配任何职位，找不到其上级，用户：{}", current.getDisplayName());
			return null;
		}
	}

	@Override
	public List<User> getDirectShepherd(String userId) {
		User current = getById(userId);
		/***********财务部特殊处理****************/
		/*if(current.getPosition() != null && current.getPosition().getGrade() == 1 
				&& "a23c4850-18c1-11e4-bce4-b870f47f73d5".equals(current.getOrgEntity().getId())){//财务部
			List<User> users = new ArrayList<User>();
			users.add(getById("167e5e56-a81e-11e3-8c18-a4db305e5cc5"));//熊森
			return users;
		}*/
		/***************************/
		
		/******自定义上下级关系********/
		List<User> usersDirectGet = CustomManagementLine.getDirectShepherd(current);
		if(usersDirectGet != null){
			return usersDirectGet;
		}
		/**********************/
		if(current.getPosition() != null && current.getPosition().getGrade() == 1 ){//其他各种部门的普通员工，上级有是副总级的，则上级领导也为副总级
			List<User> directSuperiors = getDirectSuperior(userId);
			boolean isFiveGrade = false;//上级是否为副总级
			for(User user : directSuperiors){
				if(user.getPosition().getGrade() == 5){
					isFiveGrade = true;
				}
			}
			if(isFiveGrade){
				return directSuperiors;
			}
		}
		
		List<User> directSuperiors = getDirectSuperior(userId);
		List<User> directShepherds = new ArrayList<User>();
		if(directSuperiors != null && !directSuperiors.isEmpty()){
			for(User user : directSuperiors){
				List<User> superiors = getDirectSuperior(user.getId());
				if(superiors != null && !superiors.isEmpty()){
					for(User superior :superiors){
						boolean isContains = false;
						for(User directShepherd : directShepherds){
							if(directShepherd.getId().equals(superior.getId())){
								isContains = true;
								break;
							}
						}
						
						if(!isContains){
							directShepherds.add(superior);
						}
					}
				}
			}
			
			return directShepherds;
		}else{
			logger.error("找不到该用户的领导，用户：{}", current.getDisplayName());
			return null;
		}
	}

	@Override
	public List<User> getNextByOrgEntityId(String orgEntityId, int grade) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgEntityId", orgEntityId);
		map.put("grade", grade);
		return getSqlSession().selectList("user.getNextByOrgEntityId", map);
	}
}
