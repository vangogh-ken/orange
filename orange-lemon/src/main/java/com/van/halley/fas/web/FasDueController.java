package com.van.halley.fas.web;


import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.van.halley.db.persistence.entity.FasDue;
import com.van.halley.db.persistence.entity.FreightPart;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FasAccountService;
import com.van.service.FasDueService;
import com.van.service.FasExchangeRateService;
import com.van.service.FreightPartService;

@Controller
@RequestMapping(value = "/fas/")
public class FasDueController {
	@Autowired
	private FasDueService fasDueService;
	@Autowired
	private FasAccountService fasAccountService;
	@Autowired
	private FreightPartService freightPartService;
	@Autowired
	private FasExchangeRateService fasExchangeRateService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fas-due-list")
	public String unread(Model model, FasDue fasDue, String partName, 
			String dueCountStart, String dueCountEnd, String dueTimeStart, String dueTimeEnd, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			pageView.setOrderBy("CREATE_TIME");
			pageView.setOrder("DESC");
		}
		if(!StringUtil.isNullOrEmpty(partName)){
			String filterString = " FRE_PART_ID_PAY IN(SELECT ID FROM FRE_PART WHERE PART_NAME LIKE '%" + partName + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(dueCountStart) && !StringUtil.isNullOrEmpty(dueCountEnd)){
			String filterText = " (DUE_COUNT BETWEEN " + dueCountStart + " AND " + dueCountEnd + ") ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(dueTimeStart) && !StringUtil.isNullOrEmpty(dueTimeEnd)){
			String filterText = " (DUE_TIME BETWEEN '" + dueTimeStart + "' AND '" + dueTimeEnd + "') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		pageView = fasDueService.query(pageView, fasDue);

		model.addAttribute("pageView", pageView);
		return "/content/fas/fas-due-list";
	}

	@RequestMapping(value = "fas-due-input")
	public String input(Model model, String id) {
		FasDue item = null;
		if (id != null) {
			item = fasDueService.getById(id);
		}
		
		model.addAttribute("ownAccounts", fasAccountService.getOwnAccount());
		FreightPart filter = new FreightPart();
		filter.setInternal("F");
		//model.addAttribute("payParts", freightPartService.getAll());
		model.addAttribute("payParts", freightPartService.queryForList(filter));
		model.addAttribute("item", item);
		return "/content/fas/fas-due-input";
	}

	@RequestMapping(value = "fas-due-save", method = RequestMethod.POST)
	public String add(FasDue fasDue, String payPartId, String payAccountId, String dueAccountId, RedirectAttributes redirectAttributes) {
		fasDue.setPayPart(freightPartService.getById(payPartId));
		fasDue.setPayAccount(fasAccountService.getById(payAccountId));
		fasDue.setDueAccount(fasAccountService.getById(dueAccountId));
		fasDue.setDuePart(fasDue.getDueAccount().getFreightPart());
		if(StringUtil.isNullOrEmpty(fasDue.getStatus())){
			fasDue.setStatus("未提交");
		}
		if(fasDue.getExchangeRate() == 0){
			fasDue.setExchangeRate(fasExchangeRateService.getExchangeRate(fasDue.getCurrency(), null));
		}
		
		if(StringUtil.isNullOrEmpty(fasDue.getDueNumber())){
			fasDue.setDueNumber(fasDueService.getNextDueNumber());
		}
		if(fasDue.getId() == null){
			fasDueService.add(fasDue);
		}else{
			fasDueService.modify(fasDue);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fas-due-list.do";
	}
	
	@RequestMapping(value = "fas-due-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			fasDueService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fas-due-list.do";
	}
	
	
	/**
	 * 收款销账
	 */
	@ResponseBody
	@RequestMapping(value = "fas-due-to-add-reconcile")
	public Map<String, Object> toAddReconcile(@RequestParam(value="fasDueId", required=true) String fasDueId, 
			String partName, String invoiceNumber) {
		return fasDueService.toAddReconcile(fasDueId, partName, invoiceNumber);
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-due-done-add-reconcile")
	public String doneAddReconcile(@RequestParam(value="freightInvoiceId", required=true) String freightInvoiceId,
			@RequestParam(value="fasDueId", required=true) String fasDueId,
			@RequestParam(value="moneyCount", required=true) double moneyCount) {
		if(fasDueService.doneAddReconcile(freightInvoiceId, fasDueId, moneyCount)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-due-done-delete-reconcile")
	public String doneDeleteReconcile(@RequestParam(value="fasReconcileId", required=true) String[] fasReconcileId) {
		fasDueService.doneDeleteReconcile(fasReconcileId);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-due-done-confirm-due")
	public String doneConfirmDue(@RequestParam(value="fasDueId", required=true) String[] fasDueId) {
		if(fasDueService.doneConfirmDue(fasDueId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-due-done-recall-due")
	public String doneRecallDue(@RequestParam(value="fasDueId", required=true) String[] fasDueId) {
		if(fasDueService.doneRecallDue(fasDueId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-due-done-force-reconcile")
	public String doneForceReconcile(@RequestParam(value="fasDueId", required=true) String[] fasDueId) {
		if(fasDueService.doneForceReconcile(fasDueId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-due-done-recall-reconcile")
	public String doneRecallReconcile(@RequestParam(value="fasDueId", required=true) String[] fasDueId) {
		if(fasDueService.doneRecallReconcile(fasDueId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 导出收款数据
	 * @param fasDueId
	 * @param response
	 */
	@RequestMapping(value = "fas-due-to-export-details-due")
	public void exportDue(String[] fasDueId, HttpServletResponse response){
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"编号", "单位", "币种","金额","收款日期","付款账号","我方收款账号", "备注"}, 
				fasDueService.toExport(fasDueId)), "银行收款列表.xls", response);
	}

}
