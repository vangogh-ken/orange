package com.van.halley.fre.web;


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
import com.van.halley.db.persistence.entity.FreightMaintain;
import com.van.service.FreightActionService;
import com.van.service.FreightMaintainService;
import com.van.service.FreightMaintainTypeService;
import com.van.service.FreightOrderService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightMaintainController {
	@Autowired
	private FreightMaintainService freightMaintainService;
	@Autowired
	private FreightOrderService freightOrderService;
	@Autowired
	private FreightMaintainTypeService freightMaintainTypeService;
	@Autowired
	private FreightActionService freightActionService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-maintain-list")
	public String unread(Model model, FreightMaintain freightMaintain, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = freightMaintainService.query(pageView, freightMaintain);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-maintain-list";
	}

	@RequestMapping(value = "fre-maintain-input")
	public String input(Model model, String id) {
		FreightMaintain item = null;
		if (id != null) {
			item = freightMaintainService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-maintain-input";
	}

	@RequestMapping(value = "fre-maintain-save", method = RequestMethod.POST)
	public String add(FreightMaintain freightMaintain, RedirectAttributes redirectAttributes) {
		if(freightMaintain.getId() == null){
			freightMaintainService.add(freightMaintain);
		}else{
			freightMaintainService.modify(freightMaintain);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-maintain-list.do";
	}
	
	@RequestMapping(value = "fre-maintain-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightMaintainService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-maintain-list.do";
	}
	
	/**
	 * 根据ID获取单个操作范围
	 * @param freightMaintainId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "fre-maintain-by-freightmaintainid")
	public FreightMaintain getByID(@RequestParam(value="freightMaintainId", required=true)String freightMaintainId) {
		return freightMaintainService.getById(freightMaintainId);
	}
	
	/**
	 * 跟新说明
	 * @param freightMaintainId
	 * @param descn
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "fre-maintain-modify-descn")
	public String modifyDescn(@RequestParam(value="freightMaintainId", required=true)String freightMaintainId,
			@RequestParam(value="descn", required=true)String descn) {
		FreightMaintain freightMaintain = freightMaintainService.getById(freightMaintainId);
		freightMaintain.setDescn(descn);
		freightMaintainService.modify(freightMaintain);
		return "success";
	}
	
	@RequestMapping(value = "fre-maintain-save-async", method = RequestMethod.POST)
	@ResponseBody
	public String addMaintainAsync(@RequestBody FreightMaintain freightMaintain, 
			@RequestParam(value="freightOrderId", required=true) String freightOrderId, String parentMaintainId, 
			@RequestParam(value="freightMaintainTypeIds", required=true)String freightMaintainTypeIds) {
		freightMaintainService.doneAddMaintain(freightMaintain, freightOrderId, parentMaintainId, freightMaintainTypeIds.split(","));
		return "success";
	}
	
	@RequestMapping(value = "fre-maintain-done-remove-maintain")
	@ResponseBody
	public String delete(String freightMaintainId) {
		if(freightMaintainService.doneRemoveMaintain(new String[]{freightMaintainId})){
			return "success";
		}else{
			return "error";
		}
	}
}
