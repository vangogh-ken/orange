package com.van.halley.bpm.activiti.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ManagementService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.interceptor.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.bpm.cmd.DelegateTaskCmd;
import com.van.halley.bpm.cmd.RollbackTaskCmd;
import com.van.halley.bpm.cmd.WithdrawTaskCmd;
import com.van.halley.bpm.service.HistoricQueryService;
import com.van.halley.bpm.service.ProcessDefinitionOperateService;
import com.van.halley.bpm.service.ProcessInstanceOperateService;
import com.van.halley.bpm.service.RepositoryQueryService;
import com.van.halley.bpm.service.TaskOperateService;
import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.BpmConfigBasis;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.service.BasisSubstanceService;
import com.van.service.BpmConfigBasisService;
import com.van.service.BpmConfigCategoryService;
import com.van.service.UserService;

@Controller
@RequestMapping(value = "/bpm/")
public class WorkSpaceController {
	private static Logger LOG = LoggerFactory.getLogger(WorkSpaceController.class);
	
	@Autowired
	private ProcessInstanceOperateService processInstanceOperateService;
	@Autowired
	private TaskOperateService taskOperateService;
	@Autowired
	private ProcessDefinitionOperateService processDefinitionOperateService;
	@Autowired
	private BpmConfigBasisService bpmConfigBasisService;
	@Autowired
	private BpmConfigCategoryService bpmConfigCategoryService;
	@Autowired
	private MessageHelper messageHelper;
	@Autowired
	private ManagementService managementService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private UserService userService;
	@Autowired
	private BasisSubstanceService basisSubstanceService;
	@Autowired
	private HistoricQueryService historicQueryService;
	@Autowired
	private RepositoryQueryService repositoryQueryServiceImpl;

