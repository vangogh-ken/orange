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
import com.van.halley.db.persistence.entity.WorkCalType;
import com.van.service.WorkCalTypeService;

@Controller
@RequestMapping(value = "/out/")
public class WorkCalTypeController {
	@Autowired
	private WorkCalTypeService workCalTypeService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "work-cal-type-list")
	public String unread(Model model, WorkCalType workCalType,
			@ModelAttribute PageView pageView, HttpServletRequest request,
			HttpServletResponse response) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = workCalTypeService.query(pageView, workCalType);

		model.addAttribute("pageView", pageView);
		return "/content/out/work-cal-type-list";
	}

	@RequestMapping(value = "work-cal-type-input")
	public String input(Model model, String id) {
		WorkCalType item = null;
		if (id != null) {
			item = workCalTypeService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/out/work-cal-type-input";
	}

	@RequestMapping(value = "work-cal-type-save", method = RequestMethod.POST)
	public String add(WorkCalType workCalType, String isDashboard, HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		if(workCalType.getId() == null){
			workCalTypeService.add(workCalType);
		}else{
			workCalTypeService.modify(workCalType);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:work-cal-type-list.do";
		
	}
	
	@RequestMapping(value = "work-cal-type-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			workCalTypeService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:work-cal-type-list.do";
	}
	
}
