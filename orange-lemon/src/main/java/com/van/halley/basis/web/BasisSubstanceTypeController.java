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
import com.van.halley.db.persistence.entity.BasisSubstanceType;
import com.van.service.BasisSubstanceTypeService;

@Controller
@RequestMapping(value = "/basis/")
public class BasisSubstanceTypeController {
	@Autowired
	private BasisSubstanceTypeService basisSubstanceTypeService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "basis-substance-type-list")
	public String unread(Model model, BasisSubstanceType basisSubstanceType, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = basisSubstanceTypeService.query(pageView, basisSubstanceType);

		model.addAttribute("pageView", pageView);
		return "/content/basis/basis-substance-type-list";
	}

	@RequestMapping(value = "basis-substance-type-input")
	public String input(Model model, String id) {
		BasisSubstanceType item = null;
		if (id != null) {
			item = basisSubstanceTypeService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/basis/basis-substance-type-input";
	}

	@RequestMapping(value = "basis-substance-type-save", method = RequestMethod.POST)
	public String add(BasisSubstanceType basisSubstanceType, RedirectAttributes redirectAttributes) {
		if(basisSubstanceType.getId() == null){
			basisSubstanceTypeService.add(basisSubstanceType);
		}else{
			basisSubstanceTypeService.modify(basisSubstanceType);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:basis-substance-type-list.do";
	}
	
	@RequestMapping(value = "basis-substance-type-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			basisSubstanceTypeService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:basis-substance-type-list.do";
	}
}
