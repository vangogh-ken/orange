package com.van.halley.basis.web;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
import com.van.halley.db.persistence.entity.BasisApplication;
import com.van.halley.util.StringUtil;
import com.van.service.BasisApplicationService;
import com.van.service.BasisSubstanceTypeService;

@Controller
@RequestMapping(value = "/basis/")
public class BasisApplicationController {
	@Autowired
	private BasisApplicationService basisApplicationService;
	@Autowired
	private BasisSubstanceTypeService basisSubstanceTypeService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "basis-application-list")
	public String unread(Model model, BasisApplication basisApplication, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = basisApplicationService.query(pageView, basisApplication);

		model.addAttribute("pageView", pageView);
		return "/content/basis/basis-application-list";
	}

	@RequestMapping(value = "basis-application-input")
	public String input(Model model, String id) {
		BasisApplication item = null;
		if (id != null) {
			item = basisApplicationService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/basis/basis-application-input";
	}

	@RequestMapping(value = "basis-application-save", method = RequestMethod.POST)
	public String add(BasisApplication basisApplication, RedirectAttributes redirectAttributes) {
		if(basisApplication.getId() == null){
			basisApplicationService.add(basisApplication);
		}else{
			basisApplicationService.modify(basisApplication);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:basis-application-list.do";
	}
	
	@RequestMapping(value = "basis-application-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			basisApplicationService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:basis-application-list.do";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "basis-application-to-add-application")
	public Map<String, Object> toAddActionFiled(@RequestParam(value="basisSubstanceTypeId", required=true)String basisSubstanceTypeId){
		Map<String, Object> map = new HashMap<String, Object>();
		BasisApplication filter = new BasisApplication();
		filter.setBasisSubstanceType(basisSubstanceTypeService.getById(basisSubstanceTypeId));
		map.put("hasAddData", basisApplicationService.queryForList(filter));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-application-done-add-application")
	public String doneAddActionFiled(@RequestBody BasisApplication basisApplication, 
			@RequestParam(value="basisSubstanceTypeId",required=true)String basisSubstanceTypeId,
			String basisApplicationId){
		basisApplication.setBasisSubstanceType(basisSubstanceTypeService.getById(basisSubstanceTypeId));
		if(StringUtil.isNullOrEmpty(basisApplicationId)){
			basisApplicationService.add(basisApplication);
		}else{
			basisApplication.setId(basisApplicationId);
			basisApplicationService.modify(basisApplication);
		}
		
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-application-to-revise-application")
	public Map<String, Object> toReviseActionFiled(@RequestParam(value="basisApplicationId",required=true)String basisApplicationId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("basisApplication", basisApplicationService.getById(basisApplicationId));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-application-done-remove-application")
	public String doneRemoveFiled(@RequestParam(value="basisApplicationId",required=true)String[] basisApplicationId){
		for(String id : basisApplicationId){
			basisApplicationService.delete(id);
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-application-check-applicationname")
	public boolean checkName(@RequestParam(required=true, value="applicationName") String applicationName, 
			@RequestParam(required=true, value="basisSubstanceTypeId") String basisSubstanceTypeId, String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM BASIS_APPLICATION WHERE APPLICATION_NAME = ? AND BASIS_SUBSTANCE_TYPE_ID = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, applicationName, basisSubstanceTypeId, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM BASIS_APPLICATION WHERE APPLICATION_NAME = ? AND BASIS_SUBSTANCE_TYPE_ID = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, applicationName, basisSubstanceTypeId);
			return count == 0;
		}
	}
}
