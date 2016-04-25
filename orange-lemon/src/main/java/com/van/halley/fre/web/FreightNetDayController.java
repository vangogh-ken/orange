package com.van.halley.fre.web;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
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
import com.van.halley.db.persistence.entity.FreightNetDay;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FreightNetDayService;
import com.van.service.FreightPartService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightNetDayController {
	@Autowired
	private FreightNetDayService freightNetDayService;
	@Autowired
	private FreightPartService freightPartService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-net-day-list")
	public String list(Model model, FreightNetDay freightNetDay, String partName, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(!StringUtil.isNullOrEmpty(partName)){
			String filterText = " FRE_PART_ID IN (SELECT ID FROM FRE_PART WHERE PART_NAME LIKE '%" + partName + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		pageView = freightNetDayService.query(pageView, freightNetDay);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-net-day-list";
	}
	
	@RequestMapping(value = "fre-net-day-list-fas")
	public String listFas(Model model, FreightNetDay freightNetDay, String partName, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		if(!StringUtil.isNullOrEmpty(partName)){
			String filterText = " FRE_PART_ID IN (SELECT ID FROM FRE_PART WHERE PART_NAME LIKE '%" + partName + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		pageView = freightNetDayService.query(pageView, freightNetDay);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-net-day-list-fas";
	}

	@RequestMapping(value = "fre-net-day-input")
	public String input(Model model, String id) {
		FreightNetDay item = null;
		if (id != null) {
			item = freightNetDayService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-net-day-input";
	}
	
	@RequestMapping(value = "fre-net-day-input-fas")
	public String inputFas(Model model, String id) {
		FreightNetDay item = null;
		if (id != null) {
			item = freightNetDayService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-net-day-input-fas";
	}

	@RequestMapping(value = "fre-net-day-save", method = RequestMethod.POST)
	public String add(FreightNetDay freightNetDay, String freightPartId, RedirectAttributes redirectAttributes) {
		freightNetDay.setFreightPart(freightPartService.getById(freightPartId));
		if(freightNetDay.getId() == null){
			freightNetDayService.add(freightNetDay);
		}else{
			freightNetDayService.modify(freightNetDay);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-net-day-list.do";
	}
	
	@RequestMapping(value = "fre-net-day-save-fas", method = RequestMethod.POST)
	public String addFas(FreightNetDay freightNetDay, String freightPartId, RedirectAttributes redirectAttributes) {
		freightNetDay.setFreightPart(freightPartService.getById(freightPartId));
		if(freightNetDay.getId() == null){
			freightNetDayService.add(freightNetDay);
		}else{
			freightNetDayService.modify(freightNetDay);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-net-day-list-fas.do";
	}
	
	@RequestMapping(value = "fre-net-day-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightNetDayService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-net-day-list.do";
	}
	
	@RequestMapping(value = "fre-net-day-remove-fas")
	public String removeFas(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightNetDayService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-net-day-list-fas.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-net-day-check-currency")
	public boolean checkName(@RequestParam(required=true, value="incomeOrExpense") String incomeOrExpense,
			@RequestParam(required=true, value="currency") String currency,
			@RequestParam(required=true, value="freightPartId") String freightPartId, String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM FRE_NET_DAY WHERE INCOME_OR_EXPENSE = ? AND CURRENCY=? AND FRE_PART_ID=? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, incomeOrExpense, currency, freightPartId, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM FRE_NET_DAY WHERE INCOME_OR_EXPENSE = ? AND CURRENCY=? AND FRE_PART_ID=?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, incomeOrExpense, currency, freightPartId);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-net-day-to-import-net-day", method = RequestMethod.POST)
	public String add(MultipartHttpServletRequest request, String valueAttributeId)
			throws IOException, FileUploadException {
		Map<String, String> map = FileUtil.upload("file", request);
		List<List<String>> values = IExmportUtil.importMultiColumn(new int[]{0,1,2,3,4,5,6}, 1, map.get("fileData"));
		freightNetDayService.toImport(values);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-net-day-to-export-net-day")
	public void export(String valueAttributeId, HttpServletResponse response)throws IOException, FileUploadException {
		List<FreightNetDay> freightNetDays = freightNetDayService.getAll();
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"单位名称","收/付","币种","是否为固定账期","账期天数","账期固定日期","延迟月份"}, 
				freightNetDayService.toExport(freightNetDays)), "账期列表.xls", response);
	}

}
