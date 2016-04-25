package com.van.halley.basis.web;


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
import com.van.halley.db.persistence.entity.BasisSubstance;
import com.van.service.BasisSubstanceService;

@Controller
@RequestMapping(value = "/basis/")
public class BasisSubstanceController {
	@Autowired
	private BasisSubstanceService basisSubstanceService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "basis-substance-list")
	public String unread(Model model, BasisSubstance basisSubstance, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = basisSubstanceService.query(pageView, basisSubstance);

		model.addAttribute("pageView", pageView);
		return "/content/basis/basis-substance-list";
	}

	@RequestMapping(value = "basis-substance-input")
	public String input(Model model, String id) {
		BasisSubstance item = null;
		if (id != null) {
			item = basisSubstanceService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/basis/basis-substance-input";
	}

	@RequestMapping(value = "basis-substance-save", method = RequestMethod.POST)
	public String add(BasisSubstance basisSubstance, RedirectAttributes redirectAttributes) {
		if(basisSubstance.getId() == null){
			basisSubstanceService.add(basisSubstance);
		}else{
			basisSubstanceService.modify(basisSubstance);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:basis-substance-list.do";
	}
	
	@RequestMapping(value = "basis-substance-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			basisSubstanceService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:basis-substance-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-substance-done-prime-substance")
	public String donePrimeSubstance(@RequestParam(value="basisSubstanceId",required=true)String basisSubstanceId, 
			HttpServletRequest request){
		basisSubstanceService.donePrimeSubstance(basisSubstanceId, request);
		return "success";
	}
	
	/**
	 * 根据过滤条件获取对应类型的数据
	 * @param basisSubstanceId
	 * @param filterText
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "basis-substance-get-value-substance")
	public Map<String, Object> getSingleValueMap(@RequestParam(value="basisSubstanceId",required=true)String basisSubstanceId, 
			@RequestParam(value="filterText",required=true)String filterText){
		return basisSubstanceService
				.getBasisValueMapSingle(basisSubstanceService
						.getById(basisSubstanceId).getBasisSubstanceType().getId(), filterText);
	}
}
