package com.van.halley.quartz.web;


import org.quartz.CronExpression;
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
import com.van.halley.db.persistence.entity.QuartzCron;
import com.van.service.QuartzCronService;

@Controller
@RequestMapping(value = "/quartz/")
public class QuartzCronController {
	@Autowired
	private QuartzCronService quartzCronService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "quartz-cron-list")
	public String list(Model model, QuartzCron quartzCron, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = quartzCronService.query(pageView, quartzCron);

		model.addAttribute("pageView", pageView);
		return "/content/quartz/quartz-cron-list";
	}

	@RequestMapping(value = "quartz-cron-input")
	public String input(Model model, String id) {
		QuartzCron item = null;
		if (id != null) {
			item = quartzCronService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/quartz/quartz-cron-input";
	}

	@RequestMapping(value = "quartz-cron-save", method = RequestMethod.POST)
	public String add(QuartzCron quartzCron, RedirectAttributes redirectAttributes) {
		if(quartzCron.getId() == null){
			quartzCronService.add(quartzCron);
		}else{
			quartzCronService.modify(quartzCron);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:quartz-cron-list.do";
	}
	
	@RequestMapping(value = "quartz-cron-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			quartzCronService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:quartz-cron-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "quartz-cron-check-cron-name")
	public boolean checkName(@RequestParam(required=true, value="cronName") String cronName,  String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM QUARTZ_CRON WHERE CRON_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, cronName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM QUARTZ_CRON WHERE CRON_NAME = ? ";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, cronName);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "quartz-cron-check-cron-expression")
	public boolean checkExpression(@RequestParam(required=true, value="cronExpression") String cronExpression,  String id) {
		if(CronExpression.isValidExpression(cronExpression)){//校验表达式
			if(id != null){
				String sql = "SELECT COUNT(*) AS count FROM QUARTZ_CRON WHERE CRON_EXPRESSION = ? AND ID <> ?";
				int count = jdbcTemplate.queryForObject(sql, Integer.class, cronExpression, id);
				return count == 0;
			}else{
				String sql = "SELECT COUNT(*) AS count FROM QUARTZ_CRON WHERE CRON_EXPRESSION = ? ";
				int count = jdbcTemplate.queryForObject(sql, Integer.class, cronExpression);
				return count == 0;
			}
		}else{
			return false;
		}
	}

}
