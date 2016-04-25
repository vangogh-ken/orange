package com.van.halley.rep.web;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.ReportParam;
import com.van.halley.db.persistence.entity.ReportTemplate;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.service.ReportCategoryService;
import com.van.service.ReportParamService;
import com.van.service.ReportTemplateService;

@Controller
@RequestMapping(value = "/rep/")
public class ReportTemplateController {
	@Autowired
	private ReportTemplateService reportTemplateService;
	@Autowired
	private ReportParamService reportParamService;
	@Autowired
	private ReportCategoryService reportCategoryService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "rep-template-list-statistics")
	public String query(Model model, ReportTemplate reportTemplate, String reportCategoryId, 
			@ModelAttribute PageView<ReportTemplate> pageView) {
		if (pageView == null) {
			pageView = new PageView<ReportTemplate>(1);
		}
		if(StringUtil.isNullOrEmpty(reportTemplate.getTemplateType())){
			reportTemplate.setTemplateType("报表");
		}
		
		if(!StringUtil.isNullOrEmpty(reportCategoryId)){
			reportTemplate.setReportCategory(reportCategoryService.getById(reportCategoryId));
		}
		if(!StringUtil.isNullOrEmpty(reportTemplate.getTemplateName())){
			String filterText = " TEMPLATE_NAME LIKE '%" + reportTemplate.getTemplateName() + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " +filterText);
			}
			
			reportTemplate.setTemplateName(null);
		}
		if(StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			pageView.setOrderBy("CREATE_TIME");
			pageView.setOrder("DESC");
		}
		
		pageView = reportTemplateService.query(pageView, reportTemplate);
		model.addAttribute("pageView", pageView);
		model.addAttribute("reportCategories", reportCategoryService.getAll());
		return "/content/rep/rep-template-list-statistics";
	}
	
	@RequestMapping(value = "rep-template-list-statistics-view")
	public String queryView(Model model, HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("userSession");
		
		List<ReportTemplate> list = reportTemplateService.getByPositionId(user.getPosition().getId());
		List<String> catagoryIds = new ArrayList<String>();
		for(ReportTemplate reportTemplate : list){
			String catagoryId = reportTemplate.getReportCategory().getId();
			if(!catagoryIds.contains(catagoryId)){
				catagoryIds.add(catagoryId);
			}
		}
		
		model.addAttribute("reportTemplates", list);
		model.addAttribute("catagoryIds", catagoryIds);//使用该IDS，以过滤个人报表中没有的类型就不显示
		model.addAttribute("reportCategories", reportCategoryService.getAll());
		return "/content/rep/rep-template-list-statistics-view";
	}

	@RequestMapping(value = "rep-template-input-statistics")
	public String input(Model model, String id) {
		ReportTemplate item = null;
		if (id != null) {
			item = reportTemplateService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("reportCategories", reportCategoryService.getAll());
		return "/content/rep/rep-template-input-statistics";
	}

	@RequestMapping(value = "rep-template-save-statistics", method = RequestMethod.POST)
	public String add(ReportTemplate reportTemplate, String reportCategoryId, MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
		if(StringUtils.isBlank(reportTemplate.getStatus())){
			reportTemplate.setStatus("T");
		}
		Map<String, String> map = FileUtil.upload("muiltFile", request);
		if(map == null || StringUtil.isNullOrEmpty(map.get("fileData"))){//如果没有模板文件，则只更新其基本数据
			if(StringUtil.isNullOrEmpty(reportTemplate.getId())){
				reportTemplate.setReportCategory(reportCategoryService.getById(reportCategoryId));
				reportTemplateService.add(reportTemplate);
			}else{
				reportTemplate.setReportCategory(reportCategoryService.getById(reportCategoryId));
				reportTemplateService.modify(reportTemplate);
			}
		}else{
			reportTemplate.setTemplateFile(map.get("fileData"));
			reportTemplate.setReportCategory(reportCategoryService.getById(reportCategoryId));
			reportTemplateService.addOrModify(reportTemplate);
		}
		
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:rep-template-list-statistics.do";
	}
	
	@RequestMapping(value = "rep-template-remove-statistics")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		reportTemplateService.deleteTemplate(selectedItem);
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:rep-template-list-statistics.do";
	}
	
	@RequestMapping(value = "rep-template-list-normal")
	public String listNormal(Model model, ReportTemplate reportTemplate, @ModelAttribute PageView<ReportTemplate> pageView) {
		if (pageView == null) {
			pageView = new PageView<ReportTemplate>(1);
		}
		if(StringUtil.isNullOrEmpty(reportTemplate.getTemplateType())){
			reportTemplate.setTemplateType("普通");
		}
		pageView = reportTemplateService.query(pageView, reportTemplate);
		model.addAttribute("pageView", pageView);
		return "/content/rep/rep-template-list-normal";
	}

	@RequestMapping(value = "rep-template-input-normal")
	public String inputNormal(Model model, String id) {
		ReportTemplate item = null;
		if (id != null) {
			item = reportTemplateService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("reportCategories", reportCategoryService.getAll());
		return "/content/rep/rep-template-input-normal";
	}

	@RequestMapping(value = "rep-template-save-normal", method = RequestMethod.POST)
	public String addNormal(ReportTemplate reportTemplate, String reportCategoryId, MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
		Map<String, String> map = FileUtil.upload("muiltFile", request);
		if(map == null || StringUtil.isNullOrEmpty(map.get("fileData"))){//如果没有模板文件，则只更新其基本数据
			if(StringUtil.isNullOrEmpty(reportTemplate.getId())){
				reportTemplate.setReportCategory(reportCategoryService.getById(reportCategoryId));
				reportTemplateService.add(reportTemplate);
			}else{
				reportTemplate.setReportCategory(reportCategoryService.getById(reportCategoryId));
				reportTemplateService.modify(reportTemplate);
			}
		}else{
			reportTemplate.setTemplateFile(map.get("fileData"));
			reportTemplate.setReportCategory(reportCategoryService.getById(reportCategoryId));
			reportTemplateService.addOrModify(reportTemplate);
		}
		
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:rep-template-list-normal.do";
	}
	
	@RequestMapping(value = "rep-template-remove-normal")
	public String removeNormal(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		reportTemplateService.deleteTemplate(selectedItem);
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:rep-template-list-normal.do";
	}
	
	@RequestMapping(value = "rep-template-download-template-file")
	public void download(String reportTemplateId, HttpServletResponse response) throws IOException {
		reportTemplateService.downloadTemplate(reportTemplateId, response);
	}
	
	@ResponseBody
	@RequestMapping(value = "rep-template-check-templatename")
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
	
	@RequestMapping(value = "rep-template-to-release-report")
	@ResponseBody
	public Map<String, Object> toReleaseReport(@RequestParam(value="reportTemplateId", required=true)String reportTemplateId) {
		Map<String, Object> map = new HashMap<String, Object>();
		ReportParam filter = new ReportParam();
		filter.setReportTemplateId(reportTemplateId);
		map.put("reportParams", reportParamService.queryForList(filter));
		map.put("reportTemplate", reportTemplateService.getById(reportTemplateId));
		return map;
	}
	
	@RequestMapping(value = "rep-template-done-relese-report")
	@ResponseBody
	public void doneReleaseReport(@RequestParam(value="reportTemplateId", required=true)String reportTemplateId,
			HttpServletResponse response) throws IOException {
		ReportTemplate reportTemplate = reportTemplateService.getById(reportTemplateId);
		String templateName = reportTemplate.getTemplateName();
		String suffix = reportTemplate.getTemplateFile().split("\\.")[1];
		
		//FileUtil.download(reportTemplateService.releaseReport(reportTemplateId), templateName + "." + suffix, response);
		FileUtil.download(reportTemplateService.generateReport(reportTemplateId, reportTemplateService.parseParams(reportTemplateId)), 
				templateName + "." + suffix, response);
	}
	
	/**
	 * 在线预览报表
	 */
	@RequestMapping(value = "rep-template-browse-relese-report")
	@ResponseBody
	public String browseReleaseReport(@RequestParam(value="reportTemplateId", required=true)String reportTemplateId,
			HttpServletResponse response) throws IOException {
		ReportTemplate reportTemplate = reportTemplateService.getById(reportTemplateId);
		String suffix = reportTemplate.getTemplateFile().split("\\.")[1];
		String reportFileId = StringUtil.getUUID();
		//FileUtils.copyInputStreamToFile(reportTemplateService.releaseReport(reportTemplateId), new File(FileUtil.attachmentPath, reportFileId + "." + suffix));
		FileUtils.copyInputStreamToFile(reportTemplateService.generateReport(reportTemplateId, reportTemplateService.parseParams(reportTemplateId)), 
				new File(FileUtil.attachmentPath, reportFileId + "." + suffix));
		return reportFileId + "." + suffix;
	}
	
	/**
	 * 图形报表
	 * @throws IOException 
	 */
	@RequestMapping(value = "rep-template-graph-relese-report")
	public String graphReleaseReport(Model model, @RequestParam(value="reportTemplateId", required=true)String reportTemplateId,
			HttpServletResponse response) throws IOException{
		ReportTemplate reportTemplate = reportTemplateService.getById(reportTemplateId);
		if(StringUtils.isBlank(reportTemplate.getGraphUrl())){
			response.getWriter().print("<script>alert('wrong graph config!');window.close();</script>");
			return null;
		}
		model.addAttribute("reportTemplateId", reportTemplateId);
		return reportTemplate.getGraphUrl();
	}
	
	@ResponseBody
	@RequestMapping(value = "rep-template-graph-parse-report")
	public Map<String, Object> graphParseReport(@RequestParam(value="reportTemplateId", required=true)String reportTemplateId,
			HttpServletResponse response) throws IOException{
		return reportTemplateService.parseDataSource(reportTemplateId, 
				reportTemplateService.parseParams(reportTemplateId));
	}
	
	@RequestMapping(value = "rep-template-done-invalid-report")
	@ResponseBody
	public String doneInvalidReport(@RequestParam(value="reportTemplateId", required=true)String[] reportTemplateId) {
		if(reportTemplateService.doneInvalidReport(reportTemplateId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "rep-template-done-reuse-report")
	@ResponseBody
	public String doneReuseReport(@RequestParam(value="reportTemplateId", required=true)String[] reportTemplateId) {
		if(reportTemplateService.doneReuseReport(reportTemplateId)){
			return "success";
		}else{
			return "error";
		}
	}

	@RequestMapping(value = "rep-template-to-add-data-source")
	@ResponseBody
	public Map<String, Object> toAddDataSource(@RequestParam(value="reportTemplateId", required=true)String reportTemplateId) {
		return reportTemplateService.toAddDataSource(reportTemplateId);
	}

}
