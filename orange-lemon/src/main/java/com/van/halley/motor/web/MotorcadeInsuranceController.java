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
import com.van.halley.db.persistence.entity.MotorcadeInsurance;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FasInvoiceTypeService;
import com.van.service.MotorcadeInsuranceService;
import com.van.service.MotorcadeTruckService;

@Controller
@RequestMapping(value = "/motor/")
public class MotorcadeInsuranceController {
	@Autowired
	private MotorcadeInsuranceService motorcadeInsuranceService;
	@Autowired
	private MotorcadeTruckService motorcadeTruckService;
	@Autowired
	private FasInvoiceTypeService fasInvoiceTypeService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "motor-insurance-list")
	public String unread(Model model, MotorcadeInsurance motorcadeInsurance, 
			String headstockNumber, String purchaseTimeStart, String purchaseTimeEnd, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		if(!StringUtil.isNullOrEmpty(motorcadeInsurance.getInsuranceType())){
			String filterText = " (INSURANCE_TYPE LIKE '%" + motorcadeInsurance.getInsuranceType() + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
			
			motorcadeInsurance.setInsuranceType(null);
		}
		
		if(!StringUtil.isNullOrEmpty(motorcadeInsurance.getInsuranceCompany())){
			String filterText = " (INSURANCE_COMPANY LIKE '%" + motorcadeInsurance.getInsuranceCompany() + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
			
			motorcadeInsurance.setInsuranceCompany(null);
		}
		
		if(!StringUtil.isNullOrEmpty(headstockNumber)){
			String filterText = " MOTOR_TRUCK_ID IN (SELECT ID FROM MOTOR_TRUCK WHERE HEADSTOCK_NUMBER LIKE '%" + headstockNumber + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(purchaseTimeStart) && !StringUtil.isNullOrEmpty(purchaseTimeEnd)){
			String filterText = " (PURCHASE_TIME BETWEEN '" + purchaseTimeStart + "' AND '" + purchaseTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		pageView = motorcadeInsuranceService.query(pageView, motorcadeInsurance);
		model.addAttribute("pageView", pageView);
		return "/content/motor/motor-insurance-list";
	}

	@RequestMapping(value = "motor-insurance-input")
	public String input(Model model, String id) {
		MotorcadeInsurance item = null;
		if (id != null) {
			item = motorcadeInsuranceService.getById(id);
		}
		model.addAttribute("motorcadeTrucks", motorcadeTruckService.getAll());
		model.addAttribute("fasInvoiceTypes", fasInvoiceTypeService.getAll());
		model.addAttribute("item", item);
		return "/content/motor/motor-insurance-input";
	}

	@RequestMapping(value = "motor-insurance-save", method = RequestMethod.POST)
	public String add(MotorcadeInsurance motorcadeInsurance, String motorcadeDriverId, String motorcadeTruckId, 
			String fasInvoiceTypeId, RedirectAttributes redirectAttributes) {
		
		motorcadeInsurance.setMotorcadeTruck(motorcadeTruckService.getById(motorcadeTruckId));
		motorcadeInsurance.setFasInvoiceType(fasInvoiceTypeService.getById(fasInvoiceTypeId));
		motorcadeInsurance.setCurrency("人民币");
		motorcadeInsurance.setExchangeRate(1);
		if(motorcadeInsurance.getId() == null){
			motorcadeInsuranceService.add(motorcadeInsurance);
		}else{
			motorcadeInsuranceService.modify(motorcadeInsurance);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:motor-insurance-list.do";
	}
	
	@RequestMapping(value = "motor-insurance-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for(String id : selectedItem){
			motorcadeInsuranceService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:motor-insurance-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-insurance-check-typename")
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

	@RequestMapping(value = "motor-insurance-to-export-insurance-to-file")
	@ResponseBody
	public String doneBatchExport(@RequestParam(value="selectedItem", required=true)String[] selectedItem) throws IOException{
		String targetFile = StringUtil.getUUID() + ".xls";
		FileUtils.copyInputStreamToFile(IExmportUtil.exportMultiColumn(
				new String[]{"车牌号", "购买时间", "保险险种","保险公司","开始时间","结束时间","金额","月均金额", "票种", "说明"}, 
				motorcadeInsuranceService.doneBatchExport(selectedItem)), new File(FileUtil.attachmentPath, targetFile));
		return targetFile;
	}

}
