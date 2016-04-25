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
import com.van.halley.db.persistence.entity.WorkCalPart;
import com.van.service.WorkCalPartService;
import com.van.service.WorkCalRuleService;

@Controller
@RequestMapping(value = "/out/")
public class WorkCalPartController {
	@Autowired
	private WorkCalPartService workCalPartService;
	@Autowired
	private WorkCalRuleService workCalRuleService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "work-cal-part-list")
	public String unread(Model model, WorkCalPart workCalPart,
			@ModelAttribute PageView pageView, HttpServletRequest request,
			HttpServletResponse response) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = workCalPartService.query(pageView, workCalPart);

		model.addAttribute("pageView", pageView);
		return "/content/out/work-cal-part-list";
	}

	@RequestMapping(value = "work-cal-part-input")
	public String input(Model model, String id) {
		WorkCalPart item = null;
		if (id != null) {
			item = workCalPartService.getById(id);
		}
		model.addAttribute("workCalRules", workCalRuleService.getAll());
		model.addAttribute("item", item);
		return "/content/out/work-cal-part-input";
	}

	@RequestMapping(value = "work-cal-part-save", method = RequestMethod.POST)
	public String add(WorkCalPart workCalPart, String isDashboard, HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		if(workCalPart.getId() == null){
			workCalPartService.add(workCalPart);
		}else{
			workCalPartService.modify(workCalPart);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:work-cal-part-list.do";
		
	}
	
	@RequestMapping(value = "work-cal-part-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			workCalPartService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:work-cal-part-list.do";
	}
	
}
