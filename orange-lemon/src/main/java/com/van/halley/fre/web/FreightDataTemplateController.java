package com.van.halley.fre.web;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.van.halley.db.persistence.entity.FreightDataTemplate;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.service.FreightActionService;
import com.van.service.FreightActionTypeService;
import com.van.service.FreightDataTemplateService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightDataTemplateController {
	@Autowired
	private FreightDataTemplateService freightDataTemplateService;
	@Autowired
	private FreightActionService freightActionService;
	@Autowired
	private FreightActionTypeService freightActionTypeService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-data-template-list")
	public String unread(Model model, FreightDataTemplate freightDataTemplate, String freightActionTypeId,
			HttpServletRequest request,
			@ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(!StringUtil.isNullOrEmpty(freightActionTypeId)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_ACTION_ID IN(SELECT ID FROM FRE_ACTION WHERE FRE_ACTION_TYPE_ID='"+ freightActionTypeId +"')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_ACTION_ID IN(SELECT ID FROM FRE_ACTION WHERE FRE_ACTION_TYPE_ID='"+ freightActionTypeId +"')");
			}
		}
		
		freightDataTemplate.setOwner((User)request.getSession().getAttribute("userSession"));
		pageView = freightDataTemplateService.query(pageView, freightDataTemplate);
		
		model.addAttribute("freightActionTypes", freightActionTypeService.getAll());
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-data-template-list";
	}

	@RequestMapping(value = "fre-data-template-input")
	public String input(Model model, String id, String freightOrderId) {
		FreightDataTemplate item = null;
		if (id != null) {
			item = freightDataTemplateService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-data-template-input";
	}

	@RequestMapping(value = "fre-data-template-save", method = RequestMethod.POST)
	public String add(FreightDataTemplate freightDataTemplate, String freightOrderId,
			RedirectAttributes redirectAttributes) {
		if(freightDataTemplate.getId() == null){
			freightDataTemplateService.add(freightDataTemplate);
		}else{
			freightDataTemplateService.modify(freightDataTemplate);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-data-template-list.do";
	}
	
	@RequestMapping(value = "fre-data-template-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightDataTemplateService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-data-template-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-data-template-to-add-template")
	public Map<String, Object> toAddTempate(@RequestParam(value="freightActionId", required=true)String freightActionId){
		return freightDataTemplateService.toAddTemplate(freightActionId);
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-data-template-done-add-template")
	public String doneAddTemplate(@RequestParam(value="templateName", required=true)String templateName, 
			@RequestParam(value="freightActionValueId", required=true)String[] freightActionValueId,
			HttpServletRequest request){
		freightDataTemplateService.doneAddTemplate(templateName,  
				(User)request.getSession().getAttribute("userSession"), freightActionValueId);
		
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-data-template-check-templatename")
	public boolean checkColumnName(@RequestParam(required=true, value="templateName") String templateName,
			HttpServletRequest request) {
			String sql = "SELECT COUNT(*) AS count FROM FRE_DATA_TEMPLATE WHERE TEMPLATE_NAME = ? AND USER_ID=?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, templateName, 
					((User)request.getSession().getAttribute("userSession")).getId());
			return count == 0;
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-data-template-to-copy-template")
	public Map<String, Object> toCopyTempate(@RequestParam(required=true, value="freightActionId")String freightActionId, 
			HttpServletRequest request){
		return freightDataTemplateService.toCopyTempate(freightActionId, ((User)request.getSession().getAttribute("userSession")).getId());
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-data-template-done-copy-template")
	public String doneCopyTempate(@RequestParam(required=true, value="freightActionId")String freightActionId, 
			@RequestParam(required=true, value="freightDataTemplateId")String freightDataTemplateId, 
			@RequestParam(required=true, value="sheathe")String sheathe){
		freightActionService.initPrime(freightActionId);//如果是复制新增的动作，没有进行初始化，则先初始化一次
		freightDataTemplateService.doneCopyTemplate(freightActionId, freightDataTemplateId, sheathe);
		return "success";
	}
}
