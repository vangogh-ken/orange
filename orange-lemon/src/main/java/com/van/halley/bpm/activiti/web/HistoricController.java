package com.van.halley.bpm.activiti.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.van.halley.bpm.service.HistoricQueryService;

@Controller
@RequestMapping(value = "/bpm/")
public class HistoricController {
	@Autowired
	private HistoricQueryService historicQueryService;
	
	/*@RequestMapping(value="bpm-historic-task-finished-list")
	public String finishedHistoricTask(Model model, PageView pageView){
		if (pageView == null) {
			pageView = new PageView(1);
		}
		//historicQueryService.finishedHistoricTask(pageView);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("finished", "T");
		historicQueryService.queryHistoricTaskInstance(pageView, map);
		model.addAttribute("pageView", pageView);
		return "/content/bpm/bpm-historic-task-finished-list";
	}
	
	@RequestMapping(value="bpm-historic-task-unfinished-list")
	public String unfinishedHistoricTask(Model model, PageView pageView){
		if (pageView == null) {
			pageView = new PageView(1);
		}
		//historicQueryService.unfinishedHistoricTask(pageView);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("finished", "F");
		historicQueryService.queryHistoricTaskInstance(pageView, map);
		model.addAttribute("pageView", pageView);
		return "/content/bpm/bpm-historic-task-unfinished-list";
	}
	
	@RequestMapping(value="bpm-historic-process-finished-list")
	public String finishedHistoricProcess(Model model, PageView pageView, HttpServletRequest request){
		if (pageView == null) {
			pageView = new PageView(1);
		}
		Map<String, Object> map = StringUtil.toSingleValueMap(request.getParameterMap());
		map.put("finished", "T");
		historicQueryService.queryHistoricProcessInstance(pageView, map);
		model.addAttribute("pageView", pageView);
		return "/content/bpm/bpm-historic-process-finished-list";
	}
	
	@RequestMapping(value="bpm-historic-process-unfinished-list")
	public String unfinishedHistoricProcess(Model model, PageView pageView, HttpServletRequest request){
		if (pageView == null) {
			pageView = new PageView(1);
		}
		Map<String, Object> map = StringUtil.toSingleValueMap(request.getParameterMap());
		map.put("finished", "F");
		historicQueryService.queryHistoricProcessInstance(pageView, map);
		model.addAttribute("pageView", pageView);
		return "/content/bpm/bpm-historic-process-unfinished-list";
	}*/
}
