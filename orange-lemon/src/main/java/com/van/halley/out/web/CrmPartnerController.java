package com.van.halley.out.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.CrmPartner;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.CrmPartnerService;
import com.van.service.UserService;

@Controller
@RequestMapping(value = "/crm/")
public class CrmPartnerController {
	@Autowired
	private CrmPartnerService crmPartnerService;
	@Autowired
	private UserService userService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "crm-partner-list")
	public String list(Model model, CrmPartner crmPartner, String typeByContact,
			@ModelAttribute PageView pageView, HttpServletRequest request) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		/*User user = (User)request.getSession().getAttribute("userSession");
		String filterText = "(USER_ID='" + user.getId() + "' OR ORG_ENTITY_ID='" + user.getOrgEntity().getId() + "' " +
						" OR ORG_ENTITY_ID IN(SELECT ORG_ENTITY_ID FROM SYS_AUTH_ORG_ENTITY_IDENTITY WHERE USER_ID='" + user.getId() +"'))";
		if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
			pageView.setFilterText(filterText);
		}else{
			pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
		}*/
		if(!StringUtil.isNullOrEmpty(crmPartner.getPartnerName())){
			String filter = " PARTNER_NAME LIKE '%" + crmPartner.getPartnerName() + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filter);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filter);
			}
			
			crmPartner.setPartnerName(null);
		}
		if(!StringUtil.isNullOrEmpty(typeByContact)){
			String filter = null;
			if(typeByContact.equals("ContactedToday")){//今天已联系
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_PARTNER_FOLLOW WHERE CURRENT_FOLLOW_TIME=CURDATE())";
			}else if(typeByContact.equals("ContactedWeek")){//本周已联系
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_PARTNER_FOLLOW WHERE WEEK(CURRENT_FOLLOW_TIME)=WEEK(CURDATE()))";
			}else if(typeByContact.equals("ContactedMonth")){//本月已联系
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_PARTNER_FOLLOW WHERE MONTH(CURRENT_FOLLOW_TIME)=MONTH(CURDATE()))";
			}else if(typeByContact.equals("ToContactToday")){//今天需联系, 下次联系时间等于今天且最近联系时间不等于今天
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_PARTNER_FOLLOW WHERE NEXT_FOLLOW_TIME=CURDATE() AND CURRENT_FOLLOW_TIME!=CURDATE())";
			}else if(typeByContact.equals("ToContactWeek")){//本周需联系, 下次联系时间在本周内且最近联系时间小于本周第一天
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_PARTNER_FOLLOW WHERE WEEK(NEXT_FOLLOW_TIME)=WEEK(CURDATE()) AND WEEK(CURRENT_FOLLOW_TIME)!=WEEK(CURDATE()))";
			}else if(typeByContact.equals("ToContactMonth")){//本月需联系, 下次联系时间在本月且最近联系时间不等于下次联系时间
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_PARTNER_FOLLOW WHERE MONTH(NEXT_FOLLOW_TIME)=MONTH(CURDATE()) AND MONTH(CURRENT_FOLLOW_TIME)!=MONTH(CURDATE()))";
			}else if(typeByContact.equals("CreateInToday")){//今天新录入
				filter = (" DATE(CREATE_TIME) = DATE(SYSDATE()) ");
			}else if(typeByContact.equals("CreateInWeek")){//本周新录入
				filter = (" WHERE(CREATE_TIME) = WHERE(SYSDATE()) ");
			}else if(typeByContact.equals("CreateInMonth")){//本月新录入
				filter = (" MONTH(CREATE_TIME) = MONTH(SYSDATE()) ");
			}
			
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filter);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filter);
			}
		}
		
		pageView = crmPartnerService.query(pageView, crmPartner);
		model.addAttribute("pageView", pageView);
		return "/content/crm/crm-partner-list";
	}
	
	@RequestMapping(value = "crm-partner-list-salesman")
	public String listSalesman(Model model, CrmPartner crmPartner, String typeByContact,
			@ModelAttribute PageView pageView, HttpServletRequest request) {
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
		
		if(!StringUtil.isNullOrEmpty(crmPartner.getPartnerName())){
			String filter = " PARTNER_NAME LIKE '%" + crmPartner.getPartnerName() + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filter);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filter);
			}
			
			crmPartner.setPartnerName(null);
		}
		
		if(!StringUtil.isNullOrEmpty(typeByContact)){
			String filter = null;
			if(typeByContact.equals("ContactedToday")){//今天已联系
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_PARTNER_FOLLOW WHERE CURRENT_FOLLOW_TIME=CURDATE())";
			}else if(typeByContact.equals("ContactedWeek")){//本周已联系
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_PARTNER_FOLLOW WHERE WEEK(CURRENT_FOLLOW_TIME)=WEEK(CURDATE()))";
			}else if(typeByContact.equals("ContactedMonth")){//本月已联系
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_PARTNER_FOLLOW WHERE MONTH(CURRENT_FOLLOW_TIME)=MONTH(CURDATE()))";
			}else if(typeByContact.equals("ToContactToday")){//今天需联系, 下次联系时间等于今天且最近联系时间不等于今天
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_PARTNER_FOLLOW WHERE NEXT_FOLLOW_TIME=CURDATE() AND CURRENT_FOLLOW_TIME!=CURDATE())";
			}else if(typeByContact.equals("ToContactWeek")){//本周需联系, 下次联系时间在本周内且最近联系时间小于本周第一天
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_PARTNER_FOLLOW WHERE WEEK(NEXT_FOLLOW_TIME)=WEEK(CURDATE()) AND WEEK(CURRENT_FOLLOW_TIME)!=WEEK(CURDATE()))";
			}else if(typeByContact.equals("ToContactMonth")){//本月需联系, 下次联系时间在本月且最近联系时间不等于下次联系时间
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_PARTNER_FOLLOW WHERE MONTH(NEXT_FOLLOW_TIME)=MONTH(CURDATE()) AND MONTH(CURRENT_FOLLOW_TIME)!=MONTH(CURDATE()))";
			}else if(typeByContact.equals("CreateInToday")){//今天新录入
				filter = (" DATE(CREATE_TIME) = DATE(SYSDATE()) ");
			}else if(typeByContact.equals("CreateInWeek")){//本周新录入
				filter = (" WHERE(CREATE_TIME) = WHERE(SYSDATE()) ");
			}else if(typeByContact.equals("CreateInMonth")){//本月新录入
				filter = (" MONTH(CREATE_TIME) = MONTH(SYSDATE()) ");
			}
			
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filter);
			}
		}
		
		pageView = crmPartnerService.query(pageView, crmPartner);
		model.addAttribute("pageView", pageView);
		return "/content/crm/crm-partner-list-salesman";
	}

	@RequestMapping(value = "crm-partner-input")
	public String input(Model model, String id) {
		CrmPartner item = null;
		if (id != null) {
			item = crmPartnerService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("users", userService.getAll());
		return "/content/crm/crm-partner-input";
	}

	@RequestMapping(value = "crm-partner-save", method = RequestMethod.POST)
	public String add(CrmPartner crmPartner, String followerUserId, RedirectAttributes redirectAttributes) {
		User user = userService.getById(followerUserId);
		crmPartner.setFollower(user);
		crmPartner.setOrgEntity(user == null ? null : user.getOrgEntity());
		if(crmPartner.getId() == null){
			crmPartnerService.add(crmPartner);
		}else{
			crmPartnerService.modify(crmPartner);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:crm-partner-list.do";
	}
	
	@RequestMapping(value = "crm-partner-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			crmPartnerService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:crm-partner-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "crm-partner-check-partnername")
	public boolean check(String id, String partnerName){
		String sql = null;
		if(StringUtil.isNullOrEmpty(id)){
			sql = "SELECT COUNT(1) FROM CRM_PARTNER WHERE CUSTOMER_NAME=?";
			return jdbcTemplate.queryForObject(sql, Integer.class, partnerName) == 0;
		}else{
			sql = "SELECT COUNT(1) FROM CRM_PARTNER WHERE CUSTOMER_NAME=? AND ID !=?";
			return jdbcTemplate.queryForObject(sql, Integer.class, partnerName, id) == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "crm-partner-to-import-partner", method = RequestMethod.POST)
	public String add(MultipartHttpServletRequest request, String valueAttributeId)throws IOException, FileUploadException {
		Map<String, String> map = FileUtil.upload("file", request);
		List<List<String>> values = IExmportUtil.importMultiColumn(new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14}, 1, map.get("fileData"));
		crmPartnerService.toImport(values);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "crm-partner-to-export-partner")
	public void export(String valueAttributeId, HttpServletResponse response)throws IOException, FileUploadException {
		List<CrmPartner> crmPartners = crmPartnerService.getAll();
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"类型","名称","等级","经营","优势","地址","国别","省份","城市","联系人","职位","电话","邮箱","跟进部门","跟进人"}, 
				crmPartnerService.toExport(crmPartners)), "渠道列表.xls", response);
	}


}
