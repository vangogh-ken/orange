package com.van.halley.rep.web;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.ReportSet;
import com.van.halley.util.StringUtil;
import com.van.service.ReportSetService;

@Controller
@RequestMapping(value = "/rep/")
public class ReportSetController {
	@Autowired
	private ReportSetService reportSetService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "rep-set-list")
	public String unread(Model model, ReportSet reportSet, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = reportSetService.query(pageView, reportSet);

		model.addAttribute("pageView", pageView);
		return "/content/rep/rep-set-list";
	}

	@RequestMapping(value = "rep-set-input")
	public String input(Model model, String id) {
		ReportSet item = null;
		if (id != null) {
			item = reportSetService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/rep/rep-set-input";
	}

	@RequestMapping(value = "rep-set-save", method = RequestMethod.POST)
	public String add(ReportSet reportSet, RedirectAttributes redirectAttributes) {
		if(reportSet.getId() == null){
			reportSetService.add(reportSet);
		}else{
			reportSetService.modify(reportSet);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:rep-set-list.do";
	}
	
	@RequestMapping(value = "rep-set-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for(String id : selectedItem){
			reportSetService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:rep-set-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "rep-set-check-templatename")
	public boolean checkName(@RequestParam(required=true, value="templateName") String templateName,  String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM REP_TEMPLATE WHERE TEMPLATE_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, templateName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM REP_TEMPLATE WHERE TEMPLATE_NAME = ? ";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, templateName);
			return count == 0;
		}
	}
	
	
	@RequestMapping(value = "rep-set-to-add-set")
	@ResponseBody
	public Map<String, Object> toAddSet(@RequestParam(value="reportTemplateId", required=true)String reportTemplateId) {
		Map<String, Object> map = new HashMap<String, Object>();
		ReportSet filter = new ReportSet();
		filter.setReportTemplateId(reportTemplateId);
		map.put("hasAddData", reportSetService.queryForList(filter));
		return map;
	}
	
	@RequestMapping(value = "rep-set-to-revise-set")
	@ResponseBody
	public Map<String, Object> toReviseSet(@RequestParam(value="reportTemplateId", required=true)String reportTemplateId,
			@RequestParam(value="reportSetId", required=true)String reportSetId) {
		Map<String, Object> map = new HashMap<String, Object>();
		ReportSet filter = new ReportSet();
		filter.setReportTemplateId(reportTemplateId);
		map.put("hasAddData", reportSetService.queryForList(filter));
		map.put("reportSet", reportSetService.getById(reportSetId));
		return map;
	}
	
	@RequestMapping(value = "rep-set-done-add-set")
	@ResponseBody
	public String doneAddSet(@RequestBody(required=true)ReportSet reportSet) {
		if(StringUtil.isNullOrEmpty(reportSet.getId())){
			reportSetService.add(reportSet);
		}else{
			reportSetService.modify(reportSet);
		}
		return "success";
	}
	
	@RequestMapping(value = "rep-set-done-delete-set")
	@ResponseBody
	public String doneDeleteSet(@RequestParam(value="reportSetId", required=true)String[] reportSetId) {
		for(String id : reportSetId){
			reportSetService.delete(id);
		}
		return "success";
	}

}
