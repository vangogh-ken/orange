package com.van.orange.lemon.shop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/shop")
@Controller
public class ItemController {
	
	@RequestMapping(value = "index")
	public String index(){
		return "/s_content/shop/index";
	}
	
	@RequestMapping(value = "login")
	public String login(){
		return "/s_content/shop/login";
	}
	
	@RequestMapping(value = "item")
	public String item(){
		return "/s_content/shop/item";
	}
	
	@RequestMapping(value = "cart")
	public String cart(){
		return "/s_content/shop/cart";
	}
	
	@RequestMapping(value = "checkout")
	public String checkout(){
		return "/s_content/shop/checkout";
	}
	
	@RequestMapping(value = "list")
	public String list(){
		return "/s_content/shop/list";
	}
	
	@RequestMapping(value = "enroll")
	public String enroll(){
		return "/s_content/shop/enroll";
	}
	
	@RequestMapping(value = "forgotton-password")
	public String forgottonPassword(){
		return "/s_content/shop/forgotton-password";
	}
}
