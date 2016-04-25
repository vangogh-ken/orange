package com.van.halley.fre.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.FreightBoxRequire;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FreightBoxRequireService;
import com.van.service.FreightOrderService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightBoxRequireController {
	@Autowired
	private FreightBoxRequireService freightBoxRequireService;
	@Autowired
	private FreightOrderService freightOrderService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-box-require-list")
	public String query(Model model, FreightBoxRequire freightBoxRequire, 
			String TDH, HttpServletRequest request, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		String displayName = ((User)request.getSession().getAttribute("userSession")).getDisplayName();
		String filterString = " FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE MANIPULATOR='" + displayName +"')";
		if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
			pageView.setFilterText(filterString);
		}else{
			pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
		}
		
		if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
			pageView.setFilterText(" FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE ORDER_STATUS = '已审核') AND BOX_SOURCE != '外理箱'");
		}else{
			pageView.setFilterText(pageView.getFilterText() + " AND FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE ORDER_STATUS = '已审核') AND BOX_SOURCE != '外理箱'");
		}
		
		if(StringUtil.isNullOrEmpty(freightBoxRequire.getStatus())){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" STATUS IN ('待选箱', '已选箱')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND STATUS IN ('待选箱', '已选箱')");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(TDH)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" BL_NO LIKE '%" + TDH + "%'");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND BL_NO LIKE '%" + TDH + "%'");
			}
		}
		pageView = freightBoxRequireService.query(pageView, freightBoxRequire);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-box-require-list";
	}
	
	/**
	 * 放箱管理：只能显示订单状态未已审核的订单的箱需，且集装箱来源不为外理箱
	 */
	@RequestMapping(value = "fre-box-require-list-release")
	public String queryRelease(Model model, FreightBoxRequire freightBoxRequire, String orderNumber, 
			String boxNumber, String TDH, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
			pageView.setFilterText(" FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE ORDER_STATUS = '已审核') AND BOX_SOURCE != '外理箱'");
		}else{
			pageView.setFilterText(pageView.getFilterText() + " AND FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE ORDER_STATUS = '已审核') AND BOX_SOURCE != '外理箱'");
		}
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			String filterText = " FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE ORDER_NUMBER LIKE '%" + orderNumber + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(boxNumber)){
			String filterText = " ID IN (SELECT FRE_BOX_REQUIRE_ID FROM FRE_ORDER_BOX WHERE FRE_BOX_ID IN(SELECT ID FROM FRE_BOX WHERE BOX_NUMBER LIKE '%" + boxNumber + "%'))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(StringUtil.isNullOrEmpty(freightBoxRequire.getStatus())){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" STATUS IN ('待选箱', '已选箱')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND STATUS IN ('待选箱', '已选箱')");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(TDH)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" BL_NO LIKE '%" + TDH + "%'");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND BL_NO LIKE '%" + TDH + "%'");
			}
		}
		
		pageView = freightBoxRequireService.query(pageView, freightBoxRequire);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-box-require-list-release";
	}
	
	/**
	 * 对于外理箱，操作自行放箱
	 */
	@RequestMapping(value = "fre-box-require-list-manipulator")
	public String queryReleaseMainpulator(Model model, FreightBoxRequire freightBoxRequire, 
			String orderNumber, String boxNumber, String TDH, HttpServletRequest request, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		String manipulator = ((User)request.getSession().getAttribute("userSession")).getDisplayName();
		if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
			pageView.setFilterText(" FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE ORDER_STATUS = '已审核' AND MANIPULATOR = '" + manipulator +"') AND BOX_SOURCE = '外理箱'");
		}else{
			pageView.setFilterText(pageView.getFilterText() + " AND FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE ORDER_STATUS = '已审核' AND MANIPULATOR = '" + manipulator +"') AND BOX_SOURCE == '外理箱'");
		}
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			String filterText = " FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE ORDER_NUMBER LIKE '%" + orderNumber + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(boxNumber)){
			String filterText = " ID IN (SELECT FRE_BOX_REQUIRE_ID FROM FRE_ORDER_BOX WHERE FRE_BOX_ID IN(SELECT ID FROM FRE_BOX WHERE BOX_NUMBER LIKE '%" + boxNumber + "%'))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(StringUtil.isNullOrEmpty(freightBoxRequire.getStatus())){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" STATUS IN ('待选箱', '已选箱')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND STATUS IN ('待选箱', '已选箱')");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(TDH)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" BL_NO LIKE '%" + TDH + "%'");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND BL_NO LIKE '%" + TDH + "%'");
			}
		}
		pageView = freightBoxRequireService.query(pageView, freightBoxRequire);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-box-require-list-manipulator";
	}

	@RequestMapping(value = "fre-box-require-input")
	public String input(Model model, String id, String freightOrderId) {
		FreightBoxRequire item = null;
		if (id != null) {
			item = freightBoxRequireService.getById(id);
		}else{
			item = new FreightBoxRequire();
			item.setFreightOrder(freightOrderService.getById(freightOrderId));
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-box-require-input";
	}

	@RequestMapping(value = "fre-box-require-save", method = RequestMethod.POST)
	public String add(FreightBoxRequire freightBoxRequire, String freightOrderId, 
			RedirectAttributes redirectAttributes) {
		if(!StringUtil.isNullOrEmpty(freightOrderId)){
			freightBoxRequire.setFreightOrder(freightOrderService.getById(freightOrderId));
		}
		if(freightBoxRequire.getId() == null){
			freightBoxRequireService.add(freightBoxRequire);
		}else{
			freightBoxRequireService.modify(freightBoxRequire);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-box-require-list.do";
	}
	
	@RequestMapping(value = "fre-box-require-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightBoxRequireService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-box-require-list.do";
	}
	
	//查看使用
	@ResponseBody
	@RequestMapping(value = "fre-box-require-to-view")
	public List<FreightBoxRequire> get(@RequestParam(value="freightOrderId", required=true)String freightOrderId) {
		FreightBoxRequire filter = new FreightBoxRequire();
		filter.setFreightOrder(freightOrderService.getById(freightOrderId));
		return freightBoxRequireService.queryForList(filter);
	}
	
	/**
	 * 修改箱需
	 */
	@ResponseBody
	@RequestMapping(value = "fre-box-require-to-revise-require")
	public Map<String, Object> toReviseRequire(@RequestParam(value="freightBoxRequireId", required=true) String freightBoxRequireId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("freightBoxRequire", freightBoxRequireService.getById(freightBoxRequireId));
		return map;
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~订单界面操作
	@ResponseBody
	@RequestMapping(value = "fre-box-require-to-add-require")
	public Map<String, Object> toAddRequire(@RequestParam(value="freightOrderId", required=true)String freightOrderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		FreightBoxRequire filter = new FreightBoxRequire();
		filter.setFreightOrder(freightOrderService.getById(freightOrderId));
		map.put("hasAddData", freightBoxRequireService.queryForList(filter));
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "fre-box-require-done-add-require")
	public String doneAddRequire(@RequestBody FreightBoxRequire freightBoxRequire, 
			@RequestParam(value="freightOrderId", required=true)String freightOrderId,
			String freightBoxRequireId) {
		if(freightBoxRequireService.doneAddRequire(freightBoxRequire, freightOrderId, freightBoxRequireId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-box-require-done-remove-require")
	public String doneRemoveRequire(@RequestParam(value="freightBoxRequireId", required=true)String[] freightBoxRequireId) {
		if(freightBoxRequireService.doneRemoveRequire(freightBoxRequireId)){
			return "success";
		}else{
			return "erorr";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-box-require-done-release-require")
	public String toRealseRequire(
			@RequestParam(value="freightBoxRequireId", required=true)String[] freightBoxRequireId, 
			HttpServletRequest request) {
		if(freightBoxRequireService.doneReleaseRequire(freightBoxRequireId, request)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 选箱
	 */
	/*@ResponseBody
	@RequestMapping(value = "fre-box-require-to-choose", method = RequestMethod.POST)
	public String choose(@RequestParam(value="boxIds", required=true)String boxIds, 
			@RequestParam(value="freightBoxRequireId", required=true) String freightBoxRequireId) {
		if(freightBoxRequireService.chooseFreightBox(boxIds.split(","), freightBoxRequireId)){
			return "success";
		}else{
			return "error";
		}
	}*/
	
	/**
	 * 放箱配封
	 */
	/*@ResponseBody
	@RequestMapping(value = "fre-box-require-to-release", method = RequestMethod.POST)
	public String release(String freightBoxRequireId){
		if(freightBoxRequireService.releaseFreightBox(freightBoxRequireId)){
			return "success";
		}else{
			return "error";
		}
	}*/
	/*@ResponseBody
	@RequestMapping(value = "fre-box-require-to-release-only", method = RequestMethod.POST)
	public String releaseOnly(String freightBoxRequireId){
		if(freightBoxRequireService.releaseFreightBoxOnly(freightBoxRequireId)){
			return "success";
		}else{
			return "error";
		}
	}*/
	
	//退回至客服
	@ResponseBody
	@RequestMapping(value = "fre-box-require-to-back-require")
	public String toBackRequire(@RequestParam(value="freightBoxRequireId", required=true)String[] freightBoxRequireId, 
			HttpServletRequest request) {
		if(freightBoxRequireService.toBackRequire(freightBoxRequireId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 修改集装箱来源
	 */
	@ResponseBody
	@RequestMapping(value = "fre-box-require-to-revise-source")
	public Map<String, Object> toReviseSource(@RequestParam(value="freightBoxRequireId", required=true)String freightBoxRequireId) {
		return freightBoxRequireService.toReviseSource(freightBoxRequireId);
	}
	@ResponseBody
	@RequestMapping(value = "fre-box-require-done-revise-source")
	public String doneReviseSource(@RequestParam(value="freightBoxRequireId", required=true)String freightBoxRequireId, 
			@RequestParam(value="boxSource", required=true)String boxSource) {
		if(freightBoxRequireService.doneReviseSource(freightBoxRequireId, boxSource)){
			return "success";
		}else{
			return "error";
		}
	}
	/**
	 * 选箱配封
	 */
	@ResponseBody
	@RequestMapping(value = "fre-box-require-to-choose-boxseal")
	public Map<String, Object> toChooseBoxseal(@RequestParam(value="freightBoxRequireId", required=true)String freightBoxRequireId) {
		return freightBoxRequireService.toChooseBoxseal(freightBoxRequireId);
	}
	@ResponseBody
	@RequestMapping(value = "fre-box-require-done-choose-boxseal")
	public Map<String, Object> doneChooseBoxseal(@RequestParam(value="freightBoxRequireId", required=true)String freightBoxRequireId, 
			@RequestParam(value="boxNumber", required=true)String[] boxNumber,
			@RequestParam(value="sealNumber", required=true)String[] sealNumber,
			String[] location) {
		return freightBoxRequireService.doneChooseBoxseal(freightBoxRequireId, boxNumber, sealNumber, location);
	}
	
	/**
	 * 确定放箱
	 */
	@ResponseBody
	@RequestMapping(value = "fre-box-require-done-release-box")
	public String doneReleaseBox(@RequestParam(value="freightBoxRequireId", required=true)String[] freightBoxRequireId) {
		if(freightBoxRequireService.doneReleaseBox(freightBoxRequireId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 取消放箱
	 */
	@ResponseBody
	@RequestMapping(value = "fre-box-require-done-recall-box")
	public String doneRecallBox(@RequestParam(value="freightBoxRequireId", required=true)String[] freightBoxRequireId) {
		if(freightBoxRequireService.doneRecallBox(freightBoxRequireId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-box-require-done-import-boxseal")
	public Map<String, Object> doneImportBoxseal(@RequestParam(value="freightBoxRequireId", required=true)String freightBoxRequireId, 
			MultipartHttpServletRequest request) {
		Map<String, String> map = FileUtil.upload("file", request);
		List<List<String>> values = IExmportUtil.importMultiColumn(new int[]{0, 1, 2}, 1, map.get("fileData"));
		return freightBoxRequireService.doneImportBoxseal(freightBoxRequireId, values);
	}
}
