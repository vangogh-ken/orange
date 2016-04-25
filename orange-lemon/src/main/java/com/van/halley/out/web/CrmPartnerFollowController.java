package com.van.halley.out.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.van.halley.db.persistence.entity.CrmPartnerFollow;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.service.CrmPartnerFollowService;
import com.van.service.CrmPartnerService;

@Controller
@RequestMapping(value = "/crm/")
public class CrmPartnerFollowController {
	@Autowired
	private CrmPartnerFollowService crmPartnerFollowService;
	@Autowired
	private CrmPartnerService crmPartnerService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "crm-partner-follow-list")
	public String unread(Model model, CrmPartnerFollow crmPartnerFollow,
			@ModelAttribute PageView pageView, HttpServletRequest request,
			HttpServletResponse response) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		User user = (User)request.getSession().getAttribute("userSession");
		String filterText = "(USER_ID='" + user.getId() + "' OR ORG_ENTITY_ID='" + user.getOrgEntity().getId() + "' " +
						" OR ORG_ENTITY_ID IN(SELECT ORG_ENTITY_ID FROM SYS_AUTH_ORG_ENTITY_IDENTITY WHERE USER_ID='" + user.getId() +"'))";
		if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
			pageView.setFilterText(filterText);
		}else{
			pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
		}
		pageView = crmPartnerFollowService.query(pageView, crmPartnerFollow);
		model.addAttribute("pageView", pageView);
		return "/content/crm/crm-partner-follow-list";
	}

	@RequestMapping(value = "crm-partner-follow-input")
	public String input(Model model, String id) {
		CrmPartnerFollow item = null;
		if (id != null) {
			item = crmPartnerFollowService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("crmPartners", crmPartnerService.getAll());
		return "/content/crm/crm-partner-follow-input";
	}

	@RequestMapping(value = "crm-partner-follow-save", method = RequestMethod.POST)
	public String add(CrmPartnerFollow crmPartnerFollow, 
			@RequestParam(value="crmPartnerId", required=true)String crmPartnerId, 
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		crmPartnerFollow.setCrmPartner(crmPartnerService.getById(crmPartnerId));
		if(crmPartnerFollow.getId() == null){
			User user = (User)request.getSession().getAttribute("userSession");
			crmPartnerFollow.setFollower(user);
			crmPartnerFollow.setOrgEntity(user.getOrgEntity());
			crmPartnerFollowService.add(crmPartnerFollow);
		}else{
			crmPartnerFollowService.modify(crmPartnerFollow);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:crm-partner-follow-list.do";
	}
	
	@RequestMapping(value = "crm-partner-follow-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			crmPartnerFollowService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:crm-partner-follow-list.do";
	}
	
	/**
	 * 当前客户的跟进人是否为当前用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "crm-partner-follow-check-follower")
	public boolean check(@RequestParam(value="crmPartnerId", required=true)String crmPartnerId, HttpServletRequest request){
		String sql = "SELECT COUNT(1) FROM CRM_PARTNER WHERE ID=? AND USER_ID=?";
		int count = jdbcTemplate.queryForObject(sql, Integer.class, crmPartnerId, ((User)request.getSession().getAttribute("userSession")).getId());
		return count == 1;
	}
	
	@ResponseBody
	@RequestMapping(value = "crm-partner-follow-last-follow")
	public CrmPartnerFollow getLast(@RequestParam(value="crmPartnerId", required=true)String crmPartnerId){
		return crmPartnerFollowService.getLastByCrmPartnerId(crmPartnerId);
	}
}
