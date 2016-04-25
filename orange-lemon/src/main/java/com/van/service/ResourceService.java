package com.van.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.Resource;

public interface ResourceService {
	/**
	 * 缓存角色ID/用户ID 》 资源
	 */
	public static Map<String, List<Resource>> cachedResource = new HashMap<String, List<Resource>>();
	
	public PageView<Resource> query(PageView<Resource> pageView, Resource resource);
	
	public List<Resource> queryForList(Resource resource);
	
	public Resource queryForOne(Resource resource);

	public void add(Resource resource);

	public void delete(String id);

	public void modify(Resource resource);

	public Resource getById(String id);

	public List<Resource> getAll();
	/**
	 * 根据用户Id获取该用户的权限
	 * @param userId
	 * @return
	 */
	public List<Resource> getResourceByUserId(String userId);

	/**
	 * 根据用户Id获取该用户的权限
	 * @param roleId
	 * @return
	 */
	public List<Resource> getResourceByRoleId(String roleId);
	/**
	 * 获取职位拥有的资源
	 * @param positionId
	 * @return
	 */
	public List<Resource> getResourceByPositionId(String positionId);
	
}
