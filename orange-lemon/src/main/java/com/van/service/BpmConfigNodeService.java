package com.van.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BpmConfigNode;
import com.van.halley.db.persistence.entity.User;

public interface BpmConfigNodeService {
	public List<BpmConfigNode> getAll();

	public List<BpmConfigNode> queryForList(BpmConfigNode bpmConfigNode);

	public PageView<BpmConfigNode> query(PageView<BpmConfigNode> pageView, BpmConfigNode bpmConfigNode);

	public void add(BpmConfigNode bpmConfigNode);

	public void delete(String id);

	public void modify(BpmConfigNode bpmConfigNode);

	public BpmConfigNode getById(String id);
	
	public void deleteByPdId(String pdId);
	
	public BpmConfigNode getByPdIdAndTdKey(String pdId, String tdKey);
	
	//根据任务更新业务状态
	public void updateBasisSubstance(Task task);

	/**
	 * 设置类型状态
	 * @param bpmConfigNodeId
	 * @return
	 */
	public Map<String, Object> toConfigStatus(String bpmConfigNodeId);
	/**
	 * 完成设置类型状态
	 * @param bpmConfigNodeId
	 * @param basisStatusId
	 */
	public void doneConfigStatus(String bpmConfigNodeId, String basisStatusId);
	/**
	 * 设置权限
	 * @param bpmConfigNodeId
	 * @return
	 */
	public Map<String, Object> toConfigAuth(String bpmConfigNodeId);
	/**
	 * 完成设置权限
	 * @param bpmConfigNodeId
	 * @param authId
	 * @param authType
	 */
	public void doneConfigAuth(String bpmConfigNodeId, String authId, String authType);
	/**
	 * 删除权限
	 * @param bpmConfigNodeId
	 * @param authId
	 */
	public void doneDeleteAuth(String bpmConfigNodeId, String authId);
	/**
	 * 根据流程节点获取其权限用户
	 * @param bpmConfigNodeId 流程节点ID
	 * @param initiatorId 流程发起人ID
	 * @return
	 */
	public List<User> getAuthByBpmConfigNodeId(String bpmConfigNodeId, String initiatorId);
	/**
	 * 获取该节点配置的权限信息
	 * @param bpmConfigNodeId
	 * @return
	 */
	public List<String> getAuthNameByBpmConfigNodeId(String bpmConfigNodeId);
}
