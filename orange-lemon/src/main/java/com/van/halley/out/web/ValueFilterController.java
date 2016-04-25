package com.van.halley.out.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.ValueFilter;
import com.van.service.ValueClassService;
import com.van.service.ValueFilterService;

@Controller
@RequestMapping(value = "/dic/")
public class ValueFilterController {
	@Autowired
	private ValueFilterService valueFilterService;
	@Autowired
	private ValueClassService valueClassService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "value-filter-list")
	public String unread(Model model, ValueFilter valueFilter, String valueClassId, 
			@ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(valueClassId != null){
			valueFilter.setValueClass(valueClassService.getById(valueClassId));
		}
		pageView = valueFilterService.query(pageView, valueFilter);
		
		model.addAttribute("valueClasses", valueClassService.getAll());
		model.addAttribute("pageView", pageView);
		return "/content/dic/value-filter-list";
	}

	@RequestMapping(value = "value-filter-input")
	public String input(Model model, String id) {
		ValueFilter item = null;
		if (id != null) {
			item = valueFilterService.getById(id);
		}
		model.addAttribute("valueClasses", valueClassService.getAll());
		model.addAttribute("item", item);
		return "/content/dic/value-filter-input";
	}

	@RequestMapping(value = "value-filter-save", method = RequestMethod.POST)
	public String add(ValueFilter valueFilter, String valueClassId, RedirectAttributes redirectAttributes) {
		valueFilter.setValueClass(valueClassService.getById(valueClassId));
		if(valueFilter.getId() == null){
			valueFilterService.add(valueFilter);
		}else{
			valueFilterService.modify(valueFilter);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:value-filter-list.do";
	}
	
	@RequestMapping(value = "value-filter-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			valueFilterService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:value-filter-list.do";
	}

}
