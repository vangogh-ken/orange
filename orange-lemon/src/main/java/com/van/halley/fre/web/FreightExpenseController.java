package com.van.halley.fre.web;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.van.halley.db.persistence.entity.FreightExpense;
import com.van.halley.db.persistence.entity.FreightStatement;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.fre.util.FreightFilterUtil;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FreightExpenseService;
import com.van.service.FreightExpenseTypeService;
import com.van.service.FreightInvoiceService;
import com.van.service.FreightPartService;
import com.van.service.FreightStatementService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightExpenseController {
	private static Logger LOG = LoggerFactory.getLogger(FreightExpenseController.class);
	@Autowired
	private FreightExpenseService freightExpenseService;
	@Autowired
	private FreightPartService freightPartService;
	@Autowired
	private FreightStatementService freightStatementService;
	@Autowired
	private FreightExpenseTypeService freightExpenseTypeService;
	@Autowired
	private FreightInvoiceService freightInvoiceService;
	@Autowired
	private MessageHelper messageHelper;

	/**
	 * 维护
	 */
	@RequestMapping(value = "fre-expense-list")
	public String list(Model model, FreightExpense freightExpense, String orderNumber, @ModelAttribute PageView<FreightExpense> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightExpense>(1);
		}
		if(!StringUtil.isNullOrEmpty(orderNumber)){//根据订单号过滤
			pageView.setFilterText(" FRE_ORDER_ID IN(SELECT O.ID FROM FRE_ORDER O WHERE O.ORDER_NUMBER LIKE '%"+orderNumber+"%')");
		}
		
		pageView = freightExpenseService.query(pageView, freightExpense);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-expense-list";
	}
	
	/**
	 * 业务核对
	 */
	@RequestMapping(value = "fre-expense-list-valid-service")
	public String validservice(Model model, FreightExpense freightExpense, String typeName, String orderNumber, 
			String NB, String partName, String boxNumber, String TDH, String CMHC, HttpServletRequest request, 
			@ModelAttribute PageView<FreightExpense> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightExpense>(1);
		}
		//费用类型
		if(!StringUtil.isNullOrEmpty(typeName)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_EXPENSE_TYPE_ID IN(SELECT T.ID FROM FRE_EXPENSE_TYPE T WHERE T.TYPE_NAME LIKE '%"+typeName+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_EXPENSE_TYPE_ID IN(SELECT T.ID FROM FRE_EXPENSE_TYPE T WHERE T.TYPE_NAME LIKE '%"+typeName+"%')");
			}
		}
				
		//订单号
		/*if(!StringUtil.isNullOrEmpty(orderNumber)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_ORDER_ID IN(SELECT O.ID FROM FRE_ORDER O WHERE O.ORDER_NUMBER LIKE '%"+orderNumber+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_ORDER_ID IN(SELECT O.ID FROM FRE_ORDER O WHERE O.ORDER_NUMBER LIKE '%"+orderNumber+"%')");
			}
		}*/
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" EXPENSE_NUMBER LIKE '%" + orderNumber + "%' ");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND EXPENSE_NUMBER LIKE '%" + orderNumber + "%' ");
			}
		}
		
		//是否为内部费用
		if(!StringUtil.isNullOrEmpty(NB)){
			String filterText = null;
			if("T".equals(NB)){
				filterText = " (FRE_ORDER_ID IS NULL OR FRE_ORDER_ID='A') ";
			}else{
				filterText = " (FRE_ORDER_ID IS NOT NULL) ";
			}
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		//单位名称
		if(!StringUtil.isNullOrEmpty(partName)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_PART_ID_B IN(SELECT P.ID FROM FRE_PART P WHERE P.PART_NAME LIKE '%"+partName+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_PART_ID_B IN(SELECT P.ID FROM FRE_PART P WHERE P.PART_NAME LIKE '%"+partName+"%')");
			}
		}
		//箱号
		/*if(!StringUtil.isNullOrEmpty(boxNumber)){
			String filterString = FreightFilterUtil.sqlFilterNumber("FRE_ORDER_ID", boxNumber);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}*/
		
		if(!StringUtil.isNullOrEmpty(boxNumber)){
			String filterString = "EXISTS (SELECT 1 FROM (SELECT FRE_EXPENSE_ID FROM FRE_EXPENSE_BOX AS EB LEFT JOIN FRE_ORDER_BOX AS OB ON EB.FRE_ORDER_BOX_ID=OB.ID "
								+ "LEFT JOIN FRE_BOX AS B ON OB.FRE_BOX_ID=B.ID WHERE B.BOX_NUMBER='" + boxNumber + "') AS T WHERE T.FRE_EXPENSE_ID=ID) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		//提单号
		if(!StringUtil.isNullOrEmpty(TDH)){
			String filterString = FreightFilterUtil.sqlFilterColumn("FRE_ORDER_ID", "TDH", TDH);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		//船名航次
		if(!StringUtil.isNullOrEmpty(CMHC)){
			String filterString = FreightFilterUtil.sqlFilterColumn("FRE_ORDER_ID", "CMHC", CMHC);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		freightExpense.setCreator((User)request.getSession().getAttribute("userSession"));
		pageView = freightExpenseService.query(pageView, freightExpense);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-expense-list-valid-service";
	}
	
	@RequestMapping(value = "fre-expense-list-valid-commerce")
	public String validcommerce(Model model, FreightExpense freightExpense, String typeName, String orderNumber,
			String NB, String partName, String boxNumber, String TDH, String CMHC, String TS, @ModelAttribute PageView<FreightExpense> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightExpense>(1);
		}
		
		//费用类型
		if(!StringUtil.isNullOrEmpty(typeName)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_EXPENSE_TYPE_ID IN(SELECT T.ID FROM FRE_EXPENSE_TYPE T WHERE T.TYPE_NAME LIKE '%"+typeName+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_EXPENSE_TYPE_ID IN(SELECT T.ID FROM FRE_EXPENSE_TYPE T WHERE T.TYPE_NAME LIKE '%"+typeName+"%')");
			}
		}
				
		//订单号
		/*if(!StringUtil.isNullOrEmpty(orderNumber)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_ORDER_ID IN(SELECT O.ID FROM FRE_ORDER O WHERE O.ORDER_NUMBER LIKE '%"+orderNumber+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_ORDER_ID IN(SELECT O.ID FROM FRE_ORDER O WHERE O.ORDER_NUMBER LIKE '%"+orderNumber+"%')");
			}
		}*/
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" EXPENSE_NUMBER LIKE '%" + orderNumber + "%' ");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND EXPENSE_NUMBER LIKE '%" + orderNumber + "%' ");
			}
		}
		
		//是否为内部费用
		if(!StringUtil.isNullOrEmpty(NB)){
			String filterText = null;
			if("T".equals(NB)){
				filterText = " (FRE_ORDER_ID IS NULL OR FRE_ORDER_ID='A') ";
			}else{
				filterText = " (FRE_ORDER_ID IS NOT NULL) ";
			}
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		//单位名称
		if(!StringUtil.isNullOrEmpty(partName)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_PART_ID_B IN(SELECT P.ID FROM FRE_PART P WHERE P.PART_NAME LIKE '%"+partName+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_PART_ID_B IN(SELECT P.ID FROM FRE_PART P WHERE P.PART_NAME LIKE '%"+partName+"%')");
			}
		}
		//箱号
		/*if(!StringUtil.isNullOrEmpty(boxNumber)){
			String filterString = FreightFilterUtil.sqlFilterNumber("FRE_ORDER_ID", boxNumber);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}*/
		if(!StringUtil.isNullOrEmpty(boxNumber)){
			String filterString = "EXISTS (SELECT 1 FROM (SELECT FRE_EXPENSE_ID FROM FRE_EXPENSE_BOX AS EB LEFT JOIN FRE_ORDER_BOX AS OB ON EB.FRE_ORDER_BOX_ID=OB.ID "
								+ "LEFT JOIN FRE_BOX AS B ON OB.FRE_BOX_ID=B.ID WHERE B.BOX_NUMBER='" + boxNumber + "') AS T WHERE T.FRE_EXPENSE_ID=ID) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		//提单号
		if(!StringUtil.isNullOrEmpty(TDH)){
			String filterString = FreightFilterUtil.sqlFilterColumn("FRE_ORDER_ID", "TDH", TDH);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		//船名航次
		if(!StringUtil.isNullOrEmpty(CMHC)){
			String filterString = FreightFilterUtil.sqlFilterColumn("FRE_ORDER_ID", "CMHC", CMHC);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		//特殊费用
		if(!StringUtil.isNullOrEmpty(TS)){
			if("T".equals(TS) || "F".equals(TS)){
				String filterString = " FRE_PRICE_ID IN (SELECT ID FROM FRE_PRICE WHERE ACTUAL='" + TS + "')";
				if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
					pageView.setFilterText(filterString);
				}else{
					pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
				}
			}else if("TS".equals(TS)){
				String filterString = " EXPENSE_NUMBER LIKE '%-TS-%'";
				if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
					pageView.setFilterText(filterString);
				}else{
					pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
				}
			}
		}
		
		if(StringUtil.isNullOrEmpty(freightExpense.getStatus())){
			freightExpense.setStatus("已审核");
		}
		pageView = freightExpenseService.query(pageView, freightExpense);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-expense-list-valid-commerce";
	}
	
	/**
	 * 财务核对费用，默认只查看已审核的费用。
	 */
	@RequestMapping(value = "fre-expense-list-valid-fas")
	public String validfas(Model model, FreightExpense freightExpense, String typeName, String orderNumber,
			String NB, String partName, String boxNumber, String TDH, String CMHC, @ModelAttribute PageView<FreightExpense> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightExpense>(1);
		}
		//费用类型
		if(!StringUtil.isNullOrEmpty(typeName)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_EXPENSE_TYPE_ID IN(SELECT T.ID FROM FRE_EXPENSE_TYPE T WHERE T.TYPE_NAME LIKE '%"+typeName+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_EXPENSE_TYPE_ID IN(SELECT T.ID FROM FRE_EXPENSE_TYPE T WHERE T.TYPE_NAME LIKE '%"+typeName+"%')");
			}
		}
				
		//订单号
		/*if(!StringUtil.isNullOrEmpty(orderNumber)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_ORDER_ID IN(SELECT O.ID FROM FRE_ORDER O WHERE O.ORDER_NUMBER LIKE '%"+orderNumber+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_ORDER_ID IN(SELECT O.ID FROM FRE_ORDER O WHERE O.ORDER_NUMBER LIKE '%"+orderNumber+"%')");
			}
		}*/
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" EXPENSE_NUMBER LIKE '%" + orderNumber + "%' ");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND EXPENSE_NUMBER LIKE '%" + orderNumber + "%' ");
			}
		}
		
		//是否为内部费用
		if(!StringUtil.isNullOrEmpty(NB)){
			String filterText = null;
			if("T".equals(NB)){
				filterText = " (FRE_ORDER_ID IS NULL OR FRE_ORDER_ID='A') ";
			}else{
				filterText = " (FRE_ORDER_ID IS NOT NULL) ";
			}
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		//单位名称
		if(!StringUtil.isNullOrEmpty(partName)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_PART_ID_B IN(SELECT P.ID FROM FRE_PART P WHERE P.PART_NAME LIKE '%"+partName+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_PART_ID_B IN(SELECT P.ID FROM FRE_PART P WHERE P.PART_NAME LIKE '%"+partName+"%')");
			}
		}
		//箱号
		/*if(!StringUtil.isNullOrEmpty(boxNumber)){
			String filterString = FreightFilterUtil.sqlFilterNumber("FRE_ORDER_ID", boxNumber);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}*/
		
		if(!StringUtil.isNullOrEmpty(boxNumber)){
			String filterString = "EXISTS (SELECT 1 FROM (SELECT FRE_EXPENSE_ID FROM FRE_EXPENSE_BOX AS EB LEFT JOIN FRE_ORDER_BOX AS OB ON EB.FRE_ORDER_BOX_ID=OB.ID "
								+ "LEFT JOIN FRE_BOX AS B ON OB.FRE_BOX_ID=B.ID WHERE B.BOX_NUMBER='" + boxNumber + "') AS T WHERE T.FRE_EXPENSE_ID=ID) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		//提单号
		if(!StringUtil.isNullOrEmpty(TDH)){
			String filterString = FreightFilterUtil.sqlFilterColumn("FRE_ORDER_ID", "TDH", TDH);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		//船名航次
		if(!StringUtil.isNullOrEmpty(CMHC)){
			String filterString = FreightFilterUtil.sqlFilterColumn("FRE_ORDER_ID", "CMHC", CMHC);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(StringUtil.isNullOrEmpty(freightExpense.getStatus())){
			freightExpense.setStatus("已审核");
		}
		pageView = freightExpenseService.query(pageView, freightExpense);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-expense-list-valid-fas";
	}
	
	@RequestMapping(value = "fre-expense-list-audit")
	public String auditList(Model model, FreightExpense freightExpense, String typeName, String orderNumber,
			String NB, String partName, String boxNumber, String TDH, String CMHC, String orderCreateTimeStart, 
			String orderCreateTimeEnd, @ModelAttribute PageView<FreightExpense> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightExpense>(1);
		}
		if(StringUtil.isNullOrEmpty(freightExpense.getStatus())){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" STATUS IN('审核中','审核中(一般异常)','初审中(特殊异常)','复审中(特殊异常)', '初审中(特殊费用)', '复审中(特殊费用)' )");
			}
		}
		//费用类型
		if(!StringUtil.isNullOrEmpty(typeName)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_EXPENSE_TYPE_ID IN(SELECT T.ID FROM FRE_EXPENSE_TYPE T WHERE T.TYPE_NAME LIKE '%"+typeName+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_EXPENSE_TYPE_ID IN(SELECT T.ID FROM FRE_EXPENSE_TYPE T WHERE T.TYPE_NAME LIKE '%"+typeName+"%')");
			}
		}
				
		//订单号
		/*if(!StringUtil.isNullOrEmpty(orderNumber)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_ORDER_ID IN(SELECT O.ID FROM FRE_ORDER O WHERE O.ORDER_NUMBER LIKE '%"+orderNumber+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_ORDER_ID IN(SELECT O.ID FROM FRE_ORDER O WHERE O.ORDER_NUMBER LIKE '%"+orderNumber+"%')");
			}
		}*/
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" EXPENSE_NUMBER LIKE '%" + orderNumber + "%' ");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND EXPENSE_NUMBER LIKE '%" + orderNumber + "%' ");
			}
		}
		
		//是否为内部费用
		if(!StringUtil.isNullOrEmpty(NB)){
			String filterText = null;
			if("T".equals(NB)){
				filterText = " (FRE_ORDER_ID IS NULL OR FRE_ORDER_ID='A') ";
			}else{
				filterText = " (FRE_ORDER_ID IS NOT NULL) ";
			}
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		//单位名称
		if(!StringUtil.isNullOrEmpty(partName)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_PART_ID_B IN(SELECT P.ID FROM FRE_PART P WHERE P.PART_NAME LIKE '%"+partName+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_PART_ID_B IN(SELECT P.ID FROM FRE_PART P WHERE P.PART_NAME LIKE '%"+partName+"%')");
			}
		}
		//箱号
		/*if(!StringUtil.isNullOrEmpty(boxNumber)){
			String filterString = FreightFilterUtil.sqlFilterNumber("FRE_ORDER_ID", boxNumber);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}*/
		if(!StringUtil.isNullOrEmpty(boxNumber)){
			String filterString = "EXISTS (SELECT 1 FROM (SELECT FRE_EXPENSE_ID FROM FRE_EXPENSE_BOX AS EB LEFT JOIN FRE_ORDER_BOX AS OB ON EB.FRE_ORDER_BOX_ID=OB.ID "
								+ "LEFT JOIN FRE_BOX AS B ON OB.FRE_BOX_ID=B.ID WHERE B.BOX_NUMBER='" + boxNumber + "') AS T WHERE T.FRE_EXPENSE_ID=ID) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		//提单号
		if(!StringUtil.isNullOrEmpty(TDH)){
			String filterString = FreightFilterUtil.sqlFilterColumn("FRE_ORDER_ID", "TDH", TDH);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		//船名航次
		if(!StringUtil.isNullOrEmpty(CMHC)){
			String filterString = FreightFilterUtil.sqlFilterColumn("FRE_ORDER_ID", "CMHC", CMHC);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		if(!StringUtil.isNullOrEmpty(orderCreateTimeStart) && !StringUtil.isNullOrEmpty(orderCreateTimeEnd)){
			String filterString = " FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE CREATE_TIME BETWEEN '" + orderCreateTimeStart + "' AND '" + orderCreateTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		pageView = freightExpenseService.query(pageView, freightExpense);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-expense-list-audit";
	}
	

	@RequestMapping(value = "fre-expense-input")
	public String input(Model model, String id) {
		FreightExpense item = null;
		if (id != null) {
			item = freightExpenseService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("freightExpenseTypes", freightExpenseTypeService.getAll());
		return "/content/fre/fre-expense-input";
	}

	@RequestMapping(value = "fre-expense-save", method = RequestMethod.POST)
	public String add(FreightExpense freightExpense, String freightExpenseTypeId, RedirectAttributes redirectAttributes) {
		freightExpense.setFreightExpenseType(freightExpenseTypeService.getById(freightExpenseTypeId));
		if(freightExpense.getId() == null){
			freightExpenseService.add(freightExpense);
		}else{
			freightExpenseService.modify(freightExpense);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-expense-list.do";
	}
	
	@RequestMapping(value = "fre-expense-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightExpenseService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-expense-list.do";
	}
	/**
	 * 获取该订单下的所有费用
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-to-view")
	public Map<String, Object> toView(@RequestParam(value="freightOrderId", required=true)String freightOrderId) {
		return freightExpenseService.toViewExpense(freightOrderId);
	}
	/**
	 * 添加正常费用
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-to-add-normal-expense")
	public Map<String, Object> getByFreightOrderId(@RequestParam(value="freightOrderId", required=true)String freightOrderId) {
		return freightExpenseService.toAddNormal(freightOrderId);
	}
	/**
	 * 内部单位执行相应委托时，需要填报相应的费用；
	 * 此费用应当是当前填报人的组织机构对应的freightPart为PartA，该订单创建人的组织机构对应的freightPart为PartB.
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-to-add-internal-expense")
	public Map<String, Object> getByFreightActionId(@RequestParam(value="freightActionId", required=true)String freightActionId) {
		return freightExpenseService.toAddInternal(freightActionId);
	}
	
	/**
	 * 订单添加费用
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-done-add-normal-expense")
	public String doneAddNormal(@RequestBody FreightExpense freightExpense, 
			@RequestParam(value="freightOrderId", required=true)String freightOrderId,
			@RequestParam(value="freightPartId", required=true)String freightPartId,
			String freightExpenseId, String freightPriceId, String freightExpenseTypeId,
			String freightActionId, @RequestParam(value="fasInvoiceTypeId", required=true)String fasInvoiceTypeId, String[] freightOrderBoxId, String forBox, 
			HttpServletRequest request) {
		User creator = (User)request.getSession().getAttribute("userSession");
		if(freightPartService.getByOrgEntityId(creator.getOrgEntity().getId()) == null){//判断组织机构与单位是否映射，如果无映射则失败
			LOG.error("用户 {} 所在的部门 {} 没有与单位相映射， 添加费用失败。", creator.getDisplayName(), creator.getOrgEntity().getOrgEntityName());
			return "error";
		}
		
		if("T".equals(forBox) && freightOrderBoxId != null && freightOrderBoxId.length > 0){
			freightExpenseService.doneBatchNormal(freightExpense, freightOrderId, freightActionId,
					freightPartId, freightPriceId, freightExpenseTypeId, fasInvoiceTypeId, freightOrderBoxId, creator);
		}else{
			if(!StringUtil.isNullOrEmpty(freightExpenseId)){//如果不为空，说明是修改单条费用
				freightExpense.setId(freightExpenseId);
			}
			freightExpenseService.doneAddNormal(freightExpense, freightOrderId, freightActionId,
					freightPartId, freightPriceId, freightExpenseTypeId, fasInvoiceTypeId, freightOrderBoxId, creator);
		}
		
		return "success";
	}
	
	/**
	 * 内部部门执行委托费用填报
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-done-add-internal-expense")
	public String doneAddInternal(@RequestBody FreightExpense freightExpense, 
			@RequestParam(value="freightActionId", required=true)String freightActionId,
			@RequestParam(value="freightPartId", required=true)String freightPartId,
			@RequestParam(value="freightExpenseTypeId", required=true)String freightExpenseTypeId,
			String freightPriceId, @RequestParam(value="fasInvoiceTypeId", required=true)String fasInvoiceTypeId, 
			String freightExpenseId, String[] freightOrderBoxId, String forBox, HttpServletRequest request) {
		User creator = (User)request.getSession().getAttribute("userSession");
		if(freightPartService.getByOrgEntityId(creator.getOrgEntity().getId()) == null){//判断组织机构与单位是否映射，如果无映射则失败
			LOG.error("用户 {} 所在的部门 {} 没有与单位相映射， 添加费用失败。", creator.getDisplayName(), creator.getOrgEntity().getOrgEntityName());
			return "error";
		}
		
		if("T".equals(forBox) && freightOrderBoxId != null && freightOrderBoxId.length > 0){
			freightExpenseService.doneBatchInternal(freightExpense, freightActionId, freightPartId, 
					freightExpenseTypeId, freightPriceId, fasInvoiceTypeId, freightOrderBoxId, creator);
		}else{
			if(!StringUtil.isNullOrEmpty(freightExpenseId)){//如果不为空，说明是修改单条费用
				freightExpense.setId(freightExpenseId);
			}
			freightExpenseService.doneAddInternal(freightExpense, freightActionId, freightPartId, 
					freightExpenseTypeId, freightPriceId, fasInvoiceTypeId, freightOrderBoxId, creator);
		}
		/*if(!StringUtil.isNullOrEmpty(freightExpenseId)){
			freightExpense.setId(freightExpenseId);
		}
		freightExpenseService.doneAddInternal(freightExpense, freightActionId, freightPartId, 
				freightExpenseTypeId, freightPriceId, fasInvoiceTypeId, freightOrderBoxId, creator);*/
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-expense-byorderid")
	public List<FreightExpense> getExpense(@RequestParam(value="freightOrderId", required=true)String freightOrderId) {
		return freightExpenseService.getByFreightOrderId(freightOrderId);
	}
	
	/**
	 * 根据账单获取费用名称
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-get-bystatementid")
	public List<FreightExpense> getByStatement(@RequestParam(value="freightStatementId", required=true)String freightStatementId) {
		return freightExpenseService.getByFreightStatementId(freightStatementId);
	}
	
	/**
	 * 根据发票号
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-get-byinvoiceid")
	public List<FreightExpense> getByInvoice(@RequestParam(value="freightInvoiceId", required=true)String freightInvoiceId) {
		FreightExpense filter = new FreightExpense();
		FreightStatement freightStatement = freightStatementService.getById(freightInvoiceService.getById(freightInvoiceId).getFreightStatement().getId());
		if(freightStatement != null){
			filter.setFreightStatement(freightStatement);
			return freightExpenseService.queryForList(filter);
		}else{
			return null;
		}
	}
	
	/**
	 * 删除费用
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-done-remove-expense")
	public String removeFromOrderAsync(@RequestParam(value="freightExpenseId", required=true)String[] freightExpenseId) {
		freightExpenseService.doneRemoveExpense(freightExpenseId);
		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "fre-expense-by-expenseid")
	public FreightExpense getById(@RequestParam(value="freightExpenseId", required=true)String freightExpenseId) {
		return freightExpenseService.getById(freightExpenseId);
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-expense-revise-predict")
	public String revisePredict(@RequestBody(required=true)FreightExpense freightExpense) {
		freightExpenseService.revisePredictCount(freightExpense);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-expense-revise-actual")
	public String reviseActual(@RequestBody(required=true)FreightExpense freightExpense) {
		freightExpenseService.reviseActualCount(freightExpense);
		return "success";
	}
	
	/**
	 * 对特殊费用的处理；先将在费用关联的成本复制一次保存。然后再标记这个成本，并设置实际金额。
	 * @param freightExpense
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value = "fre-expense-revise-special")
	public String reviseSepcial(@RequestParam(value="freightExpenseId", required=true)String freightExpenseId, 
			@RequestParam(value="actualCount", required=true)double actualCount) {
		if(freightExpenseService.reviseSpecialExpense(freightExpenseId, actualCount)){
			return "success";
		}else{
			return "error";
		}
	}*/
	
	/**
	 * 批量标记特殊费用
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-to-batch-special")
	public Map<String, Object> toBatchSpecial(@RequestParam(value="freightExpenseId", required=true)String[] freightExpenseId) {
		return freightExpenseService.toBatchSpecial(freightExpenseId);
	}
	
	/**
	 * 完成批量标记特殊
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-done-batch-special")
	public String doneBatchSpecial(@RequestParam(value="freightExpenseId", required=true)String[] freightExpenseId,
			@RequestParam(value="actualCount", required=true)double actualCount) {
		if(freightExpenseService.doneBatchSpecial(freightExpenseId, actualCount)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 批量校验，判断是否能够批量修改
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-valid-batch")
	public String validBatch(String[] selectedItem) {
		if(freightExpenseService.validBatchForRevise(selectedItem)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 批量修改预计费用
	 * @param selectedItem
	 * @param predictCount
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-revise-predict-batch")
	public String revisePredictBatch(String[] selectedItem, double predictCount) {
		if(freightExpenseService.revisePredictCountBatch(selectedItem, predictCount)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 批量修改实际费用
	 * @param selectedItem
	 * @param actualCount
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-revise-actual-batch")
	public String reviseActualBatch(String[] selectedItem, double actualCount) {
		if(freightExpenseService.reviseActualCountBatch(selectedItem, actualCount)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 提交费用审核，与订单中的提交审核一致（暂未将其删除）
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-to-expense-audit")
	public String toExpenseAudit(@RequestParam(value="freightExpenseId", required=true)String[] freightExpenseId) {
		if(freightExpenseService.toAuditExpense(freightExpenseId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 完成审核
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-done-expense-audit")
	public String doneExpenseAudit(@RequestParam(value="freightExpenseId", required=true)String[] freightExpenseId) {
		if(freightExpenseService.doneAuditExpense(freightExpenseId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 退回审核
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-back-expense-audit")
	public String backExpenseAudit(@RequestParam(value="freightExpenseId", required=true)String[] freightExpenseIds) {
		if(freightExpenseService.backAuditExpense(freightExpenseIds)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 完成复审
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-done-expense-rehear")
	public String doneExpenseRehear(@RequestParam(value="freightExpenseId", required=true)String[] freightExpenseIds) {
		if(freightExpenseService.doneRehearExpense(freightExpenseIds)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 退回复审
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-back-expense-rehear")
	public String backExpenseRehear(@RequestParam(value="freightExpenseId", required=true)String[] freightExpenseId) {
		if(freightExpenseService.backRehearExpense(freightExpenseId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 修改单条费用
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-to-revise-expense")
	public FreightExpense reviseExpense(@RequestParam(value="freightExpenseId", required=true)String freightExpenseId) {
		return freightExpenseService.getById(freightExpenseId);
	}
	
	/**
	 *根据标记为特殊费用的生成一个新费用。
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-create-expense-special")
	public String createExpenseSpecial(@RequestParam(value="freightExpenseId", required=true)String[] freightExpenseId,
			HttpServletRequest request) {
		if(freightExpenseService.createExpenseSpecial(freightExpenseId, (User)request.getSession().getAttribute("userSession"))){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-expense-done-recall-special")
	public String doneRecallSpecial(@RequestParam(value="freightExpenseId", required=true)String[] freightExpenseId) {
		if(freightExpenseService.doneRecallSpecial(freightExpenseId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 导出费用列表
	 */
	@ResponseBody
	@RequestMapping(value = "fre-expense-to-export-expense")
	public void exportAll(HttpServletResponse response)throws IOException, FileUploadException {
		List<FreightExpense> freightExpenses = freightExpenseService.getAll();
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"订单号", "费用编号", "费用名称","收支","收付单位","币种","汇率","票种","计价方式","预计单价","预计总额","实际单价","实际总额", "税金", "状态"}, 
				freightExpenseService.toExport(freightExpenses)), "费用列表.xls", response);
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-expense-to-export-expense-audit")
	public void exportAudit(FreightExpense freightExpense, String typeName, String partName, String orderNumber,
		String boxNumber, String TDH, String CMHC, String orderCreateTimeStart, String orderCreateTimeEnd, HttpServletResponse response){
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"订单号", "费用编号", "费用名称","收支","收付单位","币种","汇率","票种","计价方式","预计单价","预计总额","实际单价","实际总额", "税金", "状态", "集装箱号", "提单号","品名","箱型箱量"}, 
				freightExpenseService.toExportByFilterText(freightExpense, typeName, partName, orderNumber, boxNumber, TDH, CMHC, 
						orderCreateTimeStart, orderCreateTimeEnd)), "费用列表.xls", response);
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-expense-to-export-expense-statement")
	public void exportStatement(String freightStatementId, HttpServletResponse response){
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"订单号", "费用编号", "费用名称","收支","收付单位","币种","汇率","票种","计价方式","预计单价","预计总额","实际单价","实际总额", "税金", "状态", "箱型","箱量","箱属", "集装箱号", "提单号","目的港", "备注"}, 
				freightExpenseService.toExportByFreightStatementId(freightStatementId)), "费用列表.xls", response);
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-expense-to-export-expense-split")
	public void exportStatementSplit(String freightStatementId, HttpServletResponse response){
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"订单号", "费用编号", "费用名称","收支","收付单位","币种","汇率","票种","计价方式","预计价格","实际价格", "提单号", "目的港", "集装箱号","箱型","箱量","箱属", "备注"}, 
				freightExpenseService.toExportByFreightStatementIdWithSplit(freightStatementId)), "费用列表.xls", response);
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-expense-to-export-details")
	public String exportDetails(String[] selectedItem) throws IOException{
		String targetFile = StringUtil.getUUID() + ".xls";
		FileUtils.copyInputStreamToFile(IExmportUtil.exportMultiColumn(
				new String[]{"订单号", "费用编号", "费用名称","收支","收付单位","币种","汇率","票种","计价方式","预计单价","预计总价","实际单价","实际总价","状态"},
				freightExpenseService.toExportDetails(selectedItem)), new File(FileUtil.attachmentPath, targetFile));
		return targetFile;
	}
}
