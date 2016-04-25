package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.UserRoleDao;
import com.van.halley.db.persistence.entity.UserRole;
import com.van.service.UserRoleService;

@Transactional
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	private UserRoleDao userRoleDao;

	public List<UserRole> getAll() {
		return userRoleDao.getAll();
	}

	public List<UserRole> queryForList(UserRole userRole) {
		return userRoleDao.queryForList(userRole);
	}

	public UserRole queryForOne(UserRole userRole) {
		return userRoleDao.queryForOne(userRole);
	}

	public PageView query(PageView pageView, UserRole userRole) {
		List<UserRole> list = userRoleDao.query(pageView, userRole);
		pageView.setResults(list);
		return pageView;
	}

	public void add(UserRole userRole) {
		userRoleDao.add(userRole);
	}

	public void delete(String id) {
		userRoleDao.delete(id);
	}

	public void modify(UserRole userRole) {
		userRoleDao.modify(userRole);
	}

	public UserRole getById(String id) {
		return userRoleDao.getById(id);
	}

	@Override
	public void allocation(String userId, String[] roleIds) {
		userRoleDao.deleteUserRoleByUserId(userId);
		for(String roleId : roleIds){
			userRoleDao.add(new UserRole(userId, roleId));
		}
	}
}
