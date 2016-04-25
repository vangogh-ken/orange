package com.van.halley.fre.web;


import java.util.List;

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
import com.van.halley.db.persistence.entity.FreightMaintainType;
import com.van.halley.util.StringUtil;
import com.van.service.FreightMaintainTypeService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightMaintainTypeController {
	@Autowired
	private FreightMaintainTypeService freightMaintainTypeService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-maintain-type-list")
	public String unread(Model model, FreightMaintainType freightMaintainType, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = freightMaintainTypeService.query(pageView, freightMaintainType);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-maintain-type-list";
	}

	@RequestMapping(value = "fre-maintain-type-input")
	public String input(Model model, String id) {
		FreightMaintainType item = null;
		if (id != null) {
			item = freightMaintainTypeService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-maintain-type-input";
	}

	@RequestMapping(value = "fre-maintain-type-save", method = RequestMethod.POST)
	public String add(FreightMaintainType freightMaintainType, RedirectAttributes redirectAttributes) {
		if(freightMaintainType.getId() == null){
			freightMaintainTypeService.add(freightMaintainType);
		}else{
			freightMaintainTypeService.modify(freightMaintainType);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-maintain-type-list.do";
	}
	
	@RequestMapping(value = "fre-maintain-type-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightMaintainTypeService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-maintain-type-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-maintain-type-check-typename")
	public boolean checkColumnName(@RequestParam(required=true, value="typeName") String typeName, String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM FRE_MAINTAIN_TYPE WHERE TYPE_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, typeName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM FRE_MAINTAIN_TYPE WHERE TYPE_NAME = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, typeName);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-maintain-type-all")
	public List<FreightMaintainType> getAll(String priority){
		if(!StringUtil.isNullOrEmpty(priority)){
			FreightMaintainType filter = new FreightMaintainType();
			filter.setPriority(Integer.parseInt(priority));
			return freightMaintainTypeService.queryForList(filter);
		}else{
			return freightMaintainTypeService.getAll();
		}
	}

}
