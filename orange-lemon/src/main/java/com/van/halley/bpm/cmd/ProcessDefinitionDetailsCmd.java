package com.van.halley.bpm.cmd;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cmd.GetBpmnModelCmd;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.van.core.spring.ApplicationContextHelper;
import com.van.halley.bpm.graph.CustomProcessDiagramGenerator;
import com.van.halley.bpm.support.BpmGraphDetailsUtils;

public class ProcessDefinitionDetailsCmd implements Command<Map<String, Object>> {
	private static Logger logger = LoggerFactory
			.getLogger(ProcessDefinitionDetailsCmd.class);
	private String processDefinitionKey;
	private CustomProcessDiagramGenerator customProcessDiagramGenerator;
	private static int minX = 0;
	private static int minY = 0;
	
	public ProcessDefinitionDetailsCmd(String processInstanceId){
		this.processDefinitionKey = processInstanceId;
		this.customProcessDiagramGenerator = new CustomProcessDiagramGenerator();
	}

	@Override
	public Map<String, Object> execute(CommandContext commandContext) {
		RepositoryService repositoryService = ApplicationContextHelper.getBean(RepositoryService.class);
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().
				processDefinitionKey(processDefinitionKey).latestVersion().singleResult();
		
		String processDefinitionId = pd.getId();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		
		//获取对图片的偏移量
		GetBpmnModelCmd getBpmnModelCmd = new GetBpmnModelCmd(processDefinitionId);
		BpmnModel bpmnModel = getBpmnModelCmd.execute(Context
				.getCommandContext());
		Point point = customProcessDiagramGenerator.getMinXAndMinY(bpmnModel);
		this.minX = point.x;
		this.minY = point.y;
		this.minX = (this.minX <= 5) ? 5 : this.minX;
		this.minY = (this.minY <= 5) ? 5 : this.minY;
		this.minX -= 5;
		this.minY -= 5;
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("positions", BpmGraphDetailsUtils.getActivityPositions(processDefinition, minX, minY));
		resultMap.put("details", BpmGraphDetailsUtils.getProcessDefinitionInfo(processDefinition));
		return resultMap;
	}

	
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
	
}
