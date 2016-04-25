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
import com.van.halley.db.persistence.entity.MotorcadeDriver;
import com.van.halley.util.StringUtil;
import com.van.service.MotorcadeDriverService;

@Controller
@RequestMapping(value = "/motor/")
public class MotorcadeDriverController {
	@Autowired
	private MotorcadeDriverService motorcadeDriverService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "motor-driver-list")
	public String list(Model model, MotorcadeDriver motorcadeDriver, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		if(!StringUtil.isNullOrEmpty(motorcadeDriver.getDisplayName())){
			String filterText = " DISPLAY_NAME LIKE '%" + motorcadeDriver.getDisplayName() + "%' ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + "AND " + filterText);
			}
			
			motorcadeDriver.setDisplayName(null);
		}
		
		if(!StringUtil.isNullOrEmpty(motorcadeDriver.getDrivingNumber())){
			String filterText = " DRIVING_NUMBER LIKE '%" + motorcadeDriver.getDrivingNumber() + "%' ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + "AND " + filterText);
			}
			
			motorcadeDriver.setDrivingNumber(null);
		}
		pageView = motorcadeDriverService.query(pageView, motorcadeDriver);
		model.addAttribute("pageView", pageView);
		return "/content/motor/motor-driver-list";
	}

	@RequestMapping(value = "motor-driver-input")
	public String input(Model model, String id) {
		MotorcadeDriver item = null;
		if (id != null) {
			item = motorcadeDriverService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/motor/motor-driver-input";
	}

	@RequestMapping(value = "motor-driver-save", method = RequestMethod.POST)
	public String add(MotorcadeDriver motorcadeDriver, RedirectAttributes redirectAttributes) {
		if(motorcadeDriver.getId() == null){
			motorcadeDriverService.add(motorcadeDriver);
		}else{
			motorcadeDriverService.modify(motorcadeDriver);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:motor-driver-list.do";
	}
	
	@RequestMapping(value = "motor-driver-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for(String id : selectedItem){
			motorcadeDriverService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:motor-driver-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-driver-check-displayname")
	public boolean checkColumnName(@RequestParam(required=true, value="displayName") String displayName, String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM MOTOR_DRIVER WHERE DISPLAY_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, displayName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM MOTOR_DRIVER WHERE DISPLAY_NAME = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, displayName);
			return count == 0;
		}
	}

}
