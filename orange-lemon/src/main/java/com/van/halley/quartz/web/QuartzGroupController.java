package com.van.halley.quartz.web;


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
import com.van.halley.db.persistence.entity.QuartzGroup;
import com.van.service.QuartzGroupService;

@Controller
@RequestMapping(value = "/quartz/")
public class QuartzGroupController {
	@Autowired
	private QuartzGroupService quartzGroupService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "quartz-group-list")
	public String list(Model model, QuartzGroup quartzGroup, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = quartzGroupService.query(pageView, quartzGroup);

		model.addAttribute("pageView", pageView);
		return "/content/quartz/quartz-group-list";
	}

	@RequestMapping(value = "quartz-group-input")
	public String input(Model model, String id) {
		QuartzGroup item = null;
		if (id != null) {
			item = quartzGroupService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/quartz/quartz-group-input";
	}

	@RequestMapping(value = "quartz-group-save", method = RequestMethod.POST)
	public String add(QuartzGroup quartzGroup, RedirectAttributes redirectAttributes) {
		if(quartzGroup.getId() == null){
			quartzGroupService.add(quartzGroup);
		}else{
			quartzGroupService.modify(quartzGroup);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:quartz-group-list.do";
	}
	
	@RequestMapping(value = "quartz-group-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			quartzGroupService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:quartz-group-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "quartz-group-check-group-name")
	public boolean checkName(@RequestParam(required=true, value="groupName") String groupName,  String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM QUARTZ_GROUP WHERE GROUP_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, groupName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM QUARTZ_GROUP WHERE GROUP_NAME = ? ";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, groupName);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "quartz-group-check-group-key")
	public boolean checkKey(@RequestParam(required=true, value="groupKey") String groupKey,  String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM QUARTZ_GROUP WHERE GROUP_KEY = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, groupKey, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM QUARTZ_GROUP WHERE GROUP_KEY = ? ";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, groupKey);
			return count == 0;
		}
	}

}
