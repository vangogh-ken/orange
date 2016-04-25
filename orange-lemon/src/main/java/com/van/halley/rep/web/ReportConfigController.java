package com.van.halley.rep.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.ReportConfig;
import com.van.service.ReportConfigService;

@Controller
@RequestMapping(value = "/report-config/")
public class ReportConfigController {
	@Autowired
	private ReportConfigService reportConfigService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "report-config-list")
	public String queryModel(Model model, ReportConfig reportConfig, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		reportConfigService.query(pageView, reportConfig);
		model.addAttribute("pageView", pageView);
		return "/content/report-config/report-config-list";
	}

	@RequestMapping(value = "report-config-input")
	public String input(Model model, String id) {
		ReportConfig item = null;
		if(id != null){
			item = reportConfigService.getById(id);
		}
		
		model.addAttribute("item", item);
		return "/content/report-config/report-config-input";
	}

	@RequestMapping(value = "report-config-save")
	public String addModel(ReportConfig reportConfig) {
		if(reportConfig.getId() != null){
			reportConfigService.modify(reportConfig);
		}else{
			reportConfigService.add(reportConfig);
		}
		
		return "redirect: report-config-list.do";
	}

	@RequestMapping(value = "report-config-remove")
	public String delete(Model model, String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			reportConfigService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:report-config-list.do";
	}

}
