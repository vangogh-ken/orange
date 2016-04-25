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
import com.van.halley.db.persistence.entity.Role;
import com.van.halley.util.StringUtil;
import com.van.service.RoleService;

@Controller
@RequestMapping(value = "/role/")
public class RoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "role-list")
	public String query(Model model, Role role,
			@ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}

		roleService.query(pageView, role);
		model.addAttribute("pageView", pageView);
		return "/content/role/role-list";
	}

	@ResponseBody
	@RequestMapping(value = "role-all")
	public Map<String ,Object> getAll(String userId) {
		Map<String ,Object> map = new HashMap<String, Object>();
		if(userId != null){
			List<Role> roleUser = roleService.getRoleByUserId(userId);
			map.put("roleUser", roleUser);
		}
		
		List<Role> roleAll = roleService.getAll();
		map.put("roleAll", roleAll);
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "role-get-all")
	public List<Role> get(){
		return roleService.getAll();
	}
	
	@RequestMapping(value = "role-input")
	public String input(Model model, String id) {
		Role item = null;
		if (id != null) {
			item = roleService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/role/role-input";
	}

	/** 保存 */
	@RequestMapping(value = "role-save")
	public String save(Model model, Role role,
			RedirectAttributes redirectAttributes) {

		if (role.getId() == null) {
			role.setId(StringUtil.getUUID());
			roleService.add(role);
			messageHelper.addFlashMessage(redirectAttributes, "添加成功");
		} else {
			roleService.modify(role);
			messageHelper.addFlashMessage(redirectAttributes, "更新成功");
		}

		return "redirect:role-list.do";
	}

	@RequestMapping(value = "role-delete")
	public String deleteById(Model model, String id,
			RedirectAttributes redirectAttributes) {
		roleService.delete(id);
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:role-list.do";
	}

	@RequestMapping(value = "role-remove")
	public String deleteAll(String[] selectedItem, Model model,
			RedirectAttributes redirectAttributes) {
		for (String roleId : selectedItem) {
			roleService.delete(roleId);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:role-list.do";
	}

	@ResponseBody
	@RequestMapping(value = "role-check-name")
	public boolean checkName(@RequestParam(required=true, value="name") String name,
			String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM SYS_AUTH_ROLE WHERE NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, name, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM SYS_AUTH_ROLE WHERE NAME = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, name);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "role-check-rolekey")
	public boolean checkRoleKey(@RequestParam(required=true, value="roleKey") String roleKey,
			String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM SYS_AUTH_ROLE WHERE ROLE_KEY = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, roleKey, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM SYS_AUTH_ROLE WHERE ROLE_KEY = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, roleKey);
			return count == 0;
		}
		
	}

	/**
	 * 分配流程权限
	 */
	@ResponseBody
	@RequestMapping(value = "role-to-allocation-resource")
	public Map<String, Object> toAllocationResource(@RequestParam(value="roleId", required=true)String roleId){
		return roleService.toAllocationResource(roleId);
	}
	/**
	 * 分配流程权限
	 */
	@ResponseBody
	@RequestMapping(value = "role-done-allocation-resource")
	public String doneAllocationResource(@RequestParam(value="roleId", required=true)String roleId,
			@RequestParam(value="resourceId", required=true)String[] resourceId){
		if(roleService.doneAllocationResource(roleId, resourceId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 分配流程权限
	 */
	@ResponseBody
	@RequestMapping(value = "role-to-allocation-bpm")
	public Map<String, Object> toAllocationBpm(@RequestParam(value="roleId", required=true)String roleId){
		return roleService.toAllocationBpm(roleId);
	}
	/**
	 * 分配流程权限
	 */
	@ResponseBody
	@RequestMapping(value = "role-done-allocation-bpm")
	public String doneAllocationBpm(@RequestParam(value="roleId", required=true)String roleId,
			@RequestParam(value="bpmId", required=true)String[] bpmId){
		if(roleService.doneAllocationBpm(roleId, bpmId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 分配报表权限
	 */
	@ResponseBody
	@RequestMapping(value = "role-to-allocation-report")
	public Map<String, Object> toAllocationReport(@RequestParam(value="roleId", required=true)String roleId){
		return roleService.toAllocationReport(roleId);
	}
	/**
	 * 分配报表权限
	 */
	@ResponseBody
	@RequestMapping(value = "role-done-allocation-report")
	public String doneAllocationReport(@RequestParam(value="roleId", required=true)String roleId,
			@RequestParam(value="reportTemplateId", required=true)String[] reportTemplateId){
		if(roleService.doneAllocationReport(roleId, reportTemplateId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 分配任务权限
	 */
	@ResponseBody
	@RequestMapping(value = "role-to-allocation-quartz")
	public Map<String, Object> toAllocationQuartz(@RequestParam(value="roleId", required=true)String roleId){
		return roleService.toAllocationQuartz(roleId);
	}
	/**
	 * 分配任务权限
	 */
	@ResponseBody
	@RequestMapping(value = "role-done-allocation-quartz")
	public String doneAllocationQuartz(@RequestParam(value="roleId", required=true)String roleId,
			@RequestParam(value="quartzTaskId", required=true)String[] quartzTaskId){
		if(roleService.doneAllocationQuartz(roleId, quartzTaskId)){
			return "success";
		}else{
			return "error";
		}
	}
}
