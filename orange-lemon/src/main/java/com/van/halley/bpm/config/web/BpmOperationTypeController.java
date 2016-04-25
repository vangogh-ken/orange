package com.van.halley.bpm.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.BpmOperationType;
import com.van.service.BpmOperationTypeService;

@Controller
@RequestMapping(value = "/bpm/")
public class BpmOperationTypeController {
	@Autowired
	private BpmOperationTypeService bpmOperationTypeService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "bpm-operation-type-list")
	public String queryModel(BpmOperationType bpmOperationType, @ModelAttribute PageView pageView, Model model) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		bpmOperationTypeService.query(pageView, bpmOperationType);
		model.addAttribute("pageView", pageView);
		return "/content/bpm/bpm-operation-type-list";
	}

	@RequestMapping(value = "bpm-operation-type-input")
	public String input(Model model, String id) {
		BpmOperationType item = null;
		if(id != null){
			item = bpmOperationTypeService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/bpm/bpm-operation-type-input";
	}

	@RequestMapping(value = "bpm-operation-type-save")
	public String addModel(BpmOperationType bpmOperationType) {
		if(bpmOperationType.getId() != null){
			bpmOperationTypeService.modify(bpmOperationType);
		}else{
			bpmOperationTypeService.add(bpmOperationType);
		}
		
		return "redirect:bpm-operation-type-list.do";
	}

	@RequestMapping(value = "bpm-operation-type-remove")
	public String delete(Model model, String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			bpmOperationTypeService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:bpm-operation-type-list.do";
	}

}
