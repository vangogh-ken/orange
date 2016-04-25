package com.van.halley.motor.web;


import java.util.HashMap;
import java.util.Map;

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
import com.van.halley.db.persistence.entity.MotorcadeFee;
import com.van.service.MotorcadeFeeService;

@Controller
@RequestMapping(value = "/motor/")
public class MotorcadeFeeController {
	@Autowired
	private MotorcadeFeeService motorcadeFeeService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "motor-fee-list")
	public String unread(Model model, MotorcadeFee motorcadeFee, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		pageView = motorcadeFeeService.query(pageView, motorcadeFee);
		model.addAttribute("pageView", pageView);
		return "/content/motor/motor-fee-list";
	}

	@RequestMapping(value = "motor-fee-input")
	public String input(Model model, String id) {
		MotorcadeFee item = null;
		if (id != null) {
			item = motorcadeFeeService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/motor/motor-fee-input";
	}

	@RequestMapping(value = "motor-fee-save", method = RequestMethod.POST)
	public String add(MotorcadeFee motorcadeFee, RedirectAttributes redirectAttributes) {
		if(motorcadeFee.getId() == null){
			motorcadeFeeService.add(motorcadeFee);
		}else{
			motorcadeFeeService.modify(motorcadeFee);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:motor-fee-list.do";
	}
	
	@RequestMapping(value = "motor-fee-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for(String id : selectedItem){
			motorcadeFeeService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:motor-fee-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-fee-check-typename")
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
	
	@ResponseBody
	@RequestMapping(value = "motor-fee-done-delete-fee", method = RequestMethod.POST)
	public String doneDeleteFee(@RequestParam(value="motorcadeFeeId", required=true)String[] motorcadeFeeId){
		for(String id : motorcadeFeeId){
			motorcadeFeeService.delete(id);
		}
		
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-fee-to-revise-fee", method = RequestMethod.POST)
	public Map<String, Object> toReviseFee(@RequestParam(value="motorcadeFeeId", required=true)String motorcadeFeeId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("motorcadeFee", motorcadeFeeService.getById(motorcadeFeeId));
		return map;
	}
	/*
	@ResponseBody
	@RequestMapping(value = "motor-fee-to-import-expense-type", method = RequestMethod.POST)
	public String add(MultipartHttpServletRequest request, String valueAttributeId)
			throws IOException, FileUploadException {
		Map<String, String> map = FileUtil.upload("file", request);
		List<List<String>> values = IExmportUtil.importMultiColumn(new int[]{0}, 1, map.get("fileData"));
		motorcadeDispatchService.toImport(values);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "motor-fee-to-export-expense-type")
	public void export(HttpServletResponse response)throws IOException, FileUploadException {
		List<FreightExpenseType> freightExpenseTypes = freightExpenseTypeService.getAll();
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"费用名称"}, motorcadeDispatchService.toExport(freightExpenseTypes)), "费用列表.xls", response);
	}*/

}
