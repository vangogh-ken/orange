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
import com.van.halley.db.persistence.entity.FreightExpenseType;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FreightExpenseTypeService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightExpenseTypeController {
	@Autowired
	private FreightExpenseTypeService freightExpenseTypeService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-expense-type-list")
	public String unread(Model model, FreightExpenseType freightExpenseType, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(!StringUtil.isNullOrEmpty(freightExpenseType.getTypeName())){
			String filterText = " TYPE_NAME LIKE '%" + freightExpenseType.getTypeName() + "%' ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
			
			freightExpenseType.setTypeName(null);
		}
		
		pageView = freightExpenseTypeService.query(pageView, freightExpenseType);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-expense-type-list";
	}

	@RequestMapping(value = "fre-expense-type-input")
	public String input(Model model, String id) {
		FreightExpenseType item = null;
		if (id != null) {
			item = freightExpenseTypeService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-expense-type-input";
	}

	@RequestMapping(value = "fre-expense-type-save", method = RequestMethod.POST)
	public String add(FreightExpenseType freightExpenseType, RedirectAttributes redirectAttributes) {
		if(freightExpenseType.getId() == null){
			freightExpenseTypeService.add(freightExpenseType);
		}else{
			freightExpenseTypeService.modify(freightExpenseType);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-expense-type-list.do";
	}
	
	@RequestMapping(value = "fre-expense-type-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		freightExpenseTypeService.remove(selectedItem);
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-expense-type-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-expense-type-check-typename")
	public boolean checkColumnName(@RequestParam(required=true, value="typeName") String typeName, String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM FRE_EXPENSE_TYPE WHERE TYPE_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, typeName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM FRE_EXPENSE_TYPE WHERE TYPE_NAME = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, typeName);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-expense-type-to-import-expense-type", method = RequestMethod.POST)
	public String add(MultipartHttpServletRequest request, String valueAttributeId)
			throws IOException, FileUploadException {
		Map<String, String> map = FileUtil.upload("file", request);
		List<List<String>> values = IExmportUtil.importMultiColumn(new int[]{0}, 1, map.get("fileData"));
		freightExpenseTypeService.toImport(values);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-expense-type-to-export-expense-type")
	public void export(HttpServletResponse response)throws IOException, FileUploadException {
		List<FreightExpenseType> freightExpenseTypes = freightExpenseTypeService.getAll();
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"费用名称"}, freightExpenseTypeService.toExport(freightExpenseTypes)), "费用列表.xls", response);
	}


}
