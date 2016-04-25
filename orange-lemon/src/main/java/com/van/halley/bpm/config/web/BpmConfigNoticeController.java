package com.van.halley.bpm.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.bpm.service.RepositoryQueryService;
import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.BpmConfigNotice;
import com.van.service.BpmConfigNodeService;
import com.van.service.BpmConfigNoticeService;
import com.van.service.BpmMailTemplateService;

@Controller
@RequestMapping(value = "/bpm/")
public class BpmConfigNoticeController {
	@Autowired
	private BpmConfigNoticeService bpmConfigNoticeService;
	@Autowired
	private BpmConfigNodeService bpmConfigNodeService;
	@Autowired
	private BpmMailTemplateService bpmMailTemplateService;
	@Autowired
	private RepositoryQueryService repositoryQueryServiceImpl;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "bpm-config-notice-list")
	public String queryModel(Model model, BpmConfigNotice bpmConfigNotice, String nodeId,
			String templateId, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		if(nodeId != null){
			bpmConfigNotice.setBpmConfigNode(bpmConfigNodeService.getById(nodeId));
		}
		if(templateId != null){
			bpmConfigNotice.setBpmMailTemplate(bpmMailTemplateService.getById(templateId));
		}
		
		bpmConfigNoticeService.query(pageView, bpmConfigNotice);
		model.addAttribute("pageView", pageView);
		model.addAttribute("nodes", bpmConfigNodeService.getAll());
		model.addAttribute("templates", bpmMailTemplateService.getAll());
		model.addAttribute("pds", repositoryQueryServiceImpl.getProcessDefinitions());
		return "/content/bpm/bpm-config-notice-list";
	}

	@RequestMapping(value = "bpm-config-notice-input")
	public String input(Model model, String id) {
		BpmConfigNotice item = null;
		if(id != null){
			item = bpmConfigNoticeService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("nodes", bpmConfigNodeService.getAll());
		model.addAttribute("templates", bpmMailTemplateService.getAll());
		return "/content/bpm/bpm-config-notice-input";
	}

	@RequestMapping(value = "bpm-config-notice-save")
	public String addModel(BpmConfigNotice bpmConfigNotice, String nodeId, String templateId) {
		if(nodeId != null){
			bpmConfigNotice.setBpmConfigNode(bpmConfigNodeService.getById(nodeId));
		}
		if(templateId != null){
			bpmConfigNotice.setBpmMailTemplate(bpmMailTemplateService.getById(templateId));
		}
		
		if(bpmConfigNotice.getId() != null){
			bpmConfigNoticeService.modify(bpmConfigNotice);
		}else{
			bpmConfigNoticeService.add(bpmConfigNotice);
		}
		
		return "redirect:bpm-config-notice-list.do";
	}

	@RequestMapping(value = "bpm-config-notice-remove")
	public String delete(Model model, String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			bpmConfigNoticeService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:bpm-config-notice-list.do";
	}

}
