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
import com.van.halley.db.persistence.entity.ValueClass;
import com.van.service.ValueClassService;

@Controller
@RequestMapping(value = "/dic/")
public class ValueClassController {
	@Autowired
	private ValueClassService valueClassService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "value-class-list")
	public String unread(Model model, ValueClass valueClass, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = valueClassService.query(pageView, valueClass);

		model.addAttribute("pageView", pageView);
		return "/content/dic/value-class-list";
	}

	@RequestMapping(value = "value-class-input")
	public String input(Model model, String id) {
		ValueClass item = null;
		if (id != null) {
			item = valueClassService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/dic/value-class-input";
	}

	@RequestMapping(value = "value-class-save", method = RequestMethod.POST)
	public String add(ValueClass valueClass, RedirectAttributes redirectAttributes) {
		if(valueClass.getId() == null){
			valueClassService.add(valueClass);
		}else{
			valueClassService.modify(valueClass);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:value-class-list.do";
	}
	
	@RequestMapping(value = "value-class-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			valueClassService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:value-class-list.do";
	}

}
