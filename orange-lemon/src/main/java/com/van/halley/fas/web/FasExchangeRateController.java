package com.van.halley.fas.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.FasExchangeRate;
import com.van.service.FasExchangeRateService;

@Controller
@RequestMapping(value = "/fas/")
public class FasExchangeRateController {
	@Autowired
	private FasExchangeRateService fasExchangeRateService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fas-exchange-rate-list")
	public String unread(Model model, FasExchangeRate fasExchangeRate, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = fasExchangeRateService.query(pageView, fasExchangeRate);

		model.addAttribute("pageView", pageView);
		return "/content/fas/fas-exchange-rate-list";
	}

	@RequestMapping(value = "fas-exchange-rate-input")
	public String input(Model model, String id) {
		FasExchangeRate item = null;
		if (id != null) {
			item = fasExchangeRateService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fas/fas-exchange-rate-input";
	}

	@RequestMapping(value = "fas-exchange-rate-save", method = RequestMethod.POST)
	public String add(FasExchangeRate fasExchangeRate, RedirectAttributes redirectAttributes) {
		if(fasExchangeRate.getId() == null){
			fasExchangeRateService.add(fasExchangeRate);
		}else{
			fasExchangeRateService.modify(fasExchangeRate);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fas-exchange-rate-list.do";
	}
	
	@RequestMapping(value = "fas-exchange-rate-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			fasExchangeRateService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fas-exchange-rate-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-exchange-rate-check-currency")
	public boolean checkName(@RequestParam(required=true, value="currency") String currency,  String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM FAS_EXCHANGE_RATE WHERE CURRENCY = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, currency, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM FAS_EXCHANGE_RATE WHERE CURRENCY = ? ";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, currency);
			return count == 0;
		}
	}

}
