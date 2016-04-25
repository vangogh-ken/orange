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
import com.van.halley.db.persistence.entity.FasInvoice;
import com.van.halley.util.StringUtil;
import com.van.service.FasInvoiceService;
import com.van.service.FasInvoiceTypeService;

@Controller
@RequestMapping(value = "/fas/")
public class FasInvoiceController {
	@Autowired
	private FasInvoiceService fasInvoiceService;
	@Autowired
	private FasInvoiceTypeService fasInvoiceTypeService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fas-invoice-list")
	public String unread(Model model, FasInvoice fasInvoice, String invoiceNumber_, String fasInvoiceTypeId, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(!StringUtil.isNullOrEmpty(fasInvoiceTypeId)){
			fasInvoice.setFasInvoiceType(fasInvoiceTypeService.getById(fasInvoiceTypeId));
		}
		if(!StringUtil.isNullOrEmpty(invoiceNumber_)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" INVOICE_NUMBER LIKE '%"+ invoiceNumber_ +"%'");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND INVOICE_NUMBER LIKE '%"+ invoiceNumber_ +"%'");
			}
		}
		
		pageView = fasInvoiceService.query(pageView, fasInvoice);
		
		model.addAttribute("pageView", pageView);
		model.addAttribute("fasInvoiceTypes", fasInvoiceTypeService.getAll());
		return "/content/fas/fas-invoice-list";
	}

	@RequestMapping(value = "fas-invoice-input")
	public String input(Model model, String id) {
		FasInvoice item = null;
		if (id != null) {
			item = fasInvoiceService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("fasInvoiceTypes", fasInvoiceTypeService.getAll());
		return "/content/fas/fas-invoice-input";
	}
	
	@RequestMapping(value = "fas-invoice-input-batch")
	public String inputBatch(Model model, String id) {
		FasInvoice item = null;
		if (id != null) {
			item = fasInvoiceService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("fasInvoiceTypes", fasInvoiceTypeService.getAll());
		return "/content/fas/fas-invoice-input-batch";
	}

	@RequestMapping(value = "fas-invoice-save", method = RequestMethod.POST)
	public String add(FasInvoice fasInvoice, String fasInvoiceTypeId, RedirectAttributes redirectAttributes) {
		fasInvoice.setFasInvoiceType(fasInvoiceTypeService.getById(fasInvoiceTypeId));
		if(fasInvoice.getId() == null){
			fasInvoiceService.add(fasInvoice);
		}else{
			fasInvoiceService.modify(fasInvoice);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fas-invoice-list.do";
	}
	
	/**
	 * 批量添加发票
	 * @param fasInvoice
	 * @param startInvoiceNumber
	 * @param endInvoiceNumber
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "fas-invoice-save-batch", method = RequestMethod.POST)
	public String addBatch(FasInvoice fasInvoice, 
			@RequestParam(value="startInvoiceNumber",required=true)String startInvoiceNumber, 
			@RequestParam(value="endInvoiceNumber",required=true)String endInvoiceNumber, 
			String fasInvoiceTypeId, RedirectAttributes redirectAttributes) {
		
		fasInvoice.setFasInvoiceType(fasInvoiceTypeService.getById(fasInvoiceTypeId));
		int count = fasInvoiceService.addBatch(fasInvoice, startInvoiceNumber, endInvoiceNumber);
		messageHelper.addFlashMessage(redirectAttributes,"添加成功" + count + "条");
		return "redirect:fas-invoice-list.do";
	}
	
	@RequestMapping(value = "fas-invoice-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			fasInvoiceService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fas-invoice-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-invoice-check-invoicenumber")
	public boolean checkName(@RequestParam(required=true, value="invoiceNumber") String invoiceNumber,  String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM FAS_INVOICE WHERE INVOICE_NUMBER = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, invoiceNumber, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM FAS_INVOICE WHERE INVOICE_NUMBER = ? ";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, invoiceNumber);
			return count == 0;
		}
	}

}
