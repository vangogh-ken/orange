package com.van.halley.bpm.activiti.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ManagementService;
import org.activiti.engine.impl.interceptor.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.van.halley.bpm.cmd.HistoryProcessInstanceDiagramCmd;
import com.van.halley.bpm.cmd.ProcessDefinitionDetailsCmd;
import com.van.halley.bpm.cmd.ProcessInstanceDetailsCmd;
import com.van.halley.bpm.service.ProcessDefinitionOperateService;

@Controller
@RequestMapping(value = "/bpm/")
public class GraphController {
	@Autowired
	private ManagementService managementService;
	@Autowired
	private ProcessDefinitionOperateService processDefinitionOperateService;

	//========================流程实时图===========================
	@RequestMapping(value = "bpm-process-instance-graph")
	public String pageInstance(Model model, String processInstanceId){
		model.addAttribute("processInstanceId", processInstanceId);
		return "/content/bpm/bpm-process-instance-graph";
	}
	
	@RequestMapping(value = "bpm-process-instance-png")
	public void activePNG(String processInstanceId,
			HttpServletResponse response) throws IOException {
		Command<InputStream> cmd = new HistoryProcessInstanceDiagramCmd(
				processInstanceId);
		InputStream is = managementService.executeCommand(cmd);
		response.setContentType("image/png");

		int len = 0;
		byte[] b = new byte[1024];

		while ((len = is.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}
	
	@RequestMapping(value = "bpm-process-instance-trace")
	@ResponseBody
	public Map<String, Object> instanceTrace(Model model, String processInstanceId){
		Command<Map<String, Object>> cmd = 
				new ProcessInstanceDetailsCmd(processInstanceId);
		Map<String, Object> map = 
				managementService.executeCommand(cmd);
		return map;
	}
	
	
	//========================流程定义图===========================
	@RequestMapping(value = "bpm-process-definition-graph")
	public String pageDefinition(Model model, String processDefinitionKey){
		model.addAttribute("processDefinitionKey", processDefinitionKey);
		return "/content/bpm/bpm-process-definition-graph";
	}
	
	@RequestMapping(value = "bpm-process-definition-png")
	public void readResource(String processDefinitionKey, HttpServletResponse response)
			throws IOException {
		Map<String, Object> map = processDefinitionOperateService.getResource(processDefinitionKey, "PNG");
		InputStream in = (InputStream) map.get("inputStream");
		
		response.setContentType("image/png");
		int len = 0;
		byte[] b = new byte[1024];
		while ((len = in.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}
	
	@RequestMapping(value = "bpm-process-definiton-trace")
	@ResponseBody
	public Map<String, Object> definitonTrace(Model model, String processDefinitionKey){
		Command<Map<String, Object>> cmd = 
				new ProcessDefinitionDetailsCmd(processDefinitionKey);
		Map<String, Object> map = 
				managementService.executeCommand(cmd);
		return map;
	}
	
	
}
