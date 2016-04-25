package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.UserRole;

public interface UserRoleService {
	public List<UserRole> getAll();

	public List<UserRole> queryForList(UserRole userRole);

	public UserRole queryForOne(UserRole userRole);

	public PageView query(PageView pageView, UserRole userRole);

	public void add(UserRole userRole);

	public void delete(String id);

	public void modify(UserRole userRole);

	public UserRole getById(String id);

	/**
	 * 给用户分配角色,分配之前将删除之前分配的
	 * @param userId
	 * @param roleIds
	 */
	public void allocation(String userId, String[] roleIds);
}
