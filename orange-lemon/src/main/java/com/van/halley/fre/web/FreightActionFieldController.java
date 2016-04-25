package com.van.halley.fre.web;


import java.util.HashMap;
import java.util.List;
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
import com.van.halley.db.persistence.entity.FreightActionField;
import com.van.halley.db.persistence.entity.FreightActionType;
import com.van.halley.util.StringUtil;
import com.van.service.FreightActionFieldService;
import com.van.service.FreightActionTypeService;
import com.van.service.ValueAttributeService;
import com.van.service.ValueClassService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightActionFieldController {
	@Autowired
	private FreightActionFieldService freightActionFieldService;
	@Autowired
	private FreightActionTypeService freightActionTypeService;
	@Autowired
	private ValueAttributeService valueAttributeService;
	@Autowired
	private ValueClassService valueClassService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-action-field-list")
	public String unread(Model model, FreightActionField freightActionField, 
			String freightActionTypeName, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(!StringUtil.isNullOrEmpty(freightActionTypeName)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_ACTION_TYPE_ID IN(SELECT ID FROM FRE_ACTION_TYPE WHERE TYPE_NAME LIKE '%"+freightActionTypeName+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_ACTION_TYPE_ID IN(SELECT ID FROM FRE_ACTION_TYPE WHERE TYPE_NAME LIKE '%"+freightActionTypeName+"%')");
			}
		}
		pageView = freightActionFieldService.query(pageView, freightActionField);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-action-field-list";
	}

	@RequestMapping(value = "fre-action-field-input")
	public String input(Model model, String id) {
		FreightActionField item = null;
		if (id != null) {
			item = freightActionFieldService.getById(id);
		}
		model.addAttribute("freightActionTypes", freightActionTypeService.getAll());
		model.addAttribute("valueAttributes", valueAttributeService.getAll());
		model.addAttribute("valueClasses", valueClassService.getAll());
		model.addAttribute("item", item);
		return "/content/fre/fre-action-field-input";
	}

	@RequestMapping(value = "fre-action-field-save", method = RequestMethod.POST)
	public String add(FreightActionField freightActionField, String freightActionTypeId,
			RedirectAttributes redirectAttributes) {
		FreightActionType freightActionType = freightActionTypeService.getById(freightActionTypeId);
		freightActionField.setFreightActionType(freightActionType);
		if(freightActionField.getId() == null){
			freightActionFieldService.add(freightActionField);
			if(!"T".equals(freightActionType.getPrime())){//如果无填报内容,则修改
				freightActionType.setPrime("T");
				freightActionTypeService.modify(freightActionType);
			}
		}else{
			freightActionFieldService.modify(freightActionField);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-action-field-list.do";
	}
	
	@RequestMapping(value = "fre-action-field-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightActionFieldService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-action-field-list.do";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "fre-action-field-check-fieldname")
	public boolean checkName(@RequestParam(required=true, value="fieldName") String fieldName, 
			@RequestParam(required=true, value="freightActionTypeId") String freightActionTypeId, String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM FRE_ACTION_FIELD WHERE FIELD_NAME = ? AND FRE_ACTION_TYPE_ID = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, fieldName, freightActionTypeId, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM FRE_ACTION_FIELD WHERE FIELD_NAME = ? AND FRE_ACTION_TYPE_ID = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, fieldName, freightActionTypeId);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-action-field-check-fieldcolumn")
	public boolean checkColumn(@RequestParam(required=true, value="fieldColumn") String fieldColumn, 
			@RequestParam(required=true, value="freightActionTypeId") String freightActionTypeId, String id) {
		if(!StringUtil.isNullOrEmpty(id)){
			String sql = "SELECT COUNT(*) AS count FROM FRE_ACTION_FIELD WHERE FIELD_COLUMN = ? AND FRE_ACTION_TYPE_ID = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, fieldColumn, freightActionTypeId, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM FRE_ACTION_FIELD WHERE FIELD_COLUMN = ? AND FRE_ACTION_TYPE_ID = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, fieldColumn, freightActionTypeId);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-action-field-by-actiontypeid")
	public List<FreightActionField> getByActionType(@RequestParam(value="freightActionTypeId",required=true)String freightActionTypeId){
		return FreightActionFieldService.cacheActionField.get(freightActionTypeId);
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-action-field-to-add-field")
	public Map<String, Object> toAddActionFiled(@RequestParam(value="freightActionTypeId",required=true)String freightActionTypeId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hasAddData", FreightActionFieldService.cacheActionField.get(freightActionTypeId));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-action-field-done-add-field")
	public String doneAddActionFiled(@RequestBody FreightActionField freightActionField, 
			@RequestParam(value="freightActionTypeId",required=true)String freightActionTypeId,
			String freightActionFieldId){
		freightActionField.setFreightActionType(freightActionTypeService.getById(freightActionTypeId));
		if(StringUtil.isNullOrEmpty(freightActionFieldId)){
			freightActionFieldService.add(freightActionField);
		}else{
			freightActionField.setId(freightActionFieldId);
			freightActionFieldService.modify(freightActionField);
		}
		
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-action-field-to-revise-field")
	public Map<String, Object> toReviseActionFiled(@RequestParam(value="freightActionFieldId",required=true)String freightActionFieldId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("freightActionField", freightActionFieldService.getById(freightActionFieldId));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-action-field-done-remove-field")
	public String doneRemoveFiled(@RequestParam(value="freightActionFieldId",required=true)String[] freightActionFieldId){
		for(String id : freightActionFieldId){
			freightActionFieldService.delete(id);
		}
		return "success";
	}

}
