package com.van.halley.basis.web;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.van.halley.db.persistence.entity.BasisStatus;
import com.van.halley.util.StringUtil;
import com.van.service.BasisStatusAttributeService;
import com.van.service.BasisStatusService;
import com.van.service.BasisSubstanceTypeService;

@Controller
@RequestMapping(value = "/basis/")
public class BasisStatusController {
	@Autowired
	private BasisStatusService basisStatusService;
	@Autowired
	private BasisStatusAttributeService basisStatusAttributeService;
	@Autowired
	private BasisSubstanceTypeService basisSubstanceTypeService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "basis-status-list")
	public String unread(Model model, BasisStatus basisStatus, String basisSubstanceTypeId, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(!StringUtil.isNullOrEmpty(basisSubstanceTypeId)){
			basisStatus.setBasisSubstanceType(basisSubstanceTypeService.getById(basisSubstanceTypeId));
		}
		pageView = basisStatusService.query(pageView, basisStatus);
		model.addAttribute("basisSubstanceTypes", basisSubstanceTypeService.getAll());
		model.addAttribute("pageView", pageView);
		return "/content/basis/basis-status-list";
	}

	@RequestMapping(value = "basis-status-input")
	public String input(Model model, String id) {
		BasisStatus item = null;
		if (id != null) {
			item = basisStatusService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/basis/basis-status-input";
	}

	@RequestMapping(value = "basis-status-save", method = RequestMethod.POST)
	public String add(BasisStatus basisStatus, RedirectAttributes redirectAttributes) {
		if(basisStatus.getId() == null){
			basisStatusService.add(basisStatus);
		}else{
			basisStatusService.modify(basisStatus);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:basis-status-list.do";
	}
	
	@RequestMapping(value = "basis-status-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			basisStatusService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:basis-status-list.do";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "basis-status-to-add-status")
	public Map<String, Object> toAddActionFiled(@RequestParam(value="basisSubstanceTypeId", required=true)String basisSubstanceTypeId){
		Map<String, Object> map = new HashMap<String, Object>();
		BasisStatus filter = new BasisStatus();
		filter.setBasisSubstanceType(basisSubstanceTypeService.getById(basisSubstanceTypeId));
		map.put("hasAddData", basisStatusService.queryForList(filter));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-status-done-add-status")
	public String doneAddActionFiled(@RequestBody BasisStatus basisStatus, 
			@RequestParam(value="basisSubstanceTypeId",required=true)String basisSubstanceTypeId,
			String basisStatusId){
		basisStatus.setBasisSubstanceType(basisSubstanceTypeService.getById(basisSubstanceTypeId));
		if(StringUtil.isNullOrEmpty(basisStatusId)){
			basisStatusService.add(basisStatus);
		}else{
			basisStatus.setId(basisStatusId);
			basisStatusService.modify(basisStatus);
		}
		
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-status-to-revise-status")
	public Map<String, Object> toReviseActionFiled(@RequestParam(value="basisStatusId",required=true)String basisStatusId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("basisStatus", basisStatusService.getById(basisStatusId));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-status-done-remove-status")
	public String doneRemoveFiled(@RequestParam(value="basisStatusId",required=true)String[] basisStatusId){
		for(String id : basisStatusId){
			basisStatusService.delete(id);
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-status-check-statustext")
	public boolean checkName(@RequestParam(required=true, value="statusText") String statusText, 
			@RequestParam(required=true, value="basisSubstanceTypeId") String basisSubstanceTypeId, String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM BASIS_STATUS WHERE STATUS_TEXT = ? AND BASIS_SUBSTANCE_TYPE_ID = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, statusText, basisSubstanceTypeId, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM BASIS_STATUS WHERE STATUS_TEXT = ? AND BASIS_SUBSTANCE_TYPE_ID = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, statusText, basisSubstanceTypeId);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-status-to-config-attribute")
	public Map<String, Object> toConfigAttribute(@RequestParam(value="basisStatusId",required=true)String basisStatusId){
		return basisStatusAttributeService.toConfigAttribute(basisStatusId);
	}
	
	@ResponseBody
	@RequestMapping(value = "basis-status-done-config-attribute")
	public String doneConfigAttribute(String basisStatusId, HttpServletRequest request){
		basisStatusAttributeService.doneConfigAttribute(basisStatusId, request);
		return "success";
	}
}
