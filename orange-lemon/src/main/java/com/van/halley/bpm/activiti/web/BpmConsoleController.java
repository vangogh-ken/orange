package com.van.halley.bpm.activiti.web;

import java.io.InputStream;
import java.util.Map;

import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.bpm.cmd.JumpCmd;
import com.van.halley.bpm.cmd.ListActivityCmd;
import com.van.halley.bpm.cmd.MigrateCmd;
import com.van.halley.bpm.cmd.UpdateProcessCmd;
import com.van.halley.core.util.IoUtils;
import com.van.halley.core.util.MessageHelper;

@Controller
@RequestMapping(value = "/bpm/")
public class BpmConsoleController {
	@Autowired
	private ManagementService managementService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private MessageHelper messageHelper;
	
	@RequestMapping("console-prepare-jump")
    public String prepareJump(@RequestParam("executionId") String executionId,
            Model model) {
        Command<Map<String, String>> cmd = new ListActivityCmd(executionId);

        Map<String, String> activityMap = managementService.executeCommand(cmd);

        model.addAttribute("activityMap", activityMap);
        model.addAttribute("executionId", executionId);
        return "/content/bpm/console-prepare-jump";
    }
	
	@RequestMapping("console-jump")
    public String jump(@RequestParam("executionId") String executionId,
            @RequestParam("activityId") String activityId) {
        Command<Object> cmd = new JumpCmd(executionId, activityId);

        managementService.executeCommand(cmd);

        return "redirect:bpm-task-instance-list.do";
    }
	
	
	/**
     * 更新流程之前，填写xml.
     */
	@RequestMapping("console-update-process-byxml")
    public String beforeUpdateProcess(@RequestParam("processDefinitionId") String processDefinitionId,
            Model model) throws Exception {
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(
                        processDefinitionId);
        InputStream is = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                        processDefinition.getResourceName());
        String xml = IoUtils.readString(is);

        model.addAttribute("xml", xml);
        model.addAttribute("processDefinitionId", processDefinitionId);
        return "/content/bpm/console-update-process-byxml";
    }
	
	/**
     * 更新流程，不生成新版本.
     */
    @RequestMapping("console-update-process-save")
    public String doUpdateProcess(
            @RequestParam("processDefinitionId") String processDefinitionId,
            @RequestParam("xml") String xml,
            RedirectAttributes redirectAttributes) throws Exception {
        byte[] bytes = xml.getBytes("utf-8");
        UpdateProcessCmd updateProcessCmd = new UpdateProcessCmd(processDefinitionId, bytes);
        managementService.executeCommand(updateProcessCmd);
        messageHelper.addFlashMessage(redirectAttributes, "操作成功");
        return "redirect:bpm-process-definition-list.do";
    }
    
    
    /**
     * 准备迁移流程.
     */
    @RequestMapping("console-migrate-input")
    public String migrateInput(
            @RequestParam("processInstanceId") String processInstanceId,
            Model model) {
        model.addAttribute("processInstanceId", processInstanceId);
        model.addAttribute("processDefinitions", repositoryService.createProcessDefinitionQuery().latestVersion().list());

        return "/content/bpm/console-migrate-input";
    }

    /**
     * 迁移流程实例.
     */
    @RequestMapping("console-migrate-save")
    public String migrateInput(
            @RequestParam("processInstanceId") String processInstanceId,
            @RequestParam("processDefinitionId") String processDefinitionId,
            RedirectAttributes redirectAttributes) {
    	managementService.executeCommand(new MigrateCmd(processInstanceId, processDefinitionId));
    	messageHelper.addFlashMessage(redirectAttributes, "操作成功");
    	return "redirect:bpm-process-instance-list.do";
    }
	
	 
}
