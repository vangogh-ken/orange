package com.van.halley.quartz.web;


import javax.annotation.PostConstruct;

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
import com.van.halley.cycle.sample.CycleJobSample;
import com.van.halley.db.persistence.entity.QuartzTask;
import com.van.service.QuartzGroupService;
import com.van.service.QuartzTaskService;

@Controller
@RequestMapping(value = "/quartz/")
public class QuartzTaskController {
	@Autowired
	private QuartzTaskService quartzTaskService;
	@Autowired
	private QuartzGroupService quartzGroupService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;
	
	@Autowired
	private CycleFacade cycleFacade;

	@PostConstruct
	public void init(){
		//cycleFacade.run(new CycleJobSample());
	}
	
	@RequestMapping(value = "quartz-task-list")
	public String list(Model model, QuartzTask quartzTask, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = quartzTaskService.query(pageView, quartzTask);

		model.addAttribute("pageView", pageView);
		return "/content/quartz/quartz-task-list";
	}

	@RequestMapping(value = "quartz-task-input")
	public String input(Model model, String id) {
		QuartzTask item = null;
		if (id != null) {
			item = quartzTaskService.getById(id);
		}
		model.addAttribute("quartzGroups", quartzGroupService.getAll());
		model.addAttribute("item", item);
		return "/content/quartz/quartz-task-input";
	}

	@RequestMapping(value = "quartz-task-save", method = RequestMethod.POST)
	public String add(QuartzTask quartzTask, @RequestParam String quartzGroupId, RedirectAttributes redirectAttributes) {
		quartzTask.setQuartzGroup(quartzGroupService.getById(quartzGroupId));
		if(quartzTask.getId() == null){
			quartzTaskService.add(quartzTask);
		}else{
			quartzTaskService.modify(quartzTask);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:quartz-task-list.do";
	}
	
	@RequestMapping(value = "quartz-task-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			quartzTaskService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:quartz-task-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "quartz-task-check-task-name")
	public boolean checkName(@RequestParam(required=true, value="taskName") String taskName, String id) {
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
	
	@ResponseBody
	@RequestMapping(value = "quartz-task-check-task-key")
	public boolean checkKey(@RequestParam(required=true, value="taskKey") String taskKey, String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM QUARTZ_TASK WHERE TASK_KEY = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, taskKey, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM QUARTZ_TASK WHERE TASK_KEY = ? ";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, taskKey);
			return count == 0;
		}
	}

}
