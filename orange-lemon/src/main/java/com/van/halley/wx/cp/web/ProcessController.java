package com.van.halley.wx.cp.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.history.HistoricTaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.van.halley.bpm.service.HistoricQueryService;
import com.van.halley.bpm.service.ProcessInstanceOperateService;
import com.van.halley.bpm.service.TaskOperateService;
import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.service.BasisSubstanceService;


@Controller
@RequestMapping(value = "/wx/")
public class ProcessController {
	private static Logger LOG = LoggerFactory.getLogger(ProcessController.class);
	@Autowired
	private ProcessInstanceOperateService processInstanceOperateService;
	@Autowired
	private BasisSubstanceService basisSubstanceService;
	@Autowired
	private TaskOperateService taskOperateService;
	@Autowired
	private HistoricQueryService historicQueryService;
	
	@RequestMapping(value = "proc_attendance")
	public String attendance(Model model, HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("userSession");
		Map<String, Object> map = processInstanceOperateService.startProcessByKey("attendanceMakeUp", user.getId());
		model.addAttribute("item", map.get("item"));
		model.addAttribute("attributes", map.get("attributes"));
		return "/content/wx/proc_attendance";
	}
	
	@RequestMapping(value = "proc_vacation")
	public String vacation(Model model, HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("userSession");
		Map<String, Object> map = processInstanceOperateService.startProcessByKey("vacation", user.getId());
		model.addAttribute("item", map.get("item"));
		model.addAttribute("attributes", map.get("attributes"));
		return "/content/wx/proc_vacation";
	}
	
	@RequestMapping(value = "workspace-task-handle-list")
	public String handleTask(Model model, @ModelAttribute PageView<HistoricTaskInstance> pageView,
			HttpServletRequest request) throws Exception {
		if (pageView == null) {
			pageView = new PageView<HistoricTaskInstance>(1);
		}
		User user = (User) request.getSession().getAttribute("userSession");
		Map<String, Object> map = StringUtil.toSingleValueMap(request.getParameterMap());
		map.put("assigneeUserId", user.getId());
		map.put("finished", "F");
		pageView.setOrder("DESC");
		pageView.setOrderBy("TI.START_TIME_");
		historicQueryService.queryHistoricTaskInstance(pageView, map);
		model.addAttribute("pageView", pageView);
		return "/content/wx/workspace-task-handle-list";
	}
	
	@ResponseBody
	@RequestMapping(value = "workspance-task-done-complete-task")
    public String completeTask(@RequestParam("basisSubstanceId") String basisSubstanceId, 
    		@RequestParam("taskId") String taskId, HttpServletRequest request) {
    	LOG.info("basisSubstanceId: {}, taskId: {}, activityId: {}", basisSubstanceId, taskId, request.getParameterValues("activityId"));
    	if(basisSubstanceService.donePrimeSubstance(basisSubstanceId, request)
    			&& taskOperateService.completeTask(taskId, request)){
    		return "success";
        }else{
        	return "error";
        }
    }
}
