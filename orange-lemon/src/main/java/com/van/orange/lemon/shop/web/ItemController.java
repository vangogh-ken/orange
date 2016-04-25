package com.van.orange.lemon.shop.web;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/shop")
public class ItemController {
	
	@RequestMapping(value = "index")
	public String index(){
		return "/s_content/shop/shop-index";
	}
}
