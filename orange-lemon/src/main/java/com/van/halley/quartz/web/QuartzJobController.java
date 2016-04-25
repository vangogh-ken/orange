package com.van.halley.quartz.web;


import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.cycle.core.CycleFacade;
import com.van.halley.db.persistence.entity.QuartzJob;
import com.van.halley.db.persistence.entity.User;
import com.van.service.QuartzCronService;
import com.van.service.QuartzJobService;
import com.van.service.QuartzTaskService;

@Controller
@RequestMapping(value = "/quartz/")
public class QuartzJobController {
	@Autowired
	private QuartzJobService quartzJobService;
	@Autowired
	private QuartzTaskService quartzTaskService;
	@Autowired
	private QuartzCronService quartzCronService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;
	@Autowired
	private CycleFacade cycleFacade;

	@RequestMapping(value = "quartz-job-list")
	public String list(Model model, QuartzJob quartzJob, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = quartzJobService.query(pageView, quartzJob);

		model.addAttribute("pageView", pageView);
		return "/content/quartz/quartz-job-list";
	}

	@RequestMapping(value = "quartz-job-input")
	public String input(Model model, String id) {
		QuartzJob item = null;
		if (id != null) {
			item = quartzJobService.getById(id);
		}
		model.addAttribute("quartzTasks", quartzTaskService.getAll());
		model.addAttribute("quartzCrons", quartzCronService.getAll());
		model.addAttribute("item", item);
		return "/content/quartz/quartz-job-input";
	}

	@RequestMapping(value = "quartz-job-save", method = RequestMethod.POST)
	public String add(QuartzJob quartzJob, 
			@RequestParam String quartzTaskId, 
			@RequestParam String quartzCronId, 
			HttpSession session,
			RedirectAttributes redirectAttributes) {
		
		quartzJob.setQuartzTask(quartzTaskService.getById(quartzTaskId));
		quartzJob.setQuartzCron(quartzCronService.getById(quartzCronId));
		quartzJob.setOwner((User)session.getAttribute("userSession"));
		
		boolean flag = true;
		String message = "操作成功";
		if(quartzJob.getStartTime() != null && quartzJob.getEndTime() != null && quartzJob.getStartTime().after(quartzJob.getEndTime())){
			flag = false;
			message = "操作失败";
		}
		if(quartzJob.getEndTime() != null && quartzJob.getEndTime().before(new Date())){
			flag = false;
			message = "操作失败";
		}
		
		if(flag){
			if(quartzJob.getId() == null){
				quartzJobService.add(quartzJob);
			}else{
				quartzJobService.modify(quartzJob);
			}
		}
		
		messageHelper.addFlashMessage(redirectAttributes, message);
		return "redirect:quartz-job-list.do";
	}
	
	@RequestMapping(value = "quartz-job-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			quartzJobService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:quartz-job-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "quartz-job-check-task-name")
	public boolean checkName(@RequestParam(required=true, value="taskName") String taskName,  String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM QUARTZ_TASK WHERE TASK_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, taskName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM QUARTZ_TASK WHERE TASK_NAME = ? ";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, taskName);
			return count == 0;
		}
	}
	
	@RequestMapping(value = "quartz-job-fire")
	public String fire(Model model, String id, RedirectAttributes redirectAttributes) {
		quartzJobService.fire(quartzJobService.getById(id));
		messageHelper.addFlashMessage(redirectAttributes,"操作成功");
		return "redirect:quartz-job-list.do";
	}
	
	@RequestMapping(value = "quartz-job-stop")
	public String stop(Model model, String id, RedirectAttributes redirectAttributes) {
		quartzJobService.stop(quartzJobService.getById(id));
		messageHelper.addFlashMessage(redirectAttributes,"操作成功");
		return "redirect:quartz-job-list.do";
	}
	
	@RequestMapping(value = "quartz-job-start")
	public String start(Model model, String id, RedirectAttributes redirectAttributes) {
		quartzJobService.start(quartzJobService.getById(id));
		messageHelper.addFlashMessage(redirectAttributes,"操作成功");
		return "redirect:quartz-job-list.do";
	}
	
	@RequestMapping(value = "quartz-job-pause")
	public String pause(Model model, String id, RedirectAttributes redirectAttributes) {
		quartzJobService.pause(quartzJobService.getById(id));
		messageHelper.addFlashMessage(redirectAttributes,"操作成功");
		return "redirect:quartz-job-list.do";
	}
	
	@RequestMapping(value = "quartz-job-resume")
	public String resume(Model model, String id, RedirectAttributes redirectAttributes) {
		quartzJobService.resume(quartzJobService.getById(id));
		messageHelper.addFlashMessage(redirectAttributes,"操作成功");
		return "redirect:quartz-job-list.do";
	}
}
