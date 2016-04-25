package com.van.halley.bpm.activiti.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.bpm.service.HistoricQueryService;
import com.van.halley.bpm.service.ProcessInstanceOperateService;
import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.util.StringUtil;
import com.van.service.BpmConfigBasisService;

@Controller
@RequestMapping(value = "/bpm/")
public class ProcessInstanceController {
	@Autowired
	private BpmConfigBasisService bpmConfigBasisService;
	@Autowired
	private ProcessInstanceOperateService processInstanceOperateService;
	@Autowired
	private MessageHelper messageHelper;
	@Autowired
	private HistoricQueryService historicQueryService;

	// 查询现有的流程
	@RequestMapping(value = "bpm-process-instance-list")
	public String queryProcessInstance(Model model, String processDefinitionKey, 
			@ModelAttribute PageView pageView, HttpServletRequest request) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		//processInstanceQueryService.all(pageView);
		historicQueryService.queryHistoricProcessInstance(pageView, StringUtil.toSingleValueMap(request.getParameterMap()));
		model.addAttribute("bpmConfigBasises", bpmConfigBasisService.getAll());
		model.addAttribute("pageView", pageView);
		return "/content/bpm/bpm-process-instance-list";
	}

	@RequestMapping(value = "bpm-process-instance-remove")
	public String remove(String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			processInstanceOperateService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:bpm-process-instance-list.do";
	}

	@RequestMapping(value = "bpm-process-instance-delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		processInstanceOperateService.delete(id);
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:bpm-process-instance-list.do";
	}
	
	@RequestMapping(value = "bpm-process-log")
	public String viewLog(String processInstanceId, Model model) {
		List<HistoricTaskInstance> tasks = historicQueryService.viewLog(processInstanceId);
		model.addAttribute("tasks", tasks);
		return "/content/bpm/bpm-process-log";
	}
	
	@ResponseBody
	@RequestMapping(value = "bpm-process-instance-to-delete")
	public String delete(@RequestParam(value="processInstanceId", required=true)String processInstanceId) {
		processInstanceOperateService.delete(processInstanceId);
		return "success";
	}
}
