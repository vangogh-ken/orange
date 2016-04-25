package com.van.halley.bpm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.van.halley.bpm.service.ProcessInstanceOperateService;
import com.van.halley.db.persistence.entity.BpmConfigBasis;
import com.van.halley.util.StringUtil;
import com.van.service.BasisAttributeService;
import com.van.service.BasisSubstanceService;
import com.van.service.BpmConfigBasisService;

@Service("processInstanceOperateService")
public class ProcessInstanceOperateServiceImpl implements ProcessInstanceOperateService {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private BasisSubstanceService basisSubstanceService;
	@Autowired
	private BasisAttributeService basisAttributeService;
	@Autowired
	private BpmConfigBasisService bpmConfigBasisService;
	private static Logger logger = LoggerFactory.getLogger(ProcessInstanceOperateServiceImpl.class);

	@Override
	public Map<String, Object> startProcessByKey(String key, String userId) {
		BpmConfigBasis bpmConfigBasis = bpmConfigBasisService.getByBpmKey(key);
		String businessKey = basisSubstanceService.toAddBpm(bpmConfigBasis.getBasisSubstanceType(), "草稿");
		
		identityService.setAuthenticatedUserId(userId);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("initiator", userId);//将发起人存入参数
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).active().latestVersion().singleResult();
		variables.put("processDefinition", processDefinition);
		variables.put("businessKey", businessKey);//将业务ID存入参数
		variables.put("throughUserTasks", getStartAndFirstActivity(key));//启动之后直接流转到第一个节点
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(key, businessKey, variables);
		Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
		
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> basisValueMap = basisSubstanceService.getBasisValueMap(businessKey);
		basisValueMap.put("taskId", task.getId());
		basisValueMap.put("processInstanceId", pi.getProcessInstanceId());
		basisValueMap.put("title", bpmConfigBasis.getBasisSubstanceType().getTypeName());
		map.put("item", basisValueMap);
		map.put("attributes", basisAttributeService.getByBasisSubstanceTypeId(bpmConfigBasis.getBasisSubstanceType().getId()));
		if(StringUtil.isNullOrEmpty(bpmConfigBasis.getConfigPrimeUrl())){
			map.put("url", "/content/common/common-business-input");
		}else{
			map.put("url", bpmConfigBasis.getConfigPrimeUrl());
		}
		
		return map;
	}

	@Override
	public Map<String, Object> getBusinessByTaskId(String taskId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		String processDefinitionKey = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(pi.getProcessDefinitionId()).singleResult().getKey();
		BpmConfigBasis bpmConfigBasis = bpmConfigBasisService.getByBpmKey(processDefinitionKey);
		Map<String, Object> basisValueMap = basisSubstanceService.getBasisValueMap(pi.getBusinessKey());
		basisValueMap.put("taskId", task.getId());
		basisValueMap.put("processInstanceId", pi.getProcessInstanceId());
		basisValueMap.put("title", bpmConfigBasis.getBasisSubstanceType().getTypeName());
		map.put("item", basisValueMap);
		map.put("attributes", basisAttributeService.getByBasisSubstanceTypeId(bpmConfigBasis.getBasisSubstanceType().getId()));
		
		if(StringUtil.isNullOrEmpty(bpmConfigBasis.getConfigPrimeUrl())){
			map.put("url", "/content/common/common-business-input");
		}else{
			map.put("url", bpmConfigBasis.getConfigPrimeUrl());
		}
		
		return map;
	}
	
	
	@Override
	public void deleteProcessInstance(String processInstanceId) {
		runtimeService.deleteProcessInstance(processInstanceId, "管理删除");
	}

	/* 
	 * 连同历史记录一起删除
	 */
	@Override
	public void delete(String processInstanceId) {
		 ProcessInstance pi = runtimeService.createProcessInstanceQuery().
				 processInstanceId(processInstanceId).singleResult(); 
		 HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().
				 processInstanceId(processInstanceId).singleResult();
		 if(pi != null){
			 runtimeService.deleteProcessInstance(processInstanceId, "管理删除");
			 historyService.deleteHistoricProcessInstance(processInstanceId);
			 basisSubstanceService.delete(pi.getBusinessKey());
		 }else{
			 if(hpi != null){
				 historyService.deleteHistoricProcessInstance(processInstanceId); 
				 basisSubstanceService.delete(hpi.getBusinessKey());
			 }else{
				 logger.info("流程实例已经被删除, 实例ID: {}", processInstanceId);
			 }
		 }
	}
	
	/* 
	 * 获取开始事件与第一个任务节点信息
	 */
	@Override
	public List<String> getStartAndFirstActivity(String processDefinitionKey) {
		List<String> acivityIds = new ArrayList<String>();
		ProcessDefinitionImpl pd = (ProcessDefinitionImpl)repositoryService
				.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey)
				.latestVersion().singleResult();
		ProcessDefinitionEntity processDefinition = 
				(ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(pd.getId());
		
		ActivityImpl activityImp = processDefinition.getInitial();
		acivityIds.add(activityImp.getId());
		List<PvmTransition> pvmTransitions = activityImp.getOutgoingTransitions();
		if(pvmTransitions != null && !pvmTransitions.isEmpty() && pvmTransitions.size() == 1){
			acivityIds.add(pvmTransitions.get(0).getDestination().getId());
		}else{
			throw new ActivitiException("Could not get activity after startEvent");
		}
		
		return acivityIds;
	}

}
