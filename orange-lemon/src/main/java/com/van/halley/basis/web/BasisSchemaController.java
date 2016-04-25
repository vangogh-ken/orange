package com.van.halley.basis.web;


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
import com.van.halley.db.persistence.entity.BasisSchema;
import com.van.halley.util.StringUtil;
import com.van.service.BasisSchemaService;
import com.van.service.BasisSubstanceTypeService;

@Controller
@RequestMapping(value = "/basis/")
public class BasisSchemaController {
	@Autowired
	private BasisSchemaService basisSchemaService;
	@Autowired
	private BasisSubstanceTypeService basisSubstanceTypeService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "basis-schema-list")
	public String unread(Model model, BasisSchema basisSchema, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = basisSchemaService.query(pageView, basisSchema);

		model.addAttribute("pageView", pageView);
		return "/content/basis/basis-schema-list";
	}

	@RequestMapping(value = "basis-schema-input")
	public String input(Model model, String id) {
		BasisSchema item = null;
		if (id != null) {
			item = basisSchemaService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/basis/basis-schema-input";
	}

	@RequestMapping(value = "basis-schema-save", method = RequestMethod.POST)
	public String add(BasisSchema basisSchema, RedirectAttributes redirectAttributes) {
		if(basisSchema.getId() == null){
			basisSchemaService.add(basisSchema);
		}else{
			basisSchemaService.modify(basisSchema);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:basis-schema-list.do";
	}
	
	@RequestMapping(value = "basis-schema-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			basisSchemaService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:basis-schema-list.do";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "basis-schema-to-add-schema")
	public Map<String, Object> toAddActionFiled(@RequestParam(value="basisSubstanceTypeId", required=true)String basisSubstanceTypeId){
		Map<String, Object> map = new HashMap<String, Object>();
		BasisSchema filter = new BasisSchema();
		filter.setBasisSubstanceType(basisSubstanceTypeService.getById(basisSubstanceTypeId));
		map.put("hasAddData", basisSchemaService.queryForList(filter));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-schema-done-add-schema")
	public String doneAddActionFiled(@RequestBody BasisSchema basisSchema, 
			@RequestParam(value="basisSubstanceTypeId",required=true)String basisSubstanceTypeId,
			String basisSchemaId){
		basisSchema.setBasisSubstanceType(basisSubstanceTypeService.getById(basisSubstanceTypeId));
		if(StringUtil.isNullOrEmpty(basisSchemaId)){
			basisSchemaService.add(basisSchema);
		}else{
			basisSchema.setId(basisSchemaId);
			basisSchemaService.modify(basisSchema);
		}
		
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-schema-to-revise-schema")
	public Map<String, Object> toReviseActionFiled(@RequestParam(value="basisSchemaId",required=true)String basisSchemaId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("basisSchema", basisSchemaService.getById(basisSchemaId));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-schema-done-remove-schema")
	public String doneRemoveFiled(@RequestParam(value="basisSchemaId",required=true)String[] basisSchemaId){
		for(String id : basisSchemaId){
			basisSchemaService.delete(id);
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-schema-check-schemaname")
	public boolean checkName(@RequestParam(required=true, value="schemaName") String schemaName, 
			@RequestParam(required=true, value="basisSubstanceTypeId") String basisSubstanceTypeId, String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM BASIS_SCHEMA WHERE SCHEMA_NAME = ? AND BASIS_SUBSTANCE_TYPE_ID = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, schemaName, basisSubstanceTypeId, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM BASIS_SCHEMA WHERE SCHEMA_NAME = ? AND BASIS_SUBSTANCE_TYPE_ID = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, schemaName, basisSubstanceTypeId);
			return count == 0;
		}
	}
}
