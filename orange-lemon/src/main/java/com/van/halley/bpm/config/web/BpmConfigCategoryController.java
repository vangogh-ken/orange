package com.van.halley.bpm.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.BpmConfigCategory;
import com.van.service.BpmConfigCategoryService;

@Controller
@RequestMapping(value = "/bpm/")
public class BpmConfigCategoryController {
	
	@Autowired
	private BpmConfigCategoryService bpmConfigCategoryService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "bpm-config-category-list")
	public String queryModel(Model model, BpmConfigCategory bpmConfigCategory, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		bpmConfigCategoryService.query(pageView, bpmConfigCategory);
		model.addAttribute("pageView", pageView);
		return "/content/bpm/bpm-config-category-list";
	}

	@RequestMapping(value = "bpm-config-category-input")
	public String input(Model model, String id) {
		BpmConfigCategory item = null;
		if(id != null){
			item = bpmConfigCategoryService.getById(id);
		}
		
		model.addAttribute("item", item);
		return "/content/bpm/bpm-config-category-input";
	}

	@RequestMapping(value = "bpm-config-category-save")
	public String addModel(BpmConfigCategory bpmConfigCategory) {
		if(bpmConfigCategory.getId() != null){
			bpmConfigCategoryService.modify(bpmConfigCategory);
		}else{
			bpmConfigCategoryService.add(bpmConfigCategory);
		}
		
		return "redirect: bpm-config-category-list.do";
	}

	@RequestMapping(value = "bpm-config-category-remove")
	public String delete(Model model, String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			bpmConfigCategoryService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:bpm-config-category-list.do";
	}

}
