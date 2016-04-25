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
import com.van.halley.db.persistence.entity.MotorcadeToll;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FasInvoiceTypeService;
import com.van.service.MotorcadeDriverService;
import com.van.service.MotorcadeTollService;
import com.van.service.MotorcadeTruckService;

@Controller
@RequestMapping(value = "/motor/")
public class MotorcadeTollController {
	@Autowired
	private MotorcadeTollService motorcadeTollService;
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

	@RequestMapping(value = "motor-toll-list")
	public String unread(Model model, MotorcadeToll motorcadeToll, 
			String headstockNumber, String displayName, String beginTimeStart, String beginTimeEnd, 
			String arriveTimeStart, String arriveTimeEnd, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		if(!StringUtil.isNullOrEmpty(motorcadeToll.getBeginStation())){
			String filterText = " (BEGIN_STATION LIKE '%" + motorcadeToll.getBeginStation() + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
			
			motorcadeToll.setBeginStation(null);
		}
		
		if(!StringUtil.isNullOrEmpty(motorcadeToll.getArriveStation())){
			String filterText = " (ARRIVE_STATION LIKE '%" + motorcadeToll.getArriveStation() + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
			
			motorcadeToll.setArriveStation(null);
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
		
		if(!StringUtil.isNullOrEmpty(beginTimeStart) && !StringUtil.isNullOrEmpty(beginTimeEnd)){
			String filterText = " (BEGIN_TIME BETWEEN '" + beginTimeStart + "' AND '" + beginTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(arriveTimeStart) && !StringUtil.isNullOrEmpty(arriveTimeEnd)){
			String filterText = " (ARRIVE_TIME BETWEEN '" + arriveTimeStart + "' AND '" + arriveTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		pageView = motorcadeTollService.query(pageView, motorcadeToll);
		model.addAttribute("pageView", pageView);
		return "/content/motor/motor-toll-list";
	}

	@RequestMapping(value = "motor-toll-input")
	public String input(Model model, String id) {
		MotorcadeToll item = null;
		if (id != null) {
			item = motorcadeTollService.getById(id);
		}
		model.addAttribute("motorcadeDrivers", motorcadeDriverService.getAll());
		model.addAttribute("motorcadeTrucks", motorcadeTruckService.getAll());
		model.addAttribute("fasInvoiceTypes", fasInvoiceTypeService.getAll());
		model.addAttribute("item", item);
		return "/content/motor/motor-toll-input";
	}

	@RequestMapping(value = "motor-toll-save", method = RequestMethod.POST)
	public String add(MotorcadeToll motorcadeToll, String motorcadeDriverId, String motorcadeTruckId, 
			String fasInvoiceTypeId, RedirectAttributes redirectAttributes) {
		
		motorcadeToll.setMotorcadeTruck(motorcadeTruckService.getById(motorcadeTruckId));
		motorcadeToll.setMotorcadeDriver(motorcadeDriverService.getById(motorcadeDriverId));
		motorcadeToll.setFasInvoiceType(fasInvoiceTypeService.getById(fasInvoiceTypeId));
		motorcadeToll.setCurrency("人民币");
		motorcadeToll.setExchangeRate(1);
		if(motorcadeToll.getId() == null){
			motorcadeTollService.add(motorcadeToll);
		}else{
			motorcadeTollService.modify(motorcadeToll);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:motor-toll-list.do";
	}
	
	@RequestMapping(value = "motor-toll-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for(String id : selectedItem){
			motorcadeTollService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:motor-toll-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-toll-check-typename")
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
	
	@RequestMapping(value = "motor-toll-to-export-toll-to-file")
	@ResponseBody
	public String doneBatchExport(@RequestParam(value="selectedItem", required=true)String[] selectedItem) throws IOException{
		String targetFile = StringUtil.getUUID() + ".xls";
		FileUtils.copyInputStreamToFile(IExmportUtil.exportMultiColumn(
				new String[]{"司机", "车牌号", "车型","车重","进站","进站时间","出站","出站时间", "金额", "票种", "说明"}, 
				motorcadeTollService.doneBatchExport(selectedItem)), new File(FileUtil.attachmentPath, targetFile));
		return targetFile;
	}

}
