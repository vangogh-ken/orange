package com.van.halley.bpm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.NativeModelQuery;
import org.activiti.engine.repository.NativeProcessDefinitionQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.van.halley.bpm.service.RepositoryQueryService;
import com.van.halley.core.page.PageView;
import com.van.halley.util.StringUtil;

@Service("repositoryQueryService")
public class RepositoryQueryServiceImpl implements RepositoryQueryService {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ManagementService managementService;

	@Override
	public PageView queryProcessDefinition(PageView pageView, Map<String, Object> map) {
		NativeProcessDefinitionQuery queryList = repositoryService.createNativeProcessDefinitionQuery();
		NativeProcessDefinitionQuery queryCount = repositoryService.createNativeProcessDefinitionQuery();
		StringBuilder sqlList = new StringBuilder();
		StringBuilder sqlCount = new StringBuilder();
		sqlList.append("SELECT PD.* FROM " + managementService.getTableName(ProcessDefinition.class) + " AS PD ");
		sqlCount.append("SELECT COUNT(1) FROM " + managementService.getTableName(ProcessDefinition.class) + " AS PD ");
		
		int sqlListIndex = sqlList.length();
		int sqlCountIndex = sqlCount.length();
		
		if(map.get("processDefinitionName") != null && !StringUtil.isNullOrEmpty(map.get("processDefinitionName").toString())){
			sqlList.append(" PD.NAME_ LIKE '%" + map.get("processDefinitionName").toString() + "%' AND ");
			sqlCount.append(" PD.NAME_ LIKE '%" + map.get("processDefinitionName").toString() + "%' AND ");
		}
		
		if(map.get("processDefinitionKey") != null && !StringUtil.isNullOrEmpty(map.get("processDefinitionKey").toString())){
			sqlList.append(" PD.KEY_ LIKE '%" + map.get("processDefinitionKey").toString() + "%' AND ");
			sqlCount.append(" PD.KEY_ LIKE '%" + map.get("processDefinitionKey").toString() + "%' AND ");
		}
		
		if(map.get("processDefinitionVersion") != null && !StringUtil.isNullOrEmpty(map.get("processDefinitionVersion").toString())){
			sqlList.append(" PD.VERSION_ = '" + map.get("processDefinitionVersion").toString() + "' AND ");
			sqlCount.append(" PD.VERSION_ = '" + map.get("processDefinitionVersion").toString() + "' AND ");
		}
		
		if("T".equals(map.get("suspended"))){
			sqlList.append(" PD.SUSPENSION_STATE_ = '0' AND ");
			sqlCount.append(" PD.SUSPENSION_STATE_ = '0' AND ");
		}else if("F".equals(map.get("suspended"))){
			sqlList.append(" PD.SUSPENSION_STATE_ = '1' AND ");
			sqlCount.append(" PD.SUSPENSION_STATE_ = '1' AND ");
		}
		
		//是否为最新版本
		if("T".equals(map.get("latest"))){
			sqlList.append(" PD.VERSION_ = (SELECT MAX(VERSION_) FROM ACT_RE_PROCDEF WHERE KEY_ = PD.KEY_ ) AND ");
			sqlCount.append(" PD.VERSION_ = (SELECT MAX(VERSION_) FROM ACT_RE_PROCDEF WHERE KEY_ = PD.KEY_ ) AND ");
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
	public PageView queryProcessModel(PageView pageView, Map<String, Object> map) {
		NativeModelQuery queryList = repositoryService.createNativeModelQuery();
		NativeModelQuery queryCount = repositoryService.createNativeModelQuery();
		StringBuilder sqlList = new StringBuilder();
		StringBuilder sqlCount = new StringBuilder();
		sqlList.append("SELECT M.* FROM " + managementService.getTableName(Model.class) + " AS M ");
		sqlCount.append("SELECT COUNT(1) FROM " + managementService.getTableName(Model.class) + " AS M ");
		
		int sqlListIndex = sqlList.length();
		int sqlCountIndex = sqlCount.length();
		
		if(map.get("modelXmlName") != null && !StringUtil.isNullOrEmpty(map.get("modelXmlName").toString())){
			sqlList.append(" M.NAME_ LIKE '%" + map.get("modelXmlName").toString() + "%' AND ");
			sqlCount.append(" M.NAME_ LIKE '%" + map.get("modelXmlName").toString() + "%' AND ");
		}
		
		if(map.get("modelKey") != null && !StringUtil.isNullOrEmpty(map.get("modelKey").toString())){
			sqlList.append(" M.KEY_ LIKE '%" + map.get("modelKey").toString() + "%' AND ");
			sqlCount.append(" M.KEY_ LIKE '%" + map.get("modelKey").toString() + "%' AND ");
		}
		
		if(map.get("modelVersion") != null && !StringUtil.isNullOrEmpty(map.get("modelVersion").toString())){
			sqlList.append(" M.VERSION_ = '" + map.get("modelVersion").toString() + "' AND ");
			sqlCount.append(" M.VERSION_ = '" + map.get("modelVersion").toString() + "' AND ");
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
	public List<String> getProcessKeys(){
		List<String> keys = new ArrayList<String>();
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().latestVersion().list();
		for(ProcessDefinition pd : list){
			keys.add(pd.getKey());
		}
		
		return keys;
	}
	
	@Override
	public List<ProcessDefinition> getProcessDefinitions(){
		return repositoryService.createProcessDefinitionQuery().list();
	}

	@Override
	public List<ProcessDefinition> getProcessDefinitionsLatestVerion() {
		return repositoryService.createProcessDefinitionQuery().latestVersion().active().list();
	}

}
