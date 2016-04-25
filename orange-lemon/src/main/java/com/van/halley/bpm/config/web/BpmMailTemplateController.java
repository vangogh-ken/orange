package com.van.halley.bpm.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.BpmMailTemplate;
import com.van.service.BpmMailTemplateService;

@Controller
@RequestMapping(value = "/bpm/")
public class BpmMailTemplateController {
	@Autowired
	private BpmMailTemplateService bpmMailTemplateService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "bpm-mail-template-list")
	public String queryModel(Model model, BpmMailTemplate bpmMailTemplate, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		bpmMailTemplateService.query(pageView, bpmMailTemplate);
		model.addAttribute("pageView", pageView);
		return "/content/bpm/bpm-mail-template-list";
	}

	@RequestMapping(value = "bpm-mail-template-input")
	public String input(Model model, String id) {
		BpmMailTemplate item = null;
		if(id != null){
			item = bpmMailTemplateService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/bpm/bpm-mail-template-input";
	}

	@RequestMapping(value = "bpm-mail-template-save")
	public String addModel(BpmMailTemplate bpmMailTemplate) {
		if(bpmMailTemplate.getId() != null){
			bpmMailTemplateService.modify(bpmMailTemplate);
		}else{
			bpmMailTemplateService.add(bpmMailTemplate);
		}
		
		return "redirect:bpm-mail-template-list.do";
	}

	@RequestMapping(value = "bpm-mail-template-remove")
	public String delete(Model model, String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			bpmMailTemplateService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:bpm-mail-template-list.do";
	}

}
