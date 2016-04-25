package com.van.halley.bpm.cmd;

import java.io.InputStream;
import java.util.Collections;

import org.activiti.bpmn.model.BpmnModel;
//import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.cmd.GetBpmnModelCmd;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;

public class ProcessDefinitionDiagramCmd implements Command<InputStream> {
	protected String processDefinitionId;

	public ProcessDefinitionDiagramCmd(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public InputStream execute(CommandContext commandContext) {
		GetBpmnModelCmd getBpmnModelCmd = new GetBpmnModelCmd(
				processDefinitionId);
		BpmnModel bpmnModel = getBpmnModelCmd.execute(commandContext);
		//修改 Version 5.16
		//InputStream is = ProcessDiagramGenerator.generateDiagram(bpmnModel,
				//"png", Collections.EMPTY_LIST);
		InputStream is = new DefaultProcessDiagramGenerator(10).generateDiagram(
				bpmnModel, "png", Collections.EMPTY_LIST);

		return is;
	}
}
