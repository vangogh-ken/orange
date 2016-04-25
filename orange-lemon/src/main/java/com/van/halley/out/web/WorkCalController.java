package com.van.halley.out.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.WorkCalRule;
import com.van.service.WorkCalInfoService;
import com.van.service.WorkCalPartService;
import com.van.service.WorkCalRuleService;

@Controller
@RequestMapping(value = "/out/")
public class WorkCalController {
	public static final int STATUS_WEEK = 0;
    public static final int STATUS_HOLIDAY = 1;
    public static final int STATUS_HOLIDAY_TO_WORKDAY = 2;
    public static final int STATUS_WORKDAY_TO_HOLIDAY = 3;
    
	@Autowired
	private WorkCalInfoService workCalInfoService;
	@Autowired
	private WorkCalRuleService workCalRuleService;
	@Autowired
	private WorkCalPartService workCalPartService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "work-cal-view")
	public String unread(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		
		return "/content/out/work-cal-view";
	}
	
	@ResponseBody
	@RequestMapping(value = "work-cal-get")
	public Map<String, List<WorkCalRule>> get(){
		Map<String, List<WorkCalRule>> map = new HashMap<String, List<WorkCalRule>>();
		WorkCalRule filter = new WorkCalRule();
		//工作日
		filter.setStatus(STATUS_WEEK);
        List<WorkCalRule> weeks = workCalRuleService.queryForList(filter);
        //假期
        filter.setStatus(STATUS_HOLIDAY);
        List<WorkCalRule> holidays = workCalRuleService.queryForList(filter);
        //调班
        filter.setStatus(STATUS_HOLIDAY_TO_WORKDAY);
        List<WorkCalRule> workdays = workCalRuleService.queryForList(filter);
        //调休
        filter.setStatus(STATUS_WORKDAY_TO_HOLIDAY);
        List<WorkCalRule> extrdays = workCalRuleService.queryForList(filter);
        
        map.put("weeks", weeks);
        map.put("holidays", holidays);
        map.put("workdays", workdays);
        map.put("extrdays", extrdays);
		
		return map;
	}
	
}
