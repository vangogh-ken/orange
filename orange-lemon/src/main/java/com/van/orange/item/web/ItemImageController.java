package com.van.orange.item.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.ItemCategory;
import com.van.halley.db.persistence.entity.ItemImage;
import com.van.service.ItemCategoryService;
import com.van.service.ItemImageService;

@Controller
@RequestMapping(value = "/item/")
public class ItemImageController {
	@Autowired
	private ItemImageService itemImageService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "item-image-list")
	public String unread(Model model, ItemImage itemImage, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = itemImageService.query(pageView, itemImage);

		model.addAttribute("pageView", pageView);
		return "/content/item/item-image-list";
	}

	@RequestMapping(value = "item-image-input")
	public String input(Model model, String id) {
		ItemImage item = null;
		if (id != null) {
			item = itemImageService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/item/item-image-input";
	}

	@RequestMapping(value = "item-image-save", method = RequestMethod.POST)
	public String add(ItemImage itemImage, RedirectAttributes redirectAttributes) {
		if(itemImage.getId() == null){
			itemImageService.add(itemImage);
		}else{
			itemImageService.modify(itemImage);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:item-image-list.do";
	}
	
	@RequestMapping(value = "item-image-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			itemImageService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:item-image-list.do";
	}
}
