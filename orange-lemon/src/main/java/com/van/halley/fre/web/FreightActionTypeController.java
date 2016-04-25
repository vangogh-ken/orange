package com.van.halley.fre.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
import com.van.halley.db.persistence.entity.FreightActionType;
import com.van.halley.db.persistence.entity.FreightMaintainAction;
import com.van.halley.util.StringUtil;
import com.van.service.FreightActionTypeService;
import com.van.service.FreightDelegateTemplateService;
import com.van.service.FreightMaintainActionService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightActionTypeController {
	@Autowired
	private FreightActionTypeService freightActionTypeService;
	@Autowired
	private FreightDelegateTemplateService freightDelegateTemplateService;
	@Autowired
	private FreightMaintainActionService freightMaintainActionService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-action-type-list")
	public String unread(Model model, FreightActionType freightActionType, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = freightActionTypeService.query(pageView, freightActionType);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-action-type-list";
	}

	@RequestMapping(value = "fre-action-type-input")
	public String input(Model model, String id) {
		FreightActionType item = null;
		if (id != null) {
			item = freightActionTypeService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("delegateTemplates", freightDelegateTemplateService.getAll());
		return "/content/fre/fre-action-type-input";
	}

	@RequestMapping(value = "fre-action-type-save", method = RequestMethod.POST)
	public String add(FreightActionType freightActionType, String freightDelegateTemplateId, 
			RedirectAttributes redirectAttributes) {
		if(!StringUtil.isNullOrEmpty(freightDelegateTemplateId)){
			freightActionType.setFreightDelegateTemplate(freightDelegateTemplateService.getById(freightDelegateTemplateId));
		}
		if(freightActionType.getId() == null){
			freightActionType.setPrime("F");//新建时,暂无字段,则无填报内容
			freightActionTypeService.add(freightActionType);
		}else{
			freightActionTypeService.modify(freightActionType);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-action-type-list.do";
	}
	
	@RequestMapping(value = "fre-action-type-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightActionTypeService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-action-type-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-action-type-check-typename")
	public boolean checkColumnName(@RequestParam(required=true, value="typeName") String typeName, String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM FRE_ACTION_TYPE WHERE TYPE_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, typeName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM FRE_ACTION_TYPE WHERE TYPE_NAME = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, typeName);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-action-type-all")
	public List<FreightActionType> getAll(){
		return freightActionTypeService.getAll();
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-action-type-get-maintaintypeid")
	public List<FreightActionType> getByMaintainType2(@RequestParam(value="freightMaintainTypeId", required=true)String freightMaintainTypeId){
		return FreightActionTypeService.cacheActionType.get(freightMaintainTypeId);
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-action-type-by-maintaintypeid")
	public Map<String, List<FreightActionType>> getByMaintainType(@RequestParam(value="freightMaintainTypeId", required=true)String freightMaintainTypeId){
		Map<String, List<FreightActionType>> map = new HashMap<String, List<FreightActionType>>();
		List<FreightActionType> hasAddData = FreightActionTypeService.cacheActionType.get(freightMaintainTypeId);
		map.put("hasAddData", hasAddData);
		
		List<FreightActionType> toAddData = FreightActionTypeService.cacheActionType.get(null);//获取到所有
		//toAddData.removeAll(hasAddData);//删除已经关联的
		map.put("toAddData", toAddData);
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-action-type-add-tomaintaintype")
	public String saveAsync(@RequestParam(value="actionTypeIds", required=true)String actionTypeIds,
			@RequestParam(value="freightMaintainTypeId", required=true)String freightMaintainTypeId){
		String[] ids = actionTypeIds.split(",");
		List<FreightMaintainAction> list = new ArrayList<FreightMaintainAction>();
		for(String freightActionTypeId : ids){
			list.add(new FreightMaintainAction(freightMaintainTypeId, freightActionTypeId));
		}
		freightMaintainActionService.batchAdd(list);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-action-type-remove-bymaintaintype")
	public String remove(@RequestParam(value="actionTypeIds", required=true)String actionTypeIds,
			@RequestParam(value="freightMaintainTypeId", required=true)String freightMaintainTypeId){
		String[] ids = actionTypeIds.split(",");
		List<FreightMaintainAction> list = new ArrayList<FreightMaintainAction>();
		for(String freightActionTypeId : ids){
			list.add(new FreightMaintainAction(freightMaintainTypeId, freightActionTypeId));
		}
		
		freightMaintainActionService.batchDeleteByMaintainAndAction(list);
		return "success";
	}
}
