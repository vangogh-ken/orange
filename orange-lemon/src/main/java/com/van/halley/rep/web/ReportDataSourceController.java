package com.van.halley.rep.web;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.van.halley.db.persistence.entity.ReportDataSource;
import com.van.halley.util.StringUtil;
import com.van.service.ReportDataSourceService;

@Controller
@RequestMapping(value = "/rep/")
public class ReportDataSourceController {
	@Autowired
	private ReportDataSourceService reportDataSourceService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "rep-data-source-list")
	public String unread(Model model, ReportDataSource reportDataSource, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = reportDataSourceService.query(pageView, reportDataSource);

		model.addAttribute("pageView", pageView);
		return "/content/rep/rep-data-source-list";
	}

	@RequestMapping(value = "rep-data-source-input")
	public String input(Model model, String id) {
		ReportDataSource item = null;
		if (id != null) {
			item = reportDataSourceService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/rep/rep-data-source-input";
	}

	@RequestMapping(value = "rep-data-source-save", method = RequestMethod.POST)
	public String add(ReportDataSource reportDataSource, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if(reportDataSource.getId() == null){
			reportDataSourceService.add(reportDataSource);
		}else{
			reportDataSourceService.modify(reportDataSource);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:rep-data-source-list.do";
	}
	
	@RequestMapping(value = "rep-data-source-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for(String id : selectedItem){
			reportDataSourceService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:rep-data-source-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "rep-data-source-check-templatename")
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
	
	@RequestMapping(value = "rep-data-source-to-add-data-source")
	@ResponseBody
	public Map<String, Object> toAddDataSource(@RequestParam(value="reportTemplateId", required=true)String reportTemplateId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hasAddData", reportDataSourceService.getByReportTemplateId(reportTemplateId));
		return map;
	}
	
	@RequestMapping(value = "rep-data-source-to-revise-data-source")
	@ResponseBody
	public Map<String, Object> toReviseDataSource(@RequestParam(value="reportTemplateId", required=true)String reportTemplateId,
			@RequestParam(value="reportDataSourceId", required=true)String reportDataSourceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		ReportDataSource filter = new ReportDataSource();
		filter.setReportTemplateId(reportTemplateId);
		map.put("hasAddData", reportDataSourceService.queryForList(filter));
		map.put("reportDataSource", reportDataSourceService.getById(reportDataSourceId));
		return map;
	}
	
	@RequestMapping(value = "rep-data-source-done-add-data-source")
	@ResponseBody
	public String doneAddDataSource(@RequestBody(required=true)ReportDataSource reportDataSource) {
		if(StringUtil.isNullOrEmpty(reportDataSource.getId())){
			reportDataSourceService.add(reportDataSource);
		}else{
			reportDataSourceService.modify(reportDataSource);
		}
		return "success";
	}
	
	@RequestMapping(value = "rep-data-source-done-delete-data-source")
	@ResponseBody
	public String doneDeleteDataSource(@RequestParam(value="reportDataSourceId", required=true)String[] reportDataSourceId) {
		for(String id : reportDataSourceId){
			reportDataSourceService.delete(id);
		}
		return "success";
	}

}
