package com.van.halley.out.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.WorkCalRule;
import com.van.service.WorkCalRuleService;
import com.van.service.WorkCalTypeService;

@Controller
@RequestMapping(value = "/out/")
public class WorkCalRuleController {
	@Autowired
	private WorkCalRuleService workCalRuleService;
	@Autowired
	private WorkCalTypeService workCalTypeService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "work-cal-rule-list")
	public String unread(Model model, WorkCalRule workCalRule,
			@ModelAttribute PageView pageView, HttpServletRequest request,
			HttpServletResponse response) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = workCalRuleService.query(pageView, workCalRule);

		model.addAttribute("pageView", pageView);
		return "/content/out/work-cal-rule-list";
	}

	@RequestMapping(value = "work-cal-rule-input")
	public String input(Model model, String id) {
		WorkCalRule item = null;
		if (id != null) {
			item = workCalRuleService.getById(id);
		}
		model.addAttribute("workCalTypes", workCalTypeService.getAll());
		model.addAttribute("item", item);
		return "/content/out/work-cal-rule-input";
	}

	@RequestMapping(value = "work-cal-rule-save", method = RequestMethod.POST)
	public String add(WorkCalRule workCalRule, String workCalTypeId, HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		
		workCalRule.setWorkCalType(workCalTypeService.getById(workCalTypeId));
		if(workCalRule.getId() == null){
			workCalRuleService.add(workCalRule);
		}else{
			workCalRuleService.modify(workCalRule);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:work-cal-rule-list.do";
		
	}
	
	@RequestMapping(value = "work-cal-rule-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			workCalRuleService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:work-cal-rule-list.do";
	}
	
}
