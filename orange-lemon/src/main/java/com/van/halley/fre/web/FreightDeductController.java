package com.van.halley.fre.web;


import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
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
import com.van.halley.db.persistence.entity.FreightDeduct;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FreightDeductService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightDeductController {
	@Autowired
	private FreightDeductService freightDeductService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-deduct-list")
	public String query(Model model, FreightDeduct freightDeduct, 
			String orderScope, String orderNumber, String delegatePart, String cargoName, String salesman, String service, String manipulator, String settleTimeStart,
			String settleTimeEnd, String reconcileTimeStart, String reconcileTimeEnd, @ModelAttribute PageView<FreightDeduct> pageView) {
		if (pageView == null) {
			pageView = new PageView<FreightDeduct>(1);
		}
		
		if(StringUtil.isNullOrEmpty(freightDeduct.getDeductType())){
			freightDeduct.setDeductType("订单提成");
		}
		
		if(!StringUtil.isNullOrEmpty(orderScope)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE ORDER_SCOPE LIKE '%" + orderScope + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE ORDER_NUMBER LIKE '%" + orderNumber + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(delegatePart)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE DELEGATE_PART LIKE '%" + delegatePart + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(cargoName)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE CARGO_NAME LIKE '%" + cargoName + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(salesman)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE SALESMAN LIKE '%" + salesman + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(service)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE CREATOR_USER_ID IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + service + "%')) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(manipulator)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE MANIPULATOR LIKE '%" + manipulator + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(settleTimeStart) && !StringUtil.isNullOrEmpty(settleTimeEnd)){
			String filterText = null;
			if(!StringUtil.isNullOrEmpty(salesman)){
				filterText = " (SETTLE_TIME_SALESMAN BETWEEN '" + settleTimeStart + "' AND '" + settleTimeEnd + "' ) ";
			}else if(!StringUtil.isNullOrEmpty(service)){
				filterText = " (SETTLE_TIME_SERVISE BETWEEN '" + settleTimeStart + "' AND '" + settleTimeEnd + "' ) ";
			}else if(!StringUtil.isNullOrEmpty(manipulator)){
				filterText = " (SETTLE_TIME_MANIPULATOR BETWEEN '" + settleTimeStart + "' AND '" + settleTimeEnd + "' ) ";
			}else{
				filterText = " (SETTLE_TIME_SALESMAN BETWEEN '" + settleTimeStart + "' AND '" + settleTimeEnd + "' ) ";
			}
			
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(reconcileTimeStart) && !StringUtil.isNullOrEmpty(reconcileTimeEnd)){
			String filterText = " (RECONCILE_TIME BETWEEN '" + reconcileTimeStart + "' AND '" + reconcileTimeEnd + "') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		pageView = freightDeductService.query(pageView, freightDeduct);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-deduct-list";
	}
	
	@RequestMapping(value = "fre-deduct-list-salesman")
	public String querySalesman(Model model, FreightDeduct freightDeduct, 
			String orderScope, String orderNumber, String delegatePart, String cargoName, String salesman, String service, String manipulator, String settleTimeStart,
			String settleTimeEnd, String reconcileTimeStart, String reconcileTimeEnd, @ModelAttribute PageView<FreightDeduct> pageView, HttpSession session) {
		if (pageView == null) {
			pageView = new PageView<FreightDeduct>(1);
		}
		
		if(StringUtil.isNullOrEmpty(freightDeduct.getDeductType())){
			freightDeduct.setDeductType("订单提成");
		}
		pageView.setFilterText(" STATUS != '" + FreightDeduct.DEDUCT_PREPARED + "' ");
		String filter = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE SALESMAN ='" + ((User)session.getAttribute("userSession")).getDisplayName() + "') ";
		if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
			pageView.setFilterText(filter);
		}else{
			pageView.setFilterText(pageView.getFilterText() + " AND " + filter);
		}
		
		if(!StringUtil.isNullOrEmpty(orderScope)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE ORDER_SCOPE LIKE '%" + orderScope + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE ORDER_NUMBER LIKE '%" + orderNumber + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(delegatePart)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE DELEGATE_PART LIKE '%" + delegatePart + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(cargoName)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE CARGO_NAME LIKE '%" + cargoName + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(salesman)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE SALESMAN LIKE '%" + salesman + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(service)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE CREATOR_USER_ID IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + service + "%')) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(manipulator)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE MANIPULATOR LIKE '%" + manipulator + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(settleTimeStart) && !StringUtil.isNullOrEmpty(settleTimeEnd)){
			String filterText = null;
			if(!StringUtil.isNullOrEmpty(salesman)){
				filterText = " (SETTLE_TIME_SALESMAN BETWEEN '" + settleTimeStart + "' AND '" + settleTimeEnd + "' ) ";
			}else if(!StringUtil.isNullOrEmpty(service)){
				filterText = " (SETTLE_TIME_SERVISE BETWEEN '" + settleTimeStart + "' AND '" + settleTimeEnd + "' ) ";
			}else if(!StringUtil.isNullOrEmpty(manipulator)){
				filterText = " (SETTLE_TIME_MANIPULATOR BETWEEN '" + settleTimeStart + "' AND '" + settleTimeEnd + "' ) ";
			}else{
				filterText = " (SETTLE_TIME_SALESMAN BETWEEN '" + settleTimeStart + "' AND '" + settleTimeEnd + "' ) ";
			}
			
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(reconcileTimeStart) && !StringUtil.isNullOrEmpty(reconcileTimeEnd)){
			String filterText = " (RECONCILE_TIME BETWEEN '" + reconcileTimeStart + "' AND '" + reconcileTimeEnd + "') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		pageView = freightDeductService.query(pageView, freightDeduct);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-deduct-list-salesman";
	}
	
	@RequestMapping(value = "fre-deduct-list-manipulator")
	public String queryManipulator(Model model, FreightDeduct freightDeduct, 
			String orderScope, String orderNumber, String delegatePart, String cargoName, String salesman, String service, String manipulator, String settleTimeStart,
			String settleTimeEnd, String reconcileTimeStart, String reconcileTimeEnd, @ModelAttribute PageView<FreightDeduct> pageView, HttpSession session) {
		if (pageView == null) {
			pageView = new PageView<FreightDeduct>(1);
		}
		
		if(StringUtil.isNullOrEmpty(freightDeduct.getDeductType())){
			freightDeduct.setDeductType("订单提成");
		}
		pageView.setFilterText(" STATUS != '" + FreightDeduct.DEDUCT_PREPARED + "' ");
		String filter = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE MANIPULATOR ='" + ((User)session.getAttribute("userSession")).getDisplayName() + "') ";
		if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
			pageView.setFilterText(filter);
		}else{
			pageView.setFilterText(pageView.getFilterText() + " AND " + filter);
		}
		
		if(!StringUtil.isNullOrEmpty(orderScope)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE ORDER_SCOPE LIKE '%" + orderScope + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE ORDER_NUMBER LIKE '%" + orderNumber + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(delegatePart)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE DELEGATE_PART LIKE '%" + delegatePart + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(cargoName)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE CARGO_NAME LIKE '%" + cargoName + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(salesman)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE SALESMAN LIKE '%" + salesman + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(service)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE CREATOR_USER_ID IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + service + "%')) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(manipulator)){
			String filterText = " FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE MANIPULATOR LIKE '%" + manipulator + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(settleTimeStart) && !StringUtil.isNullOrEmpty(settleTimeEnd)){
			String filterText = null;
			if(!StringUtil.isNullOrEmpty(salesman)){
				filterText = " (SETTLE_TIME_SALESMAN BETWEEN '" + settleTimeStart + "' AND '" + settleTimeEnd + "' ) ";
			}else if(!StringUtil.isNullOrEmpty(service)){
				filterText = " (SETTLE_TIME_SERVISE BETWEEN '" + settleTimeStart + "' AND '" + settleTimeEnd + "' ) ";
			}else if(!StringUtil.isNullOrEmpty(manipulator)){
				filterText = " (SETTLE_TIME_MANIPULATOR BETWEEN '" + settleTimeStart + "' AND '" + settleTimeEnd + "' ) ";
			}else{
				filterText = " (SETTLE_TIME_SALESMAN BETWEEN '" + settleTimeStart + "' AND '" + settleTimeEnd + "' ) ";
			}
			
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(reconcileTimeStart) && !StringUtil.isNullOrEmpty(reconcileTimeEnd)){
			String filterText = " (RECONCILE_TIME BETWEEN '" + reconcileTimeStart + "' AND '" + reconcileTimeEnd + "') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		pageView = freightDeductService.query(pageView, freightDeduct);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-deduct-list-manipulator";
	}
	
	@RequestMapping(value = "fre-deduct-input")
	public String input(Model model, String id) {
		FreightDeduct item = null;
		if (id != null) {
			item = freightDeductService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-deduct-input";
	}
	

	@RequestMapping(value = "fre-deduct-save", method = RequestMethod.POST)
	public String add(FreightDeduct freightDeduct, RedirectAttributes redirectAttributes) {
		if(freightDeduct.getId() == null){
			freightDeductService.add(freightDeduct);
		}else{
			freightDeductService.modify(freightDeduct);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-deduct-input.do";
	}
	
	
	@RequestMapping(value = "fre-deduct-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightDeductService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-deduct-list.do";
	}
	
	/**
	 * 销售提成标记
	 * @param freightDeductId
	 * @return
	 */
	@RequestMapping(value = "fre-deduct-done-salesman-deduct")
	@ResponseBody
	public String doneSettleDeduct(@RequestParam(value="freightDeductId", required=true)String[] freightDeductId){
		if(freightDeductService.doneSalesmanDeduct(freightDeductId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 操作提成标记
	 * @param freightDeductId
	 * @return
	 */
	@RequestMapping(value = "fre-deduct-done-manipulator-deduct")
	@ResponseBody
	public String doneManipulatorDeduct(@RequestParam(value="freightDeductId", required=true)String[] freightDeductId){
		if(freightDeductService.doneManipulatorDeduct(freightDeductId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-deduct-recall-salesman-deduct")
	@ResponseBody
	public String recallSalesmanDeduct(@RequestParam(value="freightDeductId", required=true)String[] freightDeductId){
		if(freightDeductService.recallSalesmanDeduct(freightDeductId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-deduct-recall-manipulator-deduct")
	@ResponseBody
	public String recallManipulatorDeduct(@RequestParam(value="freightDeductId", required=true)String[] freightDeductId){
		if(freightDeductService.recallManipulatorDeduct(freightDeductId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-deduct-to-export-deduct-to-file")
	@ResponseBody
	public String doneBatchExport(@RequestParam(value="selectedItem", required=true)String[] selectedItem) throws IOException{
		String targetFile = StringUtil.getUUID() + ".xls";
		FileUtils.copyInputStreamToFile(IExmportUtil.exportMultiColumn(
				new String[]{"类型", "订单号", "委托单位","业务归属","销售部门","销售","客服","操作", "箱型箱量箱属", "收支差", "销售提成", "是否结算","应提成时间","客服提成", "是否结算","应提成时间","操作提成", "是否结算","应提成时间", "销账时间", "资金占用天数", "状态", "创建时间", "修改时间"}, 
				freightDeductService.doneBatchExport(selectedItem)), new File(FileUtil.attachmentPath, targetFile));
		return targetFile;
	}

}
