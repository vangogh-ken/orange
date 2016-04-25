package com.van.halley.auth.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.OrgEntity;
import com.van.halley.db.persistence.entity.Position;
import com.van.halley.util.StringUtil;
import com.van.service.OrgEntityService;
import com.van.service.OrgTypeService;
import com.van.service.PositionService;
import com.van.service.UserService;

@Controller
@RequestMapping(value = "/org/")
public class OrgEntityController {
	@Autowired
	private OrgEntityService orgEntityService;
	@Autowired
	private OrgTypeService orgTypeService;
	@Autowired
	private UserService userService;
	@Autowired
	private PositionService positionService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "org-entity-list")
	public String queryModel(Model model, OrgEntity orgEntity, String parentOrgId, 
			String orgTypeId, @ModelAttribute PageView pageView) {
		if(parentOrgId != null){
			orgEntity.setParentOrg(orgEntityService.getById(parentOrgId));
		}
		
		if(orgTypeId != null){
			orgEntity.setOrgType(orgTypeService.getById(orgTypeId));
		}
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		if(!StringUtil.isNullOrEmpty(orgEntity.getOrgEntityName())){
			String filterText = " ORG_ENTITY_NAME LIKE '%" + orgEntity.getOrgEntityName() + "%' ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
			
			orgEntity.setOrgEntityName(null);
		}
		orgEntityService.query(pageView, orgEntity);
		model.addAttribute("orgEntities", orgEntityService.getAll());
		model.addAttribute("orgTypes", orgTypeService.getAll());
		model.addAttribute("pageView", pageView);
		return "/content/org/org-entity-list";
	}

	@RequestMapping(value = "org-entity-input")
	public String input(Model model, String id) {
		OrgEntity item = null;
		if(id != null){
			item = orgEntityService.getById(id);
		}
		model.addAttribute("types", orgTypeService.getAll());
		model.addAttribute("orgEntities", orgEntityService.getAll());
		model.addAttribute("users", userService.getAll());
		model.addAttribute("item", item);
		return "/content/org/org-entity-input";
	}

	@RequestMapping(value = "org-entity-save")
	public String addModel(OrgEntity orgEntity, String typeId, String parentOrgId, 
			String dutyUserId, RedirectAttributes redirectAttributes) {
		orgEntity.setOrgType(orgTypeService.getById(typeId));
		orgEntity.setParentOrg(orgEntityService.getById(parentOrgId));
		if(orgEntity.getId() != null){
			orgEntityService.modify(orgEntity);
		}else{
			orgEntityService.add(orgEntity);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:org-entity-list.do";
	}

	@RequestMapping(value = "org-entity-remove")
	public String delete(Model model, String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			orgEntityService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:org-entity-list.do";
	}
	
	/**
	 * 组织机构地图查看
	 */
	@RequestMapping(value = "org-map-view")
	public String view(Model model) {
		model.addAttribute("orgEntities", orgEntityService.getAll());
		model.addAttribute("positions", positionService.getAll());
		return "/content/org/org-map-view";
	}
	
	//////////////////////////////负责人
	@ResponseBody
	@RequestMapping(value = "org-entity-to-add-gaffer")
	public Map<String, Object> toAddGaffer(@RequestParam(value="orgEntityId",required=true)String orgEntityId){
		return orgEntityService.toAddGaffer(orgEntityId);
	}
	
	@ResponseBody
	@RequestMapping(value = "org-entity-done-add-gaffer")
	public String doneAddGaffer(@RequestParam(value="orgEntityId",required=true)String orgEntityId, @RequestParam(value="userId",required=true)String userId){
		orgEntityService.doneAddGaffer(orgEntityId, userId);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "org-entity-done-remove-gaffer")
	public String donRemoveGaffer(@RequestParam(value="orgEntityId",required=true)String orgEntityId){
		orgEntityService.doneRemoveGaffer(orgEntityId);
		return "success";
	}
	
	///////////////////////////////////职位
	@ResponseBody
	@RequestMapping(value = "org-entity-to-add-position")
	public Map<String, Object> toAddPosition(@RequestParam(value="orgEntityId",required=true)String orgEntityId){
		return orgEntityService.toAddPosition(orgEntityId);
	}
	
	@ResponseBody
	@RequestMapping(value = "org-entity-to-revise-position")
	public Map<String, Object> toRevisePosition(@RequestParam(value="positionId",required=true)String positionId){
		return orgEntityService.toRevisePosition(positionId);
	}
	
	@ResponseBody
	@RequestMapping(value = "org-entity-done-add-position")
	public String doneAddPosition(@RequestParam(value="orgEntityId",required=true)String orgEntityId, 
			@RequestBody Position position, String positionId){
		if(!StringUtil.isNullOrEmpty(positionId)){
			position.setId(positionId);
		}
		orgEntityService.doneAddPosition(orgEntityId, position);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "org-entity-done-remove-position")
	public String donRemovePosition(@RequestParam(value="positionId",required=true)String[] positionId){
		orgEntityService.doneRemovePosition(positionId);
		return "success";
	}

}
