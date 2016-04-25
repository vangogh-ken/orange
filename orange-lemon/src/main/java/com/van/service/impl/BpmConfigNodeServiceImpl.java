package com.van.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BasisStatusDao;
import com.van.halley.db.persistence.BasisSubstanceDao;
import com.van.halley.db.persistence.BpmConfigAuthDao;
import com.van.halley.db.persistence.BpmConfigBasisDao;
import com.van.halley.db.persistence.BpmConfigNodeDao;
import com.van.halley.db.persistence.ConstantAuthDao;
import com.van.halley.db.persistence.PositionDao;
import com.van.halley.db.persistence.RoleDao;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.BasisStatus;
import com.van.halley.db.persistence.entity.BasisSubstance;
import com.van.halley.db.persistence.entity.BpmConfigAuth;
import com.van.halley.db.persistence.entity.BpmConfigBasis;
import com.van.halley.db.persistence.entity.BpmConfigNode;
import com.van.halley.db.persistence.entity.ConstantAuth;
import com.van.halley.db.persistence.entity.User;
import com.van.service.BpmConfigNodeService;

@Transactional
@Service("bpmConfigNodeService")
public class BpmConfigNodeServiceImpl implements BpmConfigNodeService {
	@Autowired
	private BpmConfigNodeDao bpmConfigNodeDao;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private BpmConfigBasisDao bpmConfigBasisDao;
	@Autowired
	private BasisStatusDao basisStatusDao;
	@Autowired
	private BasisSubstanceDao basisSubstanceDao;
	@Autowired
	private BpmConfigAuthDao bpmConfigAuthDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PositionDao positionDao;
	@Autowired
	private ConstantAuthDao constantAuthDao;

	@Override
	public List<BpmConfigNode> getAll() {
		return bpmConfigNodeDao.getAll();
	}

	@Override
	public List<BpmConfigNode> queryForList(BpmConfigNode bpmConfigNode) {
		return bpmConfigNodeDao.queryForList(bpmConfigNode);
	}

	@Override
	public PageView<BpmConfigNode> query(PageView<BpmConfigNode> pageView, BpmConfigNode bpmConfigNode) {
		List<BpmConfigNode> list = bpmConfigNodeDao.query(pageView,
				bpmConfigNode);
		pageView.setResults(list);
		return pageView;
	}

	@Override
	public void add(BpmConfigNode bpmConfigNode) {
		bpmConfigNodeDao.add(bpmConfigNode);
	}

	@Override
	public void delete(String id) {
		bpmConfigNodeDao.delete(id);
	}

	@Override
	public void modify(BpmConfigNode bpmConfigNode) {
		bpmConfigNodeDao.modify(bpmConfigNode);
	}

	@Override
	public BpmConfigNode getById(String id) {
		return bpmConfigNodeDao.getById(id);
	}

	@Override
	public void deleteByPdId(String pdId){
		bpmConfigNodeDao.deleteByPdId(pdId);
	}

	@Override
	public BpmConfigNode getByPdIdAndTdKey(String pdId, String tdKey){
		return bpmConfigNodeDao.getByPdIdAndTdKey(pdId, tdKey);
	}

	/////////////////////////在任务改变的时候，更改业务数据状态/////////////////////////
	@Override
	public void updateBasisSubstance(Task task) {
		String processInstanceId = task.getProcessInstanceId();
		String processDefinitionId = task.getProcessDefinitionId();
		BpmConfigNode bpmConfigNode = 
				bpmConfigNodeDao.getByPdIdAndTdKey(processDefinitionId, task.getTaskDefinitionKey());
		String businessKey = runtimeService.createProcessInstanceQuery().
				processInstanceId(processInstanceId).singleResult().getBusinessKey();
		String status = bpmConfigNode.getSourceStatus();
		BasisSubstance basisSubstance = basisSubstanceDao.getById(businessKey);
		basisSubstance.setStatus(status);
		basisSubstanceDao.modify(basisSubstance);
	}

	@Override
	public Map<String, Object> toConfigStatus(String bpmConfigNodeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		BpmConfigNode bpmConfigNode = bpmConfigNodeDao.getById(bpmConfigNodeId);
		BpmConfigBasis bpmConfigBasis = bpmConfigBasisDao.getByBpmKey(bpmConfigNode.getPdKey());
		if(bpmConfigBasis != null){
			BasisStatus filter = new BasisStatus();
			filter.setBasisSubstanceType(bpmConfigBasis.getBasisSubstanceType());
			map.put("basisStatuses", basisStatusDao.queryForList(filter));
		}else{
			map.put("basisStatuses", null);
		}
		return map;
	}

