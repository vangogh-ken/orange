package com.van.halley.fre.web;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.FasInvoice;
import com.van.halley.db.persistence.entity.FasPay;
import com.van.halley.db.persistence.entity.FreightInvoice;
import com.van.halley.db.persistence.entity.FreightStatement;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FasInvoiceService;
import com.van.service.FreightInvoiceService;
import com.van.service.FreightStatementService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightInvoiceController {
	@Autowired
	private FreightInvoiceService freightInvoiceService;
	@Autowired
	private FasInvoiceService fasInvoiceService;
	@Autowired
	private FreightStatementService freightStatementService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-invoice-list")
	public String list(Model model, FreightInvoice freightInvoice, @ModelAttribute PageView<FreightInvoice> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightInvoice>(1);
		}
		pageView = freightInvoiceService.query(pageView, freightInvoice);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-invoice-list";
	}
	
	/**
	 * 应付款台账
	 */
	@RequestMapping(value = "fre-invoice-list-pay")
	public String payList(Model model, FreightInvoice freightInvoice, String orderNumber, String partName, 
			String invoiceNumber, String salesman, String orgEntityName, String delayDays, String netDayTimeStart, String netDayTimeEnd, String releaseTimeStart, String releaseTimeEnd,
			@ModelAttribute PageView<FreightInvoice> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightInvoice>(1);
		}
		if(StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			pageView.setOrderBy("CREATE_TIME");
			pageView.setOrder("DESC");
		}
		freightInvoice.setIncomeOrExpense("付");
		
		
		if(!StringUtil.isNullOrEmpty(salesman)){
			String filterText = " EXISTS (SELECT 1 FROM (SELECT FRE_STATEMENT_ID AS FRE_STATEMENT_ID_ FROM FRE_EXPENSE AS E WHERE EXISTS(SELECT 1 FROM (SELECT ID FROM FRE_ORDER AS O WHERE O.SALESMAN LIKE '%" + salesman +"%') AS T WHERE T.ID=E.FRE_ORDER_ID)) AS T WHERE T.FRE_STATEMENT_ID_ = FRE_STATEMENT_ID) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(orgEntityName)){
			String filterText = " EXISTS (SELECT 1 FROM (SELECT FRE_STATEMENT_ID AS FRE_STATEMENT_ID_ FROM FRE_EXPENSE AS E WHERE EXISTS"
					+ "(SELECT 1 FROM (SELECT ID FROM FRE_ORDER AS O WHERE SALESMAN IN (SELECT DISPLAY_NAME FROM SYS_AUTH_USER WHERE POSITION_ID IN(SELECT ID FROM SYS_AUTH_POSITION WHERE ORG_ENTITY_ID IN(SELECT ID FROM SYS_AUTH_ORG_ENTITY WHERE ORG_ENTITY_NAME LIKE '%" + orgEntityName + "%')))) AS T WHERE T.ID=E.FRE_ORDER_ID)) AS T WHERE T.FRE_STATEMENT_ID_ = FRE_STATEMENT_ID) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(delayDays)){
			String filterText = " TO_DAYS(SYSDATE())-TO_DAYS(EFFECT_TIME ) > " + Double.parseDouble(delayDays) + " ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			String filterText = " EXISTS (SELECT 1 FROM (SELECT FRE_STATEMENT_ID AS FRE_STATEMENT_ID_ FROM FRE_EXPENSE AS E WHERE EXISTS(SELECT 1 FROM (SELECT ID FROM FRE_ORDER AS O WHERE O.ORDER_NUMBER LIKE '%" + orderNumber +"%') AS T WHERE T.ID=E.FRE_ORDER_ID)) AS T WHERE T.FRE_STATEMENT_ID_ = FRE_STATEMENT_ID) ";
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
		
		if(!StringUtil.isNullOrEmpty(invoiceNumber)){
			String filterText = " FAS_INVOICE_ID IN (SELECT ID FROM FAS_INVOICE WHERE INVOICE_NUMBER LIKE '%" + invoiceNumber + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(netDayTimeStart) && !StringUtil.isNullOrEmpty(netDayTimeEnd)){
			String filterText = " (EFFECT_TIME BETWEEN '" + netDayTimeStart + "' AND '" + netDayTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(releaseTimeStart) && !StringUtil.isNullOrEmpty(releaseTimeEnd)){
			String filterText = " (RELEASE_TIME BETWEEN '" + releaseTimeStart + "' AND '" + releaseTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		pageView = freightInvoiceService.query(pageView, freightInvoice);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-invoice-list-pay";
	}
	
	/**
	 * 应收款台账
	 */
	@RequestMapping(value = "fre-invoice-list-due")
	public String dueList(Model model, FreightInvoice freightInvoice, String orderNumber,String partName, 
			String invoiceNumber, String salesman, String orgEntityName, String delayDays, String netDayTimeStart, String netDayTimeEnd, String releaseTimeStart, String releaseTimeEnd,
			@ModelAttribute PageView<FreightInvoice> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightInvoice>(1);
		}
		if(StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			pageView.setOrderBy("CREATE_TIME");
			pageView.setOrder("DESC");
		}
		freightInvoice.setIncomeOrExpense("收");
		
		if(!StringUtil.isNullOrEmpty(salesman)){
			String filterText = " EXISTS (SELECT 1 FROM (SELECT FRE_STATEMENT_ID AS FRE_STATEMENT_ID_ FROM FRE_EXPENSE AS E WHERE EXISTS(SELECT 1 FROM (SELECT ID FROM FRE_ORDER AS O WHERE O.SALESMAN LIKE '%" + salesman +"%') AS T WHERE T.ID=E.FRE_ORDER_ID)) AS T WHERE T.FRE_STATEMENT_ID_ = FRE_STATEMENT_ID) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(orgEntityName)){
			String filterText = " EXISTS (SELECT 1 FROM (SELECT FRE_STATEMENT_ID AS FRE_STATEMENT_ID_ FROM FRE_EXPENSE AS E WHERE EXISTS"
					+ "(SELECT 1 FROM (SELECT ID FROM FRE_ORDER AS O WHERE SALESMAN IN (SELECT DISPLAY_NAME FROM SYS_AUTH_USER WHERE POSITION_ID IN(SELECT ID FROM SYS_AUTH_POSITION WHERE ORG_ENTITY_ID IN(SELECT ID FROM SYS_AUTH_ORG_ENTITY WHERE ORG_ENTITY_NAME LIKE '%" + orgEntityName + "%')))) AS T WHERE T.ID=E.FRE_ORDER_ID)) AS T WHERE T.FRE_STATEMENT_ID_ = FRE_STATEMENT_ID) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(delayDays)){
			String filterText = " TO_DAYS(SYSDATE())-TO_DAYS(EFFECT_TIME ) > " + Double.parseDouble(delayDays) + " ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			String filterText = " EXISTS (SELECT 1 FROM (SELECT FRE_STATEMENT_ID AS FRE_STATEMENT_ID_ FROM FRE_EXPENSE AS E WHERE EXISTS(SELECT 1 FROM (SELECT ID FROM FRE_ORDER AS O WHERE O.ORDER_NUMBER LIKE '%" + orderNumber +"%') AS T WHERE T.ID=E.FRE_ORDER_ID)) AS T WHERE T.FRE_STATEMENT_ID_ = FRE_STATEMENT_ID) ";
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
		
		if(!StringUtil.isNullOrEmpty(invoiceNumber)){
			String filterText = " FAS_INVOICE_ID IN (SELECT ID FROM FAS_INVOICE WHERE INVOICE_NUMBER LIKE '%" + invoiceNumber + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(netDayTimeStart) && !StringUtil.isNullOrEmpty(netDayTimeEnd)){
			String filterText = " (EFFECT_TIME BETWEEN '" + netDayTimeStart + "' AND '" + netDayTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(releaseTimeStart) && !StringUtil.isNullOrEmpty(releaseTimeEnd)){
			String filterText = " (RELEASE_TIME BETWEEN '" + releaseTimeStart + "' AND '" + releaseTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		pageView = freightInvoiceService.query(pageView, freightInvoice);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-invoice-list-due";
	}
	
	//应收款台账-客服
	@RequestMapping(value = "fre-invoice-list-due-service")
	public String dueListService(Model model, FreightInvoice freightInvoice, String orderNumber,String partName, 
			String invoiceNumber, String salesman, String orgEntityName, String delayDays, 
			String netDayTimeStart, String netDayTimeEnd, String releaseTimeStart, String releaseTimeEnd,
			@ModelAttribute PageView<FreightInvoice> pageView, HttpSession session) {
		if (pageView == null) {
			pageView = new PageView<FreightInvoice>(1);
		}
		if(StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			pageView.setOrderBy("CREATE_TIME");
			pageView.setOrder("DESC");
		}
		freightInvoice.setIncomeOrExpense("收");
		String text = " EXISTS (SELECT 1 FROM (SELECT FRE_STATEMENT_ID AS FRE_STATEMENT_ID_ FROM FRE_EXPENSE AS E WHERE EXISTS(SELECT 1 FROM (SELECT ID FROM FRE_ORDER AS O WHERE O.CREATOR_USER_ID = '" + ((User)session.getAttribute("userSession")).getId() +"') AS T WHERE T.ID=E.FRE_ORDER_ID)) AS T WHERE T.FRE_STATEMENT_ID_ = FRE_STATEMENT_ID) ";
		if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
			pageView.setFilterText(text);
		}else{
			pageView.setFilterText(pageView.getFilterText() + " AND " + text);
		}
		
		
		
		if(!StringUtil.isNullOrEmpty(salesman)){
			String filterText = " EXISTS (SELECT 1 FROM (SELECT FRE_STATEMENT_ID AS FRE_STATEMENT_ID_ FROM FRE_EXPENSE AS E WHERE EXISTS(SELECT 1 FROM (SELECT ID FROM FRE_ORDER AS O WHERE O.SALESMAN LIKE '%" + salesman +"%') AS T WHERE T.ID=E.FRE_ORDER_ID)) AS T WHERE T.FRE_STATEMENT_ID_ = FRE_STATEMENT_ID) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(orgEntityName)){
			String filterText = " EXISTS (SELECT 1 FROM (SELECT FRE_STATEMENT_ID AS FRE_STATEMENT_ID_ FROM FRE_EXPENSE AS E WHERE EXISTS"
					+ "(SELECT 1 FROM (SELECT ID FROM FRE_ORDER AS O WHERE SALESMAN IN (SELECT DISPLAY_NAME FROM SYS_AUTH_USER WHERE POSITION_ID IN(SELECT ID FROM SYS_AUTH_POSITION WHERE ORG_ENTITY_ID IN(SELECT ID FROM SYS_AUTH_ORG_ENTITY WHERE ORG_ENTITY_NAME LIKE '%" + orgEntityName + "%')))) AS T WHERE T.ID=E.FRE_ORDER_ID)) AS T WHERE T.FRE_STATEMENT_ID_ = FRE_STATEMENT_ID) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(delayDays)){
			String filterText = " TO_DAYS(SYSDATE())-TO_DAYS(EFFECT_TIME ) > " + Double.parseDouble(delayDays) + " ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			String filterText = " EXISTS (SELECT 1 FROM (SELECT FRE_STATEMENT_ID AS FRE_STATEMENT_ID_ FROM FRE_EXPENSE AS E WHERE EXISTS(SELECT 1 FROM (SELECT ID FROM FRE_ORDER AS O WHERE O.ORDER_NUMBER LIKE '%" + orderNumber +"%') AS T WHERE T.ID=E.FRE_ORDER_ID)) AS T WHERE T.FRE_STATEMENT_ID_ = FRE_STATEMENT_ID) ";
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
		
		if(!StringUtil.isNullOrEmpty(invoiceNumber)){
			String filterText = " FAS_INVOICE_ID IN (SELECT ID FROM FAS_INVOICE WHERE INVOICE_NUMBER LIKE '%" + invoiceNumber + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(netDayTimeStart) && !StringUtil.isNullOrEmpty(netDayTimeEnd)){
			String filterText = " (EFFECT_TIME BETWEEN '" + netDayTimeStart + "' AND '" + netDayTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(releaseTimeStart) && !StringUtil.isNullOrEmpty(releaseTimeEnd)){
			String filterText = " (RELEASE_TIME BETWEEN '" + releaseTimeStart + "' AND '" + releaseTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		pageView = freightInvoiceService.query(pageView, freightInvoice);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-invoice-list-due-service";
	}
	
	//应收款台账-客服
	@RequestMapping(value = "fre-invoice-list-due-salesman")
	public String dueListSalesman(Model model, FreightInvoice freightInvoice, String orderNumber,String partName, 
			String invoiceNumber, String salesman, String orgEntityName, String delayDays, 
			String netDayTimeStart, String netDayTimeEnd, String releaseTimeStart, String releaseTimeEnd,
			@ModelAttribute PageView<FreightInvoice> pageView, HttpSession session) {
		if (pageView == null) {
			pageView = new PageView<FreightInvoice>(1);
		}
		if(StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			pageView.setOrderBy("CREATE_TIME");
			pageView.setOrder("DESC");
		}
		freightInvoice.setIncomeOrExpense("收");
		
		String text = " EXISTS (SELECT 1 FROM (SELECT FRE_STATEMENT_ID AS FRE_STATEMENT_ID_ FROM FRE_EXPENSE AS E WHERE EXISTS(SELECT 1 FROM (SELECT ID FROM FRE_ORDER AS O WHERE O.SALESMAN = '" + ((User)session.getAttribute("userSession")).getDisplayName() + "') AS T WHERE T.ID=E.FRE_ORDER_ID)) AS T WHERE T.FRE_STATEMENT_ID_ = FRE_STATEMENT_ID) ";
		if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
			pageView.setFilterText(text);
		}else{
			pageView.setFilterText(pageView.getFilterText() + " AND " + text);
		}
		
		if(!StringUtil.isNullOrEmpty(salesman)){
			String filterText = " EXISTS (SELECT 1 FROM (SELECT FRE_STATEMENT_ID AS FRE_STATEMENT_ID_ FROM FRE_EXPENSE AS E WHERE EXISTS(SELECT 1 FROM (SELECT ID FROM FRE_ORDER AS O WHERE O.SALESMAN LIKE '%" + salesman +"%') AS T WHERE T.ID=E.FRE_ORDER_ID)) AS T WHERE T.FRE_STATEMENT_ID_ = FRE_STATEMENT_ID) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(orgEntityName)){
			String filterText = " EXISTS (SELECT 1 FROM (SELECT FRE_STATEMENT_ID AS FRE_STATEMENT_ID_ FROM FRE_EXPENSE AS E WHERE EXISTS"
					+ "(SELECT 1 FROM (SELECT ID FROM FRE_ORDER AS O WHERE SALESMAN IN (SELECT DISPLAY_NAME FROM SYS_AUTH_USER WHERE POSITION_ID IN(SELECT ID FROM SYS_AUTH_POSITION WHERE ORG_ENTITY_ID IN(SELECT ID FROM SYS_AUTH_ORG_ENTITY WHERE ORG_ENTITY_NAME LIKE '%" + orgEntityName + "%')))) AS T WHERE T.ID=E.FRE_ORDER_ID)) AS T WHERE T.FRE_STATEMENT_ID_ = FRE_STATEMENT_ID) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(delayDays)){
			String filterText = " TO_DAYS(SYSDATE())-TO_DAYS(EFFECT_TIME ) > " + Double.parseDouble(delayDays) + " ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			String filterText = " EXISTS (SELECT 1 FROM (SELECT FRE_STATEMENT_ID AS FRE_STATEMENT_ID_ FROM FRE_EXPENSE AS E WHERE EXISTS(SELECT 1 FROM (SELECT ID FROM FRE_ORDER AS O WHERE O.ORDER_NUMBER LIKE '%" + orderNumber +"%') AS T WHERE T.ID=E.FRE_ORDER_ID)) AS T WHERE T.FRE_STATEMENT_ID_ = FRE_STATEMENT_ID) ";
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
		
		if(!StringUtil.isNullOrEmpty(invoiceNumber)){
			String filterText = " FAS_INVOICE_ID IN (SELECT ID FROM FAS_INVOICE WHERE INVOICE_NUMBER LIKE '%" + invoiceNumber + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(netDayTimeStart) && !StringUtil.isNullOrEmpty(netDayTimeEnd)){
			String filterText = " (EFFECT_TIME BETWEEN '" + netDayTimeStart + "' AND '" + netDayTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(releaseTimeStart) && !StringUtil.isNullOrEmpty(releaseTimeEnd)){
			String filterText = " (RELEASE_TIME BETWEEN '" + releaseTimeStart + "' AND '" + releaseTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		pageView = freightInvoiceService.query(pageView, freightInvoice);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-invoice-list-due-salesman";
	}
	
	/**
	 * 开票管理
	 */
	@RequestMapping(value = "fre-invoice-list-release")
	public String releaseList(Model model, FreightInvoice freightInvoice, String partName, String statementNumber ,
			String invoiceNumber, @ModelAttribute PageView<FreightInvoice> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightInvoice>(1);
		}
		if(StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			pageView.setOrderBy("CREATE_TIME");
			pageView.setOrder("DESC");
		}
		freightInvoice.setIncomeOrExpense("收");
		if(StringUtil.isNullOrEmpty(freightInvoice.getStatus())){
			freightInvoice.setStatus("待开票");
		}
		if(!StringUtil.isNullOrEmpty(partName)){
			String filterText = " FRE_PART_ID IN (SELECT ID FROM FRE_PART WHERE PART_NAME LIKE '%" + partName + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(statementNumber)){
			String filterText = " FRE_STATEMENT_ID IN (SELECT ID FROM FRE_STATEMENT WHERE STATEMENT_NUMBER LIKE '%" + statementNumber + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(invoiceNumber)){
			String filterText = " FAS_INVOICE_ID IN (SELECT ID FROM FAS_INVOICE WHERE INVOICE_NUMBER LIKE '%" + invoiceNumber + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		pageView = freightInvoiceService.query(pageView, freightInvoice);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-invoice-list-release";
	}
	
	/**
	 * 销账管理
	 */
	@RequestMapping(value = "fre-invoice-list-reconcile")
	public String reconcileList(Model model, FreightInvoice freightInvoice, @ModelAttribute PageView<FreightInvoice> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightInvoice>(1);
		}
		if(StringUtil.isNullOrEmpty(freightInvoice.getStatus())){
			//freightInvoice.setStatus("待销账");
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" STATUS IN ('待销账', '销账中')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND STATUS IN ('待销账', '销账中')");
			}
		}
		pageView = freightInvoiceService.query(pageView, freightInvoice);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-invoice-list-reconcile";
	}

	@RequestMapping(value = "fre-invoice-input")
	public String input(Model model, String id) {
		FreightInvoice item = null;
		if (id != null) {
			item = freightInvoiceService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-invoice-input";
	}

	@RequestMapping(value = "fre-invoice-save", method = RequestMethod.POST)
	public String add(FreightInvoice freightInvoice, RedirectAttributes redirectAttributes) {
		if(freightInvoice.getId() == null){
			freightInvoiceService.add(freightInvoice);
		}else{
			freightInvoiceService.modify(freightInvoice);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-invoice-list.do";
	}
	
	@RequestMapping(value = "fre-invoice-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightInvoiceService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-invoice-list.do";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "fre-invoice-bystatementid")
	public List<FreightInvoice> getByStatement(@RequestParam(value="freightStatementId", required=true)String freightStatementId) {
		FreightInvoice filter = new FreightInvoice();
		FreightStatement freightStatement = freightStatementService.getById(freightStatementId);
		if(freightStatement != null){
			filter.setFreightStatement(freightStatement);
			return freightInvoiceService.queryForList(filter);
		}else{
			return null;
		}
	}
	
	/**
	 * 收款：添加开票任务
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-to-invoice")
	public Map<String, Object> getByStatementIdId(@RequestParam(value="freightStatementId", required=true)String freightStatementId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		FreightInvoice filter = new FreightInvoice();
		FreightStatement freightStatement = freightStatementService.getById(freightStatementId);
		if(freightStatement != null){
			filter.setFreightStatement(freightStatement);
			map.put("hasAddInvoice", freightInvoiceService.queryForList(filter));
			map.put("freightStatement", freightStatement);
		}else{
			map.put("hasAddInvoice", null);
			map.put("freightStatement", null);
		}
		
		return map;
	}
	
	/**
	 * 收款：添加开票任务
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-done-add-invoice")
	public String saveAsync(@RequestBody FreightInvoice freightInvoice, 
			@RequestParam(value="freightStatementId", required=true)String freightStatementId,
			@RequestParam(value="fasInvoiceTypeId", required=true)String fasInvoiceTypeId) {
		freightInvoiceService.doneAddInvoice(freightInvoice, freightStatementId, fasInvoiceTypeId);
		return "success";
	}
	
	/**
	 * 收款：删除开票任务
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-done-delete-invoice")
	public String doneDeleteInvoice(@RequestParam(value="freightInvoiceId", required=true)String[] freightInvoiceId) {
		if(freightInvoiceService.doneDeleteInvoice(freightInvoiceId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 付款：添加票号
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-to-add-number")
	public Map<String, Object> getByStatementId(@RequestParam(value="freightStatementId", required=true)String freightStatementId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		FreightInvoice filter = new FreightInvoice();
		FreightStatement freightStatement = freightStatementService.getById(freightStatementId);
		if(freightStatement != null){
			filter.setFreightStatement(freightStatement);
			map.put("hasAddInvoice", freightInvoiceService.queryForList(filter));
			map.put("freightStatement", freightStatement);
		}else{
			map.put("hasAddInvoice", null);
			map.put("freightStatement", null);
		}
		
		return map;
	}
	/**
	 * 付款：添加开票和发票号
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-done-add-number")
	public String addAsync(@RequestBody FreightInvoice freightInvoice, 
			@RequestParam(value="freightStatementId", required=true)String freightStatementId, String fasInvoiceNumber) {
		freightInvoiceService.doneAddNumber(freightInvoice, freightStatementId, fasInvoiceNumber);
		return "success";
	}
	/**
	 * 付款：删除开票和发票号
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-done-delete-number")
	public String deleteAsync(@RequestParam(value="freightInvoiceId", required=true)String[] freightInvoiceId) {
		freightInvoiceService.doneDeleteNumber(freightInvoiceId);
		return "success";
	}
	
	
	/**
	 * 批量自动开具发票
	 */
	@RequestMapping(value = "fre-invoice-release-batch")
	public String batchReleaseInvoice(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		messageHelper.addFlashMessage(redirectAttributes,"开票成功" + freightInvoiceService.batchReleaseInvoice(selectedItem) + "条");
		return "redirect:fre-invoice-list-release.do";
	}
	
	@RequestMapping(value = "fre-invoice-input-release")
	public String inputRelease(Model model, String freightInvoiceId) {
		FreightInvoice item = freightInvoiceService.getById(freightInvoiceId);
		FasInvoice filter = new FasInvoice();
		filter.setFasInvoiceType(item.getFasInvoiceType());
		filter.setStatus("可用");
		model.addAttribute("fasInvoices", fasInvoiceService.queryForList(filter));
		model.addAttribute("item", item);
		return "/content/fre/fre-invoice-input-release";
	}
	
	@RequestMapping(value = "fre-invoice-save-release")
	public String singleReleaseInvoice(Model model, String freightInvoiceId, String fasInvoiceId, RedirectAttributes redirectAttributes) {
		if(freightInvoiceService.singleReleaseInvoice(freightInvoiceId, fasInvoiceId)){
			messageHelper.addFlashMessage(redirectAttributes,"开票成功");
		}else{
			messageHelper.addFlashMessage(redirectAttributes,"开票失败");
		}
		
		return "redirect:fre-invoice-list-release.do";
	}
	
	/**
	 * 转到销账
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-to-invoice-reconcile")
	public String toInvoiceReconcile(@RequestParam(value="freightInvoiceId", required=true)String[] freightInvoiceId) {
		if(freightInvoiceService.toInvoiceReconcile(freightInvoiceId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 转到开票
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-to-invoice-release")
	public String toInvoiceRelease(@RequestParam(value="freightInvoiceId", required=true)String[] freightInvoiceId) {
		if(freightInvoiceService.toInvoiceRelease(freightInvoiceId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 在待销账中作废，涉及到账单，其他待销账，税务发票，费用。最终费用恢复到未提交状态，其他作废。
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-invalid-invoice")
	public String invalidInvoice(@RequestParam(value="freightInvoiceId", required=true)String[] freightInvoiceId) {
		if(freightInvoiceService.invalidInvoice(freightInvoiceId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 付款初审
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-done-invoice-pay-audit")
	public String doneInvoicePayAudit(@RequestParam(value="freightInvoiceId", required=true)String[] freightInvoiceId) {
		if(freightInvoiceService.doneAuditInvoicePay(freightInvoiceId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 付款初审退回
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-back-invoice-pay-audit")
	public String backAuditInvoicePay(@RequestParam(value="freightInvoiceId", required=true)String[] freightInvoiceId) {
		if(freightInvoiceService.backAuditInvoicePay(freightInvoiceId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 付款复审
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-done-invoice-pay-rehear")
	public String doneRehearInvoicePay(@RequestParam(value="freightInvoiceId", required=true)String[] freightInvoiceId) {
		if(freightInvoiceService.doneRehearInvoicePay(freightInvoiceId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 付款复审退回
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-back-invoice-pay-rehear")
	public String backRehearInvoicePay(@RequestParam(value="freightInvoiceId", required=true)String[] freightInvoiceId) {
		if(freightInvoiceService.backRehearInvoicePay(freightInvoiceId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 付款终审
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-done-invoice-pay-eventide")
	public String doneEventideInvoicePay(@RequestParam(value="freightInvoiceId", required=true)String[] freightInvoiceId) {
		if(freightInvoiceService.doneEventideInvoicePay(freightInvoiceId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 付款终审退回
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-back-invoice-pay-eventide")
	public String backEventideInvoicePay(@RequestParam(value="freightInvoiceId", required=true)String[] freightInvoiceId) {
		if(freightInvoiceService.backEventideInvoicePay(freightInvoiceId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	//取消付款审核
	@ResponseBody
	@RequestMapping(value = "fre-invoice-back-invoice-pay")
	public String backAuditInvoice(@RequestParam(value="freightInvoiceId", required=true)String[] freightInvoiceId) {
		if(freightInvoiceService.backAuditInvoice(freightInvoiceId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 获取可被冲抵的开票
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-to-invoice-offset")
	public Map<String, Object> toInvoiceOffset(@RequestParam(value="freightInvoiceId", required=true)String freightInvoiceId){
		/**
		Map<String, Object> map = new HashMap<String, Object>();
		FreightInvoice freightInvoice = freightInvoiceService.getById(freightInvoiceId);
		map.put("freightInvoice", freightInvoice);
		map.put("hasOffsetInvoice", freightInvoiceService.getHasOffsetInvoice(freightInvoiceId));
		
		FreightInvoice filter = new FreightInvoice();
		filter.setStatus("待销账");
		filter.setFreightPart(freightInvoice.getFreightPart());
		if("收".equals(freightInvoice.getIncomeOrExpense())){
			filter.setIncomeOrExpense("付");
		}else{
			filter.setIncomeOrExpense("收");
		}
		map.put("toOffsetInvoice", freightInvoiceService.queryForList(filter));
		return map;
		**/
		
		return freightInvoiceService.toOffsetInvoice(freightInvoiceId);
	}
	
	/**
	 * 同币种冲抵
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-done-invoice-offset-care-currency")
	public String doneInvoiceOffsetCare(@RequestParam(value="sourceInvoiceId", required=true)String sourceInvoiceId,
			@RequestParam(value="targetInvoiceId", required=true)String targetInvoiceId){
		if(freightInvoiceService.offsetInvoice(sourceInvoiceId, targetInvoiceId, "careCurrency")){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 折后冲抵
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-done-invoice-offset-ignore-currency")
	public String doneInvoiceOffsetIgnore(@RequestParam(value="sourceInvoiceId", required=true)String sourceInvoiceId,
			@RequestParam(value="targetInvoiceId", required=true)String targetInvoiceId){
		if(freightInvoiceService.offsetInvoice(sourceInvoiceId, targetInvoiceId, "ignoreCurrency")){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 取消冲抵
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-delete-invoice-offset")
	public String deleteInvoiceOffset(@RequestParam(value="sourceInvoiceId", required=true)String sourceInvoiceId,
			@RequestParam(value="targetInvoiceId", required=true)String targetInvoiceId){
		freightInvoiceService.deleteInvoiceOffset(sourceInvoiceId, targetInvoiceId);
		return "success";
	}
	
	
	//批量冲抵
	@ResponseBody
	@RequestMapping(value = "fre-invoice-to-batch-offset")
	public Map<String, Object> toBatchoffset(@RequestParam(value="freightInvoiceId", required=true)String[] freightInvoiceId){
		return freightInvoiceService.toBatchoffset(freightInvoiceId);
	}
	
	//完成批量
	@ResponseBody
	@RequestMapping(value = "fre-invoice-done-batch-offset")
	public Map<String, Object> doneBatchoffset(@RequestParam(value="freightInvoiceIdsIn", required=true)String[] freightInvoiceIdsIn,
			@RequestParam(value="freightInvoiceIdsOut", required=true)String[] freightInvoiceIdsOut, String offsetType){
		return freightInvoiceService.doneBatchoffset(freightInvoiceIdsIn, freightInvoiceIdsOut, offsetType);
	}
	//取消批量
	@ResponseBody
	@RequestMapping(value = "fre-invoice-delete-batch-offset")
	public String deleteBatchoffset(@RequestParam(value="freightInvoiceId", required=true)String freightInvoiceId){
		if(freightInvoiceService.deleteBatchOffset(freightInvoiceId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-invoice-to-revise-fas-invoice")
	public Map<String, Object> getById(@RequestParam(value="freightInvoiceId", required=true)String freightInvoiceId){
		Map<String, Object> map = new HashMap<String, Object>();
		FreightInvoice freightInvoice = freightInvoiceService.getById(freightInvoiceId);
		map.put("freightInvoice", freightInvoice);
		FasInvoice filter = new FasInvoice();
		filter.setFasInvoiceType(freightInvoice.getFasInvoiceType());
		filter.setStatus("可用");
		map.put("fasInvoices", fasInvoiceService.queryForList(filter));
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-invoice-done-revise-fas-invoice")
	public String reviseFasInvoice(@RequestBody FreightInvoice freightInvoice, 
			@RequestParam(value="fasInvoiceId", required=true)String fasInvoiceId) {
		freightInvoiceService.reviseFasInvoice(freightInvoice, fasInvoiceId);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-invoice-to-invoice-pay")
	public FasPay toInvoicePay(@RequestParam(value="freightInvoiceId", required=true)String[] freightInvoiceId,
			HttpServletRequest request) {
		return freightInvoiceService.toInvoicePay(freightInvoiceId, (User)request.getSession().getAttribute("userSession"));
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-invoice-to-export-invoice-due")
	public void exportDue(FreightInvoice freightInvoice, String orderNumber,String partName, String invoiceNumber, 
			String netDayTimeStart, String netDayTimeEnd, String releaseTimeStart, String releaseTimeEnd,HttpServletResponse response){
		freightInvoice.setIncomeOrExpense("收");
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"业务员", "订单号", "单位名称","开票日期","发票号","发票类型","内容摘要","应收(人民币)","应收(美元)", "汇率", "折合税金","账单号","状态","账期时间"}, 
				freightInvoiceService.toExportByFilterText(freightInvoice, orderNumber, partName, invoiceNumber,
						netDayTimeStart,  netDayTimeEnd,  releaseTimeStart, releaseTimeEnd)), "开票列表.xls", response);
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-invoice-to-export-invoice-pay")
	public void exportPay(FreightInvoice freightInvoice, String orderNumber,String partName, String invoiceNumber, 
			String netDayTimeStart, String netDayTimeEnd, String releaseTimeStart, String releaseTimeEnd,HttpServletResponse response){
		freightInvoice.setIncomeOrExpense("付");
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"业务员", "订单号", "单位名称","开票日期","发票号","发票类型","内容摘要","应收(人民币)","应收(美元)", "汇率", "折合税金", "账单号","状态","账期时间"}, 
				freightInvoiceService.toExportByFilterText(freightInvoice, orderNumber, partName, invoiceNumber,
						netDayTimeStart,  netDayTimeEnd,  releaseTimeStart, releaseTimeEnd)), "开票列表.xls", response);
	}
	
	/**
	 * 预报表导出
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-to-export-forecast-to-file")
	public String forecastExportDueToFile(@RequestParam(value="selectedItem", required=true)String[] selectedItem, HttpServletResponse response) throws IOException{
		String targetFile = StringUtil.getUUID() + ".xls";
		FileUtils.copyInputStreamToFile(IExmportUtil.exportMultiColumn(
				new String[]{"业务员", "订单号", "单位","开票日期","发票号","内容摘要","应收(RBM)","应收(USD)", "账期时间", "超期天数"}, 
				freightInvoiceService.toForecastExport(selectedItem)), new File(FileUtil.attachmentPath, targetFile));
		return targetFile;
	}
	
	/**
	 * 批量冲抵冲抵导出
	 */
	@ResponseBody
	@RequestMapping(value = "fre-invoice-to-export-batch-offset-to-file")
	public String toBatchOffsetExport(@RequestParam(value="selectedItem", required=true)String selectedItem, HttpServletResponse response) throws IOException{
		String targetFile = StringUtil.getUUID() + ".xls";
		FileUtils.copyInputStreamToFile(IExmportUtil.exportMultiColumn(
				new String[]{"业务员", "订单号", "单位名称","开票日期","发票号","发票类型","内容摘要","应收(人民币)","应收(美元)", "汇率"}, 
				freightInvoiceService.toBatchOffsetExport(selectedItem)), new File(FileUtil.attachmentPath, targetFile));
		return targetFile;
	}
	
	

}
