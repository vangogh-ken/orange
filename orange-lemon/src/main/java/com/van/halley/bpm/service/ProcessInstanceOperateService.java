package com.van.halley.bpm.service;

import java.util.List;
import java.util.Map;

public interface ProcessInstanceOperateService {
	public void deleteProcessInstance(String processInstanceId);

	public Map<String, Object> startProcessByKey(String key, String userId);
	
	public Map<String, Object> getBusinessByTaskId(String taskId);

	public void delete(String processInstanceId);
	
	public List<String> getStartAndFirstActivity(String processDefinitionKey);
}
