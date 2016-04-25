package com.van.halley.rep.web;


import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.van.halley.db.persistence.entity.ReportParam;
import com.van.halley.util.StringUtil;
import com.van.service.ReportParamService;

@Controller
@RequestMapping(value = "/rep/")
public class ReportParamController {
	@Autowired
	private ReportParamService reportParamService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "rep-param-list")
	public String unread(Model model, ReportParam reportParam, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = reportParamService.query(pageView, reportParam);

		model.addAttribute("pageView", pageView);
		return "/content/rep/rep-param-list";
	}

	@RequestMapping(value = "rep-param-input")
	public String input(Model model, String id) {
		ReportParam item = null;
		if (id != null) {
			item = reportParamService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/rep/rep-param-input";
	}

	@RequestMapping(value = "rep-param-save", method = RequestMethod.POST)
	public String add(ReportParam reportParam, RedirectAttributes redirectAttributes) {
		if(reportParam.getId() == null){
			reportParamService.add(reportParam);
		}else{
			reportParamService.modify(reportParam);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:rep-param-list.do";
	}
	
	@RequestMapping(value = "rep-param-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for(String id : selectedItem){
			reportParamService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:rep-param-list.do";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "rep-param-check-templatename")
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
	
	
	@RequestMapping(value = "rep-param-to-add-param")
	@ResponseBody
	public Map<String, Object> toAddParam(@RequestParam(value="reportTemplateId", required=true)String reportTemplateId) {
		Map<String, Object> map = new HashMap<String, Object>();
		ReportParam filter = new ReportParam();
		filter.setReportTemplateId(reportTemplateId);
		map.put("hasAddData", reportParamService.queryForList(filter));
		return map;
	}
	
	@RequestMapping(value = "rep-param-to-revise-param")
	@ResponseBody
	public Map<String, Object> toReviseParam(@RequestParam(value="reportTemplateId", required=true)String reportTemplateId,
			@RequestParam(value="reportParamId", required=true)String reportParamId) {
		Map<String, Object> map = new HashMap<String, Object>();
		ReportParam filter = new ReportParam();
		filter.setReportTemplateId(reportTemplateId);
		map.put("hasAddData", reportParamService.queryForList(filter));
		map.put("reportParam", reportParamService.getById(reportParamId));
		return map;
	}
	
	@RequestMapping(value = "rep-param-done-add-param")
	@ResponseBody
	public String doneAddParam(@RequestBody(required=true)ReportParam reportParam) {
		if(StringUtil.isNullOrEmpty(reportParam.getId())){
			reportParamService.add(reportParam);
		}else{
			reportParamService.modify(reportParam);
		}
		return "success";
	}
	
	@RequestMapping(value = "rep-param-done-delete-param")
	@ResponseBody
	public String doneDeleteParam(@RequestParam(value="reportParamId", required=true)String[] reportParamId) {
		for(String id : reportParamId){
			reportParamService.delete(id);
		}
		return "success";
	}
	
	
	@RequestMapping(value = "rep-param-set-param-value")
	@ResponseBody
	public String setParamValue(@RequestParam(value="reportParamId", required=true)String[] reportParamId,
			@RequestParam(value="paramValue", required=true)Object[] paramValue) throws ParseException {
		for(int i=0, len=reportParamId.length; i<len; i++){
			ReportParam reportParam = reportParamService.getById(reportParamId[i]);
			if("VARCHAR".equals(reportParam.getParamType())){
				reportParam.setStringValue((String)paramValue[i]);
			}else if("INT".equals(reportParam.getParamType())){
				reportParam.setIntValue(Integer.parseInt((String)paramValue[i]));
			}else if("DOUBLE".equals(reportParam.getParamType())){
				reportParam.setDoubleValue(Double.parseDouble((String)paramValue[i]));
			}else if("DATETIME".equals(reportParam.getParamType())){
				reportParam.setDateValue(new SimpleDateFormat("yyyy-MM-dd").parse((String)paramValue[i]));
			}
			
			reportParamService.modify(reportParam);
		}
		
		return "success";
	}


}
