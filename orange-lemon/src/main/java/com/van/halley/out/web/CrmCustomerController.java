package com.van.halley.out.web;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.CrmCustomer;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.CrmCustomerService;
import com.van.service.UserService;

@Controller
@RequestMapping(value = "/crm/")
public class CrmCustomerController {
	@Autowired
	private CrmCustomerService crmCustomerService;
	@Autowired
	private UserService userService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "crm-customer-list")
	public String list(Model model, CrmCustomer crmCustomer, String followerDisplayName, String typeByContact,
			@ModelAttribute PageView pageView, HttpServletRequest request) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		if(StringUtil.isNullOrEmpty(crmCustomer.getCustomerName()) && 
				StringUtil.isNullOrEmpty(crmCustomer.getCity()) && 
				StringUtil.isNullOrEmpty(crmCustomer.getProvince()) && 
				StringUtil.isNullOrEmpty(followerDisplayName) &&
				StringUtil.isNullOrEmpty(crmCustomer.getCustomerType()) &&
				StringUtil.isNullOrEmpty(crmCustomer.getStatus())){
			//如果未进行查询，则只显示分配给自己的客户
			User user = (User)request.getSession().getAttribute("userSession");
			String filterText = "(USER_ID='" + user.getId() + "') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
			
		}
		
		if(!StringUtil.isNullOrEmpty(crmCustomer.getCustomerName())){
			String filter = " CUSTOMER_NAME LIKE '%" + crmCustomer.getCustomerName() + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filter);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filter);
			}
			
			crmCustomer.setCustomerName(null);
		}
		
		if(!StringUtil.isNullOrEmpty(crmCustomer.getProvince())){
			String filter = " PROVINCE LIKE '%" + crmCustomer.getProvince() + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filter);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filter);
			}
			
			crmCustomer.setProvince(null);
		}
		
		if(!StringUtil.isNullOrEmpty(crmCustomer.getCity())){
			String filter = " CITY LIKE '%" + crmCustomer.getCity() + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filter);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filter);
			}
			
			crmCustomer.setCity(null);
		}
		
		if(!StringUtil.isNullOrEmpty(followerDisplayName)){
			String filter = " USER_ID IN(SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + followerDisplayName + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filter);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filter);
			}
		}
		/*User user = (User)request.getSession().getAttribute("userSession");
		String filterText = "(USER_ID='" + user.getId() + "' OR ORG_ENTITY_ID='" + user.getOrgEntity().getId() + "' " +
						" OR ORG_ENTITY_ID IN(SELECT ORG_ENTITY_ID FROM SYS_AUTH_ORG_ENTITY_IDENTITY WHERE USER_ID='" + user.getId() +"'))";
		if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
			pageView.setFilterText(filterText);
		}else{
			pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
		}*/
		
		if(!StringUtil.isNullOrEmpty(typeByContact)){
			String filter = null;
			if(typeByContact.equals("ContactedToday")){//今天已联系
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_CUSTOMER_FOLLOW WHERE CURRENT_FOLLOW_TIME=CURDATE())";
			}else if(typeByContact.equals("ContactedWeek")){//本周已联系
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_CUSTOMER_FOLLOW WHERE WEEK(CURRENT_FOLLOW_TIME)=WEEK(CURDATE()))";
			}else if(typeByContact.equals("ContactedMonth")){//本月已联系
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_CUSTOMER_FOLLOW WHERE MONTH(CURRENT_FOLLOW_TIME)=MONTH(CURDATE()))";
			}else if(typeByContact.equals("ToContactToday")){//今天需联系, 下次联系时间等于今天且最近联系时间不等于今天
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_CUSTOMER_FOLLOW WHERE NEXT_FOLLOW_TIME=CURDATE() AND CURRENT_FOLLOW_TIME!=CURDATE())";
			}else if(typeByContact.equals("ToContactWeek")){//本周需联系, 下次联系时间在本周内且最近联系时间小于本周第一天
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_CUSTOMER_FOLLOW WHERE WEEK(NEXT_FOLLOW_TIME)=WEEK(CURDATE()) AND WEEK(CURRENT_FOLLOW_TIME)!=WEEK(CURDATE()))";
			}else if(typeByContact.equals("ToContactMonth")){//本月需联系, 下次联系时间在本月且最近联系时间不等于下次联系时间
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_CUSTOMER_FOLLOW WHERE MONTH(NEXT_FOLLOW_TIME)=MONTH(CURDATE()) AND MONTH(CURRENT_FOLLOW_TIME)!=MONTH(CURDATE()))";
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
		pageView = crmCustomerService.query(pageView, crmCustomer);
		model.addAttribute("pageView", pageView);
		return "/content/crm/crm-customer-list";
	}
	
	@RequestMapping(value = "crm-customer-list-salesman")
	public String listSalesman(Model model, CrmCustomer crmCustomer, String typeByContact,
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
		
		
		if(!StringUtil.isNullOrEmpty(crmCustomer.getCustomerName())){
			String filter = " CUSTOMER_NAME LIKE '%" + crmCustomer.getCustomerName() + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filter);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filter);
			}
			
			crmCustomer.setCustomerName(null);
		}
		if(!StringUtil.isNullOrEmpty(typeByContact)){
			String filter = null;
			if(typeByContact.equals("ContactedToday")){//今天已联系
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_CUSTOMER_FOLLOW WHERE CURRENT_FOLLOW_TIME=CURDATE())";
			}else if(typeByContact.equals("ContactedWeek")){//本周已联系
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_CUSTOMER_FOLLOW WHERE WEEK(CURRENT_FOLLOW_TIME)=WEEK(CURDATE()))";
			}else if(typeByContact.equals("ContactedMonth")){//本月已联系
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_CUSTOMER_FOLLOW WHERE MONTH(CURRENT_FOLLOW_TIME)=MONTH(CURDATE()))";
			}else if(typeByContact.equals("ToContactToday")){//今天需联系, 下次联系时间等于今天且最近联系时间不等于今天
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_CUSTOMER_FOLLOW WHERE NEXT_FOLLOW_TIME=CURDATE() AND CURRENT_FOLLOW_TIME!=CURDATE())";
			}else if(typeByContact.equals("ToContactWeek")){//本周需联系, 下次联系时间在本周内且最近联系时间小于本周第一天
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_CUSTOMER_FOLLOW WHERE WEEK(NEXT_FOLLOW_TIME)=WEEK(CURDATE()) AND WEEK(CURRENT_FOLLOW_TIME)!=WEEK(CURDATE()))";
			}else if(typeByContact.equals("ToContactMonth")){//本月需联系, 下次联系时间在本月且最近联系时间不等于下次联系时间
				filter = "ID IN(SELECT CRM_FOLLOW_ID FROM CRM_CUSTOMER_FOLLOW WHERE MONTH(NEXT_FOLLOW_TIME)=MONTH(CURDATE()) AND MONTH(CURRENT_FOLLOW_TIME)!=MONTH(CURDATE()))";
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
		pageView = crmCustomerService.query(pageView, crmCustomer);
		model.addAttribute("pageView", pageView);
		return "/content/crm/crm-customer-list-salesman";
	}

	@RequestMapping(value = "crm-customer-input")
	public String input(Model model, String id) {
		CrmCustomer item = null;
		if (id != null) {
			item = crmCustomerService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("users", userService.getAll());
		return "/content/crm/crm-customer-input";
	}

	@RequestMapping(value = "crm-customer-save", method = RequestMethod.POST)
	public String add(CrmCustomer crmCustomer, String followerUserId, RedirectAttributes redirectAttributes) {
		User user = userService.getById(followerUserId);
		crmCustomer.setFollower(user);
		crmCustomer.setOrgEntity(user == null ? null : user.getOrgEntity());
		if(crmCustomer.getId() == null){
			crmCustomer.setStatus("未跟进");
			crmCustomerService.add(crmCustomer);
		}else{
			crmCustomerService.modify(crmCustomer);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:crm-customer-list.do";
	}
	
	@RequestMapping(value = "crm-customer-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			crmCustomerService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:crm-customer-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "crm-customer-check-customername")
	public boolean check(String id, String customerName){
		String sql = null;
		if(StringUtil.isNullOrEmpty(id)){
			sql = "SELECT COUNT(1) FROM CRM_CUSTOMER WHERE CUSTOMER_NAME=?";
			return jdbcTemplate.queryForObject(sql, Integer.class, customerName) == 0;
		}else{
			sql = "SELECT COUNT(1) FROM CRM_CUSTOMER WHERE CUSTOMER_NAME=? AND ID !=?";
			return jdbcTemplate.queryForObject(sql, Integer.class, customerName, id) == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "crm-customer-to-import-customer", method = RequestMethod.POST)
	public String add(MultipartHttpServletRequest request, String valueAttributeId)
			throws IOException, FileUploadException {
		Map<String, String> map = FileUtil.upload("file", request);
		List<List<String>> values = IExmportUtil.importMultiColumn(new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}, 1, map.get("fileData"));
		crmCustomerService.toImport(values);
		return "success";
	}
	
	/*@ResponseBody
	@RequestMapping(value = "crm-customer-to-export-customer")
	public void export(String valueAttributeId, HttpServletResponse response)throws IOException, FileUploadException {
		List<CrmCustomer> crmCustomers = crmCustomerService.getAll();
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"类型","名称","等级","信用","货量","运输方式","地址","国别","省份","城市","联系人","职位","电话","邮箱","跟进部门","跟进人"},
				crmCustomerService.toExport(crmCustomers)), "客户列表.xls", response);
	}*/
	
	@ResponseBody
	@RequestMapping(value = "crm-customer-to-export-batch-to-file.do")
	public String forecastExportDueToFile(@RequestParam(value="selectedItem", required=true)String[] selectedItem) throws IOException{
		String targetFile = StringUtil.getUUID() + ".xls";
		FileUtils.copyInputStreamToFile(IExmportUtil.exportMultiColumn(
				new String[]{"类型","名称","等级","信用","货量","运输方式","地址","国别","省份","城市","联系人","职位","电话","邮箱","跟进部门","跟进人"}, 
				crmCustomerService.toBatchExport(selectedItem)), new File(FileUtil.attachmentPath, targetFile));
		return targetFile;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "crm-customer-to-apply-follow")
	public String toApplyFollow(@RequestParam(value="crmCustomerId", required=true)String[] crmCustomerId){
		if(crmCustomerService.toApplyFollow(crmCustomerId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "crm-customer-to-agree-follow")
	public String toAgreeFollow(@RequestParam(value="crmCustomerId", required=true)String[] crmCustomerId){
		if(crmCustomerService.toAgreeFollow(crmCustomerId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "crm-customer-to-refuse-follow")
	public String toRefuseFollow(@RequestParam(value="crmCustomerId", required=true)String[] crmCustomerId){
		if(crmCustomerService.toRefuseFollow(crmCustomerId)){
			return "success";
		}else{
			return "error";
		}
	}

}
