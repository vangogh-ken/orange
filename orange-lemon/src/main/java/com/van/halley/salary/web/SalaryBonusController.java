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
import com.van.halley.db.persistence.entity.SalaryBonus;
import com.van.service.SalaryBonusService;

@Controller
@RequestMapping(value = "/salary/")
public class SalaryBonusController {
	@Autowired
	private SalaryBonusService salaryBonusService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "salary-bonus-list")
	public String list(Model model, SalaryBonus salaryBonus, @ModelAttribute PageView<SalaryBonus> pageView) {
		if (pageView == null) {
			pageView = new PageView<SalaryBonus>(1);
		}
		pageView = salaryBonusService.query(pageView, salaryBonus);

		model.addAttribute("pageView", pageView);
		return "/content/salary/salary-bonus-list";
	}

	@RequestMapping(value = "salary-bonus-input")
	public String input(Model model, String id) {
		SalaryBonus item = null;
		if (id != null) {
			item = salaryBonusService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/salary/salary-bonus-input";
	}

	@RequestMapping(value = "salary-bonus-save", method = RequestMethod.POST)
	public String add(SalaryBonus salaryBonus, RedirectAttributes redirectAttributes) {
		if(salaryBonus.getId() == null){
			salaryBonusService.add(salaryBonus);
		}else{
			salaryBonusService.modify(salaryBonus);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:salary-bonus-list.do";
	}
	
	@RequestMapping(value = "salary-bonus-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			salaryBonusService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:salary-bonus-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "salary-bonus-check-cron-name")
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
	@RequestMapping(value = "salary-bonus-check-cron-expression")
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
