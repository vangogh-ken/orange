package com.van.halley.fre.web;


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
import com.van.halley.db.persistence.entity.FreightActionTypeIdentity;
import com.van.service.FreightActionTypeIdentityService;
import com.van.service.UserService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightActionTypeIdentityController {
	@Autowired
	private FreightActionTypeIdentityService freightActionTypeIdentityService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-action-type-identity-list")
	public String unread(Model model, FreightActionTypeIdentity freightActionTypeIdentity, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = freightActionTypeIdentityService.query(pageView, freightActionTypeIdentity);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-action-type-identity-list";
	}

	@RequestMapping(value = "fre-action-type-identity-input")
	public String input(Model model, String id, @RequestParam(value="freightActionTypeId", required=true)String freightActionTypeId) {
		FreightActionTypeIdentity item = null;
		if (id != null) {
			item = freightActionTypeIdentityService.getById(id);
		}else{
			item = new FreightActionTypeIdentity();
			item.setFreightActionTypeId(freightActionTypeId);
		}
		model.addAttribute("item", item);
		model.addAttribute("users", userService.getAll());
		return "/content/fre/fre-action-type-identity-input";
	}

	@RequestMapping(value = "fre-action-type-identity-save", method = RequestMethod.POST)
	public String add(FreightActionTypeIdentity freightActionTypeIdentity, RedirectAttributes redirectAttributes) {
		if(freightActionTypeIdentity.getId() == null){
			freightActionTypeIdentityService.deleteByFreightActionTypeId(freightActionTypeIdentity.getFreightActionTypeId());
			freightActionTypeIdentityService.add(freightActionTypeIdentity);
		}else{
			freightActionTypeIdentityService.modify(freightActionTypeIdentity);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-action-type-identity-list.do";
	}
	
	@RequestMapping(value = "fre-action-type-identity-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightActionTypeIdentityService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-action-type-identity-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-action-type-identity-save-async", method = RequestMethod.POST)
	public String addAsync(FreightActionTypeIdentity freightActionTypeIdentity) {
		if(freightActionTypeIdentity.getId() == null){
			freightActionTypeIdentityService.deleteByFreightActionTypeId(freightActionTypeIdentity.getFreightActionTypeId());
			freightActionTypeIdentityService.add(freightActionTypeIdentity);
		}else{
			freightActionTypeIdentityService.modify(freightActionTypeIdentity);
		}
		return "success";
	}
	
	/**
	 * 获取该动作类型关联的收托人员
	 * @param freightActionTypeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "fre-action-type-identity-by-actiontypeid")
	public List<Map<String, Object>> get(@RequestParam(value="freightActionTypeId", required=true)String freightActionTypeId) {
		String sql = "SELECT U.DISPLAY_NAME AS DISPLAY_NAME, P.POSITION_NAME POSITION_NAME FROM SYS_AUTH_USER U, SYS_AUTH_POSITION P WHERE U.POSITION_ID=P.ID AND U.ID IN (SELECT ASSIGNEE_USER_ID FROM FRE_ACTION_TYPE_IDENTITY WHERE FRE_ACTION_TYPE_ID=?)";
		return jdbcTemplate.queryForList(sql, freightActionTypeId);
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-action-type-identity-to-add-identity")
	public Map<String, Object> toAddActionIdentity(@RequestParam(value="freightActionTypeId",required=true)String freightActionTypeId){
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "SELECT U.DISPLAY_NAME AS displayName, P.POSITION_NAME AS positionName, U.ID AS userId FROM SYS_AUTH_USER U, SYS_AUTH_POSITION P WHERE U.POSITION_ID=P.ID AND U.ID IN (SELECT ASSIGNEE_USER_ID FROM FRE_ACTION_TYPE_IDENTITY WHERE FRE_ACTION_TYPE_ID=?)";
		map.put("hasAddData", jdbcTemplate.queryForList(sql, freightActionTypeId));
		map.put("users", userService.getAll());
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-action-type-identity-done-add-identity")
	public String doneAddActionFiled(@RequestParam(value="freightActionTypeId",required=true)String freightActionTypeId,
			@RequestParam(value="userId",required=true)String userId){
		freightActionTypeIdentityService.deleteByFreightActionTypeId(freightActionTypeId);//先删除
		FreightActionTypeIdentity freightActionTypeIdentity = new FreightActionTypeIdentity(freightActionTypeId, userId);
		freightActionTypeIdentityService.add(freightActionTypeIdentity);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-action-type-identity-done-remove-identity")
	public String doneRemoveFiled(@RequestParam(value="freightActionTypeId",required=true)String freightActionTypeId){
		freightActionTypeIdentityService.deleteByFreightActionTypeId(freightActionTypeId);
		return "success";
	}

}
