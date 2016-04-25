package com.van.halley.fre.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.FreightSeal;
import com.van.service.FreightSealService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightSealController {
	@Autowired
	private FreightSealService freightSealService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-seal-list")
	public String query(Model model, FreightSeal freightSeal, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = freightSealService.query(pageView, freightSeal);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-seal-list";
	}
	
	/**
	 *箱管
	 */
	@RequestMapping(value = "fre-seal-list-release")
	public String queryRelease(Model model, FreightSeal freightSeal, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = freightSealService.query(pageView, freightSeal);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-seal-list-release";
	}
	
	/**
	 *操作
	 */
	@RequestMapping(value = "fre-seal-list-manipulator")
	public String queryManipulator(Model model, FreightSeal freightSeal, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = freightSealService.query(pageView, freightSeal);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-seal-list-manipulator";
	}

	@RequestMapping(value = "fre-seal-input")
	public String input(Model model, String id) {
		FreightSeal item = null;
		if (id != null) {
			item = freightSealService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-seal-input";
	}
	
	@RequestMapping(value = "fre-seal-input-batch")
	public String inputBatch(Model model, String id) {
		FreightSeal item = null;
		if (id != null) {
			item = freightSealService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-seal-input-batch";
	}

	@RequestMapping(value = "fre-seal-save", method = RequestMethod.POST)
	public String add(FreightSeal freightSeal, RedirectAttributes redirectAttributes) {
		if(freightSeal.getId() == null){
			freightSealService.add(freightSeal);
		}else{
			freightSealService.modify(freightSeal);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-seal-input.do";
	}
	
	@RequestMapping(value = "fre-seal-save-release", method = RequestMethod.POST)
	public String addRelease(FreightSeal freightSeal, RedirectAttributes redirectAttributes) {
		if(freightSeal.getId() == null){
			freightSealService.add(freightSeal);
		}else{
			freightSealService.modify(freightSeal);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-seal-list-release.do";
	}
	
	@RequestMapping(value = "fre-seal-save-manipulator", method = RequestMethod.POST)
	public String addManipulator(FreightSeal freightSeal, RedirectAttributes redirectAttributes) {
		if(freightSeal.getId() == null){
			freightSealService.add(freightSeal);
		}else{
			freightSealService.modify(freightSeal);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-seal-list-manipulator.do";
	}
	
	@RequestMapping(value = "fre-seal-save-batch", method = RequestMethod.POST)
	public String addBatch(FreightSeal freightSeal, String startSealNumber, String endSealNumber, RedirectAttributes redirectAttributes) {
		freightSealService.addBatch(freightSeal, startSealNumber, endSealNumber);
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-seal-list.do";
	}
	
	@RequestMapping(value = "fre-seal-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightSealService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-seal-list.do";
	}

}
