package com.van.halley.bpm.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.NativeHistoricProcessInstanceQuery;
import org.activiti.engine.history.NativeHistoricTaskInstanceQuery;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.van.halley.bpm.service.HistoricQueryService;
import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BpmConfigBasis;
import com.van.halley.util.StringUtil;
import com.van.service.BasisAttributeService;
import com.van.service.BasisSubstanceService;
import com.van.service.BpmConfigBasisService;

@Service("historicQueryService")
public class HistoricQueryServiceImpl implements HistoricQueryService {
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ManagementService managementService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private BpmConfigBasisService bpmConfigBasisService;
	@Autowired
	private BasisSubstanceService basisSubstanceService;
	@Autowired
	private BasisAttributeService basisAttributeService;

	@Override
	public PageView<HistoricProcessInstance> queryHistoricProcessInstance(PageView<HistoricProcessInstance> pageView, Map<String, Object> map) {
		NativeHistoricProcessInstanceQuery queryList = historyService.createNativeHistoricProcessInstanceQuery();
		NativeHistoricProcessInstanceQuery queryCount = historyService.createNativeHistoricProcessInstanceQuery();
		StringBuilder sqlList = new StringBuilder();
		StringBuilder sqlCount = new StringBuilder();
		sqlList.append("SELECT PI.* FROM " + managementService.getTableName(HistoricProcessInstance.class) + " AS PI LEFT JOIN ");
		sqlList.append(managementService.getTableName(ProcessDefinition.class) + " AS PD ON PI.PROC_DEF_ID_=PD.ID_ ");
		
		sqlCount.append("SELECT COUNT(1) FROM " + managementService.getTableName(HistoricProcessInstance.class) + " AS PI LEFT JOIN ");
		sqlCount.append(managementService.getTableName(ProcessDefinition.class) + " AS PD ON PI.PROC_DEF_ID_=PD.ID_ ");
		
		int sqlListIndex = sqlList.length();
		int sqlCountIndex = sqlCount.length();
		
		//流程名称
		if(map.get("processDefinitionName") != null && !StringUtil.isNullOrEmpty(map.get("processDefinitionName").toString())){
			sqlList.append(" PD.NAME_ LIKE '%" + map.get("processDefinitionName").toString() + "%' AND ");
			sqlCount.append(" PD.NAME_ LIKE '%" + map.get("processDefinitionName").toString() + "%' AND ");
		}
		//是否结束
		if("T".equals(map.get("finished"))){
			sqlList.append(" PI.END_TIME_ IS NOT NULL AND ");
			sqlCount.append(" PI.END_TIME_ IS NOT NULL AND ");
		}else if("F".equals(map.get("finished"))){
			sqlList.append(" PI.END_TIME_ IS NULL AND ");
			sqlCount.append(" PI.END_TIME_ IS NULL AND ");
		}
		
		//开始时间结束时间
		if(map.get("startTime") != null && !StringUtil.isNullOrEmpty(map.get("startTime").toString())){
			sqlList.append(" PI.START_TIME_ > '" + map.get("startTime").toString() + "' AND ");
			sqlCount.append(" PI.START_TIME_ > '" + map.get("startTime").toString() + "' AND ");
		}
		
		if(map.get("endTime") != null && !StringUtil.isNullOrEmpty(map.get("endTime").toString())){
			sqlList.append(" PI.END_TIME_ < '" + map.get("endTime").toString() + "' AND ");
			sqlCount.append(" PI.END_TIME_ < '" + map.get("endTime").toString() + "' AND ");
		}
		
		//流程发起人
		if(map.get("startUserName") != null && !StringUtil.isNullOrEmpty(map.get("startUserName").toString())){
			sqlList.append(" PI.START_USER_ID_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("startUserName").toString() + "%') AND ");
			sqlCount.append(" PI.START_USER_ID_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("startUserName").toString() + "%') AND ");
		}
		
		if(map.get("startUserId") != null && !StringUtil.isNullOrEmpty(map.get("startUserId").toString())){
			sqlList.append(" PI.START_USER_ID_ ='" + map.get("startUserId").toString() + "' AND ");
			sqlCount.append(" PI.START_USER_ID_ ='" + map.get("startUserId").toString() + "' AND ");
		}
		
		//流程参与者
		if(map.get("involvedUserName") != null && !StringUtil.isNullOrEmpty(map.get("involvedUserName").toString())){
			sqlList.append(" EXISTS(SELECT LINK.USER_ID_ FROM ACT_HI_IDENTITYLINK AS LINK WHERE LINK.USER_ID_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("involvedUserName").toString() + "%') AND LINK.PROC_INST_ID_ = PI.ID_) AND ");
			sqlCount.append(" EXISTS(SELECT LINK.USER_ID_ FROM ACT_HI_IDENTITYLINK AS LINK WHERE LINK.USER_ID_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("involvedUserName").toString() + "%') AND LINK.PROC_INST_ID_ = PI.ID_) AND ");
		}
		
		if(map.get("involvedUserId") != null && !StringUtil.isNullOrEmpty(map.get("involvedUserId").toString())){
			sqlList.append(" EXISTS(SELECT LINK.USER_ID_ FROM ACT_HI_IDENTITYLINK AS LINK WHERE LINK.USER_ID_ = '"  +map.get("involvedUserId").toString() + "' AND LINK.PROC_INST_ID_ = PI.ID_) AND ");
			sqlCount.append(" EXISTS(SELECT LINK.USER_ID_ FROM ACT_HI_IDENTITYLINK AS LINK WHERE LINK.USER_ID_ = '"  +map.get("involvedUserId").toString() + "' AND LINK.PROC_INST_ID_ = PI.ID_) AND ");
		}
		
		
		if(sqlList.lastIndexOf("AND") > 0){
			sqlList = new StringBuilder(sqlList.substring(0, sqlList.lastIndexOf("AND")));
			sqlCount = new StringBuilder(sqlCount.substring(0, sqlCount.lastIndexOf("AND")));
			
			sqlList.insert(sqlListIndex, " WHERE ");
			sqlCount.insert(sqlCountIndex, " WHERE ");
		}
		
		if(!StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			sqlList.append(" ORDER BY " + pageView.getOrderBy() + " " + pageView.getOrder());
			sqlCount.append(" ORDER BY " + pageView.getOrderBy() + " " + pageView.getOrder());
		}
		
		pageView.setResults(queryList.sql(sqlList.toString()).listPage((pageView.getPageNo() - 1) * pageView.getPageSize(), pageView.getPageSize()));
		pageView.setTotalCount(queryCount.sql(sqlCount.toString()).count());
		
		return pageView;
	}
	
	@Override
	public int countHistoricProcessInstance(Map<String, Object> map) {
		//NativeHistoricProcessInstanceQuery queryList = historyService.createNativeHistoricProcessInstanceQuery();
		NativeHistoricProcessInstanceQuery queryCount = historyService.createNativeHistoricProcessInstanceQuery();
		//StringBuilder sqlList = new StringBuilder();
		StringBuilder sqlCount = new StringBuilder();
		//sqlList.append("SELECT PI.* FROM " + managementService.getTableName(HistoricProcessInstance.class) + " AS PI LEFT JOIN ");
		//sqlList.append(managementService.getTableName(ProcessDefinition.class) + " AS PD ON PI.PROC_DEF_ID_=PD.ID_ ");
		
		sqlCount.append("SELECT COUNT(1) FROM " + managementService.getTableName(HistoricProcessInstance.class) + " AS PI LEFT JOIN ");
		sqlCount.append(managementService.getTableName(ProcessDefinition.class) + " AS PD ON PI.PROC_DEF_ID_=PD.ID_ ");
		
		//int sqlListIndex = sqlList.length();
		int sqlCountIndex = sqlCount.length();
		
		//流程名称
		if(map.get("processDefinitionName") != null && !StringUtil.isNullOrEmpty(map.get("processDefinitionName").toString())){
			//sqlList.append(" PD.NAME_ LIKE '%" + map.get("processDefinitionName").toString() + "%' AND ");
			sqlCount.append(" PD.NAME_ LIKE '%" + map.get("processDefinitionName").toString() + "%' AND ");
		}
		//是否结束
		if("T".equals(map.get("finished"))){
			//sqlList.append(" PI.END_TIME_ IS NOT NULL AND ");
			sqlCount.append(" PI.END_TIME_ IS NOT NULL AND ");
		}else if("F".equals(map.get("finished"))){
			//sqlList.append(" PI.END_TIME_ IS NULL AND ");
			sqlCount.append(" PI.END_TIME_ IS NULL AND ");
		}
		
		//开始时间结束时间
		if(map.get("startTime") != null && !StringUtil.isNullOrEmpty(map.get("startTime").toString())){
			//sqlList.append(" PI.START_TIME_ > '" + map.get("startTime").toString() + "' AND ");
			sqlCount.append(" PI.START_TIME_ > '" + map.get("startTime").toString() + "' AND ");
		}
		
		if(map.get("endTime") != null && !StringUtil.isNullOrEmpty(map.get("endTime").toString())){
			//sqlList.append(" PI.END_TIME_ < '" + map.get("endTime").toString() + "' AND ");
			sqlCount.append(" PI.END_TIME_ < '" + map.get("endTime").toString() + "' AND ");
		}
				
		//流程发起人
		if(map.get("startUserName") != null && !StringUtil.isNullOrEmpty(map.get("startUserName").toString())){
			//sqlList.append(" PI.START_USER_ID_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("startUserName").toString() + "%') AND ");
			sqlCount.append(" PI.START_USER_ID_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("startUserName").toString() + "%') AND ");
		}
		
		if(map.get("startUserId") != null && !StringUtil.isNullOrEmpty(map.get("startUserId").toString())){
			//sqlList.append(" PI.START_USER_ID_ ='" + map.get("startUserId").toString() + "' AND ");
			sqlCount.append(" PI.START_USER_ID_ ='" + map.get("startUserId").toString() + "' AND ");
		}
		
		//流程参与者
		if(map.get("involvedUserName") != null && !StringUtil.isNullOrEmpty(map.get("involvedUserName").toString())){
			//sqlList.append(" EXISTS(SELECT LINK.USER_ID_ FROM ACT_HI_IDENTITYLINK AS LINK WHERE LINK.USER_ID_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("involvedUserName").toString() + "%') AND LINK.PROC_INST_ID_ = PI.ID_) AND ");
			sqlCount.append(" EXISTS(SELECT LINK.USER_ID_ FROM ACT_HI_IDENTITYLINK AS LINK WHERE LINK.USER_ID_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("involvedUserName").toString() + "%') AND LINK.PROC_INST_ID_ = PI.ID_) AND ");
		}
		
		if(map.get("involvedUserId") != null && !StringUtil.isNullOrEmpty(map.get("involvedUserId").toString())){
			//sqlList.append(" EXISTS(SELECT LINK.USER_ID_ FROM ACT_HI_IDENTITYLINK AS LINK WHERE LINK.USER_ID_ = '"  +map.get("involvedUserId").toString() + "' AND LINK.PROC_INST_ID_ = PI.ID_) AND ");
			sqlCount.append(" EXISTS(SELECT LINK.USER_ID_ FROM ACT_HI_IDENTITYLINK AS LINK WHERE LINK.USER_ID_ = '"  +map.get("involvedUserId").toString() + "' AND LINK.PROC_INST_ID_ = PI.ID_) AND ");
		}
		
		
		if(sqlCount.lastIndexOf("AND") > 0){
			//sqlList = new StringBuilder(sqlList.substring(0, sqlList.lastIndexOf("AND")));
			sqlCount = new StringBuilder(sqlCount.substring(0, sqlCount.lastIndexOf("AND")));
			
			//sqlList.insert(sqlListIndex, " WHERE ");
			sqlCount.insert(sqlCountIndex, " WHERE ");
		}
		
		return (int)queryCount.sql(sqlCount.toString()).count();
	}

	@Override
	public PageView<HistoricTaskInstance> queryHistoricTaskInstance(PageView<HistoricTaskInstance> pageView, Map<String, Object> map) {
		NativeHistoricTaskInstanceQuery queryList = historyService.createNativeHistoricTaskInstanceQuery();
		NativeHistoricTaskInstanceQuery queryCount = historyService.createNativeHistoricTaskInstanceQuery();
		StringBuilder sqlList = new StringBuilder();
		StringBuilder sqlCount = new StringBuilder();
		sqlList.append("SELECT DISTINCT TI.* FROM " + managementService.getTableName(HistoricTaskInstance.class) + " AS TI LEFT JOIN ");
		sqlList.append(managementService.getTableName(ProcessDefinition.class) + " AS PD ON TI.PROC_DEF_ID_=PD.ID_ ");
		sqlList.append(" LEFT JOIN ");
		sqlList.append(managementService.getTableName(HistoricProcessInstance.class) + " AS PI ON TI.PROC_INST_ID_=PI.PROC_INST_ID_ ");
		sqlList.append(" LEFT JOIN ");
		sqlList.append(managementService.getTableName(Task.class) + " AS TR ON TI.ID_=TR.ID_ ");
		sqlList.append(" LEFT JOIN ");
		sqlList.append(managementService.getTableName(HistoricIdentityLinkEntity.class) + " AS LINK ON LINK.TASK_ID_=TI.ID_ ");
		
		sqlCount.append("SELECT COUNT(1) FROM (");
		sqlCount.append("SELECT DISTINCT TI.ID_ FROM " + managementService.getTableName(HistoricTaskInstance.class) + " AS TI LEFT JOIN ");
		sqlCount.append(managementService.getTableName(ProcessDefinition.class) + " AS PD ON TI.PROC_DEF_ID_=PD.ID_ ");
		sqlCount.append(" LEFT JOIN ");
		sqlCount.append(managementService.getTableName(HistoricProcessInstance.class) + " AS PI ON TI.PROC_INST_ID_=PI.PROC_INST_ID_ ");
		sqlCount.append(" LEFT JOIN ");
		sqlCount.append(managementService.getTableName(Task.class) + " AS TR ON TI.ID_=TR.ID_ ");
		sqlCount.append(" LEFT JOIN ");
		sqlCount.append(managementService.getTableName(HistoricIdentityLinkEntity.class) + " AS LINK ON LINK.TASK_ID_=TI.ID_ ");
		
		int sqlListIndex = sqlList.length();
		int sqlCountIndex = sqlCount.length();
		
		if(map.get("taskName") != null && !StringUtil.isNullOrEmpty(map.get("taskName").toString())){
			sqlList.append(" TI.NAME_ LIKE '%" + map.get("taskName").toString() + "%' AND ");
			sqlCount.append(" TI.NAME_ LIKE '%" + map.get("taskName").toString() + "%' AND ");
		}
		
		if(map.get("processDefinitionName") != null && !StringUtil.isNullOrEmpty(map.get("processDefinitionName").toString())){
			sqlList.append(" PD.NAME_ LIKE '%" + map.get("processDefinitionName").toString() + "%' AND ");
			sqlCount.append(" PD.NAME_ LIKE '%" + map.get("processDefinitionName").toString() + "%' AND ");
		}
		
		//是否已完成
		if("T".equals(map.get("finished"))){
			sqlList.append(" TI.END_TIME_ IS NOT NULL AND ");
			sqlCount.append(" TI.END_TIME_ IS NOT NULL AND ");
		}else if("F".equals(map.get("finished"))){
			sqlList.append(" TI.END_TIME_ IS NULL AND ");
			sqlCount.append(" TI.END_TIME_ IS NULL AND ");
		}
		
		//开始时间结束时间
		if(map.get("startTime") != null && !StringUtil.isNullOrEmpty(map.get("startTime").toString())){
			sqlList.append(" TI.START_TIME_ > '" + map.get("startTime").toString() + "' AND ");
			sqlCount.append(" TI.START_TIME_ > '" + map.get("startTime").toString() + "' AND ");
		}
		
		if(map.get("endTime") != null && !StringUtil.isNullOrEmpty(map.get("endTime").toString())){
			sqlList.append(" TI.END_TIME_ < '" + map.get("endTime").toString() + "' AND ");
			sqlCount.append(" TI.END_TIME_ < '" + map.get("endTime").toString() + "' AND ");
		}
		
		//是否已签收
		if("T".equals(map.get("assigned"))){
			sqlList.append(" TI.ASSIGNEE_ IS NOT NULL AND ");
			sqlCount.append(" TI.ASSIGNEE_ IS NOT NULL AND ");
		}else if("F".equals(map.get("assigned"))){
			sqlList.append(" TI.ASSIGNEE_ IS NULL AND ");
			sqlCount.append(" TI.ASSIGNEE_ IS NULL AND ");
		}
		
		//流程发起人
		if(map.get("startUserName") != null && !StringUtil.isNullOrEmpty(map.get("startUserName").toString())){
			sqlList.append(" PI.START_USER_ID_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("startUserName").toString() + "%') AND ");
			sqlCount.append(" PI.START_USER_ID_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("startUserName").toString() + "%') AND ");
		}
		
		if(map.get("startUserId") != null && !StringUtil.isNullOrEmpty(map.get("startUserId").toString())){
			sqlList.append(" PI.START_USER_ID_ ='" + map.get("startUserId").toString() + "' AND ");
			sqlCount.append(" PI.START_USER_ID_ ='" + map.get("startUserId").toString() + "' AND ");
		}
		
		//任务委托人
		if(map.get("assigneeUserName") != null && !StringUtil.isNullOrEmpty(map.get("assigneeUserName").toString())){
			sqlList.append(" TI.ASSIGNEE_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("assigneeUserName").toString() + "%') AND ");
			sqlCount.append(" TI.ASSIGNEE_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("assigneeUserName").toString() + "%') AND ");
		}
		
		if(map.get("assigneeUserId") != null && !StringUtil.isNullOrEmpty(map.get("assigneeUserId").toString())){
			sqlList.append(" TI.ASSIGNEE_ ='" + map.get("assigneeUserId").toString() + "' AND ");
			sqlCount.append(" TI.ASSIGNEE_ ='" + map.get("assigneeUserId").toString() + "' AND");
		}
		//任务拥有人
		if(map.get("ownerUserName") != null && !StringUtil.isNullOrEmpty(map.get("ownerUserName").toString())){
			sqlList.append(" TI.OWNER_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("ownerUserName").toString() + "%') AND ");
			sqlCount.append(" TI.OWNER_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("ownerUserName").toString() + "%') AND ");
		}
		
		if(map.get("ownerUserId") != null && !StringUtil.isNullOrEmpty(map.get("ownerUserId").toString())){
			sqlList.append(" TI.OWNER_ ='" + map.get("ownerUserId").toString() + "' AND ");
			sqlCount.append(" TI.OWNER_ ='" + map.get("ownerUserId").toString() + "' AND");
		}
		
		//任务签收人
		if(map.get("claimUserName") != null && !StringUtil.isNullOrEmpty(map.get("claimUserName").toString())){
			sqlList.append(" (TI.ASSIGNEE_ IS NULL AND LINK.TYPE_ = 'candidate' AND LINK.USER_ID_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("claimUserName").toString() + "%')) AND ");
			sqlCount.append(" (TI.ASSIGNEE_ IS NULL AND LINK.TYPE_ = 'candidate' AND LINK.USER_ID_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("claimUserName").toString() + "%')) AND ");
		}
		
		if(map.get("claimUserId") != null && !StringUtil.isNullOrEmpty(map.get("claimUserId").toString())){
			sqlList.append(" (TI.ASSIGNEE_ IS NULL AND LINK.TYPE_ = 'candidate' AND LINK.USER_ID_ ='" + map.get("claimUserId").toString() + "') AND ");
			sqlCount.append(" (TI.ASSIGNEE_ IS NULL AND LINK.TYPE_ = 'candidate' AND LINK.USER_ID_ ='" + map.get("claimUserId").toString() + "') AND ");
		}
		
		if(sqlList.lastIndexOf("AND") > 0){
			sqlList = new StringBuilder(sqlList.substring(0, sqlList.lastIndexOf("AND")));
			sqlCount = new StringBuilder(sqlCount.substring(0, sqlCount.lastIndexOf("AND")));
			
			sqlList.insert(sqlListIndex, " WHERE ");
			sqlCount.insert(sqlCountIndex, " WHERE ");
		}
		
		if(!StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			sqlList.append(" ORDER BY " + pageView.getOrderBy() + " " + pageView.getOrder());
			sqlCount.append(" ORDER BY " + pageView.getOrderBy() + " " + pageView.getOrder());
		}
		sqlCount.append(") AS T");
		
		pageView.setResults(queryList.sql(sqlList.toString()).listPage((pageView.getPageNo() - 1) * pageView.getPageSize(), pageView.getPageSize()));
		pageView.setTotalCount(queryCount.sql(sqlCount.toString()).count());
		
		return pageView;
	}
	
	@Override
	public int countHistoricTaskInstance(Map<String, Object> map) {
		//NativeHistoricTaskInstanceQuery queryList = historyService.createNativeHistoricTaskInstanceQuery();
		NativeHistoricTaskInstanceQuery queryCount = historyService.createNativeHistoricTaskInstanceQuery();
		//StringBuilder sqlList = new StringBuilder();
		StringBuilder sqlCount = new StringBuilder();
		//sqlList.append("SELECT DISTINCT TI.* FROM " + managementService.getTableName(HistoricTaskInstance.class) + " AS TI LEFT JOIN ");
		//sqlList.append(managementService.getTableName(ProcessDefinition.class) + " AS PD ON TI.PROC_DEF_ID_=PD.ID_ ");
		//sqlList.append(" LEFT JOIN ");
		//sqlList.append(managementService.getTableName(HistoricProcessInstance.class) + " AS PI ON TI.PROC_INST_ID_=PI.PROC_INST_ID_ ");
		//sqlList.append(" LEFT JOIN ");
		//sqlList.append(managementService.getTableName(Task.class) + " AS TR ON TI.ID_=TR.ID_ ");
		//sqlList.append(" LEFT JOIN ");
		//sqlList.append(managementService.getTableName(HistoricIdentityLinkEntity.class) + " AS LINK ON LINK.TASK_ID_=TI.ID_ ");
		
		sqlCount.append("SELECT COUNT(1) FROM (");
		sqlCount.append("SELECT DISTINCT TI.ID_ FROM " + managementService.getTableName(HistoricTaskInstance.class) + " AS TI LEFT JOIN ");
		sqlCount.append(managementService.getTableName(ProcessDefinition.class) + " AS PD ON TI.PROC_DEF_ID_=PD.ID_ ");
		sqlCount.append(" LEFT JOIN ");
		sqlCount.append(managementService.getTableName(HistoricProcessInstance.class) + " AS PI ON TI.PROC_INST_ID_=PI.PROC_INST_ID_ ");
		sqlCount.append(" LEFT JOIN ");
		sqlCount.append(managementService.getTableName(Task.class) + " AS TR ON TI.ID_=TR.ID_ ");
		sqlCount.append(" LEFT JOIN ");
		sqlCount.append(managementService.getTableName(HistoricIdentityLinkEntity.class) + " AS LINK ON LINK.TASK_ID_=TI.ID_ ");
		
		//int sqlListIndex = sqlList.length();
		int sqlCountIndex = sqlCount.length();
		
		if(map.get("taskName") != null && !StringUtil.isNullOrEmpty(map.get("taskName").toString())){
			//sqlList.append(" TI.NAME_ LIKE '%" + map.get("taskName").toString() + "%' AND ");
			sqlCount.append(" TI.NAME_ LIKE '%" + map.get("taskName").toString() + "%' AND ");
		}
		
		if(map.get("processDefinitionName") != null && !StringUtil.isNullOrEmpty(map.get("processDefinitionName").toString())){
			//sqlList.append(" PD.NAME_ LIKE '%" + map.get("processDefinitionName").toString() + "%' AND ");
			sqlCount.append(" PD.NAME_ LIKE '%" + map.get("processDefinitionName").toString() + "%' AND ");
		}
		
		//是否已完成
		if("T".equals(map.get("finished"))){
			//sqlList.append(" TI.END_TIME_ IS NOT NULL AND ");
			sqlCount.append(" TI.END_TIME_ IS NOT NULL AND ");
		}else if("F".equals(map.get("finished"))){
			//sqlList.append(" TI.END_TIME_ IS NULL AND ");
			sqlCount.append(" TI.END_TIME_ IS NULL AND ");
		}
		
		//开始时间结束时间
		if(map.get("startTime") != null && !StringUtil.isNullOrEmpty(map.get("startTime").toString())){
			//sqlList.append(" TI.START_TIME_ > '" + map.get("startTime").toString() + "' AND ");
			sqlCount.append(" TI.START_TIME_ > '" + map.get("startTime").toString() + "' AND ");
		}
		
		if(map.get("endTime") != null && !StringUtil.isNullOrEmpty(map.get("endTime").toString())){
			//sqlList.append(" TI.END_TIME_ < '" + map.get("endTime").toString() + "' AND ");
			sqlCount.append(" TI.END_TIME_ < '" + map.get("endTime").toString() + "' AND ");
		}
		
		//是否已签收
		if("T".equals(map.get("assigned"))){
			//sqlList.append(" TI.ASSIGNEE_ IS NOT NULL AND ");
			sqlCount.append(" TI.ASSIGNEE_ IS NOT NULL AND ");
		}else if("F".equals(map.get("assigned"))){
			//sqlList.append(" TI.ASSIGNEE_ IS NULL AND ");
			sqlCount.append(" TI.ASSIGNEE_ IS NULL AND ");
		}
		
		//流程发起人
		if(map.get("startUserName") != null && !StringUtil.isNullOrEmpty(map.get("startUserName").toString())){
			//sqlList.append(" PI.START_USER_ID_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("startUserName").toString() + "%') AND ");
			sqlCount.append(" PI.START_USER_ID_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("startUserName").toString() + "%') AND ");
		}
		
		if(map.get("startUserId") != null && !StringUtil.isNullOrEmpty(map.get("startUserId").toString())){
			//sqlList.append(" PI.START_USER_ID_ ='" + map.get("startUserId").toString() + "' AND ");
			sqlCount.append(" PI.START_USER_ID_ ='" + map.get("startUserId").toString() + "' AND ");
		}
		
		//任务委托人
		if(map.get("assigneeUserName") != null && !StringUtil.isNullOrEmpty(map.get("assigneeUserName").toString())){
			//sqlList.append(" TI.ASSIGNEE_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("assigneeUserName").toString() + "%') AND ");
			sqlCount.append(" TI.ASSIGNEE_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("assigneeUserName").toString() + "%') AND ");
		}
		
		if(map.get("assigneeUserId") != null && !StringUtil.isNullOrEmpty(map.get("assigneeUserId").toString())){
			//sqlList.append(" TI.ASSIGNEE_ ='" + map.get("assigneeUserId").toString() + "' AND ");
			sqlCount.append(" TI.ASSIGNEE_ ='" + map.get("assigneeUserId").toString() + "' AND");
		}
		//任务拥有人
		if(map.get("ownerUserName") != null && !StringUtil.isNullOrEmpty(map.get("ownerUserName").toString())){
			//sqlList.append(" TI.OWNER_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("ownerUserName").toString() + "%') AND ");
			sqlCount.append(" TI.OWNER_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("ownerUserName").toString() + "%') AND ");
		}
		
		if(map.get("ownerUserId") != null && !StringUtil.isNullOrEmpty(map.get("ownerUserId").toString())){
			//sqlList.append(" TI.OWNER_ ='" + map.get("ownerUserId").toString() + "' AND ");
			sqlCount.append(" TI.OWNER_ ='" + map.get("ownerUserId").toString() + "' AND");
		}
		
		//任务签收人
		if(map.get("claimUserName") != null && !StringUtil.isNullOrEmpty(map.get("claimUserName").toString())){
			//sqlList.append(" (TI.ASSIGNEE_ IS NULL AND LINK.TYPE_ = 'candidate' AND LINK.USER_ID_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("claimUserName").toString() + "%')) AND ");
			sqlCount.append(" (TI.ASSIGNEE_ IS NULL AND LINK.TYPE_ = 'candidate' AND LINK.USER_ID_ IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + map.get("claimUserName").toString() + "%')) AND ");
		}
		
		if(map.get("claimUserId") != null && !StringUtil.isNullOrEmpty(map.get("claimUserId").toString())){
			//sqlList.append(" (TI.ASSIGNEE_ IS NULL AND LINK.TYPE_ = 'candidate' AND LINK.USER_ID_ ='" + map.get("claimUserId").toString() + "') AND ");
			sqlCount.append(" (TI.ASSIGNEE_ IS NULL AND LINK.TYPE_ = 'candidate' AND LINK.USER_ID_ ='" + map.get("claimUserId").toString() + "') AND ");
		}
		
		if(sqlCount.lastIndexOf("AND") > 0){
			//sqlList = new StringBuilder(sqlList.substring(0, sqlList.lastIndexOf("AND")));
			sqlCount = new StringBuilder(sqlCount.substring(0, sqlCount.lastIndexOf("AND")));
			
			//sqlList.insert(sqlListIndex, " WHERE ");
			sqlCount.insert(sqlCountIndex, " WHERE ");
		}
		
		/*if(!StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			sqlList.append(" ORDER BY " + pageView.getOrderBy() + " " + pageView.getOrder());
			sqlCount.append(" ORDER BY " + pageView.getOrderBy() + " " + pageView.getOrder());
		}*/
		sqlCount.append(") AS T");
		
		//pageView.setResults(queryList.sql(sqlList.toString()).listPage((pageView.getPageNo() - 1) * pageView.getPageSize(), pageView.getPageSize()));
		//pageView.setTotalCount(queryCount.sql(sqlCount.toString()).count());
		
		return (int)queryCount.sql(sqlCount.toString()).count();
	}
	
	@Override
	public Map<String, Object> viewBusinessByTask(String taskId) {
		HistoricTaskInstance hiTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		return viewBusiness(hiTask.getProcessInstanceId());
	}
	
	@Override
	public Map<String, Object> viewBusiness(String processInstanceId) {
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().
				processInstanceId(processInstanceId).singleResult();
		String processDefinitionKey = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(hpi.getProcessDefinitionId()).singleResult().getKey();
		BpmConfigBasis bpmConfigBasis = bpmConfigBasisService.getByBpmKey(processDefinitionKey);
		
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> basisValueMap = basisSubstanceService.getBasisValueMap(hpi.getBusinessKey());
		basisValueMap.put("processInstanceId", processInstanceId);
		basisValueMap.put("title", bpmConfigBasis.getBasisSubstanceType().getTypeName());
		map.put("item", basisValueMap);
		map.put("attributes", basisAttributeService.getByBasisSubstanceTypeId(bpmConfigBasis.getBasisSubstanceType().getId()));
		
		if(StringUtil.isNullOrEmpty(bpmConfigBasis.getConfigPrimeUrl())){
			map.put("url", "/content/common/common-business-input");
		}else{
			map.put("url", bpmConfigBasis.getConfigPrimeUrl());
		}
		
		
		/*
		String processDefinitionKey = repositoryService.createProcessDefinitionQuery().processDefinitionId(hpi.getProcessDefinitionId()).singleResult().getKey();
		
		BpmConfigBusiness bpmBusiness = bpmConfigBusinessService.getByBpmKey(processDefinitionKey);
		Map<String, Object> item = normalEntityService.getById(businessKey, bpmBusiness.getBusinessClass().getId());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", bpmBusiness.getBusinessClass().getClsName());
		map.put("item", item);
		map.put("clsId", bpmBusiness.getBusinessClass().getId());
		map.put("controllerId", bpmBusiness.getBusinessController().getId());
		map.put("attributes", normalEntityService.getAvailableAttribute(bpmBusiness.getBusinessClass().getId()));
		if(bpmBusiness.getBusinessInputUrl() == null || bpmBusiness.getBusinessInputUrl().equals("")){
			map.put("url", "/content/common/common-business-view");
		}else{
			map.put("url", bpmBusiness.getBusinessInputUrl());
		}*/
		
		return map;
	}
	

	@SuppressWarnings("deprecation")
	@Override
	public List<HistoricTaskInstance> viewLog(String processInstanceId) {
		return historyService.createHistoricTaskInstanceQuery().
				processInstanceId(processInstanceId).orderByHistoricTaskInstanceStartTime().asc().list();
	}
	
	
	
	public Map<String, Object> exportActivePng(String processInstanceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取流程实例
		HistoricProcessInstance processInstance = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		// 获取模型
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance
				.getProcessDefinitionId());
		// 获取历史活动模块实例，按照流程开始时间正序。
		List<HistoricActivityInstance> activityInstances = historyService
				.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId)
				.orderByHistoricActivityInstanceStartTime().asc().list();

		List<String> activitiIds = new ArrayList<String>();
		List<String> flowIds = new ArrayList<String>();

		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processInstance
						.getProcessDefinitionId());
		// 获取流程走过的线
		flowIds = getHighLightedFlows(processDefinition, activityInstances);

		// 获取流程走过的节点
		for (HistoricActivityInstance hai : activityInstances) {
			activitiIds.add(hai.getActivityId());
		}
		// 设置processEngine配置
		Context.setProcessEngineConfiguration(((ProcessEngineImpl) processEngine)
				.getProcessEngineConfiguration());
		//修改 Version 5.16
		//InputStream inputStream = ProcessDiagramGenerator.generateDiagram(
				//bpmnModel, "png", activitiIds, flowIds);
		InputStream inputStream = new DefaultProcessDiagramGenerator().generateDiagram(
				bpmnModel, "png", activitiIds, flowIds);
		map.put("inputStream", inputStream);
		map.put("fileName", "png");

		return map;
	}


/**
	 * 20140122 活动节点与活动节点实例之间的关系
	 * 
	 * @param processDefinitionEntity
	 * @param historicActivityInstances
	 * @return
	 */
	public List<String> getHighLightedFlows(
			ProcessDefinitionEntity processDefinitionEntity,
			List<HistoricActivityInstance> historicActivityInstances) {
		List<String> highFlows = new ArrayList<String>();

		// 首先通过历史活动实例获取活动节点，放入List
		Map<ActivityImpl, HistoricActivityInstance> map = new HashMap<ActivityImpl, HistoricActivityInstance>();
		// 存放所有 的活动
		List<ActivityImpl> activityImpls = new ArrayList<ActivityImpl>();

		for (HistoricActivityInstance activityInstance : historicActivityInstances) {
			// 历史活动实例对应的Activity
			ActivityImpl activityImpl = processDefinitionEntity
					.findActivity(activityInstance.getActivityId());
			map.put(activityImpl, activityInstance);

			activityImpls.add(activityImpl);
		}

		for (ActivityImpl activityImpl : activityImpls) {
			List<PvmTransition> pvmTransitions = activityImpl
					.getOutgoingTransitions();
			for (PvmTransition pvmTransition : pvmTransitions) {
				ActivityImpl destActivityImpl = (ActivityImpl) pvmTransition
						.getDestination();
				// 活动节点的目的活动节点也在活动节点list中，说明他们之间的线有可能经历过，但是结束时间与开始时间一致，那么就一定经历过。
				if (activityImpls.contains(destActivityImpl)) {
					HistoricActivityInstance startHistoricActivityInstance = map
							.get(activityImpl);
					HistoricActivityInstance destHistoricActivityInstance = map
							.get(destActivityImpl);
					// 两个活动节点的结束时间与开始时间一致，那么就一定经历过。
					if (startHistoricActivityInstance.getEndTime() != null
							&& (startHistoricActivityInstance.getEndTime())
									.equals(destHistoricActivityInstance
											.getStartTime())) {
						highFlows.add(pvmTransition.getId());
					} else if (startHistoricActivityInstance.getEndTime() != null
							&& destHistoricActivityInstance.getEndTime() != null) {
						highFlows.add(pvmTransition.getId());
					}
				}
			}
		}

		return highFlows;
	}


}
