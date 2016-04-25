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
import com.van.halley.db.persistence.entity.MotorcadeMaintain;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FasInvoiceTypeService;
import com.van.service.MotorcadeDriverService;
import com.van.service.MotorcadeMaintainService;
import com.van.service.MotorcadeTruckService;

@Controller
@RequestMapping(value = "/motor/")
public class MotorcadeMaintainController {
	@Autowired
	private MotorcadeMaintainService motorcadeMaintainService;
	@Autowired
	private MotorcadeTruckService motorcadeTruckService;
	@Autowired
	private MotorcadeDriverService motorcadeDriverService;
	@Autowired
	private FasInvoiceTypeService fasInvoiceTypeService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "motor-maintain-list")
	public String unread(Model model, MotorcadeMaintain motorcadeMaintain, 
			String headstockNumber, String displayName,  String maintainTimeStart, String maintainTimeEnd, 
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
		if(!StringUtil.isNullOrEmpty(maintainTimeStart) && !StringUtil.isNullOrEmpty(maintainTimeEnd)){
			String filterText = " (MAINTAIN_TIME BETWEEN '" + maintainTimeStart + "' AND '" + maintainTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		pageView = motorcadeMaintainService.query(pageView, motorcadeMaintain);
		model.addAttribute("pageView", pageView);
		return "/content/motor/motor-maintain-list";
	}

	@RequestMapping(value = "motor-maintain-input")
	public String input(Model model, String id) {
		MotorcadeMaintain item = null;
		if (id != null) {
			item = motorcadeMaintainService.getById(id);
		}
		
		model.addAttribute("motorcadeDrivers", motorcadeDriverService.getAll());
		model.addAttribute("motorcadeTrucks", motorcadeTruckService.getAll());
		model.addAttribute("fasInvoiceTypes", fasInvoiceTypeService.getAll());
		model.addAttribute("item", item);
		return "/content/motor/motor-maintain-input";
	}

	@RequestMapping(value = "motor-maintain-save", method = RequestMethod.POST)
	public String add(MotorcadeMaintain motorcadeMaintain, String motorcadeDriverId, String motorcadeTruckId, 
			String fasInvoiceTypeId, RedirectAttributes redirectAttributes) {
		motorcadeMaintain.setMotorcadeDriver(motorcadeDriverService.getById(motorcadeDriverId));
		motorcadeMaintain.setMotorcadeTruck(motorcadeTruckService.getById(motorcadeTruckId));
		motorcadeMaintain.setFasInvoiceType(fasInvoiceTypeService.getById(fasInvoiceTypeId));
		motorcadeMaintain.setCurrency("人民币");
		motorcadeMaintain.setExchangeRate(1);
		motorcadeMaintain.setMoneyAmount(motorcadeMaintain.getMoneyCount() * motorcadeMaintain.getMaintainCount());
		if(motorcadeMaintain.getId() == null){
			motorcadeMaintainService.add(motorcadeMaintain);
		}else{
			motorcadeMaintainService.modify(motorcadeMaintain);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:motor-maintain-list.do";
	}
	
	@RequestMapping(value = "motor-maintain-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for(String id : selectedItem){
			motorcadeMaintainService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:motor-maintain-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-maintain-check-typename")
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

	@RequestMapping(value = "motor-maintain-to-export-maintain-to-file")
	@ResponseBody
	public String doneBatchExport(@RequestParam(value="selectedItem", required=true)String[] selectedItem) throws IOException{
		String targetFile = StringUtil.getUUID() + ".xls";
		FileUtils.copyInputStreamToFile(IExmportUtil.exportMultiColumn(
				new String[]{"司机", "车牌号", "维修项目","维修时间","计价单位","数量","单价","总额", "票种", "说明"}, 
				motorcadeMaintainService.doneBatchExport(selectedItem)), new File(FileUtil.attachmentPath, targetFile));
		return targetFile;
	}

}
