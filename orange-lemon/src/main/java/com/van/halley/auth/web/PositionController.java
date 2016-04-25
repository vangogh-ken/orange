package com.van.halley.auth.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.Position;
import com.van.halley.db.persistence.entity.Role;
import com.van.halley.util.StringUtil;
import com.van.service.OrgEntityService;
import com.van.service.PositionService;
import com.van.service.RoleService;

@Controller
@RequestMapping(value = "/org/")
public class PositionController {
	@Autowired
	private PositionService positionService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private OrgEntityService orgEntityService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "position-list")
	public String queryModel(Model model, Position position, String orgEntityId, @ModelAttribute PageView<Position> pageView) {
		if (pageView == null) {
			pageView = new PageView<Position>(1);
		}
		if(!StringUtil.isNullOrEmpty(orgEntityId)){
			position.setOrgEntity(orgEntityService.getById(orgEntityId));
		}
		
		if(!StringUtil.isNullOrEmpty(position.getPositionName())){
			String filterText = " POSITION_NAME LIKE '%" + position.getPositionName() + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
			
			position.setPositionName(null);
		}
		
		if(StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			pageView.setOrder("CREATE_TIME");
			pageView.setOrder("DESC");
		}
		positionService.query(pageView, position);
		model.addAttribute("pageView", pageView);
		model.addAttribute("orgEntities", orgEntityService.getAll());
		return "/content/org/position-list";
	}

	@RequestMapping(value = "position-input")
	public String input(Model model, String id) {
		Position item = null;
		if(id != null){
			item = positionService.getById(id);
		}
		
		model.addAttribute("item", item);
		model.addAttribute("orgEntities", orgEntityService.getAll());
		return "/content/org/position-input";
	}

	@RequestMapping(value = "position-save")
	public String addModel(Position position, String orgEntityId) {
		position.setOrgEntity(orgEntityService.getById(orgEntityId));
		if(position.getId() != null){
			positionService.modify(position);
		}else{
			positionService.add(position);
		}
		
		return "redirect: position-list.do";
	}

	@RequestMapping(value = "position-remove")
	public String delete(Model model, String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			positionService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:position-list.do";
	}
	
	//为职位分配角色
	@ResponseBody
	@RequestMapping(value = "position-to-position-role-allocation")
	public Map<String ,Object> toPositionRoleAllocation(String positionId) {
		Map<String ,Object> map = new HashMap<String, Object>();
		if(positionId != null){
			List<Role> rolePosition = roleService.getRoleByPositionId(positionId);
			map.put("rolePosition", rolePosition);
		}
		
		List<Role> roleAll = roleService.getAll();
		map.put("roleAll", roleAll);
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "position-get-all")
	public List<Position> get(String positionId) {
		return positionService.getAll();
	}
		
	//职位角色关联 
	@RequestMapping(value = "position-done-position-role-allocation")
	public String allocationSave(Model model, String positionId, String[] roleIds,
			RedirectAttributes redirectAttributes) {
		roleService.positionRoleAllocation(positionId, roleIds);
		messageHelper.addFlashMessage(redirectAttributes, "分配成功");
		return "redirect:position-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "position-check-positionname")
	public boolean checkName(@RequestParam(required=true, value="positionName") String positionName, 
			@RequestParam(required=true, value="orgEntityId") String orgEntityId,
			String id) {
		if(!StringUtil.isNullOrEmpty(id)){
			String sql = "SELECT COUNT(*) AS count FROM SYS_AUTH_POSITION WHERE POSITION_NAME = ? AND ID <> ? AND ORG_ENTITY_ID=?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, positionName, id, orgEntityId);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM SYS_AUTH_POSITION WHERE POSITION_NAME = ? AND ORG_ENTITY_ID=?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, positionName, orgEntityId);
			return count == 0;
		}
	}

}
