package com.van.halley.salary.web;


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
import com.van.halley.db.persistence.entity.SalaryBasic;
import com.van.service.SalaryBasicService;

@Controller
@RequestMapping(value = "/salary/")
public class SalaryBasicController {
	@Autowired
	private SalaryBasicService salaryBasicService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "salary-basic-list")
	public String list(Model model, SalaryBasic salaryBasic, @ModelAttribute PageView<SalaryBasic> pageView) {
		if (pageView == null) {
			pageView = new PageView<SalaryBasic>(1);
		}
		pageView = salaryBasicService.query(pageView, salaryBasic);

		model.addAttribute("pageView", pageView);
		return "/content/salary/salary-basic-list";
	}

	@RequestMapping(value = "salary-basic-input")
	public String input(Model model, String id) {
		SalaryBasic item = null;
		if (id != null) {
			item = salaryBasicService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/salary/salary-basic-input";
	}

	@RequestMapping(value = "salary-basic-save", method = RequestMethod.POST)
	public String add(SalaryBasic salaryBasic, RedirectAttributes redirectAttributes) {
		if(salaryBasic.getId() == null){
			salaryBasicService.add(salaryBasic);
		}else{
			salaryBasicService.modify(salaryBasic);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:salary-basic-list.do";
	}
	
	@RequestMapping(value = "salary-basic-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			salaryBasicService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:salary-basic-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "salary-basic-check-cron-name")
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
	@RequestMapping(value = "salary-basic-check-cron-expression")
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
