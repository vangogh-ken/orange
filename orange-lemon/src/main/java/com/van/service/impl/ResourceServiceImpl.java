package com.van.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.ResourceDao;
import com.van.halley.db.persistence.RoleDao;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.Resource;
import com.van.security.MySecurityMetadataSource;
import com.van.service.ResourceService;

@Transactional
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
	private Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserDao userDao;
	
	//~~~在resource修改后，强制重装security 数据
	@Autowired
	private MySecurityMetadataSource mySecurityMetadataSource;

	public PageView<Resource> query(PageView<Resource> pageView, Resource resource) {
		List<Resource> list = resourceDao.query(pageView, resource);
		pageView.setResults(list);
		return pageView;
	}

	public void add(Resource resource) {
		resourceDao.add(resource);
		//refreshCache(); 新增的时候没有授权，因此不需要刷新缓存；
		mySecurityMetadataSource.forceLoadResourceDefine();

	}

	public void delete(String id) {
		resourceDao.delete(id);
		mySecurityMetadataSource.forceLoadResourceDefine();

	}

	public Resource getById(String id) {
		return resourceDao.getById(id);
	}

	public void modify(Resource resource) {
		resourceDao.modify(resource);
		mySecurityMetadataSource.forceLoadResourceDefine();
	}

	public List<Resource> getAll() {
		return resourceDao.getAll();
	}
	@Override
	public List<Resource> getResourceByUserId(String userId){
		/*User user = userDao.getById(userId);
		List<Resource> resources = new ArrayList<Resource>();
		List<Resource> resourceSet = new ArrayList<Resource>();
		List<Role> roles = user.getRoles();
		if(roles != null && !roles.isEmpty()){
			for(Role role : roles){
				resourceSet.addAll(ResourceService.cachedResource.get(role.getId()));
			}
			resources.addAll(resourceSet);
			return resources;
		}else{
			return resourceDao.getResourceByUserId(userId);//找不到角色，直接从数据库中查询；但是应当是找不到角色，数据库中也查询不到；
		}*/
		
		return resourceDao.getResourceByUserId(userId);
		//return ResourceService.cachedResource.get(userId);
	}

	@Override
	public List<Resource> getResourceByRoleId(String roleId){
		return resourceDao.getResourceByRoleId(roleId);
	}


	@Override
	public List<Resource> queryForList(Resource resource) {
		return resourceDao.queryForList(resource);
	}
	
	@Override
	public Resource queryForOne(Resource resource) {
		return resourceDao.queryForOne(resource);
	}

	@Override
	public List<Resource> getResourceByPositionId(String positionId) {
		return resourceDao.getResourceByPositionId(positionId);
	}

	

}
