package com.van.halley.bpm.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;

import com.van.halley.core.page.PageView;

public interface HistoricQueryService {
	/**
	 * 查询
	 * @param pageView
	 * @param map
	 * @return
	 */
	public PageView<HistoricProcessInstance> queryHistoricProcessInstance(PageView<HistoricProcessInstance> pageView, Map<String, Object> map);
	public int countHistoricProcessInstance(Map<String, Object> map);
	/**
	 * 查询
	 * @param pageView
	 * @param map
	 * @return
	 */
	public PageView<HistoricTaskInstance> queryHistoricTaskInstance(PageView<HistoricTaskInstance> pageView, Map<String, Object> map);
	public int countHistoricTaskInstance(Map<String, Object> map);
	
	/**
	 * 查询业务
	 * @param taskId
	 * @return
	 */
	public Map<String, Object> viewBusinessByTask(String taskId);
	/**
	 * 直接通过流程ID进行查找
	 */
	public Map<String, Object> viewBusiness(String processInstanceId);
	/**
	 * 查看历史节点
	 * @param processInstanceId
	 * @return
	 */
	public List<HistoricTaskInstance> viewLog(String processInstanceId);
}
