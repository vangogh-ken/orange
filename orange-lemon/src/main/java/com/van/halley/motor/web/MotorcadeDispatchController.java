package com.van.halley.motor.web;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.rep.ReportUtil;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.MotorcadeDispatch;
import com.van.halley.db.persistence.entity.MotorcadeFee;
import com.van.halley.db.persistence.entity.ReportTemplate;
import com.van.halley.fre.util.FreightFilterUtil;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.service.FasInvoiceTypeService;
import com.van.service.MotorcadeDispatchService;
import com.van.service.MotorcadeDriverService;
import com.van.service.MotorcadeTruckService;
import com.van.service.ReportTemplateService;

@Controller
@RequestMapping(value = "/motor/")
public class MotorcadeDispatchController {
	@Autowired
	private MotorcadeDispatchService motorcadeDispatchService;
	@Autowired
	private MotorcadeTruckService motorcadeTruckService;
	@Autowired
	private MotorcadeDriverService motorcadeDriverService;
	@Autowired
	private FasInvoiceTypeService fasInvoiceTypeService;
	@Autowired
	private ReportTemplateService reportTemplateService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "motor-dispatch-list")
	public String unread(Model model, MotorcadeDispatch motorcadeDispatch, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		if(!StringUtil.isNullOrEmpty(motorcadeDispatch.getMotorcadeDriver())){
			String filterText = " MOTOR_DRIVER LIKE '%" + motorcadeDispatch.getMotorcadeDriver() + "%' ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + "AND " + filterText);
			}
			
			motorcadeDispatch.setMotorcadeDriver(null);
		}
		
		if(!StringUtil.isNullOrEmpty(motorcadeDispatch.getMotorcadeTruck())){
			String filterText = " MOTOR_TRUCK LIKE '%" + motorcadeDispatch.getMotorcadeTruck() + "%' ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + "AND " + filterText);
			}
			
			motorcadeDispatch.setMotorcadeTruck(null);
		}
		
		if(!StringUtil.isNullOrEmpty(motorcadeDispatch.getDispatchNumber())){
			String filterText = " DISPATCH_NUMBER LIKE '%" + motorcadeDispatch.getDispatchNumber() + "%' ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + "AND " + filterText);
			}
			
			motorcadeDispatch.setDispatchNumber(null);
		}
		
		if(!StringUtil.isNullOrEmpty(motorcadeDispatch.getOrderNumber())){
			String filterText = " ORDER_NUMBER LIKE '%" + motorcadeDispatch.getOrderNumber() + "%' ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + "AND " + filterText);
			}
			
			motorcadeDispatch.setOrderNumber(null);
		}
		
		if(!StringUtil.isNullOrEmpty(motorcadeDispatch.getBoxNumber())){
			String filterText = " BOX_NUMBER LIKE '%" + motorcadeDispatch.getBoxNumber() + "%' ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + "AND " + filterText);
			}
			
			motorcadeDispatch.setBoxNumber(null);
		}
		
		pageView = motorcadeDispatchService.query(pageView, motorcadeDispatch);
		model.addAttribute("pageView", pageView);
		return "/content/motor/motor-dispatch-list";
	}

	@RequestMapping(value = "motor-dispatch-input")
	public String input(Model model, String id) {
		MotorcadeDispatch item = null;
		if (id != null) {
			item = motorcadeDispatchService.getById(id);
		}else{
			item = new MotorcadeDispatch();
			item.setCargoUnit("无");
			item.setCargoCapacity("0");
			item.setCargoWeight("0");
		}
		model.addAttribute("item", item);
		return "/content/motor/motor-dispatch-input";
	}

	@RequestMapping(value = "motor-dispatch-save", method = RequestMethod.POST)
	public String add(MotorcadeDispatch motorcadeDispatch, String motorcadeTruckId, String motorcadeDriverId, 
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if(StringUtil.isNullOrEmpty(motorcadeDispatch.getDispatchNumber())){
			String userName = ((com.van.halley.db.persistence.entity.User)request.getSession().getAttribute("userSession")).getUserName();
			motorcadeDispatch.setDispatchNumber(motorcadeDispatchService.getNextDispatchNumber(userName));
		}
		if(motorcadeDispatch.getId() == null){
			motorcadeDispatch.setStatus("未提交");
			motorcadeDispatchService.add(motorcadeDispatch);
		}else{
			motorcadeDispatchService.modify(motorcadeDispatch);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:motor-dispatch-list.do";
	}
	
	@RequestMapping(value = "motor-dispatch-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for(String id : selectedItem){
			motorcadeDispatchService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:motor-dispatch-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-dispatch-to-add-fee", method = RequestMethod.POST)
	public Map<String, Object> toAddFee(@RequestParam(value="motorcadeDispatchId", required=true)String motorcadeDispatchId){
		return motorcadeDispatchService.toAddFee(motorcadeDispatchId);
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-dispatch-done-add-fee", method = RequestMethod.POST)
	public boolean doneAddFee(@RequestParam(value="motorcadeDispatchId", required=true)String motorcadeDispatchId, 
			@RequestParam(value="fasInvoiceTypeId", required=true)String fasInvoiceTypeId, 
			String motorcadeFeeId,
			@RequestBody MotorcadeFee motorcadeFee){
		if(!StringUtil.isNullOrEmpty(motorcadeFeeId)){
			motorcadeFee.setId(motorcadeFeeId);
		}
		motorcadeFee.setCurrency("人民币");
		motorcadeFee.setExchangeRate(1);
		motorcadeFee.setFasInvoiceType(fasInvoiceTypeService.getById(fasInvoiceTypeId));
		return motorcadeDispatchService.doneAddFee(motorcadeDispatchId, motorcadeFee);
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-dispatch-to-add-dispatch-by-copy", method = RequestMethod.POST)
	public Map<String, Object> toAddDispatchByCopy(
			@RequestParam(value="motorcadeDispatchId", required=true)String motorcadeDispatchId){
		return motorcadeDispatchService.toAddDispatchByCopy(motorcadeDispatchId);
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-dispatch-done-add-dispatch-by-copy", method = RequestMethod.POST)
	public String doneAddDispatchByCopy(@RequestParam(value="motorcadeDispatchId", required=true)String motorcadeDispatchId, 
			String[] motorcadeFeeId, HttpServletRequest request){
		String userName = ((com.van.halley.db.persistence.entity.User)request.getSession().getAttribute("userSession")).getUserName();
		return motorcadeDispatchService.doneAddDispatchByCopy(motorcadeDispatchId, motorcadeFeeId, userName);
	}
	
	@RequestMapping(value = "motor-dispatch-to-view")
	public void toView(@RequestParam(value="motorcadeDispatchId", required=true)String motorcadeDispatchId, 
			HttpServletResponse response){
		ReportTemplate reportTemplate = reportTemplateService.getById("0b17d535-f6ae-4a7e-9337-57588eee26cf");
		Map<String, Object> dataSource = new HashMap<String, Object>();
		MotorcadeDispatch motorcadeDispatch = motorcadeDispatchService.getById(motorcadeDispatchId);
		dataSource.put("DISPATCH", motorcadeDispatch);
		FileUtil.download(
				ReportUtil.createSimpleReportToStream(dataSource, new File(FileUtil.attachmentPath, reportTemplate.getTemplateFile())), 
				"派车单" + motorcadeDispatch.getDispatchNumber() + "." + reportTemplate.getTemplateFile().split("\\.")[1], 
				response);
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-dispatch-to-browse")
	public String toBrowse(@RequestParam(value="motorcadeDispatchId", required=true)String motorcadeDispatchId, 
			HttpServletResponse response){
		ReportTemplate reportTemplate = reportTemplateService.getById("0b17d535-f6ae-4a7e-9337-57588eee26cf");
		Map<String, Object> dataSource = new HashMap<String, Object>();
		MotorcadeDispatch motorcadeDispatch = motorcadeDispatchService.getById(motorcadeDispatchId);
		dataSource.put("DISPATCH", motorcadeDispatch);
		return ReportUtil.createSimpleReportToFile(dataSource, new File(FileUtil.attachmentPath, reportTemplate.getTemplateFile()));
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-dispatch-to-remove-dispatch", method = RequestMethod.POST)
	public String toRemoveDispatch(
			@RequestParam(value="motorcadeDispatchId", required=true)String[] motorcadeDispatchId){
		if(motorcadeDispatchService.toRemoveDispatch(motorcadeDispatchId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-dispatch-to-validate-boxnumber", method = RequestMethod.POST)
	public boolean validateBoxNumber(
			@RequestParam(value="boxNumber", required=true)String boxNumber){
		boolean flag = true;
		boxNumber = boxNumber.trim();
		if((boxNumber.length() + 1) % 12 == 0){
			while(boxNumber.length() >= 11){
				if(!FreightFilterUtil.validateBoxNumber(boxNumber.substring(0, 11))){
					flag = false;
					break;
				}else{
					boxNumber = boxNumber.substring(11, boxNumber.length());
					if(boxNumber.length() >= 11){
						boxNumber = boxNumber.substring(1, boxNumber.length());
					}
				}
			}
		}else{
			flag = false;
		}
		
		return flag;
	}
	//确认
	@RequestMapping(value = "motor-dispatch-done-confirm-dispatch")
	@ResponseBody
	public String doneConfirmDispatch(@RequestParam(value="motorcadeDispatchId", required=true)String[] motorcadeDispatchId) {
		if(motorcadeDispatchService.doneConfirmDispatch(motorcadeDispatchId)){
			return "success";
		}else{
			return "error";
		}
	}
	//取消确认
	@RequestMapping(value = "motor-dispatch-done-recall-dispatch")
	@ResponseBody
	public String doneRecallDispatch(@RequestParam(value="motorcadeDispatchId", required=true)String[] motorcadeDispatchId) {
		if(motorcadeDispatchService.doneRecallDispatch(motorcadeDispatchId)){
			return "success";
		}else{
			return "error";
		}
	}
	/*
	@ResponseBody
	@RequestMapping(value = "motor-dispatch-to-import-expense-type", method = RequestMethod.POST)
	public String add(MultipartHttpServletRequest request, String valueAttributeId)
			throws IOException, FileUploadException {
		Map<String, String> map = FileUtil.upload("file", request);
		List<List<String>> values = IExmportUtil.importMultiColumn(new int[]{0}, 1, map.get("fileData"));
		motorcadeDispatchService.toImport(values);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-dispatch-to-export-expense-type")
	public void export(HttpServletResponse response)throws IOException, FileUploadException {
		List<FreightExpenseType> freightExpenseTypes = freightExpenseTypeService.getAll();
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"费用名称"}, motorcadeDispatchService.toExport(freightExpenseTypes)), "费用列表.xls", response);
	}*/

}
