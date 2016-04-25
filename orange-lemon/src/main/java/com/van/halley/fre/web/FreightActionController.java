package com.van.halley.fre.web;


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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.FreightAction;
import com.van.service.FreightActionService;
import com.van.service.FreightDelegateService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightActionController {
	@Autowired
	private FreightActionService freightActionService;
	@Autowired
	private FreightDelegateService freightDelegateService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-action-list")
	public String unread(Model model, FreightAction freightAction, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = freightActionService.query(pageView, freightAction);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-action-list";
	}

	@RequestMapping(value = "fre-action-input")
	public String input(Model model, String id) {
		FreightAction item = null;
		if (id != null) {
			item = freightActionService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-action-input";
	}

	@RequestMapping(value = "fre-action-save", method = RequestMethod.POST)
	public String add(FreightAction freightAction, RedirectAttributes redirectAttributes) {
		if(freightAction.getId() == null){
			freightActionService.add(freightAction);
		}else{
			freightActionService.modify(freightAction);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-action-list.do";
	}
	
	@RequestMapping(value = "fre-action-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightActionService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-action-list.do";
	}
	
	@RequestMapping(value = "fre-action-done-delete-action")
	@ResponseBody
	public String delete(@RequestParam(value="freightActionId", required = true) String freightActionId) {
		if(freightActionService.doneRemoveAction(freightActionId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-action-to-execute-action")
	@ResponseBody
	public String doneSendAction(@RequestParam(value="freightActionId", required = true) String freightActionId) {
		if(freightActionService.doneSendAction(freightActionId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-action-done-execute-action")
	@ResponseBody
	public String doneExecuteAction(@RequestParam(value="freightActionId", required = true) String freightActionId) {
		if(freightActionService.doneExecuteAction(freightActionId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-action-done-add-action")
	@ResponseBody
	public String addAsync(@RequestBody FreightAction freightAction, 
			@RequestParam(value="freightActionTypeIds",required=true) String freightActionTypeIds, 
			@RequestParam(value="freightMaintainId",required=true) String freightMaintainId) {
		freightActionService.doneAddAction(freightAction, freightMaintainId, freightActionTypeIds.split(","));
		return "success";
	}
	
	/**
	 * 打开填报界面
	 */
	@RequestMapping(value = "fre-action-to-prime-action")
	@ResponseBody
	public Map<String, Object> prime(@RequestParam(value="freightActionId", required=true) String freightActionId) {
		return freightActionService.toPrimeAction(freightActionId);
	}
	
	
	/**
	 * 选择对应箱子
	 */
	@RequestMapping(value = "fre-action-to-prime-box")
	@ResponseBody
	public String primeBox(@RequestParam(value="freightActionId", required=true) String freightActionId,
			@RequestParam(value="freightOrderBoxId", required=true) String[] freightOrderBoxId) {
		if(freightActionService.toPrimeBox(freightActionId, freightOrderBoxId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 保存动作填报信息
	 */
	@RequestMapping(value = "fre-action-done-prime-action")
	@ResponseBody
	public String primeSave(@RequestParam(value="freightActionId", required=true) String freightActionId,
			HttpServletRequest request) {
		if(freightActionService.donePrimeAction(freightActionId, request)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "fre-action-to-prepare-action")
	@ResponseBody
	public String toPrepareAction(@RequestParam(value="freightActionId", required=true) String freightActionId) {
		if(freightActionService.toPrepareAction(freightActionId)){
			return "success";
		}else{
			return "error";
		}
	}
	/**
	 * 保存动作填报信息
	 */
	@RequestMapping(value = "fre-action-done-prepare-action")
	@ResponseBody
	public String primeMakeup(@RequestParam(value="freightActionId", required=true) String freightActionId,
			HttpServletRequest request) {
		freightActionService.donePrimeAction(freightActionId, request);
		if(freightActionService.donePrepareAction(freightActionId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 通过委托ID获取对应的Action
	 */
	@RequestMapping(value = "fre-action-by-delegateid")
	@ResponseBody
	public FreightAction getByDelegateId(@RequestParam(value="freightDelegateId", required = true) String freightDelegateId) {
		return freightDelegateService.getById(freightDelegateId).getFreightAction();
	}
	
}
