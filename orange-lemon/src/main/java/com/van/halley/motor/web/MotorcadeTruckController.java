package com.van.halley.motor.web;


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
import com.van.halley.db.persistence.entity.MotorcadeTruck;
import com.van.halley.util.StringUtil;
import com.van.service.MotorcadeTruckService;

@Controller
@RequestMapping(value = "/motor/")
public class MotorcadeTruckController {
	@Autowired
	private MotorcadeTruckService motorcadeTruckService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "motor-truck-list")
	public String unread(Model model, MotorcadeTruck motorcadeTruck, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(!StringUtil.isNullOrEmpty(motorcadeTruck.getHeadstockNumber())){
			String filterText = " HEADSTOCK_NUMBER LIKE '%" + motorcadeTruck.getHeadstockNumber() + "%' ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + "AND " + filterText);
			}
			
			motorcadeTruck.setHeadstockNumber(null);
		}
		
		pageView = motorcadeTruckService.query(pageView, motorcadeTruck);
		model.addAttribute("pageView", pageView);
		return "/content/motor/motor-truck-list";
	}

	@RequestMapping(value = "motor-truck-input")
	public String input(Model model, String id) {
		MotorcadeTruck item = null;
		if (id != null) {
			item = motorcadeTruckService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/motor/motor-truck-input";
	}

	@RequestMapping(value = "motor-truck-save", method = RequestMethod.POST)
	public String add(MotorcadeTruck motorcadeTruck, RedirectAttributes redirectAttributes) {
		if(motorcadeTruck.getId() == null){
			motorcadeTruckService.add(motorcadeTruck);
		}else{
			motorcadeTruckService.modify(motorcadeTruck);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:motor-truck-list.do";
	}
	
	@RequestMapping(value = "motor-truck-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for(String id : selectedItem){
			motorcadeTruckService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:motor-truck-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-truck-check-licensenumber")
	public boolean checkColumnName(@RequestParam(required=true, value="licenseNumber") String licenseNumber, String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM MOTOR_TRUCK WHERE LICENSE_NUMBER = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, licenseNumber, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM MOTOR_TRUCK WHERE LICENSE_NUMBER = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, licenseNumber);
			return count == 0;
		}
	}

}
