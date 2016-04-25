package com.van.halley.fre.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.FreightActionValue;
import com.van.service.FreightActionValueService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightActionValueController {
	@Autowired
	private FreightActionValueService freightActionValueService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-action-value-list")
	public String unread(Model model, FreightActionValue freightActionValue, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = freightActionValueService.query(pageView, freightActionValue);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-action-value-list";
	}

	@RequestMapping(value = "fre-action-value-input")
	public String input(Model model, String id) {
		FreightActionValue item = null;
		if (id != null) {
			item = freightActionValueService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-action-value-input";
	}

	@RequestMapping(value = "fre-action-value-save", method = RequestMethod.POST)
	public String add(FreightActionValue freightActionValue, RedirectAttributes redirectAttributes) {
		if(freightActionValue.getId() == null){
			freightActionValueService.add(freightActionValue);
		}else{
			freightActionValueService.modify(freightActionValue);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-action-value-list.do";
	}
	
	@RequestMapping(value = "fre-action-value-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightActionValueService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-action-value-list.do";
	}

}
