package com.van.halley.fre.web;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.FreightAction;
import com.van.halley.db.persistence.entity.FreightDelegate;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.fre.util.FreightFilterUtil;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FreightActionService;
import com.van.service.FreightDelegateService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightDelegateController {
	@Autowired
	private FreightDelegateService freightDelegateService;
	@Autowired
	private FreightActionService freightActionService;
	@Autowired
	private MessageHelper messageHelper;

	/**
	 * 所有
	 */
	@RequestMapping(value = "fre-delegate-list")
	public String list(Model model, FreightDelegate freightDelegate, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = freightDelegateService.query(pageView, freightDelegate);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-delegate-list";
	}
	
	/**
	 * 个人处理委托时菜单,个人所属
	 */
	@RequestMapping(value = "fre-delegate-list-owner")
	public String listOwner(Model model, FreightDelegate freightDelegate, String orderNumber, String typeName, 
			String manipulator, String PM, String JZX, HttpServletRequest request, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		freightDelegate.setOwner((User)request.getSession().getAttribute("userSession"));
		if(StringUtil.isNullOrEmpty(freightDelegate.getStatus())){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(" STATUS IN('待执行', '预备执行', '确认撤销')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND STATUS IN('待执行', '预备执行', '确认撤销'))");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(" DELEGATE_NUMBER LIKE '%" + orderNumber + "%'");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND DELEGATE_NUMBER LIKE '%" + orderNumber + "%'");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(typeName)){
			String filterString = " FRE_ACTION_ID IN (SELECT ID FROM FRE_ACTION WHERE FRE_ACTION_TYPE_ID IN(SELECT ID FROM FRE_ACTION_TYPE WHERE TYPE_NAME LIKE '%"+ typeName +"%'))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(manipulator)){
			String filterString = " FRE_ACTION_ID IN(SELECT ID FROM FRE_ACTION WHERE FRE_MAINTAIN_ID IN(SELECT ID FROM FRE_MAINTAIN WHERE FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE MANIPULATOR LIKE '%" + manipulator + "%')))";
			//EXISTS (SELECT ORDER_NUMBER FROM (SELECT ORDER_NUMBER FROM FRE_ORDER WHERE MANIPULATOR LIKE '%王%') AS T WHERE DELEGATE_NUMBER LIKE CONCAT('%', T.ORDER_NUMBER,'%'))
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(PM)){
			String filterString = " FRE_ACTION_ID IN(SELECT ID FROM FRE_ACTION WHERE FRE_MAINTAIN_ID IN(SELECT ID FROM FRE_MAINTAIN WHERE FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE CARGO_NAME LIKE '%" + PM + "%')))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(JZX)){
			//String filterString = " FRE_ACTION_ID IN(SELECT ID FROM FRE_ACTION WHERE FRE_MAINTAIN_ID IN(SELECT ID FROM FRE_MAINTAIN WHERE FRE_ORDER_ID IN (" + FreightFilterUtil.numberSQL(JZX) +")))";
			String filterString = " FRE_ACTION_ID IN(SELECT ID FROM FRE_ACTION WHERE FRE_MAINTAIN_ID IN(SELECT ID FROM FRE_MAINTAIN WHERE " + FreightFilterUtil.sqlFilterNumber("FRE_ORDER_ID", JZX) + "))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			pageView.setOrder("ASC");
			pageView.setOrderBy("PLACE_TIME");
		}
		
		pageView = freightDelegateService.query(pageView, freightDelegate);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-delegate-list-owner";
	}
	
	@RequestMapping(value = "fre-delegate-list-fas")
	public String listFas(Model model, FreightDelegate freightDelegate, String ownerName, String orderNumber, String typeName, 
			String manipulator, String PM, String JZX, String NB, HttpServletRequest request, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		if(!StringUtil.isNullOrEmpty(ownerName)){
			String filterText = " USER_ID IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + ownerName + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(" DELEGATE_NUMBER LIKE '%" + orderNumber + "%'");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND DELEGATE_NUMBER LIKE '%" + orderNumber + "%'");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(typeName)){
			String filterString = " FRE_ACTION_ID IN (SELECT ID FROM FRE_ACTION WHERE FRE_ACTION_TYPE_ID IN(SELECT ID FROM FRE_ACTION_TYPE WHERE TYPE_NAME LIKE '%"+ typeName +"%'))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(manipulator)){
			String filterString = " FRE_ACTION_ID IN(SELECT ID FROM FRE_ACTION WHERE FRE_MAINTAIN_ID IN(SELECT ID FROM FRE_MAINTAIN WHERE FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE MANIPULATOR LIKE '%" + manipulator + "%')))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(PM)){
			String filterString = " FRE_ACTION_ID IN(SELECT ID FROM FRE_ACTION WHERE FRE_MAINTAIN_ID IN(SELECT ID FROM FRE_MAINTAIN WHERE FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE CARGO_NAME LIKE '%" + PM + "%')))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(JZX)){
			String filterString = " FRE_ACTION_ID IN(SELECT ID FROM FRE_ACTION WHERE FRE_MAINTAIN_ID IN(SELECT ID FROM FRE_MAINTAIN WHERE " + FreightFilterUtil.sqlFilterNumber("FRE_ORDER_ID", JZX) + "))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(NB)){
			String filterString = " FRE_ACTION_ID IN (SELECT ID FROM FRE_ACTION WHERE INTERNAL='" + NB + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			pageView.setOrder("ASC");
			pageView.setOrderBy("PLACE_TIME");
		}
		
		pageView = freightDelegateService.query(pageView, freightDelegate);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-delegate-list-fas";
	}
	
	/**
	 * 操作员所发送的所有委托,操作所属
	 */
	@RequestMapping(value = "fre-delegate-list-manipulator")
	public String listManipulator(Model model, FreightDelegate freightDelegate, 
			String orderNumber, HttpServletRequest request, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView.setFilterText(" FRE_ACTION_ID IN (SELECT ID FROM FRE_ACTION WHERE FRE_MAINTAIN_ID IN (SELECT ID FROM FRE_MAINTAIN WHERE FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE MANIPULATOR='" + ((User)request.getSession().getAttribute("userSession")).getDisplayName() +"')))");
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(" DELEGATE_NUMBER LIKE '%" + orderNumber + "%'");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND DELEGATE_NUMBER LIKE '%" + orderNumber + "%'");
			}
		}
		pageView = freightDelegateService.query(pageView, freightDelegate);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-delegate-list-manipulator";
	}
	
	/**
	 * 班列计划委托
	 */
	@RequestMapping(value = "fre-delegate-list-rail-turn")
	public String listRailTurn(Model model, FreightDelegate freightDelegate, 
			String orderNumber, String freightActionTypeId, Date turnTime,
			HttpServletRequest request, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		freightDelegate.setOwner((User)request.getSession().getAttribute("userSession"));
		if(StringUtil.isNullOrEmpty(freightDelegate.getStatus())){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(" STATUS IN('待执行', '预备执行')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND STATUS IN('待执行', '预备执行')");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			String text = " DELEGATE_NUMBER LIKE '%" + orderNumber + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(text);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + text);
			}
		}
		if(!StringUtil.isNullOrEmpty(freightActionTypeId)){
			String text = " EXISTS ( SELECT A.ID FROM FRE_ACTION AS A WHERE A.FRE_ACTION_TYPE_ID ='" + freightActionTypeId + "' AND A.ID=FRE_ACTION_ID)";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(text);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + text);
			}
		}else{
			String text = " EXISTS ( SELECT A.ID FROM FRE_ACTION AS A WHERE A.FRE_ACTION_TYPE_ID ='308bc224-8db1-11e4-b4b5-b870f47f73d5' AND A.ID=FRE_ACTION_ID)";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(text);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + text);
			}
		}
		
		if(turnTime != null){
			String text = "EXISTS ("
					+ " SELECT * FROM (SELECT V.FRE_ACTION_ID AS AAID FROM FRE_ACTION_VALUE AS V "
					+ " WHERE V.FRE_ACTION_FIELD_ID IN(SELECT ID FROM FRE_ACTION_FIELD WHERE FIELD_COLUMN='BLSJ') "
					+ " AND V.DATE_VALUE=DATE_FORMAT('" + new SimpleDateFormat("yyyy-MM-dd").format(turnTime) + "','%Y-%c-%d')) AS VV "
					+ " WHERE VV.AAID=FRE_DELEGATE.FRE_ACTION_ID)";
			
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(text);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + text);
			}
		}
		pageView = freightDelegateService.query(pageView, freightDelegate);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-delegate-list-rail-turn";
	}
	
	
	/**
	 * 公司部门所接收到的所有委托,部门所属
	 */
	@RequestMapping(value = "fre-delegate-list-org")
	public String listOrg(Model model, FreightDelegate freightDelegate, String ownerName, String orderNumber, String typeName, 
			String manipulator, String PM, String JZX, String NB, HttpServletRequest request, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		User user = (User)request.getSession().getAttribute("userSession");
		String filterText1 = " USER_ID IN(SELECT ID FROM SYS_AUTH_USER WHERE POSITION_ID IN(SELECT ID FROM SYS_AUTH_POSITION WHERE ORG_ENTITY_ID='" + user.getOrgEntity().getId() + "'))";
		if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
			pageView.setFilterText(filterText1);
		}else{
			pageView.setFilterText(pageView.getFilterText() + " AND " + filterText1);
		}
		
		if(!StringUtil.isNullOrEmpty(ownerName)){
			String filterText = " USER_ID IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + ownerName + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(" DELEGATE_NUMBER LIKE '%" + orderNumber + "%'");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND DELEGATE_NUMBER LIKE '%" + orderNumber + "%'");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(typeName)){
			String filterString = " FRE_ACTION_ID IN (SELECT ID FROM FRE_ACTION WHERE FRE_ACTION_TYPE_ID IN(SELECT ID FROM FRE_ACTION_TYPE WHERE TYPE_NAME LIKE '%"+ typeName +"%'))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(manipulator)){
			String filterString = " FRE_ACTION_ID IN(SELECT ID FROM FRE_ACTION WHERE FRE_MAINTAIN_ID IN(SELECT ID FROM FRE_MAINTAIN WHERE FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE MANIPULATOR LIKE '%" + manipulator + "%')))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(PM)){
			String filterString = " FRE_ACTION_ID IN(SELECT ID FROM FRE_ACTION WHERE FRE_MAINTAIN_ID IN(SELECT ID FROM FRE_MAINTAIN WHERE FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE CARGO_NAME LIKE '%" + PM + "%')))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(JZX)){
			String filterString = " FRE_ACTION_ID IN(SELECT ID FROM FRE_ACTION WHERE FRE_MAINTAIN_ID IN(SELECT ID FROM FRE_MAINTAIN WHERE " + FreightFilterUtil.sqlFilterNumber("FRE_ORDER_ID", JZX) + "))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(NB)){
			String filterString = " FRE_ACTION_ID IN (SELECT ID FROM FRE_ACTION WHERE INTERNAL='" + NB + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText()) ){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			pageView.setOrder("ASC");
			pageView.setOrderBy("PLACE_TIME");
		}
		pageView = freightDelegateService.query(pageView, freightDelegate);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-delegate-list-org";
	}

	@RequestMapping(value = "fre-delegate-input")
	public String input(Model model, String id) {
		FreightDelegate item = null;
		if (id != null) {
			item = freightDelegateService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-delegate-input";
	}

	@RequestMapping(value = "fre-delegate-save", method = RequestMethod.POST)
	public String add(FreightDelegate freightDelegate, RedirectAttributes redirectAttributes) {
		if(freightDelegate.getId() == null){
			freightDelegateService.add(freightDelegate);
		}else{
			freightDelegateService.modify(freightDelegate);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-delegate-list.do";
	}
	
	@RequestMapping(value = "fre-delegate-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightDelegateService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-delegate-list.do";
	}
	//直接下载委托书
	@RequestMapping(value = "fre-delegate-to-view-by-action")
	public void load(String freightActionId, HttpServletResponse response) throws IOException {
		FreightDelegate freightDelegate = freightDelegateService.initDelegate(freightActionId);
		FileUtil.download(freightDelegate.getDelegateFile(), freightDelegate.getDelegateNumber() + "." + freightDelegate.getDelegateFile().split("\\.")[1], response);
	}
	
	@RequestMapping(value = "fre-delegate-to-browse-by-action")
	@ResponseBody
	public FreightDelegate view(String freightActionId, HttpServletResponse response) throws IOException {
		return freightDelegateService.initDelegate(freightActionId);
		//FileUtil.download(freightDelegate.getDelegateFile(), freightDelegate.getDelegateNumber() + "." + freightDelegate.getDelegateFile().split("\\.")[1], response);
	}
	
	
	@RequestMapping(value = "fre-delegate-to-online-view")
	public String toOnlineView(String freightActionId, HttpServletResponse response) throws IOException {
		return "redirect:/base/to-online-view.do?sourceFileName=" + freightDelegateService.initDelegate(freightActionId);
	}
	
	/**
	 * 通过委托ID获取对应的Action， 以及Delegate
	 */
	@RequestMapping(value = "fre-delegate-by-delegateid")
	@ResponseBody
	public Map<String, Object> getByDelegateId(@RequestParam(value="freightDelegateId", required = true) String freightDelegateId) {
		FreightDelegate freightDelegate = freightDelegateService.getById(freightDelegateId);
		FreightAction freightAction = freightDelegate.getFreightAction();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("freightDelegate", freightDelegate);
		map.put("freightAction", freightAction);
		return map;
	}
	
	/**
	 * 执行委托
	 */
	@RequestMapping(value = "fre-delegate-to-execute-delegate")
	@ResponseBody
	public String executeDelegate(@RequestParam(value="freightDelegateId", required = true) String freightDelegateId) {
		if(freightActionService.doneExecuteAction(freightDelegateService.getById(freightDelegateId).getFreightAction().getId())){
			return "success";
		}else{
			return "error";
		}
		
	}
	
	@RequestMapping(value = "fre-delegate-done-batch-execute")
	@ResponseBody
	public String doneBatchExecute(String receiverUserId, 
			@RequestParam(value="freightActionId", required = true) String[] freightActionId) {
		if(freightActionService.doneExecuteBatch(freightActionId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-delegate-to-delegate-recover")
	@ResponseBody
	public String toDelegateRecover(@RequestParam(value="freightDelegateId", required = true) String[] freightDelegateId) {
		if(freightDelegateService.toRecoverDelegate(freightDelegateId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-delegate-done-delegate-recover")
	@ResponseBody
	public String doneDelegateRecover(@RequestParam(value="freightDelegateId", required = true) String[] freightDelegateId) {
		if(freightDelegateService.doneRecoverDelegate(freightDelegateId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-delegate-done-mark-delegate")
	@ResponseBody
	public String doneMarkDelegate(@RequestParam(value="freightDelegateId", required = true) String[] freightDelegateId) {
		if(freightDelegateService.doneMarkDelegate(freightDelegateId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 导出班列计划
	 */
	@RequestMapping(value = "fre-delegate-to-export-delegate-rail")
	public void batchExportRailTurn(@RequestParam(value="freightDelegateId", required = true) String[] freightDelegateId,
			@RequestParam(value="freightActionTypeId", required = true) String freightActionTypeId,
			@RequestParam(value="turnTime", required = true) Date turnTime, HttpServletResponse response) throws IOException {
		FileUtil.download(freightDelegateService.toExportDelegateRail(freightActionTypeId, turnTime, freightDelegateId), new SimpleDateFormat("yyyy-MM-dd").format(turnTime) + "班列计划统计.xls", response);
	}
	
	
	/**
	 * 委托转发
	 */
	@RequestMapping(value = "fre-delegate-done-transfer-delegate")
	@ResponseBody
	public String doneTransferDelegate(String receiverUserId, 
			@RequestParam(value="freightDelegateId", required = true) String[] freightDelegateId) {
		if(freightDelegateService.doneTransferDelegate(receiverUserId, freightDelegateId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-delegate-to-export-delegate-to-file")
	@ResponseBody
	public String doneBatchExport(@RequestParam(value="selectedItem", required=true)String[] selectedItem) throws IOException{
		String targetFile = StringUtil.getUUID() + ".xls";
		FileUtils.copyInputStreamToFile(IExmportUtil.exportMultiColumn(
				new String[]{"委托编号", "委托时间", "运输类型","委托单位","品名","收入","支出","税金", "收支差"}, 
				freightDelegateService.doneBatchExport(selectedItem)), new File(FileUtil.attachmentPath, targetFile));
		return targetFile;
	}
	
}
