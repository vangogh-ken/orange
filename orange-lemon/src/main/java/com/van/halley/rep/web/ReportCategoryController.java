package com.van.halley.rep.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.ReportCategory;
import com.van.service.ReportCategoryService;

@Controller
@RequestMapping(value = "/rep/")
public class ReportCategoryController {
	@Autowired
	private ReportCategoryService reportCategoryService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "rep-category-list")
	public String unread(Model model, ReportCategory reportCategory, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = reportCategoryService.query(pageView, reportCategory);

		model.addAttribute("pageView", pageView);
		return "/content/rep/rep-category-list";
	}

	@RequestMapping(value = "rep-category-input")
	public String input(Model model, String id) {
		ReportCategory item = null;
		if (id != null) {
			item = reportCategoryService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/rep/rep-category-input";
	}

	@RequestMapping(value = "rep-category-save", method = RequestMethod.POST)
	public String add(ReportCategory reportCategory, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if(reportCategory.getId() == null){
			reportCategoryService.add(reportCategory);
		}else{
			reportCategoryService.modify(reportCategory);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:rep-category-list.do";
	}
	
	@RequestMapping(value = "rep-category-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for(String id : selectedItem){
			reportCategoryService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:rep-category-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "rep-category-check-categoryname")
	public boolean checkName(@RequestParam(required=true, value="categoryName") String categoryName,  String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM REP_CATEGORY WHERE CATEGORY_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, categoryName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM REP_CATEGORY WHERE CATEGORY_NAME = ? ";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, categoryName);
			return count == 0;
		}
	}
	

}
