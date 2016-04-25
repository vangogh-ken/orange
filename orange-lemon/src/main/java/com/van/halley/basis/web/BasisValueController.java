package com.van.halley.basis.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.BasisValue;
import com.van.service.BasisValueService;

@Controller
@RequestMapping(value = "/basis/")
public class BasisValueController {
	@Autowired
	private BasisValueService basisValueService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "basis-value-list")
	public String unread(Model model, BasisValue basisValue, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = basisValueService.query(pageView, basisValue);

		model.addAttribute("pageView", pageView);
		return "/content/basis/basis-value-list";
	}

	@RequestMapping(value = "basis-value-input")
	public String input(Model model, String id) {
		BasisValue item = null;
		if (id != null) {
			item = basisValueService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/basis/basis-value-input";
	}

	@RequestMapping(value = "basis-value-save", method = RequestMethod.POST)
	public String add(BasisValue basisValue, RedirectAttributes redirectAttributes) {
		if(basisValue.getId() == null){
			basisValueService.add(basisValue);
		}else{
			basisValueService.modify(basisValue);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:basis-value-list.do";
	}
	
	@RequestMapping(value = "basis-value-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			basisValueService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:basis-value-list.do";
	}
}
