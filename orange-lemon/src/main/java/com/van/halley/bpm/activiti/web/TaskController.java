package com.van.halley.bpm.activiti.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.van.halley.bpm.service.HistoricQueryService;
import com.van.halley.bpm.service.TaskOperateService;
import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.util.StringUtil;

/**
 * 待办任务，各种类型的任务查询和控制
 * 
 * @author Think
 * 
 */
@Controller
@RequestMapping(value = "/bpm/")
public class TaskController {
	@Autowired
	private MessageHelper messageHelper;
	@Autowired
	private TaskOperateService taskOperateService;
	@Autowired
	private HistoricQueryService historicQueryService;

	/*@RequestMapping(value = "bpm-task-onassigned-list")
	public String onassigned(Model model, @ModelAttribute PageView pageView, HttpServletRequest request) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		Map<String, Object> map = StringUtil.toSingleValueMap(request.getParameterMap());
		map.put("finished", "F");
		map.put("assigned", "T");
		historicQueryService.queryHistoricTaskInstance(pageView, map);
		model.addAttribute("pageView", pageView);
		return "/content/bpm/bpm-task-onassigned-list";
	}

	@RequestMapping(value = "bpm-task-unassigned-list")
	public String unassigned(Model model, @ModelAttribute PageView pageView, HttpServletRequest request)
			throws Exception {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		Map<String, Object> map = StringUtil.toSingleValueMap(request.getParameterMap());
		map.put("finished", "F");
		map.put("assigned", "F");
		historicQueryService.queryHistoricTaskInstance(pageView, map);
		model.addAttribute("pageView", pageView);
		return "/content/bpm/bpm-task-unassigned-list";
	}*/
	
	@RequestMapping(value = "bpm-task-instance-list")
	public String all(Model model, @ModelAttribute PageView pageView, HttpServletRequest request)
			throws Exception {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		Map<String, Object> map = StringUtil.toSingleValueMap(request.getParameterMap());
		//map.put("finished", "F");
		historicQueryService.queryHistoricTaskInstance(pageView, map);
		model.addAttribute("pageView", pageView);
		return "/content/bpm/bpm-task-instance-list";
	}
}
