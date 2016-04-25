package com.van.halley.out.web;


import java.io.IOException;
import java.util.ArrayList;
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
import com.van.halley.db.persistence.entity.ValueAttribute;
import com.van.halley.db.persistence.entity.ValueDictionary;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.ValueAttributeService;
import com.van.service.ValueClassService;
import com.van.service.ValueDictionaryService;

@Controller
@RequestMapping(value = "/dic/")
public class ValueAttributeController {
	@Autowired
	private ValueAttributeService valueAttributeService;
	@Autowired
	private ValueClassService valueClassService;
	@Autowired
	private ValueDictionaryService valueDictionaryService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "value-attribute-list")
	public String list(Model model, ValueAttribute valueAttribute, String valueClassId, 
			@ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(valueClassId != null){
			valueAttribute.setValueClass(valueClassService.getById(valueClassId));
		}
		if(!StringUtil.isNullOrEmpty(valueAttribute.getAttributeName())){
			String filterText = " ATTR_NAME LIKE '%" + valueAttribute.getAttributeName() + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
			
			valueAttribute.setAttributeName(null);
		}
		
		if(!StringUtil.isNullOrEmpty(valueAttribute.getColumnName())){
			String filterText = " COLUMN_NAME LIKE '%" + valueAttribute.getColumnName() + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
			
			valueAttribute.setColumnName(null);
		}
		
		pageView = valueAttributeService.query(pageView, valueAttribute);
		model.addAttribute("pageView", pageView);
		return "/content/dic/value-attribute-list";
	}
	
	@RequestMapping(value = "value-attribute-list-commerce")
	public String listCommerce(Model model, ValueAttribute valueAttribute, String valueClassId, 
			@ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(valueClassId != null){
			valueAttribute.setValueClass(valueClassService.getById(valueClassId));
		}
		
		if(!StringUtil.isNullOrEmpty(valueAttribute.getAttributeName())){
			String filterText = " ATTR_NAME LIKE '%" + valueAttribute.getAttributeName() + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
			
			valueAttribute.setAttributeName(null);
		}
		
		if(!StringUtil.isNullOrEmpty(valueAttribute.getColumnName())){
			String filterText = " COLUMN_NAME LIKE '%" + valueAttribute.getColumnName() + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
			
			valueAttribute.setColumnName(null);
		}
		
		pageView = valueAttributeService.query(pageView, valueAttribute);
		model.addAttribute("pageView", pageView);
		return "/content/dic/value-attribute-list-commerce";
	}

	@RequestMapping(value = "value-attribute-input")
	public String input(Model model, String id) {
		ValueAttribute item = null;
		if (id != null) {
			item = valueAttributeService.getById(id);
		}
		model.addAttribute("valueClasses", valueClassService.getAll());
		model.addAttribute("item", item);
		return "/content/dic/value-attribute-input";
	}

	@RequestMapping(value = "value-attribute-save", method = RequestMethod.POST)
	public String add(ValueAttribute valueAttribute, String valueClassId, RedirectAttributes redirectAttributes) {
		valueAttribute.setValueClass(valueClassService.getById(valueClassId));
		if(valueAttribute.getId() == null){
			valueAttributeService.add(valueAttribute);
		}else{
			valueAttributeService.modify(valueAttribute);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:value-attribute-list.do";
	}
	
	@RequestMapping(value = "value-attribute-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			valueAttributeService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:value-attribute-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "value-attribute-to-import-dictionary", method = RequestMethod.POST)
	public String add(MultipartHttpServletRequest request, String valueAttributeId)
			throws IOException, FileUploadException {
		Map<String, String> map = FileUtil.upload("file", request);
		List<String> values = IExmportUtil.importSingleColumn(0, 1, map.get("fileData"));
		valueDictionaryService.batchAddDictionary(values, valueAttributeId);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "value-attribute-to-export-dictionary")
	public void export(String valueAttributeId, HttpServletResponse response)throws IOException, FileUploadException {
		List<String> values = new ArrayList<String>();
		List<ValueDictionary> valueDictionarys = ValueDictionaryService.cacheDictionary.get(valueAttributeId);
		for(ValueDictionary valueDictionary : valueDictionarys){
			values.add(valueDictionary.getValueContent());
		}
		ValueAttribute valueAttribute = valueAttributeService.getById(valueAttributeId);
		FileUtil.download(IExmportUtil.exportSingleColumn(valueAttribute.getAttributeName(), values), 
				valueAttribute.getAttributeName() + ".xls", response);
	}
	
	@ResponseBody
	@RequestMapping(value = "value-attribute-check-attributename")
	public boolean checkName(@RequestParam(required=true, value="attributeName") String attributeName, String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM SYS_DIC_VALUE_ATTR WHERE ATTR_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, attributeName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM SYS_DIC_VALUE_ATTR WHERE ATTR_NAME = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, attributeName);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "value-attribute-check-attributecolumn")
	public boolean checkColumn(@RequestParam(required=true, value="columnName") String columnName,  String id) {
		if(!StringUtil.isNullOrEmpty(id)){
			String sql = "SELECT COUNT(*) AS count FROM SYS_DIC_VALUE_ATTR WHERE COLUMN_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, columnName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM SYS_DIC_VALUE_ATTR WHERE COLUMN_NAME = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, columnName);
			return count == 0;
		}
	}

}
