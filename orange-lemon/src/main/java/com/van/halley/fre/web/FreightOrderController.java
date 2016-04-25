package com.van.halley.fre.web;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.FreightAction;
import com.van.halley.db.persistence.entity.FreightMaintain;
import com.van.halley.db.persistence.entity.FreightOrder;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.fre.util.FreightFilterUtil;
import com.van.halley.util.SpringSecurityUtil;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FreightActionService;
import com.van.service.FreightMaintainService;
import com.van.service.FreightOrderBoxService;
import com.van.service.FreightOrderService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightOrderController {
	@Autowired
	private FreightOrderService freightOrderService;
	@Autowired
	private FreightMaintainService freightMaintainService;
	@Autowired
	private FreightActionService freightActionService;
	@Autowired
	private FreightOrderBoxService freightOrderBoxService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-order-list")
	public String query(Model model, FreightOrder freightOrder, @ModelAttribute PageView<FreightOrder> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightOrder>(1);
		}
		pageView = freightOrderService.query(pageView, freightOrder);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-order-list";
	}
	//订单审核所见
	@RequestMapping(value = "fre-order-list-audit")
	public String queryAudit(Model model, FreightOrder freightOrder, String JZX, String TDH, String CMHC, 
			@ModelAttribute PageView<FreightOrder> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightOrder>(1);
		}
		if(!StringUtil.isNullOrEmpty(freightOrder.getOrderNumber())){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" ORDER_NUMBER LIKE '%" + freightOrder.getOrderNumber() + "%'");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND ORDER_NUMBER LIKE '%" + freightOrder.getOrderNumber() + "%'");
			}
			freightOrder.setOrderNumber(null);
		}
		if(StringUtil.isNullOrEmpty(freightOrder.getOrderStatus()) && StringUtil.isNullOrEmpty(freightOrder.getExpenseStatus())){
			String filterString = " (ORDER_STATUS IN ('审核中', '锁定中') OR (EXPENSE_STATUS IN('添加中', '添加完毕')) OR EXISTS (SELECT 1 FROM (SELECT FRE_ORDER_ID FROM FRE_EXPENSE AS E WHERE E.STATUS='审核中') AS T WHERE T.FRE_ORDER_ID=ID))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		if(!StringUtil.isNullOrEmpty(freightOrder.getExpenseStatus())){
			String filterString = " (EXPENSE_STATUS ='" + freightOrder.getExpenseStatus() + "' OR EXISTS (SELECT 1 FROM (SELECT FRE_ORDER_ID FROM FRE_EXPENSE AS E WHERE E.STATUS='" + freightOrder.getExpenseStatus() + "') AS T WHERE T.FRE_ORDER_ID=ID))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
			freightOrder.setExpenseStatus(null);
		}
		
		//集装箱
		if(!StringUtil.isNullOrEmpty(JZX)){
			String filterString = FreightFilterUtil.sqlFilterNumber("ID", JZX);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		//提单号
		if(!StringUtil.isNullOrEmpty(TDH)){
			String filterString = FreightFilterUtil.sqlFilterColumn("ID", "TDH", TDH);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		//船名航次
		if(!StringUtil.isNullOrEmpty(CMHC)){
			String filterString = FreightFilterUtil.sqlFilterColumn("ID", "CMHC", CMHC);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
				
		pageView = freightOrderService.query(pageView, freightOrder);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-order-list-audit";
	}
	
	//业务员所见
	@RequestMapping(value = "fre-order-list-salesman")
	public String querySale(Model model, FreightOrder freightOrder, @ModelAttribute PageView<FreightOrder> pageView, HttpServletRequest request) {
		if (pageView == null) {
			pageView = new PageView<FreightOrder>(1);
		}
		freightOrder.setSalesman(((User)request.getSession().getAttribute("userSession")).getDisplayName());
		/*if(StringUtil.isNullOrEmpty(freightOrder.getOrderStatus())){//如果状态为空，则设置默认状态
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" ORDER_STATUS NOT IN('已完成', '已作废')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND ORDER_STATUS NOT IN('已完成', '已作废')");
			}
		}*/
		pageView = freightOrderService.query(pageView, freightOrder);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-order-list-salesman";
	}
	//客服员所见
	@RequestMapping(value = "fre-order-list-service")
	public String queryService(Model model, FreightOrder freightOrder,  String JZX, String XS, String TDH, String CMHC, 
			@ModelAttribute PageView<FreightOrder> pageView, HttpServletRequest request) {
		if (pageView == null) {
			pageView = new PageView<FreightOrder>(1);
		}
		freightOrder.setCreatorUserId(((User)request.getSession().getAttribute("userSession")).getId());
		
		/*
		 * 客服直接显示所有
		 * if(StringUtil.isNullOrEmpty(freightOrder.getOrderStatus()) && StringUtil.isNullOrEmpty(freightOrder.getExpenseStatus())){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" (ORDER_STATUS IN ('审核中', '锁定中') OR (EXPENSE_STATUS IN('添加中', '添加完毕')) OR ID IN (SELECT FRE_ORDER_ID FROM FRE_EXPENSE WHERE STATUS='审核中'))");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND (ORDER_STATUS IN ('审核中', '锁定中') OR (EXPENSE_STATUS IN('添加中', '添加完毕')) OR ID IN (SELECT FRE_ORDER_ID FROM FRE_EXPENSE WHERE STATUS='审核中'))");
			}
		}*/
		
		if(!StringUtil.isNullOrEmpty(freightOrder.getOrderNumber())){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" ORDER_NUMBER LIKE '%" + freightOrder.getOrderNumber() + "%'");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND ORDER_NUMBER LIKE '%" + freightOrder.getOrderNumber() + "%'");
			}
			freightOrder.setOrderNumber(null);
		}
		
		if(!StringUtil.isNullOrEmpty(freightOrder.getCargoName())){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" CARGO_NAME LIKE '%" + freightOrder.getCargoName() + "%'");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND CARGO_NAME LIKE '%" + freightOrder.getCargoName() + "%'");
			}
			freightOrder.setCargoName(null);
		}
		
		if(!StringUtil.isNullOrEmpty(freightOrder.getDelegatePart())){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" DELEGATE_PART LIKE '%" + freightOrder.getDelegatePart() + "%'");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND DELEGATE_PART LIKE '%" + freightOrder.getDelegatePart() + "%'");
			}
			freightOrder.setDelegatePart(null);
		}
		
		if(!StringUtil.isNullOrEmpty(freightOrder.getDelegateNumber())){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" DELEGATE_NUMBER LIKE '%" + freightOrder.getDelegateNumber() + "%'");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND DELEGATE_NUMBER LIKE '%" + freightOrder.getDelegateNumber() + "%'");
			}
			freightOrder.setDelegateNumber(null);
		}
		
		if(!StringUtil.isNullOrEmpty(freightOrder.getSalesman())){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" SALESMAN LIKE '%" + freightOrder.getSalesman() + "%'");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND SALESMAN LIKE '%" + freightOrder.getSalesman() + "%'");
			}
			freightOrder.setSalesman(null);
		}
		
		if(!StringUtil.isNullOrEmpty(freightOrder.getExpenseStatus())){
			String filterText = "(EXPENSE_STATUS ='" + freightOrder.getExpenseStatus() + "' OR EXISTS ( SELECT 1 FROM (SELECT FRE_ORDER_ID AS FRE_ORDER_ID_ FROM FRE_EXPENSE WHERE STATUS='" + freightOrder.getExpenseStatus() + "') AS T WHERE T.FRE_ORDER_ID_=ID))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
				//pageView.setFilterText(" (EXPENSE_STATUS IN('" + freightOrder.getExpenseStatus() + "')) OR ID IN (SELECT FRE_ORDER_ID FROM FRE_EXPENSE WHERE STATUS='" + freightOrder.getExpenseStatus() + "')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
				//pageView.setFilterText(pageView.getFilterText() + " AND (EXPENSE_STATUS IN('" + freightOrder.getExpenseStatus() + "')) OR ID IN (SELECT FRE_ORDER_ID FROM FRE_EXPENSE WHERE STATUS='" + freightOrder.getExpenseStatus() + "')");
			}
			freightOrder.setExpenseStatus(null);
		}
		
		//集装箱
		if(!StringUtil.isNullOrEmpty(JZX)){
			String filterString = FreightFilterUtil.sqlFilterNumber("ID", JZX);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		//箱属
		if(!StringUtil.isNullOrEmpty(XS)){
			String filterString = " EXISTS (SELECT * FROM (SELECT FRE_ORDER_ID AS FRE_ORDER_ID_ FROM FRE_BOX_REQUIRE WHERE BOX_BELONG LIKE '%" + XS + "%') AS TXS WHERE ID=TXS.FRE_ORDER_ID_)";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		//提单号
		if(!StringUtil.isNullOrEmpty(TDH)){
			String filterString = FreightFilterUtil.sqlFilterColumn("ID", "TDH", TDH);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		//船名航次
		if(!StringUtil.isNullOrEmpty(CMHC)){
			String filterString = FreightFilterUtil.sqlFilterColumn("ID", "CMHC", CMHC);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		pageView = freightOrderService.query(pageView, freightOrder);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-order-list-service";
	}
	
	//操作员所见
	@RequestMapping(value = "fre-order-list-manipulator")
	public String queryMaintain(Model model, FreightOrder freightOrder, 
			String JZX, String TDH, String CMHC, @ModelAttribute PageView<FreightOrder> pageView, HttpServletRequest request) {
		if (pageView == null) {
			pageView = new PageView<FreightOrder>(1);
		}
		freightOrder.setManipulator(((User)request.getSession().getAttribute("userSession")).getDisplayName());
		if(StringUtil.isNullOrEmpty(freightOrder.getOrderStatus())){//如果状态为空，则设置默认状态
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" ORDER_STATUS IN('审核中', '已审核', '确认追回')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND ORDER_STATUS IN('审核中', '已审核', '确认追回')");
			}
		}
		
		//集装箱
		if(!StringUtil.isNullOrEmpty(JZX)){
			String filterString = FreightFilterUtil.sqlFilterNumber("ID", JZX);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		//提单号
		if(!StringUtil.isNullOrEmpty(TDH)){
			String filterString = FreightFilterUtil.sqlFilterColumn("ID", "TDH", TDH);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		//船名航次
		if(!StringUtil.isNullOrEmpty(CMHC)){
			String filterString = FreightFilterUtil.sqlFilterColumn("ID", "CMHC", CMHC);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		pageView = freightOrderService.query(pageView, freightOrder);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-order-list-manipulator";
	}
	//财务所见
	@RequestMapping(value = "fre-order-list-fas")
	public String queryEXPENSE(Model model, FreightOrder freightOrder, @ModelAttribute PageView<FreightOrder> pageView, HttpServletRequest request) {
		if (pageView == null) {
			pageView = new PageView<FreightOrder>(1);
		}
		freightOrder.setManipulator(((User)request.getSession().getAttribute("userSession")).getDisplayName());
		pageView = freightOrderService.query(pageView, freightOrder);
		model.addAttribute("pageView", pageView);
		model.addAttribute("pageType", "expense");
		return "/content/fre/fre-order-list-fas";
	}
	
	//仅查看
	@RequestMapping(value = "fre-order-list-query")
	public String queryView(Model model, FreightOrder freightOrder, String JZX, String TDH, String CMHC,
			String DZ, String FZ, @ModelAttribute PageView<FreightOrder> pageView, HttpServletRequest request) {
		if (pageView == null) {
			pageView = new PageView<FreightOrder>(1);
		}
		/*
		 * 暂时取消限制
		 * if(StringUtil.isNullOrEmpty(freightOrder.getOrderStatus())){//如果状态为空，则设置默认状态
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" ORDER_STATUS ='已完成' ");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND ORDER_STATUS ='已完成' ");
			}
		}
		*/
		
		//集装箱
		if(!StringUtil.isNullOrEmpty(JZX)){
			String filterString = FreightFilterUtil.sqlFilterNumber("ID", JZX);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		//提单号
		if(!StringUtil.isNullOrEmpty(TDH)){
			String filterString = FreightFilterUtil.sqlFilterColumn("ID", "TDH", TDH);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		//船名航次
		if(!StringUtil.isNullOrEmpty(CMHC)){
			String filterString = FreightFilterUtil.sqlFilterColumn("ID", "CMHC", CMHC);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		//到站
		if(!StringUtil.isNullOrEmpty(DZ)){
			String filterString = FreightFilterUtil.sqlFilterColumn("ID", "DZ", DZ);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		//发站
		if(!StringUtil.isNullOrEmpty(FZ)){
			String filterString = FreightFilterUtil.sqlFilterColumn("ID", "FZ", FZ);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		pageView = freightOrderService.query(pageView, freightOrder);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-order-list-query";
	}

	@RequestMapping(value = "fre-order-input")
	public String input(Model model, String id) {
		FreightOrder item = null;
		if (id != null) {
			item = freightOrderService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("freightOrderBoxs", freightOrderBoxService.getByFreightOrderId(id));
		model.addAttribute("KeyData", freightOrderService.getKeyData(id));
		return "/content/fre/fre-order-input";
	}
	
	@RequestMapping(value = "fre-order-input-service")
	public String inputService(Model model, String id) {
		FreightOrder item = null;
		if (id != null) {
			item = freightOrderService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("freightOrderBoxs", freightOrderBoxService.getByFreightOrderId(id));
		model.addAttribute("KeyData", freightOrderService.getKeyData(id));
		return "/content/fre/fre-order-input-service";
	}
	
	/**
	 * 业务所见
	 */
	@RequestMapping(value = "fre-order-input-salesman")
	public String inputSale(Model model, String id) {
		FreightOrder item = null;
		if (id != null) {
			item = freightOrderService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("freightOrderBoxs", freightOrderBoxService.getByFreightOrderId(id));
		model.addAttribute("KeyData", freightOrderService.getKeyData(id));
		return "/content/fre/fre-order-input-salesman";
	}
	
	/**
	 * 操作所见
	 */
	@RequestMapping(value = "fre-order-input-manipulator")
	public String inputManipulator(Model model, String id) {
		FreightOrder item = null;
		if (id != null) {
			item = freightOrderService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("freightOrderBoxs", freightOrderBoxService.getByFreightOrderId(id));
		model.addAttribute("KeyData", freightOrderService.getKeyData(id));
		return "/content/fre/fre-order-input-manipulator";
	}
	
	/**
	 * 审核所见
	 */
	@RequestMapping(value = "fre-order-input-audit")
	public String auditList(Model model, String id) {
		FreightOrder item = null;
		if (id != null) {
			item = freightOrderService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("freightOrderBoxs", freightOrderBoxService.getByFreightOrderId(id));
		model.addAttribute("KeyData", freightOrderService.getKeyData(id));
		return "/content/fre/fre-order-input-audit";
	}

	@RequestMapping(value = "fre-order-save", method = RequestMethod.POST)
	public String add(FreightOrder freightOrder, MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
		Map<String, String> map = FileUtil.upload("muiltFile", request);
		if(map != null){
			freightOrder.setCommission(map.get("fileData"));
		}
		String orderStatus = freightOrder.getOrderStatus();
		if(StringUtil.isNullOrEmpty(orderStatus)){
			freightOrder.setOrderStatus("未提交");
			freightOrder.setExpenseStatus("未添加");
		}
		if(StringUtil.isNullOrEmpty(freightOrder.getOrderNumber())){
			freightOrder.setOrderNumber(freightOrderService.getNextOrderNumber(SpringSecurityUtil.getCurrentUserName()));
		}
		String id = null;
		if(freightOrder.getId() == null){
			freightOrder.setCreatorUserId(((User)request.getSession().getAttribute("userSession")).getId());
			freightOrder.setOrgEntityId(((User)request.getSession().getAttribute("userSession")).getOrgEntity().getId());
			id = StringUtil.getUUID();
			freightOrder.setId(id);
			freightOrder.setPlaceTime(new Date());
			freightOrderService.add(freightOrder);
		}else{
			freightOrderService.modify(freightOrder);
			id = freightOrder.getId();
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-order-input.do?id=" + id;
	}
	
	@RequestMapping(value = "fre-order-save-service", method = RequestMethod.POST)
	public String addService(FreightOrder freightOrder, MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
		Map<String, String> map = FileUtil.upload("muiltFile", request);
		if(map != null){
			freightOrder.setCommission(map.get("fileData"));
		}
		String orderStatus = freightOrder.getOrderStatus();
		if(StringUtil.isNullOrEmpty(orderStatus)){
			freightOrder.setOrderStatus("未提交");
			freightOrder.setExpenseStatus("未添加");
		}
		if(StringUtil.isNullOrEmpty(freightOrder.getOrderNumber())){
			freightOrder.setOrderNumber(freightOrderService.getNextOrderNumber(SpringSecurityUtil.getCurrentUserName()));
		}
		String id = null;
		if(freightOrder.getId() == null){
			freightOrder.setCreatorUserId(((User)request.getSession().getAttribute("userSession")).getId());
			freightOrder.setOrgEntityId(((User)request.getSession().getAttribute("userSession")).getOrgEntity().getId());
			id = StringUtil.getUUID();
			freightOrder.setId(id);
			freightOrder.setPlaceTime(new Date());
			freightOrderService.add(freightOrder);
		}else{
			freightOrderService.modify(freightOrder);
			id = freightOrder.getId();
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-order-input-service.do?id=" + id;
	}
	
	@RequestMapping(value = "fre-order-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		if(freightOrderService.doneRemoveOrder(selectedItem)){
			messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		}else{
			messageHelper.addFlashMessage(redirectAttributes,"删除失败");
		}
		
		return "redirect:fre-order-list.do";
	}
	
	@RequestMapping(value = "fre-order-remove-service")
	public String removeSale(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		if(freightOrderService.doneRemoveOrder(selectedItem)){
			messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		}else{
			messageHelper.addFlashMessage(redirectAttributes,"删除失败");
		}
		return "redirect:fre-order-list-service.do";
	}
	
	@RequestMapping(value = "fre-order-nodes")
	@ResponseBody
	public List<Map<String, Object>> getNode (@RequestParam(value="freightOrderId", required=true) String freightOrderId) {
		FreightOrder freightOrder = freightOrderService.getById(freightOrderId);
		FreightMaintain filter = new FreightMaintain();
		filter.setFreightOrder(freightOrderService.getById(freightOrderId));
		List<FreightMaintain> childMaintains = freightMaintainService.queryForList(filter);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", freightOrderId);
		map.put("name", freightOrder.getOrderNumber() + "_" + freightOrder.getOrderStatus());
		map.put("nodeType", "order");
		map.put("children", getMaintain(childMaintains));
		map.put("open", true);
		map.put("iconSkin", "order-normal");
		list.add(map);
		return list;
	}
	
	public List<Map<String, Object>> getMaintain(List<FreightMaintain> freightMaintains){
		if (freightMaintains == null) {
            return null;
        }
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        try {
            for (FreightMaintain maintain : freightMaintains) {
            	//如果没有父节点,说明才是order下面的直接节点
            	if(maintain.getParentMaintain() == null){
            		Map<String, Object> map = new HashMap<String, Object>();
                	map.put("id", maintain.getId());
    				map.put("name", maintain.getDisplayIndex() + "_" + maintain.getFreightMaintainType().getTypeName());
    				map.put("nodeType", "maintain");
    				map.put("parentMaintainId", "");
    				map.put("priority", maintain.getFreightMaintainType().getPriority());
    				map.put("descn", maintain.getDescn());
    				map.put("iconSkin", "maintain-normal");
    				
                    List<Map<String, Object>> childList = getChildMaintain(maintain.getId(), freightMaintains);
                    List<Map<String, Object>> childAction = getAction(maintain);
    				if(childList != null && !childList.isEmpty()){
    					childList.addAll(childAction);
    					map.put("children", childList);
    					map.put("open", true);
    				}else if(childAction != null && !childAction.isEmpty()){
    					map.put("children", childAction);
    					map.put("open", true);
    				}
    				
    				list.add(map);
            	}
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
        }

        return list;
	}
	
	public List<Map<String, Object>> getChildMaintain(String id, List<FreightMaintain> freightMaintains){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(FreightMaintain maintain : freightMaintains){
			Map<String, Object> map = new HashMap<String, Object>();
			if(maintain.getParentMaintain() != null && maintain.getParentMaintain().getId().equals(id)){
				map.put("id", maintain.getId());
				map.put("name", maintain.getDisplayIndex() + "_" + maintain.getFreightMaintainType().getTypeName());
				map.put("nodeType", "maintain");
				map.put("parentMaintainId", maintain.getParentMaintain().getId());
				map.put("priority", maintain.getFreightMaintainType().getPriority());
				map.put("descn", maintain.getDescn());
				map.put("iconSkin", "maintain-normal");
				
				List<Map<String, Object>> childList = getChildMaintain(maintain.getId(), freightMaintains);
				List<Map<String, Object>> childAction = getAction(maintain);
				if(childList != null && !childList.isEmpty()){
					childList.addAll(childAction);
					map.put("children", childList);
				}else if(childAction != null && !childAction.isEmpty()){
					map.put("children", childAction);
					map.put("open", true);
				}
				list.add(map);
			}
		}
		
		return list;
	}
	
	public List<Map<String, Object>> getAction(FreightMaintain freightMaintain){
		FreightAction filter = new FreightAction();
		filter.setFreightMaintain(freightMaintain);
		List<FreightAction> actions = freightActionService.queryForList(filter);
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(actions != null && !actions.isEmpty()){
			for(FreightAction action : actions){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", action.getId());
				map.put("name", action.getDisplayIndex() + "_" + action.getFreightActionType().getTypeName() + "  " + action.getStatus());
				map.put("nodeType", "action");
				map.put("actionTypeId", action.getFreightActionType().getId());
				map.put("parentMaintainId", action.getFreightMaintain().getId());
				String status = action.getStatus();
				map.put("status", status);
				map.put("delegate", action.getDelegate());
				map.put("prime", action.getPrime());
				map.put("internal", action.getInternal());
				map.put("createTime", action.getCreateTime());
				map.put("modifyTime", action.getModifyTime());
				if("未执行".equals(status)){
					map.put("iconSkin", "action-1");
				}else if("预备执行".equals(status)){
					map.put("iconSkin", "action-2");
				}else if("预备完成".equals(status)){
					map.put("iconSkin", "action-3");
				}else if("已发送".equals(status)){
					map.put("iconSkin", "action-4");
				}else if("已执行".equals(status)){
					map.put("iconSkin", "action-5");
				}
				
				list.add(map);
			}
		}
		
		return list;
	}
	
	/**
	 *提交审核
	 */
	@RequestMapping(value = "fre-order-to-order-audit")
	@ResponseBody()
	public String toOrderAudit(@RequestParam(value="freightOrderId", required=true)String[] freightOrderId) {
		if(freightOrderService.toOrderAudit(freightOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
	//追回订单
	@RequestMapping(value = "fre-order-to-order-recover")
	@ResponseBody()
	public String toOrderRecover(@RequestParam(value="freightOrderId", required=true)String[] freightOrderId) {
		if(freightOrderService.toOrderRecover(freightOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-order-done-order-recover")
	@ResponseBody()
	public String doneOrderRecover(@RequestParam(value="freightOrderId", required=true)String[] freightOrderId) {
		if(freightOrderService.doneOrderRecover(freightOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-order-done-order-audit")
	@ResponseBody()
	public String doneOrderAudit(@RequestParam(value="freightOrderId", required=true)String[] freightOrderId) {
		if(freightOrderService.doneOrderAudit(freightOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-order-back-order-audit")
	@ResponseBody()
	public String backOrderAudit(@RequestParam(value="freightOrderId", required=true)String[] freightOrderId) {
		if(freightOrderService.backOrderAudit(freightOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-order-to-expense-audit")
	@ResponseBody()
	public String toExpenseAudit(@RequestParam(value="freightOrderId", required=true)String[] freightOrderId) {
		if(freightOrderService.toExpenseAudit(freightOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-order-done-expense-audit")
	@ResponseBody()
	public String doneExpenseAudit(@RequestParam(value="freightOrderId", required=true)String[] freightOrderId) {
		if(freightOrderService.doneExpenseAudit(freightOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-order-back-expense-audit")
	@ResponseBody()
	public String backExpenseAudit(@RequestParam(value="freightOrderId", required=true)String[] freightOrderId) {
		if(freightOrderService.backExpenseAudit(freightOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 费用添加完毕
	 * @param freightOrderId
	 * @return
	 */
	@RequestMapping(value = "fre-order-finish-expense")
	@ResponseBody()
	public String finishExpense(@RequestParam(value="freightOrderId", required=true)String[] freightOrderId) {
		if(freightOrderService.doneCompleteExpense(freightOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-order-recall-complete-expense-without-deduct")
	@ResponseBody()
	public String recallCompleteExpenseWithoutDeduct(@RequestParam(value="freightOrderId", required=true)String[] freightOrderId) {
		if(freightOrderService.recallCompleteExpenseWithoutDeduct(freightOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-order-recall-complete-expense-with-deduct")
	@ResponseBody()
	public String recallCompleteExpenseWithDeduct(@RequestParam(value="freightOrderId", required=true)String[] freightOrderId) {
		if(freightOrderService.recallCompleteExpenseWithDeduct(freightOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 保存亏损订单情况说明
	 */
	@RequestMapping(value = "fre-order-done-add-deficitreason")
	@ResponseBody()
	public String doneAddDeficitReason(@RequestParam(value="freightOrderId", required=true)String freightOrderId,
			@RequestParam(value="deficitReason", required=true)String deficitReason) {
		if(freightOrderService.doneAddDeficitReason(freightOrderId, deficitReason)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-order-finish-order")
	@ResponseBody
	public String finishOrder(@RequestParam(value="freightOrderId", required=true)String[] freightOrderId) {
		if(freightOrderService.finishOrder(freightOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-order-invalid-order")
	@ResponseBody
	public String invalidOrder(@RequestParam(value="freightOrderId", required=true)String[] freightOrderId) {
		if(freightOrderService.invalidOrder(freightOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-order-download-commission")
	@ResponseBody
	public void downloadCommission(String freightOrderId, HttpServletResponse response) throws IOException {
		FreightOrder freightOrder = freightOrderService.getById(freightOrderId);
		if(StringUtil.isNullOrEmpty(freightOrder.getCommission())){
			response.getWriter().print("<script>alert(\"没有委托书！\");window.close();</script>");
		}else{
			String fileName = freightOrder.getDelegateNumber() + "." + freightOrder.getCommission().split("\\.")[1];
			FileUtil.download(freightOrder.getCommission(), fileName, response);
		}
	}
	
	
	//拖动节点产生的效果
	@RequestMapping(value = "fre-order-nodes-drop")
	@ResponseBody
	public String drop(@RequestParam(value="sourceNodeId", required=true) String sourceNodeId, 
			@RequestParam(value="targetNodeId", required=true) String targetNodeId, 
			@RequestParam(value="nodeType", required=true) String nodeType) {
		if("action".equals(nodeType)){//动作
			FreightAction sourceAction = freightActionService.getById(sourceNodeId);
			FreightAction targetAction = freightActionService.getById(targetNodeId);
			FreightAction filter = new FreightAction();
			filter.setFreightMaintain(sourceAction.getFreightMaintain());
			List<FreightAction> list = freightActionService.queryForList(filter);
			int sourceIndex = sourceAction.getDisplayIndex();
			int targetIndex = targetAction.getDisplayIndex();
			if(sourceIndex > targetIndex){//从下往上拖动,之间的节点都得+1
				for(FreightAction freightAction : list){
					int index = freightAction.getDisplayIndex();
					if(index >= targetIndex && index < sourceIndex){
						freightAction.setDisplayIndex(index + 1);
						freightActionService.modify(freightAction);
					}
				}
				sourceAction.setDisplayIndex(targetIndex);
				freightActionService.modify(sourceAction);
			}else{//从上往下拖动,之间的节点都得-1
				for(FreightAction freightAction : list){
					int index = freightAction.getDisplayIndex();
					if(index > sourceIndex && index <= targetIndex){
						freightAction.setDisplayIndex(index - 1);
						freightActionService.modify(freightAction);
					}
				}
				sourceAction.setDisplayIndex(targetIndex);
				freightActionService.modify(sourceAction);
			}
			
		}else{//操作
			FreightMaintain sourceMaintain = freightMaintainService.getById(sourceNodeId);
			FreightMaintain targetMaintain = freightMaintainService.getById(targetNodeId);
			FreightMaintain filter = new FreightMaintain();
			filter.setFreightOrder(sourceMaintain.getFreightOrder());
			List<FreightMaintain> list = null;
			if(sourceMaintain.getParentMaintain() != null){
				filter.setParentMaintain(sourceMaintain.getParentMaintain());
				list = freightMaintainService.queryForList(filter);
			}else{
				list = freightMaintainService.queryForList(filter);//获取到全部的
				for(FreightMaintain freightMaintain : list){
					if(freightMaintain.getParentMaintain() != null){
						list.remove(freightMaintain);
					}
				}
			}
			
			int sourceIndex = sourceMaintain.getDisplayIndex();
			int targetIndex = targetMaintain.getDisplayIndex();
			if(sourceIndex > targetIndex){//从下往上拖动,之间的节点都得+1
				for(FreightMaintain freightMaintain : list){
					int index = freightMaintain.getDisplayIndex();
					if(index >= targetIndex && index < sourceIndex){
						freightMaintain.setDisplayIndex(index + 1);
						freightMaintainService.modify(freightMaintain);
					}
				}
				sourceMaintain.setDisplayIndex(targetIndex);
				freightMaintainService.modify(sourceMaintain);
			}else{//从上往下拖动,之间的节点都得-1
				for(FreightMaintain freightAction : list){
					int index = freightAction.getDisplayIndex();
					if(index > sourceIndex && index <= targetIndex){
						freightAction.setDisplayIndex(index - 1);
						freightMaintainService.modify(freightAction);
					}
				}
				sourceMaintain.setDisplayIndex(targetIndex);
				freightMaintainService.modify(sourceMaintain);
			}
		}
		return "success";
	}
	
	/**
	 * 复制新增
	 */
	@RequestMapping(value = "fre-order-to-add-order-by-copy")
	@ResponseBody
	public Map<String, Object> toAddOrderByCopy(@RequestParam(value="freightOrderId", required=true)String freightOrderId) {
		return freightOrderService.toAddOrderByCopy(freightOrderId);
	}
	
	@RequestMapping(value = "fre-order-done-add-order-by-copy")
	@ResponseBody
	public String doneAddOrderByCopy(@RequestParam(value="freightOrderId", required=true)String freightOrderId,
			String[] freightBoxRequireId, String[] freightExpenseId, String[] freightActionValueId,
			HttpServletRequest request) {
		 User creator = (User)request.getSession().getAttribute("userSession");
		 
		 return freightOrderService.doneAddOrderByCopy(freightOrderId, freightBoxRequireId, freightExpenseId, freightActionValueId, creator);
	}
	
	/**
	 * 复制费用
	 */
	@RequestMapping(value = "fre-order-to-copy-expense")
	@ResponseBody
	public Map<String, Object> toCopyExpense(@RequestParam(value="freightOrderId", required=true)String freightOrderId) {
		return freightOrderService.toCopyExpense(freightOrderId);
	}
	
	@RequestMapping(value = "fre-order-done-copy-expense")
	@ResponseBody
	public String doneCopyExpense(
			@RequestParam(value="sourceOrderId", required=true)String sourceOrderId,
			@RequestParam(value="targetOrderId", required=true)String[] targetOrderId,
			@RequestParam(value="freightExpenseId", required=true)String[] freightExpenseId,
			@RequestParam(value="sheatheAllBox", required=true)String sheatheAllBox) {
		 if(freightOrderService.doneCopyExpense(sourceOrderId, targetOrderId, freightExpenseId, sheatheAllBox)){
			 return "success";
		 }else{
			 return "error";
		 }
	}

	
	//订单复审、终审
	@RequestMapping(value = "fre-order-done-rehear-order")
	@ResponseBody()
	public String doneRehearOrder(@RequestParam(value="freightOrderId", required=true)String[] freightOrderId) {
		if(freightOrderService.doneRehearOrder(freightOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
	@RequestMapping(value = "fre-order-back-rehear-order")
	@ResponseBody()
	public String backRehearOrder(@RequestParam(value="freightOrderId", required=true)String[] freightOrderId) {
		if(freightOrderService.backRehearOrder(freightOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
	@RequestMapping(value = "fre-order-done-eventide-order")
	@ResponseBody()
	public String doneEventideOrder(@RequestParam(value="freightOrderId", required=true)String[] freightOrderId) {
		if(freightOrderService.doneEventideOrder(freightOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
	@RequestMapping(value = "fre-order-back-eventide-order")
	@ResponseBody()
	public String backEventideOrder(@RequestParam(value="freightOrderId", required=true)String[] freightOrderId) {
		if(freightOrderService.backEventideOrder(freightOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
		
	@ResponseBody
	@RequestMapping(value = "fre-order-to-export-batch-order-to-file")
	public String toBatchOrderExport(@RequestParam(value="selectedItem", required=true)String[] selectedItem) throws IOException{
		String targetFile = StringUtil.getUUID() + ".xls";
		FileUtils.copyInputStreamToFile(IExmportUtil.exportMultiColumn(
				new String[]{"业务归属", "一级类型", "二级类型","三级类型","四级类型","品名","单位","订单号", "20英尺", "40英尺", "TEU", "收支差" ,"销项税", "进项税", "税差", "财务税差", "销售税差", "销售部门", "业务员"}, 
				freightOrderService.toBatchOrderExport(selectedItem)), new File(FileUtil.attachmentPath, targetFile));
		return targetFile;
	}
}
