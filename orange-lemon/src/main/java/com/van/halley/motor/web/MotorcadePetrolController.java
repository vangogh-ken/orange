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
import com.van.halley.db.persistence.entity.MotorcadePetrol;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FasInvoiceTypeService;
import com.van.service.MotorcadeDriverService;
import com.van.service.MotorcadePetrolService;
import com.van.service.MotorcadeTruckService;

@Controller
@RequestMapping(value = "/motor/")
public class MotorcadePetrolController {
	@Autowired
	private MotorcadePetrolService motorcadePetrolService;
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

	@RequestMapping(value = "motor-petrol-list")
	public String unread(Model model, MotorcadePetrol motorcadePetrol, 
			String headstockNumber, String displayName,  String lubricateTimeStart, String lubricateTimeEnd, 
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
		
		if(!StringUtil.isNullOrEmpty(lubricateTimeStart) && !StringUtil.isNullOrEmpty(lubricateTimeEnd)){
			String filterText = " (LUBRICATE_TIME BETWEEN '" + lubricateTimeStart + "' AND '" + lubricateTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		pageView = motorcadePetrolService.query(pageView, motorcadePetrol);
		model.addAttribute("pageView", pageView);
		return "/content/motor/motor-petrol-list";
	}

	@RequestMapping(value = "motor-petrol-input")
	public String input(Model model, String id) {
		MotorcadePetrol item = null;
		if (id != null) {
			item = motorcadePetrolService.getById(id);
		}
		model.addAttribute("motorcadeDrivers", motorcadeDriverService.getAll());
		model.addAttribute("motorcadeTrucks", motorcadeTruckService.getAll());
		model.addAttribute("fasInvoiceTypes", fasInvoiceTypeService.getAll());
		model.addAttribute("item", item);
		return "/content/motor/motor-petrol-input";
	}

	@RequestMapping(value = "motor-petrol-save", method = RequestMethod.POST)
	public String add(MotorcadePetrol motorcadePetrol, String motorcadeDriverId, String motorcadeTruckId, 
			String fasInvoiceTypeId, RedirectAttributes redirectAttributes) {
		
		motorcadePetrol.setMotorcadeDriver(motorcadeDriverService.getById(motorcadeDriverId));
		motorcadePetrol.setMotorcadeTruck(motorcadeTruckService.getById(motorcadeTruckId));
		motorcadePetrol.setFasInvoiceType(fasInvoiceTypeService.getById(fasInvoiceTypeId));
		motorcadePetrol.setCurrency("人民币");
		motorcadePetrol.setExchangeRate(1);
		if(motorcadePetrol.getId() == null){
			motorcadePetrolService.add(motorcadePetrol);
		}else{
			motorcadePetrolService.modify(motorcadePetrol);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:motor-petrol-list.do";
	}
	
	@RequestMapping(value = "motor-petrol-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for(String id : selectedItem){
			motorcadePetrolService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:motor-petrol-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-petrol-check-typename")
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

	@RequestMapping(value = "motor-petrol-to-export-petrol-to-file")
	@ResponseBody
	public String doneBatchExport(@RequestParam(value="selectedItem", required=true)String[] selectedItem) throws IOException{
		String targetFile = StringUtil.getUUID() + ".xls";
		FileUtils.copyInputStreamToFile(IExmportUtil.exportMultiColumn(
				new String[]{"车牌号", "司机", "加油容积","加油时间","公里数","上次油耗","实时月均油耗","金额", "票种", "说明"}, 
				motorcadePetrolService.doneBatchExport(selectedItem)), new File(FileUtil.attachmentPath, targetFile));
		return targetFile;
	}

}
