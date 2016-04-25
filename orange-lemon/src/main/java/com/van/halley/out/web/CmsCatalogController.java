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
import com.van.halley.db.persistence.entity.CmsCatalog;
import com.van.service.CmsCatalogService;

@Controller
@RequestMapping(value = "/cms/")
public class CmsCatalogController {
	@Autowired
	private CmsCatalogService cmsCatalogService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "cms-catalog-list")
	public String unread(Model model, CmsCatalog cmsCatalog,
			@ModelAttribute PageView pageView, HttpServletRequest request,
			HttpServletResponse response) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = cmsCatalogService.query(pageView, cmsCatalog);

		model.addAttribute("pageView", pageView);
		return "/content/cms/cms-catalog-list";
	}

	@RequestMapping(value = "cms-catalog-input")
	public String input(Model model, String id) {
		CmsCatalog item = null;
		if (id != null) {
			item = cmsCatalogService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/cms/cms-catalog-input";
	}

	@RequestMapping(value = "cms-catalog-save", method = RequestMethod.POST)
	public String add(CmsCatalog cmsCatalog, RedirectAttributes redirectAttributes) {
		if(cmsCatalog.getId() == null){
			cmsCatalogService.add(cmsCatalog);
		}else{
			cmsCatalogService.modify(cmsCatalog);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:cms-catalog-list.do";
	}
	
	@RequestMapping(value = "cms-catalog-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			cmsCatalogService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:cms-catalog-list.do";
	}

}
