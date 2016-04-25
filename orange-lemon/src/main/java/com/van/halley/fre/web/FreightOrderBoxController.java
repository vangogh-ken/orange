package com.van.halley.fre.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import com.van.halley.db.persistence.entity.FreightBoxRequire;
import com.van.halley.db.persistence.entity.FreightOrderBox;
import com.van.halley.db.persistence.entity.FreightSeal;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FreightBoxRequireService;
import com.van.service.FreightBoxService;
import com.van.service.FreightOrderBoxService;
import com.van.service.FreightOrderService;
import com.van.service.FreightSealService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightOrderBoxController {
	@Autowired
	private FreightOrderBoxService freightOrderBoxService;
	@Autowired
	private FreightOrderService freightOrderService;
	@Autowired
	private FreightBoxService freightBoxService;
	@Autowired
	private FreightBoxRequireService freightBoxRequireService;
	@Autowired
	private FreightSealService freightSealService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-order-box-list")
	public String query(Model model, FreightOrderBox freightOrderBox, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(StringUtil.isNullOrEmpty(freightOrderBox.getStatus())){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" STATUS IN ('已选箱', '已放箱')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND STATUS IN ('已选箱', '已放箱')");
			}
		}
		pageView = freightOrderBoxService.query(pageView, freightOrderBox);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-order-box-list";
	}
	
	/**
	 * 箱管
	 */
	@RequestMapping(value = "fre-order-box-list-release")
	public String queryRelease(Model model, FreightOrderBox freightOrderBox, 
			String orderNumber, String boxNumber, String boxType, String boxBelong, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(StringUtil.isNullOrEmpty(freightOrderBox.getStatus())){
			String filterText = " STATUS IN ('已选箱', '已放箱') AND FRE_BOX_REQUIRE_ID IN(SELECT ID FROM FRE_BOX_REQUIRE WHERE BOX_SOURCE <> '外理箱') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			String filterText = " FRE_BOX_REQUIRE_ID IN (SELECT ID FROM FRE_BOX_REQUIRE WHERE FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE ORDER_NUMBER LIKE '%" + orderNumber + "%'))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + "AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(boxNumber)){
			String filterText = " FRE_BOX_ID IN (SELECT ID FROM FRE_BOX WHERE BOX_NUMBER LIKE '%" + boxNumber +"%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + "AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(boxType)){
			String filterText = " FRE_BOX_REQUIRE_ID IN (SELECT ID FROM FRE_BOX_REQUIRE WHERE BOX_TYPE LIKE \"%" + boxType +"%\")";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + "AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(boxBelong)){
			String filterText = " FRE_BOX_REQUIRE_ID IN (SELECT ID FROM FRE_BOX_REQUIRE WHERE BOX_BELONG LIKE '%" + boxBelong +"%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + "AND " + filterText);
			}
		}
		pageView = freightOrderBoxService.query(pageView, freightOrderBox);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-order-box-list-release";
	}
	
	/**
	 * 操作
	 */
	@RequestMapping(value = "fre-order-box-list-manipulator")
	public String queryManipulator(Model model, FreightOrderBox freightOrderBox, 
			String orderNumber, String boxNumber, String boxType, String boxBelong, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(StringUtil.isNullOrEmpty(freightOrderBox.getStatus())){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" STATUS IN ('已选箱', '已放箱') AND FRE_BOX_REQUIRE_ID IN(SELECT ID FROM FRE_BOX_REQUIRE WHERE BOX_SOURCE = '外理箱') ");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND STATUS IN ('已选箱', '已放箱') AND FRE_BOX_REQUIRE_ID IN(SELECT ID FROM FRE_BOX_REQUIRE WHERE BOX_SOURCE = '外理箱') ");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			String filterText = " FRE_BOX_REQUIRE_ID IN (SELECT ID FROM FRE_BOX_REQUIRE WHERE FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE ORDER_NUMBER LIKE '%" + orderNumber + "%'))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + "AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(boxNumber)){
			String filterText = " FRE_BOX_ID IN (SELECT ID FROM FRE_BOX WHERE BOX_NUMBER LIKE '%" + boxNumber +"%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + "AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(boxType)){
			String filterText = " FRE_BOX_REQUIRE_ID IN (SELECT ID FROM FRE_BOX_REQUIRE WHERE BOX_TYPE LIKE \"%" + boxType +"%\")";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + "AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(boxBelong)){
			String filterText = " FRE_BOX_REQUIRE_ID IN (SELECT ID FROM FRE_BOX_REQUIRE WHERE BOX_BELONG LIKE '%" + boxBelong +"%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + "AND " + filterText);
			}
		}
		
		pageView = freightOrderBoxService.query(pageView, freightOrderBox);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-order-box-list-manipulator";
	}

	@RequestMapping(value = "fre-order-box-input")
	public String input(Model model, String id) {
		FreightOrderBox item = null;
		if (id != null) {
			item = freightOrderBoxService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-order-box-input";
	}

	@RequestMapping(value = "fre-order-box-save", method = RequestMethod.POST)
	public String add(FreightOrderBox freightOrderBox, RedirectAttributes redirectAttributes) {
		if(freightOrderBox.getId() == null){
			freightOrderBoxService.add(freightOrderBox);
		}else{
			freightOrderBoxService.modify(freightOrderBox);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-order-box-list.do";
	}
	
	@RequestMapping(value = "fre-order-box-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightOrderBoxService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-order-box-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-order-box-to-view")
	public List<FreightOrderBox> getByOrderId(@RequestParam(value="freightOrderId", required=true) String freightOrderId) {
		return freightOrderBoxService.getByFreightOrderId(freightOrderId);
	}
	
	/**
	 * 获取与箱需管理的箱封信息
	 */
	@ResponseBody
	@RequestMapping(value = "fre-order-box-byrequireid")
	public List<FreightOrderBox> getByRequireId(@RequestParam(value="freightBoxRequireId", required=true) String freightBoxRequireId) {
		FreightOrderBox filter = new FreightOrderBox();
		filter.setFreightBoxRequire(freightBoxRequireService.getById(freightBoxRequireId));
		return freightOrderBoxService.queryForList(filter);
	}
	
	/**
	 * 修改封号使用，通过ID获取，并返回未使用的封号
	 */
	@ResponseBody
	@RequestMapping(value = "fre-order-box-to-revise-seal")
	public Map<String, Object> getOne(@RequestParam(value="freightOrderBoxId", required=true) String freightOrderBoxId) {
		Map<String, Object> map = new HashMap<String, Object>();
		FreightOrderBox freightOrderBox = freightOrderBoxService.getById(freightOrderBoxId);
		map.put("freightOrderBox", freightOrderBox);
		FreightSeal filter = new FreightSeal();
		//filter.setSealType(freightOrderBox.getFreightSeal().getSealType());
		filter.setSealType("海船封");
		filter.setSealBelong(freightOrderBox.getFreightBox().getBoxBelong());
		filter.setStatus("未使用");
		List<FreightSeal> freightSeals = freightSealService.queryForList(filter);
		//freightSeals.add(freightOrderBox.getFreightSeal());不需要将原封号放进可选中
		map.put("freightSeals", freightSeals);
		
		return map;
	}
	
	/**
	 * 修改封号
	 */
	@ResponseBody
	@RequestMapping(value = "fre-order-box-done-revise-seal")
	public String modifySeal(@RequestBody FreightOrderBox freightOrderBox, 
			@RequestParam(value="freightSealId", required=true)String freightSealId) {
		freightOrderBoxService.modifySeal(freightOrderBox, freightSealId);
		return "success";
	}
	
	
	/**
	 * 修改箱号
	 */
	@ResponseBody
	@RequestMapping(value = "fre-order-box-to-revise-box")
	public Map<String, Object> toReviseBox(@RequestParam(value="freightOrderBoxId", required=true) String freightOrderBoxId) {
		return freightOrderBoxService.toReviseBox(freightOrderBoxId);
	}
	
	/**
	 * 修改封号
	 */
	@ResponseBody
	@RequestMapping(value = "fre-order-box-done-revise-box")
	public String doneReviseBox(@RequestBody FreightOrderBox freightOrderBox, 
			@RequestParam(value="freightBoxId", required=true)String freightBoxId) {
		if(freightOrderBoxService.doneReviseBox(freightOrderBox, freightBoxId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-order-box-to-export-boxseal")
	public void toExportBoxseal(@RequestParam(value="freightBoxRequireId", required=true) String freightBoxRequireId, HttpServletResponse response) {
		FreightBoxRequire freightBoxRequire = freightBoxRequireService.getById(freightBoxRequireId);
		FreightOrderBox filter = new FreightOrderBox();	
		filter.setFreightBoxRequire(freightBoxRequire);
		List<FreightOrderBox> freightOrderBoxs = freightOrderBoxService.queryForList(filter);
			FileUtil.download(IExmportUtil.exportMultiColumn(
					new String[]{"箱号", "封号", "提箱地址"}, 
					freightOrderBoxService.toExport(freightOrderBoxs)), freightBoxRequire.getFreightOrder().getOrderNumber() + "-箱封列表.xls", response);
	}
	
	/**
	 * 配铁路封
	 *//*
	@ResponseBody
	@RequestMapping(value = "fre-order-box-to-modify-rail-seal")
	public Map<String, Object> toModifyRailSeal(@RequestParam(value="freightOrderBoxId", required=true) String freightOrderBoxId) {
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("freightBoxParticular", freightOrderBoxService.getByFreightOrderBoxId(freightOrderBoxId));
		return map;
	}
	
	*//**
	 * 配铁路封
	 *//*
	@ResponseBody
	@RequestMapping(value = "fre-order-box-done-modify-rail-seal")
	public String doneModifyRailSeal(@RequestBody(required=true)FreightBoxParticular freightBoxParticular) {
		//freightBoxParticularService.modify(freightBoxParticular);
		return "success";
	}*/

}
