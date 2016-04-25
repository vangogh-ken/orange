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
import com.van.halley.db.persistence.entity.BasisAttribute;
import com.van.halley.util.StringUtil;
import com.van.service.BasisAttributeService;
import com.van.service.BasisSubstanceTypeService;

@Controller
@RequestMapping(value = "/basis/")
public class BasisAttributeController {
	@Autowired
	private BasisAttributeService basisAttributeService;
	@Autowired
	private BasisSubstanceTypeService basisSubstanceTypeService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "basis-attribute-list")
	public String unread(Model model, BasisAttribute basisAttribute, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = basisAttributeService.query(pageView, basisAttribute);

		model.addAttribute("pageView", pageView);
		return "/content/basis/basis-attribute-list";
	}

	@RequestMapping(value = "basis-attribute-input")
	public String input(Model model, String id) {
		BasisAttribute item = null;
		if (id != null) {
			item = basisAttributeService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/basis/basis-attribute-input";
	}

	@RequestMapping(value = "basis-attribute-save", method = RequestMethod.POST)
	public String add(BasisAttribute basisAttribute, RedirectAttributes redirectAttributes) {
		if(basisAttribute.getId() == null){
			basisAttributeService.add(basisAttribute);
		}else{
			basisAttributeService.modify(basisAttribute);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:basis-attribute-list.do";
	}
	
	@RequestMapping(value = "basis-attribute-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			basisAttributeService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:basis-attribute-list.do";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "basis-attribute-to-add-attribute")
	public Map<String, Object> toAddAttribute(@RequestParam(value="basisSubstanceTypeId", required=true)String basisSubstanceTypeId){
		Map<String, Object> map = new HashMap<String, Object>();
		BasisAttribute filter = new BasisAttribute();
		filter.setBasisSubstanceType(basisSubstanceTypeService.getById(basisSubstanceTypeId));
		map.put("hasAddData", basisAttributeService.queryForList(filter));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-attribute-done-add-attribute")
	public String doneAddAttribute(@RequestBody BasisAttribute basisAttribute, 
			@RequestParam(value="basisSubstanceTypeId",required=true)String basisSubstanceTypeId,
			String basisAttributeId){
		basisAttribute.setBasisSubstanceType(basisSubstanceTypeService.getById(basisSubstanceTypeId));
		if(StringUtil.isNullOrEmpty(basisAttributeId)){
			basisAttributeService.add(basisAttribute);
		}else{
			basisAttribute.setId(basisAttributeId);
			basisAttributeService.modify(basisAttribute);
		}
		
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-attribute-done-add-batch")
	public String doneAddBatch(@RequestBody BasisAttribute basisAttribute, 
			@RequestParam(value="basisSubstanceTypeId",required=true)String basisSubstanceTypeId,
			@RequestParam(value="batchCount",required=true)int batchCount,
			@RequestParam(value="batchStart",required=true)int batchStart,
			@RequestParam(value="batchEnd",required=true)int batchEnd){
		basisAttribute.setBasisSubstanceType(basisSubstanceTypeService.getById(basisSubstanceTypeId));
		basisAttributeService.doneAddBatch(basisAttribute, batchCount, batchStart, batchEnd);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-attribute-to-revise-attribute")
	public Map<String, Object> toReviseActionFiled(@RequestParam(value="basisAttributeId",required=true)String basisAttributeId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("basisAttribute", basisAttributeService.getById(basisAttributeId));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-attribute-done-remove-attribute")
	public String doneRemoveFiled(@RequestParam(value="basisAttributeId",required=true)String[] basisAttributeId){
		for(String id : basisAttributeId){
			basisAttributeService.delete(id);
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-attribute-check-attributename")
	public boolean checkName(@RequestParam(required=true, value="attributeName") String attributeName, 
			@RequestParam(required=true, value="basisSubstanceTypeId") String basisSubstanceTypeId, String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM BASIS_ATTR WHERE ATTR_NAME = ? AND BASIS_SUBSTANCE_TYPE_ID = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, attributeName, basisSubstanceTypeId, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM BASIS_ATTR WHERE ATTR_NAME = ? AND BASIS_SUBSTANCE_TYPE_ID = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, attributeName, basisSubstanceTypeId);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-attribute-check-attributecolumn")
	public boolean checkColumn(@RequestParam(required=true, value="attributeColumn") String attributeColumn, 
			@RequestParam(required=true, value="basisSubstanceTypeId") String basisSubstanceTypeId, String id) {
		if(!StringUtil.isNullOrEmpty(id)){
			String sql = "SELECT COUNT(*) AS count FROM BASIS_ATTR WHERE ATTR_COLUMN = ? AND BASIS_SUBSTANCE_TYPE_ID = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, attributeColumn, basisSubstanceTypeId, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM BASIS_ATTR WHERE ATTR_COLUMN = ? AND BASIS_SUBSTANCE_TYPE_ID = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, attributeColumn, basisSubstanceTypeId);
			return count == 0;
		}
	}
}
