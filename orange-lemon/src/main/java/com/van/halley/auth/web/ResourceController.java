package com.van.halley.auth.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.van.halley.db.persistence.entity.Resource;
import com.van.service.ResourceService;

@Controller
@RequestMapping(value = "/resource/")
public class ResourceController {
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "resource-list")
	public String list(Model model, Resource resource, String parentId, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		if(parentId != null){
			resource.setParentResource(resourceService.getById(parentId));
		}
		//System.out.println(resource.getResourceType());
		pageView = resourceService.query(pageView, resource);
		model.addAttribute("pageView", pageView);
		model.addAttribute("resourceAll", resourceService.getAll());
		
		return "/content/resource/resource-list";
	}

	/**
	 * 跳到新增页面
	 */
	@RequestMapping(value = "resource-input")
	public String addUI(Model model, String id) {
		Resource item = null;
		if (id != null) {
			item = resourceService.getById(id);
		}
		List<Resource> resources = resourceService.getAll();
		model.addAttribute("res", resources);
		model.addAttribute("item", item);
		return "/content/resource/resource-input";
	}

	/**
	 * 保存新增
	 */
	@RequestMapping(value = "resource-save")
	public String add(Model model, Resource resource, String parentId, RedirectAttributes redirectAttributes) {
		resource.setParentResource(resourceService.getById(parentId));
		if (resource.getId() == null) {
			resourceService.add(resource);
			messageHelper.addFlashMessage(redirectAttributes, "添加成功");
		} else {
			resourceService.modify(resource);
			messageHelper.addFlashMessage(redirectAttributes, "更新成功");
		}

		return "redirect:resource-list.do?parentId=" + parentId;
	}

	/**
	 * 根据id删除
	 */
	@RequestMapping(value = "resource-delete")
	public String deleteById(Model model, String id,
			RedirectAttributes redirectAttributes) {
		resourceService.delete(id);
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:resource-list.do";
	}

	@RequestMapping(value = "resource-remove")
	public String deleteAll(String[] selectedItem, Model model,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			resourceService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:resource-list.do";
	}
	
	@RequestMapping(value = "resource-to-enable")
	public String enable(Model model, String id, RedirectAttributes redirectAttributes) {
		Resource resource = resourceService.getById(id);
		resource.setEnable("T");
		resourceService.modify(resource);
		messageHelper.addFlashMessage(redirectAttributes, "激活成功");
		return "redirect:resource-list.do";
	}
	
	@RequestMapping(value = "resource-to-disable")
	public String disbable(Model model, String id, RedirectAttributes redirectAttributes) {
		Resource resource = resourceService.getById(id);
		resource.setEnable("F");
		resourceService.modify(resource);
		messageHelper.addFlashMessage(redirectAttributes, "禁用成功");
		return "redirect:resource-list.do";
	}

	@ResponseBody
	@RequestMapping(value = "resource-all")
	public Map<String ,Object> getAll(String roleId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(roleId != null){
			List<Resource> resourcesRole = resourceService.getResourceByRoleId(roleId);
			map.put("resourcesRole", resourcesRole);
		}
		
		List<Resource> resourcesAll = resourceService.getAll();
		map.put("resourcesAll", resourcesAll);
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "resource-check-resourcekey")
	public boolean checkResourceKey(@RequestParam(required=true, value="resourceKey") String resourceKey,
			String id){
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM SYS_AUTH_RESOURCE WHERE RESOURCE_KEY = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, resourceKey, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM SYS_AUTH_RESOURCE WHERE RESOURCE_KEY = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, resourceKey);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "resource-check-resourceurl")
	public boolean checkResourceUrl(@RequestParam(required=true, value="resourceUrl") String resourceUrl,
			String id){
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM SYS_AUTH_RESOURCE WHERE RESOURCE_URL = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, resourceUrl, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM SYS_AUTH_RESOURCE WHERE RESOURCE_URL = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, resourceUrl);
			return count == 0;
		}
	}
}
