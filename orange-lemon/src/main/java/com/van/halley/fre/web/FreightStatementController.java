package com.van.halley.fre.web;


import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.van.halley.db.persistence.entity.FreightStatement;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.service.FasInvoiceTypeService;
import com.van.service.FreightPartService;
import com.van.service.FreightStatementService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightStatementController {
	@Autowired
	private FreightStatementService freightStatementService;
	@Autowired
	private FreightPartService freightPartService;
	@Autowired
	private FasInvoiceTypeService fasInvoiceTypeService;
	@Autowired
	private MessageHelper messageHelper;
	
	/**
	 * 对账(维护)
	 */
	@RequestMapping(value = "fre-statement-list")
	public String list(Model model, FreightStatement freightStatement, @ModelAttribute PageView<FreightStatement> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightStatement>(1);
		}
		pageView = freightStatementService.query(pageView, freightStatement);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-statement-list";
	}
	
	/**
	 * 客服
	 */
	@RequestMapping(value = "fre-statement-list-service")
	public String listSale(Model model, FreightStatement freightStatement, String statementNumber, String orderNumber, 
			String partName, HttpServletRequest request, @ModelAttribute PageView<FreightStatement> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightStatement>(1);
		}
		freightStatement.setCreator((User)request.getSession().getAttribute("userSession"));
		freightStatement.setIncomeOrExpense("收");
		freightStatement.setStatementNumber(null);
		if(StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			pageView.setOrderBy("CREATE_TIME");
			pageView.setOrder("DESC");
		}
		
		if(!StringUtil.isNullOrEmpty(statementNumber)){
			String filterText = " STATEMENT_NUMBER LIKE '%" + statementNumber + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			String filterText = " ID IN(SELECT FRE_STATEMENT_ID FROM FRE_EXPENSE WHERE EXPENSE_NUMBER LIKE '%" + orderNumber + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(partName)){
			String filterText = " FRE_PART_ID IN (SELECT ID FROM FRE_PART WHERE PART_NAME LIKE '%" + partName + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(freightStatement.getStatus()) && "已销账".equals(freightStatement.getStatus())){
			String filterText = " STATUS != '未提交' AND NOT EXISTS (SELECT 1 FROM (SELECT FRE_STATEMENT_ID FROM FRE_INVOICE WHERE STATUS != '已销账') AS T WHERE T.FRE_STATEMENT_ID=ID) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
			
			freightStatement.setStatus(null);
		}
		
		pageView = freightStatementService.query(pageView, freightStatement);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-statement-list-service";
	}
	@RequestMapping(value = "fre-statement-input-service")
	public String inputSale(Model model, String id) {
		FreightStatement item = null;
		if (id != null) {
			item = freightStatementService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("fasInvoiceTypes", fasInvoiceTypeService.getAll());
		model.addAttribute("freightParts", freightPartService.getAll());
		return "/content/fre/fre-statement-input-service";
	}
	
	@RequestMapping(value = "fre-statement-save-service", method = RequestMethod.POST)
	public String addSale(FreightStatement freightStatement, @RequestParam(value="freightPartBId",required=true)String freightPartBId, 
			@RequestParam(value="fasInvoiceTypeId",required=true)String fasInvoiceTypeId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		User creator = (User)request.getSession().getAttribute("userSession");
		freightStatementService.doneAddStatement(freightStatement, freightPartBId, fasInvoiceTypeId, creator);
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-statement-list-service.do";
	}
	
	/**
	 * 商务
	 */
	@RequestMapping(value = "fre-statement-list-commerce")
	public String commerce(Model model, FreightStatement freightStatement, String statementNumber, String orderNumber, 
			String partName,  HttpServletRequest request, @ModelAttribute PageView<FreightStatement> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightStatement>(1);
		}
		freightStatement.setCreator((User)request.getSession().getAttribute("userSession"));
		freightStatement.setStatementNumber(null);
		if(StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			pageView.setOrderBy("CREATE_TIME");
			pageView.setOrder("DESC");
		}
		
		if(!StringUtil.isNullOrEmpty(statementNumber)){
			String filterText = " STATEMENT_NUMBER LIKE '%" + statementNumber + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			String filterText = " ID IN(SELECT FRE_STATEMENT_ID FROM FRE_EXPENSE WHERE EXPENSE_NUMBER LIKE '%" + orderNumber + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(partName)){
			String filterText = " FRE_PART_ID IN (SELECT ID FROM FRE_PART WHERE PART_NAME LIKE '%" + partName + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(freightStatement.getStatus()) && "已销账".equals(freightStatement.getStatus())){
			String filterText = " STATUS != '未提交' AND NOT EXISTS (SELECT 1 FROM (SELECT FRE_STATEMENT_ID FROM FRE_INVOICE WHERE STATUS != '已销账') AS T WHERE T.FRE_STATEMENT_ID=ID) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
			
			freightStatement.setStatus(null);
		}
		
		pageView = freightStatementService.query(pageView, freightStatement);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-statement-list-commerce";
	}
	@RequestMapping(value = "fre-statement-input-commerce")
	public String inputcommerce(Model model, String id) {
		FreightStatement item = null;
		if (id != null) {
			item = freightStatementService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("fasInvoiceTypes", fasInvoiceTypeService.getAll());
		model.addAttribute("freightParts", freightPartService.getAll());
		return "/content/fre/fre-statement-input-commerce";
	}
	
	@RequestMapping(value = "fre-statement-save-commerce", method = RequestMethod.POST)
	public String addcommerce(FreightStatement freightStatement, @RequestParam(value="freightPartBId",required=true)String freightPartBId, 
			@RequestParam(value="fasInvoiceTypeId",required=true)String fasInvoiceTypeId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		User creator = (User)request.getSession().getAttribute("userSession");
		freightStatementService.doneAddStatement(freightStatement, freightPartBId, fasInvoiceTypeId, creator);
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-statement-list-commerce.do";
	}
	
	/**
	 * 对账(财务)
	 */
	@RequestMapping(value = "fre-statement-list-fas")
	public String listFas(Model model, FreightStatement freightStatement, @ModelAttribute PageView<FreightStatement> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightStatement>(1);
		}
		pageView = freightStatementService.query(pageView, freightStatement);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-statement-list-fas";
	}
	
	/**
	 * 账单审核、退回 
	 */
	@RequestMapping(value = "fre-statement-list-audit")
	public String listAudit(Model model, FreightStatement freightStatement, @ModelAttribute PageView<FreightStatement> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightStatement>(1);
		}
		if(StringUtil.isNullOrEmpty(freightStatement.getStatus())){
			freightStatement.setStatus("审核中");
		}
		pageView = freightStatementService.query(pageView, freightStatement);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-statement-list-audit";
	}
	
	@RequestMapping(value = "fre-statement-to-statement-audit")
	@ResponseBody()
	public String toStatementAudit(@RequestParam(value="freightStatementId", required=true)String[] freightStatementId) {
		if(freightStatementService.toStatementAudit(freightStatementId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-statement-done-statement-audit")
	@ResponseBody()
	public String doneStatementAudit(@RequestParam(value="freightStatementId", required=true)String[] freightStatementId) {
		if(freightStatementService.doneStatementAudit(freightStatementId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 取消审核
	 */
	@RequestMapping(value = "fre-statement-done-recall-statement")
	@ResponseBody()
	public String doneRecallStatement(@RequestParam(value="freightStatementId", required=true)String[] freightStatementId) {
		if(freightStatementService.doneRecallStatement(freightStatementId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-statement-back-statement-audit")
	@ResponseBody()
	public String backStatementAudit(@RequestParam(value="freightStatementId", required=true)String[] freightStatementId) {
		if(freightStatementService.backStatementAudit(freightStatementId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/** 账单待开票**********************************************/
	@RequestMapping(value = "fre-statement-list-invoice")
	public String listInvoice(Model model, FreightStatement freightStatement, 
			String fasInvoiceTypeId, String partName, String moneyCountDollarStart, String moneyCountDollarEnd,
			String moneyCountRmbStart, String moneyCountRmbEnd, @ModelAttribute PageView<FreightStatement> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightStatement>(1);
		}
		
		freightStatement.setIncomeOrExpense("收");
		if(!StringUtil.isNullOrEmpty(fasInvoiceTypeId)){
			freightStatement.setFasInvoiceType(fasInvoiceTypeService.getById(fasInvoiceTypeId));
		}
		if(!StringUtil.isNullOrEmpty(partName)){
			String filterText = " FRE_PART_ID IN (SELECT ID FROM FRE_PART WHERE PART_NAME LIKE '%" + partName + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		//是数据库BUG？STATUS放置前面就语法错误。
		if(StringUtil.isNullOrEmpty(freightStatement.getStatus())){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" STATUS IN ('待开票', '开票中') ");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND STATUS IN ('待开票', '开票中') ");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(moneyCountDollarStart) && !StringUtil.isNullOrEmpty(moneyCountDollarEnd)){
			String filterText = " (MONEY_COUNT_DOLLAR BETWEEN " + moneyCountDollarStart + " AND " + moneyCountDollarEnd + ") ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(moneyCountRmbStart) && !StringUtil.isNullOrEmpty(moneyCountRmbEnd)){
			String filterText = " (MONEY_COUNT_RMB BETWEEN " + moneyCountRmbStart + " AND " + moneyCountRmbEnd + ") ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		pageView = freightStatementService.query(pageView, freightStatement);
		model.addAttribute("pageView", pageView);
		model.addAttribute("fasInvoiceTypes", fasInvoiceTypeService.getAll());
		return "/content/fre/fre-statement-list-invoice";
	}

	/**
	 * 新增(通用)
	 */
	@RequestMapping(value = "fre-statement-input")
	public String input(Model model, String id) {
		FreightStatement item = null;
		if (id != null) {
			item = freightStatementService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("fasInvoiceTypes", fasInvoiceTypeService.getAll());
		model.addAttribute("freightParts", freightPartService.getAll());
		return "/content/fre/fre-statement-input";
	}
	
	
	
	/**
	 * 新增(财务)
	 */
	@RequestMapping(value = "fre-statement-input-fas")
	public String inputFas(Model model, String id) {
		FreightStatement item = null;
		if (id != null) {
			item = freightStatementService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("fasInvoiceTypes", fasInvoiceTypeService.getAll());
		model.addAttribute("freightParts", freightPartService.getAll());
		return "/content/fre/fre-statement-input-fas";
	}

	@RequestMapping(value = "fre-statement-save", method = RequestMethod.POST)
	public String add(FreightStatement freightStatement, @RequestParam(value="freightPartBId",required=true)String freightPartBId, 
			@RequestParam(value="fasInvoiceTypeId",required=true)String fasInvoiceTypeId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		User creator = (User)request.getSession().getAttribute("userSession");
		freightStatementService.doneAddStatement(freightStatement, freightPartBId, fasInvoiceTypeId, creator);
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-statement-list.do";
	}
	@RequestMapping(value = "fre-statement-save-fas", method = RequestMethod.POST)
	public String addFas(FreightStatement freightStatement, @RequestParam(value="freightPartBId",required=true)String freightPartBId, 
			@RequestParam(value="fasInvoiceTypeId",required=true)String fasInvoiceTypeId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		User creator = (User)request.getSession().getAttribute("userSession");
		freightStatementService.doneAddStatement(freightStatement, freightPartBId, fasInvoiceTypeId, creator);
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-statement-list-fas.do";
	}
	
	/**
	 * 删除 通用
	 */
	@RequestMapping(value = "fre-statement-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		freightStatementService.doneRemoveStatement(selectedItem);
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-statement-list.do";
	}
	
	/**
	 * 业务删除
	 */
	@RequestMapping(value = "fre-statement-remove-sale")
	public String removeSale(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		freightStatementService.doneRemoveStatement(selectedItem);
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-statement-list-sale.do";
	}
	
	@RequestMapping(value = "fre-statement-remove-service")
	public String service(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		freightStatementService.doneRemoveStatement(selectedItem);
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-statement-list-service.do";
	}
	
	@RequestMapping(value = "fre-statement-remove-commerce")
	public String commerce(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		freightStatementService.doneRemoveStatement(selectedItem);
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-statement-list-commerce.do";
	}
	
	/**
	 * 财务删除
	 */
	@RequestMapping(value = "fre-statement-remove-fas")
	public String removeFas(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		freightStatementService.doneRemoveStatement(selectedItem);
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-statement-list-fas.do";
	}
	
	/**
	 * 点击开票申请，获取账单，判断是否可以提交开票申请，并填报相应的开票说明。
	 */
	@ResponseBody
	@RequestMapping(value = "fre-statement-prepare-release")
	public FreightStatement prepareRelease(String freightStatementId){
		return freightStatementService.getById(freightStatementId);
	}
	
	/**
	 * 申请开票，状态变为待开票
	 */
	@ResponseBody
	@RequestMapping(value = "fre-statement-to-release")
	public String toRelease(String freightStatementId, String descn){
		FreightStatement freightStatement = freightStatementService.getById(freightStatementId);
		freightStatement.setDescn(descn);
		freightStatement.setStatus("待开票");
		freightStatementService.modify(freightStatement);
		return "success";
	}
	
	/**
	 * 点击申请付款时候，弹出界面。获取账单信息。
	 */
	@ResponseBody
	@RequestMapping(value = "fre-statement-prepare-pay")
	public FreightStatement preparePay(String freightStatementId){
		return freightStatementService.getById(freightStatementId);
	}
	
	/**
	 * 开票时间为非固定账期计算开始时间
	 * 申请付款，涉及到的开票信息状态变为付款初审，账单信息变为付款审核中
	 */
	@ResponseBody
	@RequestMapping(value = "fre-statement-to-pay")
	public String toPay(String freightStatementId, String descn, Date releaseTime){
		freightStatementService.toPayStatement(freightStatementId, descn, releaseTime);
		return "success";
	}
	
	/**
	 * 作废账单
	 */
	@ResponseBody
	@RequestMapping(value = "fre-statement-invalid-statement")
	public String doneInvalidStatement(@RequestParam(value="freightStatementId", required=true)String[] freightStatementId){
		if(freightStatementService.doneInvalidStatement(freightStatementId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	
	/**
	 * 冲抵账单
	 */
	@ResponseBody
	@RequestMapping(value = "fre-statement-to-statement-offset")
	public Map<String, Object> toOffsetStatement(@RequestParam(value="freightStatementId", required=true)String freightStatementId){
		return freightStatementService.toAddOffset(freightStatementId);
	}
	
	/**
	 * 同币种冲抵
	 */
	@ResponseBody
	@RequestMapping(value = "fre-statement-done-statement-offset-care-currency")
	public String doneStatementOffsetCare(@RequestParam(value="sourceStatementId", required=true)String sourceStatementId,
			@RequestParam(value="targetStatementId", required=true)String targetStatementId){
		if(freightStatementService.doneAddOffset(sourceStatementId, targetStatementId, "careCurrency")){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 折后冲抵
	 */
	@ResponseBody
	@RequestMapping(value = "fre-statement-done-statement-offset-ignore-currency")
	public String doneStatementOffsetIgnore(@RequestParam(value="sourceStatementId", required=true)String sourceStatementId,
			@RequestParam(value="targetStatementId", required=true)String targetStatementId){
		if(freightStatementService.doneAddOffset(sourceStatementId, targetStatementId, "ignoreCurrency")){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 取消冲抵
	 */
	@ResponseBody
	@RequestMapping(value = "fre-statement-delete-statement-offset")
	public String deleteStatementOffset(@RequestParam(value="sourceStatementId", required=true)String sourceStatementId,
			@RequestParam(value="targetStatementId", required=true)String targetStatementId){
		if(freightStatementService.doneRemoveOffset(sourceStatementId, targetStatementId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 商务添加
	 */
	@ResponseBody
	@RequestMapping(value = "fre-statement-to-add-expense-commerce")
	public Map<String, Object> toAddExpense(@RequestParam(value="freightStatementId", required=true)String freightStatementId,
			String FYMC, String DDH, String PM, String WTDW, String XH, String XS, String TDH, String CMHC, String NB){
		return freightStatementService.toAddExpense(freightStatementId, FYMC, DDH, PM, WTDW, XH, XS, TDH, CMHC, NB);
	}
	
	/**
	 * 客服添加
	 */
	@ResponseBody
	@RequestMapping(value = "fre-statement-to-add-expense-service")
	public Map<String, Object> toAddExpenseFilter(@RequestParam(value="freightStatementId", required=true)String freightStatementId,
			String FYMC, String DDH, String XH, String TDH, String CMHC, HttpServletRequest request){
		return freightStatementService.toAddExpenseFilter(freightStatementId, FYMC, DDH, XH, TDH, CMHC);
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-statement-done-add-expense")
	public String doneAddExpense(@RequestParam(value="freightStatementId", required=true)String freightStatementId,
			@RequestParam(value="freightExpenseId", required=true)String[] freightExpenseId){
		freightStatementService.doneAddExpense(freightStatementId, freightExpenseId);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-statement-done-delete-expense")
	public String doneDeleteExpense(@RequestParam(value="freightExpenseId", required=true)String[] freightExpenseId){
		freightStatementService.doneDeleteExpense(freightExpenseId);
		return "success";
	}
	
	/**
	 * 税务退回开票申请
	 */
	@RequestMapping(value = "fre-statement-back-invoice-statement")
	@ResponseBody()
	public String backInvoiceStatement(@RequestParam(value="freightStatementId", required=true)String[] freightStatementId) {
		if(freightStatementService.backInvoiceStatement(freightStatementId)){
			return "success";
		}else{
			return "error";
		}
	}

	
	@ResponseBody
	@RequestMapping(value = "fre-statement-done-revise-expense")
	public String reviseActual(@RequestParam(value="freightExpenseId", required=true)String freightExpenseId, 
			@RequestParam(value="actualCount", required=true)double actualCount) {
		if(freightStatementService.doneReviseExpense(freightExpenseId, actualCount)){
			return "success";
		}else{
			return "error";
		}
	}
}
