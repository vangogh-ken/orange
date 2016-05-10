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
import com.van.halley.db.persistence.entity.BasisAttribute;
import com.van.halley.db.persistence.entity.ItemAttribute;
import com.van.halley.db.persistence.entity.ItemCategory;
import com.van.halley.util.StringUtil;
import com.van.service.ItemCategoryService;

@Controller
@RequestMapping(value = "/item/")
public class ItemCategoryController {
	@Autowired
	private ItemCategoryService itemCategoryService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "item-category-list")
	public String unread(Model model, ItemCategory itemCategory, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = itemCategoryService.query(pageView, itemCategory);

		model.addAttribute("pageView", pageView);
		return "/s_content/item/item-category-list";
	}

	@RequestMapping(value = "item-category-input")
	public String input(Model model, String id) {
		ItemCategory item = null;
		if (id != null) {
			item = itemCategoryService.getById(id);
		}
		model.addAttribute("itemCategories", itemCategoryService.getAll());
		model.addAttribute("item", item);
		return "/s_content/item/item-category-input";
	}

	@RequestMapping(value = "item-category-save", method = RequestMethod.POST)
	public String add(ItemCategory itemCategory, RedirectAttributes redirectAttributes) {
		if(itemCategory.getId() == null){
			itemCategoryService.add(itemCategory);
		}else{
			itemCategoryService.modify(itemCategory);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:item-category-list.do";
	}
	
	@RequestMapping(value = "item-category-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			itemCategoryService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:item-category-list.do";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "item-category-to-add-attribute")
	public Map<String, Object> toAddAttribute(@RequestParam(value="itemCategoryId", required=true)String itemCategoryId){
		return itemCategoryService.toAddAttribute(itemCategoryId);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "item-category-to-revise-attribute")
	public Map<String, Object> toReviseAttribute(@RequestParam(value="itemAttributeId",required=true)String itemAttributeId){
		return itemCategoryService.toReviseAttribute(itemAttributeId);
	}
	
	@ResponseBody
	@RequestMapping(value = "item-category-done-remove-attribute")
	public String doneRemoveAttribute(@RequestParam(value="itemAttributeId",required=true)String[] itemAttributeId){
			itemCategoryService.doneRemoveAttribute(itemAttributeId);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "item-category-done-add-attribute")
	public String doneAddAttribute(@RequestBody ItemAttribute itemAttribute, String itemAttributeId){
		itemCategoryService.doneAddAttribute(itemAttribute, itemAttributeId);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "item-category-done-add-image")
	public String doneAddImage(@RequestBody ItemAttribute itemAttribute, String itemAttributeId){
		itemCategoryService.doneAddAttribute(itemAttribute, itemAttributeId);
		return "success";
	}
	
}
