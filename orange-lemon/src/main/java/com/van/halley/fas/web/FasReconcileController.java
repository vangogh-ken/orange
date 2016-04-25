package com.van.halley.fas.web;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.FasReconcile;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FasAccountService;
import com.van.service.FasReconcileService;
import com.van.service.FreightInvoiceService;

@Controller
@RequestMapping(value = "/fas/")
public class FasReconcileController {
	@Autowired
	private FasReconcileService fasReconcileService;
	@Autowired
	private FreightInvoiceService freightInvoiceService;
	@Autowired
	private FasAccountService  fasAccountService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fas-reconcile-list")
	public String unread(Model model, FasReconcile fasReconcile, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = fasReconcileService.query(pageView, fasReconcile);

		model.addAttribute("pageView", pageView);
		return "/content/fas/fas-reconcile-list";
	}

	@RequestMapping(value = "fas-reconcile-input")
	public String input(Model model, String id) {
		FasReconcile item = null;
		if (id != null) {
			item = fasReconcileService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fas/fas-reconcile-input";
	}

	@RequestMapping(value = "fas-reconcile-save", method = RequestMethod.POST)
	public String add(FasReconcile fasReconcile, RedirectAttributes redirectAttributes) {
		if(fasReconcile.getId() == null){
			fasReconcileService.add(fasReconcile);
		}else{
			fasReconcileService.modify(fasReconcile);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fas-reconcile-list.do";
	}
	
	@RequestMapping(value = "fas-reconcile-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			fasReconcileService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fas-reconcile-list.do";
	}
	
	
	/*@ResponseBody
	@RequestMapping(value = "fas-reconcile-by-freightinvoice")
	public Map<String, Object> getByStatementIdId(@RequestParam(value="freightInvoiceId", required=true)String freightInvoiceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		FasReconcile filter = new FasReconcile();
		FreightInvoice freightInvoice = freightInvoiceService.getById(freightInvoiceId);
		if(freightInvoice != null){
			filter.setFreightInvoice(freightInvoice);
			map.put("hasAddReconcile", fasReconcileService.queryForList(filter));
			map.put("freightInvoice", freightInvoice);
			map.put("fasAccounts", fasAccountService.getOwnAccount());
		}else{
			map.put("hasAddReconcile", null);
			map.put("freightInvoice", null);
			map.put("fasAccounts", null);
		}
		
		return map;
	}
	
	
	*//**
	 * 添加销账记录
	 *//*
	@ResponseBody
	@RequestMapping(value = "fas-reconcile-save-to-freightinvoice")
	public String saveAsync(@RequestBody(required=true) FasReconcile fasReconcile, 
			@RequestParam(value="freightInvoiceId", required=true)String freightInvoiceId,
			@RequestParam(value="fasAccountId", required=true)String fasAccountId) {
		fasReconcileService.addReconcile(fasReconcile, freightInvoiceId, fasAccountId);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-reconcile-remove-async")
	public String removeAsync(@RequestParam(value="reconcileIds", required=true)String reconcileIds) {
		String[] fasReconcileIds = reconcileIds.split(",");
		fasReconcileService.removeReconcile(fasReconcileIds);
		return "success";
	}*/
	
	@ResponseBody
	@RequestMapping(value = "fas-reconcile-to-export-reconcile-pay")
	public void exportPay(String fasPayId, HttpServletResponse response){
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"业务员", "订单号", "单位名称","开票日期","发票号","发票类型","内容摘要","应付(人民币)","应付(美元)","销账金额","销账时间"}, 
				fasReconcileService.toExportByFasPayID(fasPayId)), "销账列表.xls", response);
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-reconcile-to-export-reconcile-due")
	public void exportDue(String fasDueId, HttpServletResponse response){
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"业务员", "订单号", "单位名称","开票日期","发票号","发票类型","内容摘要","应收(人民币)","应收(美元)","销账金额","销账时间"}, 
				fasReconcileService.toExportByFasDueID(fasDueId)), "销账列表.xls", response);
	}
}
