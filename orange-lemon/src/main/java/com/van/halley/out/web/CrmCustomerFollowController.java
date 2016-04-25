package com.van.halley.out.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.CrmCustomer;
import com.van.halley.db.persistence.entity.CrmCustomerFollow;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.CrmCustomerFollowService;
import com.van.service.CrmCustomerService;
import com.van.service.UserService;

@Controller
@RequestMapping(value = "/crm/")
public class CrmCustomerFollowController {
	@Autowired
	private CrmCustomerFollowService crmCustomerFollowService;
	@Autowired
	private CrmCustomerService crmCustomerService;
	@Autowired
	private UserService userService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "crm-customer-follow-list")
	public String list(Model model, CrmCustomerFollow crmCustomerFollow, 
			String crmCustomerName, String followerDisplayName,
			String currentFollowTimeStart, String currentFollowTimeEnd, @ModelAttribute PageView pageView, HttpServletRequest request) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		StringBuilder filterText = new StringBuilder();
		User user = (User)request.getSession().getAttribute("userSession");
		//List<User> superiors = userService.getDirectSuperior(user.getId());
		//List<User> shepherds = userService.getDirectShepherd(user.getId());
		List<User> underlings = userService.getDirectUnderling(user.getId());
		List<User> subordinates = userService.getDirectSubordinate(user.getId());
		filterText.append(" USER_ID IN('" + user.getId() + "'");
		if(underlings != null && !underlings.isEmpty()){
			for(User underling : underlings){
				filterText.append(",'" +  underling.getId() + "'");
			}
		}
		
		if(subordinates != null && !subordinates.isEmpty()){
			for(User subordinate : subordinates){
				filterText.append(",'" +  subordinate.getId() + "'");
			}
		}
		filterText.append(")");
		
		if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
			pageView.setFilterText(filterText.toString());
		}else{
			pageView.setFilterText(pageView.getFilterText() + " AND " + filterText.toString());
		}
		
		if(!StringUtil.isNullOrEmpty(crmCustomerName)){
			String filter = " CRM_CUSTOMER_ID IN (SELECT ID FROM CRM_CUSTOMER WHERE CUSTOMER_NAME LIKE '%" + crmCustomerName + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filter);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filter);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(followerDisplayName)){
			String filter = " USER_ID IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + followerDisplayName + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filter);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filter);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(currentFollowTimeStart) && !StringUtil.isNullOrEmpty(currentFollowTimeEnd)){
			String filter = " (CURRENT_FOLLOW_TIME BETWEEN '" + currentFollowTimeStart + "' AND '" + currentFollowTimeEnd + "') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filter);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filter);
			}
		}
		
		pageView = crmCustomerFollowService.query(pageView, crmCustomerFollow);
		model.addAttribute("pageView", pageView);
		return "/content/crm/crm-customer-follow-list";
	}

	
	@RequestMapping(value = "crm-customer-to-follow-customer")
	public String toFollowCustomer(Model model, @RequestParam String crmCustomerId, 
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("userSession");
		CrmCustomer crmCustomer = crmCustomerService.getById(crmCustomerId);
		boolean flag = false;
		//除本人之外，上级领导仍然可以进行拜访
		if(crmCustomer.getFollower() != null){
			if(user.getId().equals(crmCustomer.getFollower().getId())){
				flag = true;
			}else{
				List<User> underlings = userService.getDirectUnderling(user.getId());
				if(underlings != null && !underlings.isEmpty()){
					for(User u : underlings){
						if(u.getId().equals(crmCustomer.getFollower().getId())){
							flag = true;
							break;
						}
					}
				}
				
				if(!flag){
					List<User> subordinate = userService.getDirectSubordinate(user.getId());
					if(subordinate != null && !subordinate.isEmpty()){
						for(User u : subordinate){
							if(u.getId().equals(crmCustomer.getFollower().getId())){
								flag = true;
								break;
							}
						}
					}
				}
			}
		}
		
		
		if(flag){
			CrmCustomerFollow item = new CrmCustomerFollow();
			item.setCrmCustomer(crmCustomer);
			model.addAttribute("item", item);
			model.addAttribute("lastFollow", crmCustomerFollowService.getLastByCrmCustomerId(crmCustomerId, user.getId()));
			return "/content/crm/crm-customer-to-follow-customer";
		}else{
			messageHelper.addFlashMessage(redirectAttributes,"操作失败");
			return "redirect:crm-customer-list.do";
		}
		
		/*if(crmCustomer.getFollower() == null || !user.getId().equals(crmCustomer.getFollower().getId())){
			messageHelper.addFlashMessage(redirectAttributes,"操作失败");
			return "redirect:crm-customer-list.do";
		}else{
			CrmCustomerFollow item = new CrmCustomerFollow();
			item.setCrmCustomer(crmCustomer);
			model.addAttribute("item", item);
			model.addAttribute("lastFollow", crmCustomerFollowService.getLastByCrmCustomerId(crmCustomerId));
			return "/content/crm/crm-customer-to-follow-customer";
		}*/
	}
	
	@RequestMapping(value = "crm-customer-follow-input")
	public String input(Model model, String id, HttpServletRequest request) {
		CrmCustomerFollow item = null;
		if (id != null) {
			item = crmCustomerFollowService.getById(id);
		}
		model.addAttribute("item", item);
		//model.addAttribute("crmCustomers", crmCustomerService.getAll());
		return "/content/crm/crm-customer-follow-input";
	}

	@RequestMapping(value = "crm-customer-follow-save", method = RequestMethod.POST)
	public String add(CrmCustomerFollow crmCustomerFollow, 
			@RequestParam(value="crmCustomerId", required=true)String crmCustomerId, 
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		crmCustomerFollow.setCrmCustomer(crmCustomerService.getById(crmCustomerId));
		
		if(crmCustomerFollow.getId() == null){
			User user = (User)request.getSession().getAttribute("userSession");
			crmCustomerFollow.setFollower(user);
			crmCustomerFollow.setOrgEntity(user.getOrgEntity());
			crmCustomerFollow.setStatus("未提交");
			crmCustomerFollowService.add(crmCustomerFollow);
		}else{
			crmCustomerFollowService.modify(crmCustomerFollow);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:crm-customer-follow-list.do";
	}
	
	@RequestMapping(value = "crm-customer-follow-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			crmCustomerFollowService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:crm-customer-follow-list.do";
	}
	
	/**
	 * 当前客户的跟进人是否为当前用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "crm-customer-follow-check-follower")
	public boolean check(@RequestParam(value="crmCustomerId", required=true)String crmCustomerId, HttpServletRequest request){
		String sql = "SELECT COUNT(1) FROM CRM_CUSTOMER WHERE ID=? AND USER_ID=?";
		int count = jdbcTemplate.queryForObject(sql, Integer.class, crmCustomerId, ((User)request.getSession().getAttribute("userSession")).getId());
		return count == 1;
	}
	
	@ResponseBody
	@RequestMapping(value = "crm-customer-follow-last-follow")
	public CrmCustomerFollow getLast(@RequestParam(value="crmCustomerId", required=true)String crmCustomerId, HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("userSession");
		return crmCustomerFollowService.getLastByCrmCustomerId(crmCustomerId, user.getId());
	}
	
	@ResponseBody
	@RequestMapping(value = "crm-customer-follow-to-superior-suggest")
	public String toSuperiorSuggest(@RequestParam(value="crmFollowCustomerId", required=true)String[] crmFollowCustomerId){
		if(crmCustomerFollowService.toSuperiorSuggest(crmFollowCustomerId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "crm-customer-follow-to-shepher-suggest")
	public String toShepherSuggest(@RequestParam(value="crmFollowCustomerId", required=true)String[] crmFollowCustomerId){
		if(crmCustomerFollowService.toShepherSuggest(crmFollowCustomerId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "crm-customer-follow-to-filing")
	public String toFiling(@RequestParam(value="crmFollowCustomerId", required=true)String[] crmFollowCustomerId){
		if(crmCustomerFollowService.toFiling(crmFollowCustomerId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "crm-customer-follow-to-export-batch-to-file")
	public String export(@RequestParam(value="selectedItem", required=true)String[] selectedItem) throws IOException{
		String targetFile = StringUtil.getUUID() + ".xls";
		FileUtils.copyInputStreamToFile(IExmportUtil.exportMultiColumn(
				new String[]{"客户名称", "联系人", "职位","联系电话","本次拜访时间","上次拜访时间","下次拜访时间","拜访内容","计划", "建议"}, 
				crmCustomerFollowService.toBatchExport(selectedItem)), new File(FileUtil.attachmentPath, targetFile));
		return targetFile;
	}

}
