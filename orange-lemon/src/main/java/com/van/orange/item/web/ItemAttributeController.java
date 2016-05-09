package com.van.orange.item.web;


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
import com.van.halley.db.persistence.entity.ItemAttribute;
import com.van.halley.util.StringUtil;
import com.van.service.ItemAttributeService;

@Controller
@RequestMapping(value = "/item/")
public class ItemAttributeController {
	@Autowired
	private ItemAttributeService itemAttributeService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "item-attribute-list")
	public String unread(Model model, ItemAttribute itemAttribute, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = itemAttributeService.query(pageView, itemAttribute);

		model.addAttribute("pageView", pageView);
		return "/content/item/item-attribute-list";
	}

	@RequestMapping(value = "item-attribute-input")
	public String input(Model model, String id) {
		ItemAttribute item = null;
		if (id != null) {
			item = itemAttributeService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/item/item-attribute-input";
	}

	@RequestMapping(value = "item-attribute-save", method = RequestMethod.POST)
	public String add(ItemAttribute itemAttribute, RedirectAttributes redirectAttributes) {
		if(itemAttribute.getId() == null){
			itemAttributeService.add(itemAttribute);
		}else{
			itemAttributeService.modify(itemAttribute);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:item-attribute-list.do";
	}
	
	@RequestMapping(value = "item-attribute-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			itemAttributeService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:item-attribute-list.do";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "item-attribute-to-add-attribute")
	public Map<String, Object> toAddAttribute(@RequestParam(value="itemCategoryId", required=true)String itemCategoryId){
		Map<String, Object> map = new HashMap<String, Object>();
		ItemAttribute filter = new ItemAttribute();
		filter.setItemCategoryId(itemCategoryId);
		map.put("hasAddData", itemAttributeService.queryForList(filter));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "item-attribute-done-add-attribute")
	public String doneAddAttribute(@RequestBody ItemAttribute itemAttribute, 
			@RequestParam(value="itemCategoryId",required=true)String itemCategoryId,
			String itemAttributeId){
		itemAttribute.setItemCategoryId(itemCategoryId);
		if(StringUtil.isNullOrEmpty(itemAttributeId)){
			itemAttributeService.add(itemAttribute);
		}else{
			itemAttribute.setId(itemAttributeId);
			itemAttributeService.modify(itemAttribute);
		}
		
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "item-attribute-done-add-batch")
	public String doneAddBatch(@RequestBody ItemAttribute itemAttribute, 
			@RequestParam(value="itemCategoryId",required=true)String itemCategoryId,
			@RequestParam(value="batchCount",required=true)int batchCount,
			@RequestParam(value="batchStart",required=true)int batchStart,
			@RequestParam(value="batchEnd",required=true)int batchEnd){
		itemAttribute.setItemCategoryId(itemCategoryId);
		//itemAttributeService.doneAddBatch(itemAttribute, batchCount, batchStart, batchEnd);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "item-attribute-to-revise-attribute")
	public Map<String, Object> toReviseActionFiled(@RequestParam(value="itemAttributeId",required=true)String itemAttributeId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemAttribute", itemAttributeService.getById(itemAttributeId));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "item-attribute-done-remove-attribute")
	public String doneRemoveFiled(@RequestParam(value="itemAttributeId",required=true)String[] itemAttributeId){
		for(String id : itemAttributeId){
			itemAttributeService.delete(id);
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "item-attribute-check-attributename")
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
	@RequestMapping(value = "item-attribute-check-attributecolumn")
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
