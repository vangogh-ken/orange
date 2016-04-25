package com.van.halley.bpm.support;

import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.ObjectUtils;

import com.google.common.collect.Maps;
import com.van.core.spring.ApplicationContextHelper;

/**
 * 流程定义缓存
 * 
 * 将不能直接获取的，转化为静态数据，提高二次访问的效率。
 * 
 */
public class ProcessDefinitionCache {

	private static Map<String, ProcessDefinition> map = Maps.newHashMap();

	private static Map<String, List<ActivityImpl>> activities = Maps
			.newHashMap();

	private static Map<String, ActivityImpl> singleActivity = Maps.newHashMap();

	private static RepositoryService repositoryService;
	
	static{
		repositoryService = ApplicationContextHelper.getBean("repositoryService");
	}
	
	/*public static void setRepositoryService(RepositoryService repositoryService) {
		ProcessDefinitionCache.repositoryService = repositoryService;
	}*/
	
	//根据流程定义ID获取流程定义
	public static ProcessDefinition get(String processDefinitionId) {
		/*if(repositoryService == null){
			repositoryService = ApplicationContextHelper.getBean("repositoryService");
			setRepositoryService(repositoryService);
		}*/
		
		ProcessDefinition processDefinition = map.get(processDefinitionId);
		if (processDefinition == null) {
			processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(processDefinitionId);
			if (processDefinition != null) {
				put(processDefinitionId, processDefinition);
			}
		}
		return processDefinition;
	}

	//流程定义ID 与 Activity
	public static void put(String processDefinitionId,
			ProcessDefinition processDefinition) {
		map.put(processDefinitionId, processDefinition);
		ProcessDefinitionEntity pde = (ProcessDefinitionEntity) processDefinition;
		activities.put(processDefinitionId, pde.getActivities());
		for (ActivityImpl activityImpl : pde.getActivities()) {
			singleActivity.put(
					processDefinitionId + "_" + activityImpl.getId(),
					activityImpl);
		}
	}

	public static ActivityImpl getActivity(String processDefinitionId,
			String activityId) {
		ProcessDefinition processDefinition = get(processDefinitionId);
		if (processDefinition != null) {
			ActivityImpl activityImpl = singleActivity.get(processDefinitionId
					+ "_" + activityId);
			if (activityImpl != null) {
				return activityImpl;
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public static String getActivityName(String processDefinitionId,
			String activityId) {
		ActivityImpl activity = getActivity(processDefinitionId, activityId);
		if (activity != null) {
			return ObjectUtils.toString(activity.getProperty("name"));
		}
		return null;
	}
	
	/**
	 * 根据流程实例获取当前正在执行的activity
	 * @param pi
	 * @return
	 */
	public static String getCurrentActivityName(ProcessInstance pi){
		String processDefinitionId = pi.getProcessDefinitionId();
		String activityId = pi.getActivityId();
		
		return getActivityName(processDefinitionId, activityId);
	}
	
	/**
	 * 通过PDid 获取流程名称
	 * @param processDefinitionId
	 * @return
	 */
	public static String getProcessName(String processDefinitionId){
		ProcessDefinition processDefinition = get(processDefinitionId);
		if(processDefinition != null){
			return processDefinition.getName();
		}
		
		return null;
	}
	
	/**
	 * 获取流程KEY
	 * @param processDefinitionId
	 * @return
	 */
	public static String getProcessKey(String processDefinitionId){
		ProcessDefinition processDefinition = get(processDefinitionId);
		if(processDefinition != null){
			return processDefinition.getKey();
		}
		
		return null;
	}
}
