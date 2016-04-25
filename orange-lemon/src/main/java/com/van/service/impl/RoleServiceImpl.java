package com.van.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BpmConfigBasisDao;
import com.van.halley.db.persistence.QuartzTaskDao;
import com.van.halley.db.persistence.ReportTemplateDao;
import com.van.halley.db.persistence.ResourceDao;
import com.van.halley.db.persistence.RoleDao;
import com.van.halley.db.persistence.entity.Role;
import com.van.halley.db.persistence.entity.UserRole;
import com.van.service.RoleService;

@Transactional
@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private ReportTemplateDao reportTemplateDao;
	@Autowired
	private BpmConfigBasisDao bpmConfigBasisDao;
	@Autowired
	private QuartzTaskDao quartzTaskDao;

	public PageView query(PageView pageView, Role role) {
		List<Role> list = roleDao.query(pageView, role);
		pageView.setResults(list);
		return pageView;
	}

	public void add(Role role) {
		roleDao.add(role);

	}

	public void delete(String id) {
		roleDao.delete(id);

	}

	public Role getById(String id) {
		return roleDao.getById(id);
	}

	public void modify(Role role) {
		roleDao.modify(role);
	}

	public List<Role> getAll() {
		return roleDao.getAll();
	}

	public void saveUserRole(List<UserRole> userRoles) {
		roleDao.deleteUserRole(userRoles.get(0).getUserId().toString());
		for (UserRole ur : userRoles) {
			roleDao.addUserRole(ur);
		}
	}

	@Override
	public boolean isExistRoleByName(String roleName) {
		Role filter = new Role();
		filter.setRoleName(roleName);
		List<Role> list = roleDao.queryForList(filter);
		if(list != null && !list.isEmpty()){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<Role> getRoleByUserId(String id) {
		return roleDao.getRoleByUserId(id);
	}

	@Override
	public List<Role> getRoleByPositionId(String positionId) {
		return roleDao.getRoleByPositionId(positionId);
	}

	@Override
	public void positionRoleAllocation(String positionId, String[] roleIds) {
		roleDao.deletePositionRole(positionId);
		if(roleIds.length > 0){
			for(String roleId : roleIds){
				roleDao.addPositionRole(positionId, roleId);
			}
		}
	}

	@Override
	public Map<String, Object> toAllocationReport(String roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportTemplateAll", reportTemplateDao.getAllStatistics());
		map.put("reportTemplateRole", reportTemplateDao.getByRoleId(roleId));
		return map;
	}

	@Override
	public boolean doneAllocationReport(String roleId, String[] reportTemplateIds) {
		reportTemplateDao.deleteRoleReport(roleId);
		if(reportTemplateIds != null && reportTemplateIds.length > 0){
			for(String reportTemplateId : reportTemplateIds){
				reportTemplateDao.addRoleReport(roleId, reportTemplateId);
			}
		}
		return true;
	}

	@Override
	public Map<String, Object> toAllocationBpm(String roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bpmAll", bpmConfigBasisDao.getAll());
		map.put("bpmRole", bpmConfigBasisDao.getByRoleId(roleId));
		return map;
	}

	@Override
	public boolean doneAllocationBpm(String roleId, String[] bpmIds) {
		bpmConfigBasisDao.deleteRoleBpm(roleId);
		if(bpmIds != null && bpmIds.length > 0){
			for(String bpmId : bpmIds){
				bpmConfigBasisDao.addRoleBpm(roleId, bpmId);
			}
		}
		return true;
	}

	@Override
	public Map<String, Object> toAllocationResource(String roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resourcesAll", resourceDao.getAll());
		map.put("resourcesRole", resourceDao.getResourceByRoleId(roleId));
		return map;
	}

	@Override
	public boolean doneAllocationResource(String roleId, String[] resourceIds) {
		resourceDao.deleteRoleResource(roleId);
		if(resourceIds != null && resourceIds.length > 0){
			for(String resourceId : resourceIds){
				resourceDao.addRoleResource(roleId, resourceId);
			}
		}
		return true;
	}

	@Override
	public Map<String, Object> toAllocationQuartz(String roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("quartzTaskAll", quartzTaskDao.getAll());
		map.put("quartzTaskRole", quartzTaskDao.getByRoleId(roleId));
		return map;
	}

	@Override
	public boolean doneAllocationQuartz(String roleId, String[] quartzTaskIds) {
		quartzTaskDao.deleteRoleQuartz(roleId);
		if(quartzTaskIds != null && quartzTaskIds.length > 0){
			for(String quartzTaskId : quartzTaskIds){
				quartzTaskDao.addRoleQuartz(roleId, quartzTaskId);
			}
		}
		return true;
	}
	
	

}
