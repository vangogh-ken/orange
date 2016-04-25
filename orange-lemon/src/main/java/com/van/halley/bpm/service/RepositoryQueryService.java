package com.van.halley.bpm.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;

import com.van.halley.core.page.PageView;

public interface RepositoryQueryService {
	/**
	 * 查询流程定义
	 * @param pageView
	 * @param map
	 * @return
	 */
	public PageView queryProcessDefinition(PageView pageView, Map<String, Object> map);
	/**
	 * 查询流程模型
	 * @param pageView
	 * @param map
	 * @return
	 */
	public PageView queryProcessModel(PageView pageView, Map<String, Object> map);
	public List<String> getProcessKeys();
	public List<ProcessDefinition> getProcessDefinitions();
	public List<ProcessDefinition> getProcessDefinitionsLatestVerion();
}
