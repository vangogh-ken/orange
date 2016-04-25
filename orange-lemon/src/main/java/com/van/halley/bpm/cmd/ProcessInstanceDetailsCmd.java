package com.van.halley.bpm.cmd;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cmd.GetBpmnModelCmd;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.van.core.spring.ApplicationContextHelper;
import com.van.halley.bpm.graph.CustomProcessDiagramGenerator;
import com.van.halley.bpm.support.BpmGraphDetailsUtils;

public class ProcessInstanceDetailsCmd implements Command<Map<String, Object>> {
	private static Logger logger = LoggerFactory
			.getLogger(ProcessInstanceDetailsCmd.class);
	private String processInstanceId;
	private CustomProcessDiagramGenerator customProcessDiagramGenerator;
	private static int minX = 0;
	private static int minY = 0;
	
	public ProcessInstanceDetailsCmd(String processInstanceId){
		this.processInstanceId = processInstanceId;
		this.customProcessDiagramGenerator = new CustomProcessDiagramGenerator();
	}

	@Override
	public Map<String, Object> execute(CommandContext commandContext) {
		RepositoryService repositoryService = ApplicationContextHelper.getBean(RepositoryService.class);
		HistoryService historyService = ApplicationContextHelper.getBean(HistoryService.class);
		
		////获取每个活动节点的坐标
		String processDefinitionId = historyService.createHistoricProcessInstanceQuery().
				processInstanceId(processInstanceId).singleResult().getProcessDefinitionId();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(processDefinitionId);
		
		//获取对图片的偏移量
		GetBpmnModelCmd getBpmnModelCmd = new GetBpmnModelCmd(
				processDefinitionId);
		BpmnModel bpmnModel = getBpmnModelCmd.execute(Context
				.getCommandContext());
		Point point = customProcessDiagramGenerator.getMinXAndMinY(bpmnModel);
		this.minX = point.x;
		this.minY = point.y;
		this.minX = (this.minX <= 5) ? 5 : this.minX;
		this.minY = (this.minY <= 5) ? 5 : this.minY;
		this.minX -= 5;
		this.minY -= 5;
		
		List<HistoricActivityInstance> historicActivities = historyService.createHistoricActivityInstanceQuery().
				processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();
		Map<String, String> details = BpmGraphDetailsUtils.getProcessInstanceInfo(historicActivities, processDefinition);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("positions", BpmGraphDetailsUtils.getActivityPositions(processDefinition, minX, minY));
		resultMap.put("details", details);
		return resultMap;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
}