	/**
	 * @param processInstanceId
	 * @param type 流程完成之后查询图片的方法改变，因此此处作出区别
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "workspace-home")
	public String home(Model model, HttpServletRequest request) {
		String userId = ((User)request.getSession().getAttribute("userSession")).getId();
		model.addAttribute("categories", bpmConfigCategoryService.getAll());
		List<BpmConfigBasis> list = bpmConfigBasisService.getByUserId(userId);
		List<String> catagoryIds = new ArrayList<String>();
		for(BpmConfigBasis bpmConfigBasis : list){
			String catagoryId = bpmConfigBasis.getBpmConfigCategory().getId();
			if(!catagoryIds.contains(catagoryId)){
				catagoryIds.add(catagoryId);
			}
		}
		
		model.addAttribute("catagoryIds", catagoryIds);
		model.addAttribute("businessesUser", list);
		model.addAttribute("pds", repositoryQueryServiceImpl.getProcessDefinitionsLatestVerion());
		return "/content/bpm/workspace-home";
	}

	@RequestMapping(value = "workspace-process-definition-png")
	public void readResource(String key, HttpServletResponse response)
			throws IOException {
		Map<String, Object> map = processDefinitionOperateService.getResource(key, "PNG");
		InputStream in = (InputStream) map.get("inputStream");
		byte[] b = new byte[1024 * 50];
		int len = -1;
		while ((len = in.read(b, 0, 1024 * 50)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	//启动流程
	@RequestMapping(value = "workspace-start-by-key")
	public String start(Model model, String key, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("userSession");
		Map<String, Object> map = processInstanceOperateService.startProcessByKey(key, user.getId());
		model.addAttribute("item", map.get("item"));
		model.addAttribute("attributes", map.get("attributes"));
		return map.get("url").toString();
	}

	// 运行中的流程
	@RequestMapping(value = "worksapce-historic-process-running-list")
	public String running(Model model, PageView<HistoricProcessInstance> pageView,
			HttpServletRequest request) {
		if (pageView == null) {
			pageView = new PageView<HistoricProcessInstance>(1);
		}
		
		User user = (User) request.getSession().getAttribute("userSession");
		Map<String, Object> map = StringUtil.toSingleValueMap(request.getParameterMap());
		map.put("startUserId", user.getId());
		map.put("finished", "F");
		historicQueryService.queryHistoricProcessInstance(pageView, map);

		model.addAttribute("pageView", pageView);
		return "/content/bpm/worksapce-historic-process-running-list";
	}

	// 发起的流程
	@RequestMapping(value = "worksapce-historic-process-initiat-list")
	public String initiat(Model model, PageView<HistoricProcessInstance> pageView,
			HttpServletRequest request) {
		if (pageView == null) {
			pageView = new PageView<HistoricProcessInstance>(1);
		}

		User user = (User) request.getSession().getAttribute("userSession");
		Map<String, Object> map = StringUtil.toSingleValueMap(request.getParameterMap());
		map.put("startUserId", user.getId());
		historicQueryService.queryHistoricProcessInstance(pageView, map);
		
		model.addAttribute("pageView", pageView);
		return "/content/bpm/worksapce-historic-process-initiat-list";
	}

	// 参与的流程
	@RequestMapping(value = "worksapce-historic-process-involved-list")
	public String involved(Model model, PageView<HistoricProcessInstance> pageView,
			HttpServletRequest request) {
		if (pageView == null) {
			pageView = new PageView<HistoricProcessInstance>(1);
		}
		
		User user = (User) request.getSession().getAttribute("userSession");
		Map<String, Object> map = StringUtil.toSingleValueMap(request.getParameterMap());
		map.put("involvedUserId", user.getId());
		map.put("finished", "T");
		historicQueryService.queryHistoricProcessInstance(pageView, map);

		model.addAttribute("pageView", pageView);
		return "/content/bpm/worksapce-historic-process-involved-list";
	}

	// 完成的流程
	@RequestMapping(value = "worksapce-historic-process-finished-list")
	public String finished(Model model, PageView<HistoricProcessInstance> pageView,
			HttpServletRequest request) {
		if (pageView == null) {
			pageView = new PageView<HistoricProcessInstance>(1);
		}
		User user = (User) request.getSession().getAttribute("userSession");
		Map<String, Object> map = StringUtil.toSingleValueMap(request.getParameterMap());
		map.put("startUserId", user.getId());
		map.put("finished", "T");
		historicQueryService.queryHistoricProcessInstance(pageView, map);

		model.addAttribute("pageView", pageView);
		return "/content/bpm/worksapce-historic-process-finished-list";
	}

	//待签收任务
	@RequestMapping(value = "workspace-task-claim-list")
	public String claimTask(Model model, @ModelAttribute PageView<HistoricTaskInstance> pageView,
			HttpServletRequest request) throws Exception {
		if (pageView == null) {
			pageView = new PageView<HistoricTaskInstance>(1);
		}
		User user = (User) request.getSession().getAttribute("userSession");
		Map<String, Object> map = StringUtil.toSingleValueMap(request.getParameterMap());
		map.put("claimUserId", user.getId());
		map.put("finished", "F");
		historicQueryService.queryHistoricTaskInstance(pageView, map);
		
		model.addAttribute("pageView", pageView);
		return "/content/bpm/workspace-task-claim-list";
	}

	//待办
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
		historicQueryService.queryHistoricTaskInstance(pageView, map);
		model.addAttribute("pageView", pageView);
		return "/content/bpm/workspace-task-handle-list";
	}

	//已完成
	@RequestMapping(value = "workspace-task-finished-list")
	public String finishedTask(Model model, @ModelAttribute PageView<HistoricTaskInstance> pageView,
			HttpServletRequest request) throws Exception {
		if (pageView == null) {
			pageView = new PageView<HistoricTaskInstance>(1);
		}
		
		User user = (User) request.getSession().getAttribute("userSession");
		Map<String, Object> map = StringUtil.toSingleValueMap(request.getParameterMap());
		map.put("assigneeUserId", user.getId());
		map.put("finished", "T");
		historicQueryService.queryHistoricTaskInstance(pageView, map);
		
		//taskQueryService.finished(user.getId(), pageView);
		model.addAttribute("pageView", pageView);
		return "/content/bpm/workspace-task-finished-list";
	}
	
	//代理中 
	@RequestMapping(value = "workspace-task-delegate-list")
	public String delegateTasks(Model model, @ModelAttribute PageView<HistoricTaskInstance> pageView,
			HttpServletRequest request) throws Exception {
		if (pageView == null) {
			pageView = new PageView<HistoricTaskInstance>(1);
		}
		
		User user = (User) request.getSession().getAttribute("userSession");
		Map<String, Object> map = StringUtil.toSingleValueMap(request.getParameterMap());
		map.put("ownerUserId", user.getId());
		historicQueryService.queryHistoricTaskInstance(pageView, map);
		model.addAttribute("pageView", pageView);
		return "/content/bpm/workspace-task-delegate-list";
	}

	// 打开业务数据表单
	@RequestMapping(value = "workspace-task-handle-input")
	public String handleWithVariable(Model model, String taskId) {
		Map<String, Object> map = processInstanceOperateService.getBusinessByTaskId(taskId);
		model.addAttribute("item", map.get("item"));
		model.addAttribute("attributes", map.get("attributes"));
		return map.get("url").toString();
	}


	// 签收任务，跳转至待处理任务列表
	@RequestMapping(value = "workspace-task-claim")
	public String claim(String taskId, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		User user = (User) request.getSession().getAttribute("userSession");
		taskOperateService.claimTask(taskId, user.getId());
		messageHelper.addFlashMessage(redirectAttributes, "签收成功");
		return "redirect:workspace-task-claim-list.do";
	}
	
	/**
	 * 查看数据都使用这个mapping
	 */
	@RequestMapping(value = "bpm-view-business")
	public String viewBusiness(Model model, String processInstanceId){
		Map<String, Object> map = historicQueryService.viewBusiness(processInstanceId);
		model.addAttribute("readonly", "readonly");
		model.addAttribute("attributes", map.get("attributes"));
		model.addAttribute("title", map.get("title"));
		model.addAttribute("item", map.get("item"));
		return map.get("url").toString();
	}
	