	@Override
	public void doneConfigStatus(String bpmConfigNodeId, String basisStatusId) {
		BpmConfigNode bpmConfigNode = bpmConfigNodeDao.getById(bpmConfigNodeId);
		bpmConfigNode.setBasisStatus(basisStatusDao.getById(basisStatusId));
		bpmConfigNodeDao.modify(bpmConfigNode);
	}

	@Override
	public Map<String, Object> toConfigAuth(String bpmConfigNodeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bpmConfigNode", bpmConfigNodeDao.getById(bpmConfigNodeId));
		map.put("users", userDao.getByBpmConfigNodeId(bpmConfigNodeId));
		map.put("roles", roleDao.getByBpmConfigNodeId(bpmConfigNodeId));
		map.put("positions", positionDao.getByBpmConfigNodeId(bpmConfigNodeId));
		map.put("constantAuthes", constantAuthDao.getByBpmConfigNodeId(bpmConfigNodeId));
		return map;
	}

	@Override
	public void doneConfigAuth(String bpmConfigNodeId, String authId,
			String authType) {
		BpmConfigAuth filter = new BpmConfigAuth();
		filter.setBpmNodeId(bpmConfigNodeId);
		filter.setAuthId(authId);
		filter.setAuthType(authType);
		
		if(bpmConfigAuthDao.count(filter) == 0){
			BpmConfigAuth bpmConfigAuth = new BpmConfigAuth();
			bpmConfigAuth.setBpmNodeId(bpmConfigNodeId);
			bpmConfigAuth.setAuthId(authId);
			bpmConfigAuth.setAuthType(authType);
			bpmConfigAuthDao.add(bpmConfigAuth);
		}
	}

	@Override
	public void doneDeleteAuth(String bpmConfigNodeId, String authId) {
		bpmConfigAuthDao.doneDeleteAuth(bpmConfigNodeId, authId);
	}

	@Override
	public List<User> getAuthByBpmConfigNodeId(String bpmConfigNodeId, String initiatorId) {
		List<BpmConfigAuth> bpmConfigAuthes = bpmConfigAuthDao.getByBpmConfigNodeId(bpmConfigNodeId);
		List<User> users = new ArrayList<User>();
		for(BpmConfigAuth bpmConfigAuth : bpmConfigAuthes){
			if("U".equals(bpmConfigAuth.getAuthType())){//用户
				users.add(userDao.getById(bpmConfigAuth.getAuthId()));
			}else if("P".equals(bpmConfigAuth.getAuthType())){//职位
				users.addAll(userDao.getByPositionId(bpmConfigAuth.getAuthId()));
			}else if("R".equals(bpmConfigAuth.getAuthType())){//职位
				users.addAll(userDao.getByRoleId(bpmConfigAuth.getAuthId()));
			}else if("C".equals(bpmConfigAuth.getAuthType())){
				ConstantAuth constantAuth = constantAuthDao.getById(bpmConfigAuth.getAuthId());
				if("直接上级".equals(constantAuth.getConstantName())){
					users.addAll(userDao.getDirectSuperior(initiatorId));
				}else if("直接领导".equals(constantAuth.getConstantName())){
					users.addAll(userDao.getDirectShepherd(initiatorId));
				}else if("发起人".equals(constantAuth.getConstantName())){
					users.add(userDao.getById(initiatorId));
				}
			}
		}
		return users;
	}

	@Override
	public List<String> getAuthNameByBpmConfigNodeId(String bpmConfigNodeId) {
		List<BpmConfigAuth> bpmConfigAuthes = bpmConfigAuthDao.getByBpmConfigNodeId(bpmConfigNodeId);
		List<String> candidates = new ArrayList<String>();
		for(BpmConfigAuth bpmConfigAuth : bpmConfigAuthes){
			if("U".equals(bpmConfigAuth.getAuthType())){//用户
				candidates.add(userDao.getById(bpmConfigAuth.getAuthId()).getDisplayName());
			}else if("P".equals(bpmConfigAuth.getAuthType())){//职位
				List<User> users = userDao.getByPositionId(bpmConfigAuth.getAuthId());
				if(users != null && !users.isEmpty()){
					for(User user : users){
						candidates.add(user.getDisplayName());
					}
				}
				
			}else if("R".equals(bpmConfigAuth.getAuthType())){//职位
				List<User> users = userDao.getByRoleId(bpmConfigAuth.getAuthId());
				if(users != null && !users.isEmpty()){
					for(User user : users){
						candidates.add(user.getDisplayName());
					}
				}
			}else if("C".equals(bpmConfigAuth.getAuthType())){
				ConstantAuth constantAuth = constantAuthDao.getById(bpmConfigAuth.getAuthId());
				candidates.add(constantAuth.getConstantName());
			}
		}
		return candidates;
	}
}
