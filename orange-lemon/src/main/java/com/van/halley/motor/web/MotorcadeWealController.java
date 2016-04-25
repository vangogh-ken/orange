package com.van.halley.motor.web;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
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
import com.van.halley.db.persistence.entity.MotorcadeWeal;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FasInvoiceTypeService;
import com.van.service.MotorcadeDriverService;
import com.van.service.MotorcadeTruckService;
import com.van.service.MotorcadeWealService;

@Controller
@RequestMapping(value = "/motor/")
public class MotorcadeWealController {
	@Autowired
	private MotorcadeWealService motorcadeWealService;
	@Autowired
	private MotorcadeDriverService motorcadeDriverService;
	@Autowired
	private MotorcadeTruckService motorcadeTruckService;
	@Autowired
	private FasInvoiceTypeService fasInvoiceTypeService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "motor-weal-list")
	public String unread(Model model, MotorcadeWeal motorcadeWeal, 
			String headstockNumber, String displayName,  String wealTimeStart, String wealTimeEnd, 
			@ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		if(!StringUtil.isNullOrEmpty(headstockNumber)){
			String filterText = " MOTOR_TRUCK_ID IN (SELECT ID FROM MOTOR_TRUCK WHERE HEADSTOCK_NUMBER LIKE '%" + headstockNumber + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(displayName)){
			String filterText = " MOTOR_DRIVER_ID IN (SELECT ID FROM MOTOR_DRIVER WHERE DISPLAY_NAME LIKE '%" + displayName + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(wealTimeStart) && !StringUtil.isNullOrEmpty(wealTimeEnd)){
			String filterText = " (WEAL_TIME BETWEEN '" + wealTimeStart + "' AND '" + wealTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		pageView = motorcadeWealService.query(pageView, motorcadeWeal);
		model.addAttribute("pageView", pageView);
		return "/content/motor/motor-weal-list";
	}

	@RequestMapping(value = "motor-weal-input")
	public String input(Model model, String id) {
		MotorcadeWeal item = null;
		if (id != null) {
			item = motorcadeWealService.getById(id);
		}
		model.addAttribute("motorcadeDrivers", motorcadeDriverService.getAll());
		model.addAttribute("motorcadeTrucks", motorcadeTruckService.getAll());
		model.addAttribute("fasInvoiceTypes", fasInvoiceTypeService.getAll());
		model.addAttribute("item", item);
		return "/content/motor/motor-weal-input";
	}

	@RequestMapping(value = "motor-weal-save", method = RequestMethod.POST)
	public String add(MotorcadeWeal motorcadeWeal, String motorcadeDriverId, String motorcadeTruckId, 
			String fasInvoiceTypeId, RedirectAttributes redirectAttributes) {
		
		motorcadeWeal.setMotorcadeDriver(motorcadeDriverService.getById(motorcadeDriverId));
		motorcadeWeal.setMotorcadeTruck(motorcadeTruckService.getById(motorcadeTruckId));
		motorcadeWeal.setFasInvoiceType(fasInvoiceTypeService.getById(fasInvoiceTypeId));
		motorcadeWeal.setCurrency("人民币");
		motorcadeWeal.setExchangeRate(1);
		if(motorcadeWeal.getId() == null){
			motorcadeWealService.add(motorcadeWeal);
		}else{
			motorcadeWealService.modify(motorcadeWeal);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:motor-weal-list.do";
	}
	
	@RequestMapping(value = "motor-weal-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for(String id : selectedItem){
			motorcadeWealService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:motor-weal-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-weal-check-typename")
	public boolean checkColumnName(@RequestParam(required=true, value="typeName") String typeName, String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM FRE_EXPENSE_TYPE WHERE TYPE_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, typeName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM FRE_EXPENSE_TYPE WHERE TYPE_NAME = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, typeName);
			return count == 0;
		}
	}
	
	@RequestMapping(value = "motor-weal-to-export-weal-to-file")
	@ResponseBody
	public String doneBatchExport(@RequestParam(value="selectedItem", required=true)String[] selectedItem) throws IOException{
		String targetFile = StringUtil.getUUID() + ".xls";
		FileUtils.copyInputStreamToFile(IExmportUtil.exportMultiColumn(
				new String[]{"类型", "时间", "金额","票种", "说明"}, 
				motorcadeWealService.doneBatchExport(selectedItem)), new File(FileUtil.attachmentPath, targetFile));
		return targetFile;
	}
}
