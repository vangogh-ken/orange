package com.van.halley.auth.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.OrgType;
import com.van.service.OrgTypeService;

@Controller
@RequestMapping(value = "/org/")
public class OrgTypeController {
	
	@Autowired
	private OrgTypeService orgTypeService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "org-type-list")
	public String queryModel(Model model, OrgType orgType, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		orgTypeService.query(pageView, orgType);
		model.addAttribute("pageView", pageView);
		return "/content/org/org-type-list";
	}

	@RequestMapping(value = "org-type-input")
	public String input(Model model, String id) {
		OrgType item = null;
		if(id != null){
			item = orgTypeService.getById(id);
		}
		
		model.addAttribute("item", item);
		return "/content/org/org-type-input";
	}

	@RequestMapping(value = "org-type-save")
	public String addModel(OrgType orgType) {
		if(orgType.getId() != null){
			orgTypeService.modify(orgType);
		}else{
			orgTypeService.add(orgType);
		}
		
		return "redirect: org-type-list.do";
	}

	@RequestMapping(value = "org-type-remove")
	public String delete(Model model, String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			orgTypeService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:org-type-list.do";
	}

}
