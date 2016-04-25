package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.Role;
import com.van.halley.db.persistence.entity.User;

public interface UserService {
	public PageView<User> query(PageView<User> pageView, User user);

	public void add(User user);

	public void delete(String id);

	public void modify(User user);

	public User getById(String id);

	public int countUser(String userName, String userPassword);

	public User getByUserName(String userName);

	public Role findbyUserRole(String userId);

	public List<User> getAll();
	
	public List<User> queryForList(User user);

	public User getByDisplayName(String dispalyName);

	public boolean isExistsUserName(String userName);

	public boolean isExistsDisplayName(String displayNmae);
	
	/**
	 * 获取直接上级，如果所在组织机构下最高职位等级的人员包括自己，则取所在组织机构的负责人
	 * @param userId
	 * @return
	 */
	public List<User> getDirectSuperior(String userId);
	/**
	 * 获取直接领导，取直接上级的直接上级
	 * @param userId
	 * @return
	 */
	public List<User> getDirectShepherd(String userId);
	/**
	 * 获取同一部门的其他人
	 * @param userId
	 * @return
	 */
	public List<User> getCoworker(String userId);
	/**
	 * 获取一级下属
	 * @param userId
	 * @return
	 */
	public List<User> getDirectUnderling(String userId);
	/**
	 * 获取二级下属
	 * @param userId
	 * @return
	 */
	public List<User> getDirectSubordinate(String userId);

	/**
	 * 微信邀请
	 * @param userId
	 * @return
	 */
	public boolean userInviteWeixin(String userId);
}
