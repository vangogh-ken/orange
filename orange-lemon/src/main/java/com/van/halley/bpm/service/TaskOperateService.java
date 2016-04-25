package com.van.halley.bpm.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.impl.pvm.process.ActivityImpl;

public interface TaskOperateService {
	/**
	 * 获取业务数据
	 * @param taskId
	 * @return
	 */
	public String getBusinessKey(String taskId);

	/**
	 * 签收任务
	 * @param taskId
	 * @param userId
	 */
	public void claimTask(String taskId, String userId);
	
	/**
	 * 完成任务
	 * @param taskId
	 * @param request
	 */
	public boolean completeTask(String taskId, HttpServletRequest request);
	
	/**
	 * 获取下一个节点信息
	 * @param taskId
	 * @return
	 */
	public Map<String, String> getNextActivity(String taskId);
	/**
	 * 根据具体的流程判断此线是否会作为可选项
	 * @param activityAfterGateway
	 * @return
	 */
	public boolean canTransfer(ActivityImpl activityAfterGateway, String transitionName, String taskId);
}
