package com.van.orange.shop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.van.halley.db.persistence.entity.ItemAttribute;
import com.van.halley.db.persistence.entity.ItemImage;
import com.van.halley.db.persistence.entity.ItemSubstance;
import com.van.service.ItemAttributeService;
import com.van.service.ItemImageService;
import com.van.service.ItemSubstanceService;

@RequestMapping(value = "/shop")
@Controller
public class ItemController {
	@Autowired
	private ItemSubstanceService itemSubstanceService;
	@Autowired
	private ItemAttributeService itemAttributeService;
	@Autowired
	private ItemImageService itemImageService;
	
	@RequestMapping(value = "index")
	public String index(){
		return "/s_content/shop/index";
	}
	
	@RequestMapping(value = "login")
	public String login(){
		return "/s_content/shop/login";
	}
	
	@RequestMapping(value = "item")
	public String item(Model model, @RequestParam String itemSubstanceId){
		ItemSubstance itemSubstance = itemSubstanceService.getById(itemSubstanceId);
		model.addAttribute("itemSubstance", itemSubstance);
		
		ItemAttribute filter = new ItemAttribute();
		filter.setItemCategoryId(itemSubstance.getItemCategoryId());
		model.addAttribute("itemAttributes", itemAttributeService.queryForList(filter));
		
		ItemImage image = new ItemImage();
		image.setItemSubstanceId(itemSubstanceId);
		model.addAttribute("itemAttributes", itemImageService.queryForList(image));
		return "/s_content/shop/item";
	}
	
	@RequestMapping(value = "list")
	public String list(Model model, @RequestParam String itemCategoryId){
		ItemSubstance filter = new ItemSubstance();
		filter.setItemCategoryId(itemCategoryId);
		model.addAttribute("itemSubstances", itemSubstanceService.queryForList(filter));
		return "/s_content/shop/list";
	}
	
	@RequestMapping(value = "cart")
	public String cart(){
		return "/s_content/shop/cart";
	}
	
	@RequestMapping(value = "checkout")
	public String checkout(){
		return "/s_content/shop/checkout";
	}
	
	
	@RequestMapping(value = "enroll")
	public String enroll(){
		return "/s_content/shop/enroll";
	}
	
	@RequestMapping(value = "forgotton-password")
	public String forgottonPassword(){
		return "/s_content/shop/forgotton-password";
	}
	
	@RequestMapping(value = "about")
	public String about(){
		return "/s_content/shop/about";
	}
	
	@RequestMapping(value = "account")
	public String account(){
		return "/s_content/shop/account";
	}
	
	@RequestMapping(value = "compare")
	public String compare(){
		return "/s_content/shop/compare";
	}
	
	@RequestMapping(value = "contacts")
	public String contacts(){
		return "/s_content/shop/contacts";
	}
	
	@RequestMapping(value = "faq")
	public String faq(){
		return "/s_content/shop/faq";
	}
	
	@RequestMapping(value = "privacy-policy")
	public String privacyPolicy(){
		return "/s_content/shop/privacy-policy";
	}
	
	@RequestMapping(value = "search-result")
	public String searchResult(){
		return "/s_content/shop/search-result";
	}
	
	@RequestMapping(value = "terms-condition")
	public String termsCondition(){
		return "/s_content/shop/terms-condition";
	}
	
	@RequestMapping(value = "wish-list")
	public String wishList(){
		return "/s_content/shop/wish-list";
	}
}
