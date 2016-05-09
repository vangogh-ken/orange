package com.van.orange.item.web;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.van.halley.db.persistence.entity.BasisSubstance;
import com.van.halley.db.persistence.entity.ItemSubstance;
import com.van.service.ItemSubstanceService;

@Controller
@RequestMapping(value = "/item/")
public class ItemSubstanceController {
	@Autowired
	private ItemSubstanceService itemSubstanceService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "item-substance-list")
	public String unread(Model model, ItemSubstance itemSubstance, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = itemSubstanceService.query(pageView, itemSubstance);

		model.addAttribute("pageView", pageView);
		return "/content/item/item-substance-list";
	}

	@RequestMapping(value = "item-substance-input")
	public String input(Model model, String id) {
		ItemSubstance item = null;
		if (id != null) {
			item = itemSubstanceService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/item/item-substance-input";
	}

	@RequestMapping(value = "item-substance-save", method = RequestMethod.POST)
	public String add(ItemSubstance itemSubstance, RedirectAttributes redirectAttributes) {
		if(itemSubstance.getId() == null){
			itemSubstanceService.add(itemSubstance);
		}else{
			itemSubstanceService.modify(itemSubstance);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:item-substance-list.do";
	}
	
	@RequestMapping(value = "item-substance-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			itemSubstanceService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:item-substance-list.do";
	}
}