	///////////////////////////获取下个节点信息///////////////////////////////
	@ResponseBody
	@RequestMapping(value = "bpm-business-next-activity")
	public Map<String, String> getNextActivityDetails(String taskId){
		return taskOperateService.getNextActivity(taskId);
	}
	
	///////////////////////////迁移到下个节点///////////////////////////////
	@ResponseBody
	@RequestMapping(value = "bpm-business-transact-next")
	public Map<String, String> transactToNext(String taskId){
		return taskOperateService.getNextActivity(taskId);
	}
	
	
	//**********************************工作流任务处理，特色处理方式****************************************//
	
	@RequestMapping(value = "workspace-task-withdraw")
	public String withdraw(String taskId, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		Command<Integer> cmd = new WithdrawTaskCmd(taskId);
		int result = managementService.executeCommand(cmd);
		if(result == 0){
			messageHelper.addFlashMessage(redirectAttributes, "撤销成功");
		}else if(result == 1){
			messageHelper.addFlashMessage(redirectAttributes, "撤销失败，流程已经结束");
		}else if(result == 2){
			messageHelper.addFlashMessage(redirectAttributes, "撤销失败，下发任务已经完成");
		}
		
		return "redirect:workspace-task-finished-list.do";
	}
	
	@RequestMapping(value = "workspace-task-rollback")
	public String rollback(String taskId, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		Command<Integer> cmd = new RollbackTaskCmd(taskId);
		int result = managementService.executeCommand(cmd);
		if(result == 0){
			messageHelper.addFlashMessage(redirectAttributes, "退回成功");
		}else if(result == 1){
			messageHelper.addFlashMessage(redirectAttributes, "退回失败，流程已经结束");
		}else if(result == 2){
			messageHelper.addFlashMessage(redirectAttributes, "退回失败，下发任务已经完成");
		}
		
		return "redirect:workspace-task-handle-list.do";
	}
	
	@RequestMapping(value = "workspace-task-change")
	public String change(String taskId, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		Command<Integer> cmd = new RollbackTaskCmd(taskId);
		int result = managementService.executeCommand(cmd);
		if(result == 0){
			messageHelper.addFlashMessage(redirectAttributes, "退回成功");
		}else if(result == 1){
			messageHelper.addFlashMessage(redirectAttributes, "退回失败，流程已经结束");
		}else if(result == 2){
			messageHelper.addFlashMessage(redirectAttributes, "退回失败，下发任务已经完成过");
		}
		
		return "redirect:workspace-task-handle-list.do";
	}
	

	/**
    * 转办.
    */
	@RequestMapping("workspace-task-prepare-delegate")
	public String prepareDelegate(Model model, @RequestParam("taskId") String taskId) {
		model.addAttribute("users", userService.getAll());
	    return "/content/bpm/workspace-task-prepare-delegate";
	}
	
    @RequestMapping("workspace-task-delegate")
    public String doDelegate(@RequestParam("taskId") String taskId,
            @RequestParam("attorney") String attorney) {
        DelegateTaskCmd cmd = new DelegateTaskCmd(taskId, attorney);
        managementService.executeCommand(cmd);

        return "redirect:workspace-task-handle-list.do";
    }
    
    /**
     * 协办.
     */
    @RequestMapping("workspace-task-prepare-assist")
	public String prepareAssist(Model model, @RequestParam("taskId") String taskId) {
    	model.addAttribute("users", userService.getAll());
	    return "/content/bpm/workspace-task-prepare-assist";
	}

    @RequestMapping("workspace-task-assist")
    public String assist(@RequestParam("taskId") String taskId,
            @RequestParam("attorney") String attorney) {
        taskService.delegateTask(taskId, attorney);

        return "redirect:workspace-task-handle-list.do";
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
