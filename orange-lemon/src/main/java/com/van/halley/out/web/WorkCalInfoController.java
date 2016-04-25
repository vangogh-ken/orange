package com.van.halley.out.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.van.ext.data.CsvExportor;
import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.db.persistence.entity.WorkCalInfo;
import com.van.halley.util.StringUtil;
import com.van.service.OrgEntityService;
import com.van.service.UserService;
import com.van.service.WorkCalInfoService;

/**
 * 
 * 日程管理
 * @author anxinxx
 *
 */
@Controller
@RequestMapping(value = "/out/")
public class WorkCalInfoController {
	@Autowired
	private WorkCalInfoService workCalInfoService;
	@Autowired
	private OrgEntityService orgEntityService;
	@Autowired
	private UserService userService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "work-cal-info-list-manage")
	public String query(Model model, WorkCalInfo workCalInfo, 
			String userId, String orgEntityId, String year, String month, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(StringUtil.isNullOrEmpty(userId) && StringUtil.isNullOrEmpty(orgEntityId)){
			pageView = workCalInfoService.query(pageView, workCalInfo);
		}else if(StringUtil.isNullOrEmpty(userId) && !StringUtil.isNullOrEmpty(orgEntityId)){
			StringBuilder sql = new StringBuilder();
			List<String> params = new ArrayList<String>();
			sql.append("SELECT ID FROM OUT_WORK_CAL_INFO WHERE USER_ID IN ");
			sql.append("(SELECT ID FROM SYS_AUTH_USER WHERE POSITION_ID IN ");
			sql.append("(SELECT ID FROM SYS_AUTH_POSITION WHERE UNDER_ORG_ID =?)) ");
			params.add(orgEntityId);
			
			if(!StringUtil.isNullOrEmpty(year)){
				sql.append("AND YEAR(START_TIME)=? ");
				params.add(year);
			}
			if(!StringUtil.isNullOrEmpty(month)){
				sql.append("AND MONTH(START_TIME)=? ");
				params.add(month);
			}
			if(!StringUtil.isNullOrEmpty(workCalInfo.getType())){
				sql.append("AND TYPE=? ");
				params.add(workCalInfo.getType());
			}
			List<String> ids = jdbcTemplate.queryForList(sql.toString(), String.class, params.toArray());
			List<WorkCalInfo> workCalInfoes = workCalInfoService.getAll();
			List<WorkCalInfo> results = new ArrayList<WorkCalInfo>();
			for(WorkCalInfo wci : workCalInfoes){
				if(ids.contains(wci.getId())){
					results.add(wci);
				}
			}
			pageView.setResults(results);
			pageView.setTotalCount(results.size());
		}else if(!StringUtil.isNullOrEmpty(userId) && StringUtil.isNullOrEmpty(orgEntityId)){
			
			StringBuilder sql = new StringBuilder();
			List<String> params = new ArrayList<String>();
			sql.append("SELECT ID FROM OUT_WORK_CAL_INFO WHERE USER_ID=? ");
			params.add(userId);
			
			if(!StringUtil.isNullOrEmpty(year)){
				sql.append("AND YEAR(START_TIME)=? ");
				params.add(year);
			}
			if(!StringUtil.isNullOrEmpty(month)){
				sql.append("AND MONTH(START_TIME)=? ");
				params.add(month);
			}
			if(!StringUtil.isNullOrEmpty(workCalInfo.getType())){
				sql.append("AND TYPE=? ");
				params.add(workCalInfo.getType());
			}
			List<String> ids = jdbcTemplate.queryForList(sql.toString(), String.class, params.toArray());
			List<WorkCalInfo> workCalInfoes = workCalInfoService.getAll();
			List<WorkCalInfo> results = new ArrayList<WorkCalInfo>();
			for(WorkCalInfo wci : workCalInfoes){
				if(ids.contains(wci.getId())){
					results.add(wci);
				}
			}
			pageView.setResults(results);
			pageView.setTotalCount(results.size());
		}
		
		model.addAttribute("pageView", pageView);
		model.addAttribute("orgEntities", orgEntityService.getAll());
		model.addAttribute("users", userService.getAll());
		
		return "/content/out/work-cal-info-list-manage";
	}
	
	@RequestMapping(value = "work-cal-info-list")
	public String queryOwner(Model model, WorkCalInfo workCalInfo, String year, String month, 
			HttpServletRequest request, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		workCalInfo.setOwner((User)request.getSession().getAttribute("userSession"));
		if(!StringUtil.isNullOrEmpty(year)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" YEAR(START_TIME)='" + year + "'");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND YEAR(START_TIME)='" + year + "'");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(month)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" MONTH(START_TIME)='" + month + "'");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND MONTH(START_TIME)='" + month + "'");
			}
		}
		workCalInfoService.query(pageView, workCalInfo);
		model.addAttribute("pageView", pageView);
		return "/content/out/work-cal-info-list";
	}

	@RequestMapping(value = "work-cal-info-input")
	public String input(Model model, String id) {
		WorkCalInfo item = null;
		if (id != null) {
			item = workCalInfoService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/out/work-cal-info-input";
	}

	@RequestMapping(value = "work-cal-info-save", method = RequestMethod.POST)
	public String add(WorkCalInfo workCalInfo, String isDashboard, HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		//将标题设置为时间段
		/*if(workCalInfo.getTitle() == null){
			workCalInfo.setTitle(workCalInfo.getPhase());
		}
		
		if(workCalInfo.getType() == null){
			workCalInfo.setType("RC");
		}*/
		
		/*DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//如果主页则只能修改当天的日程
		if(dateFormat.format(new Date()).equals(dateFormat.format(workCalInfo.getStartTime()))
				&& isDashboard!=null && isDashboard.equals("T")){
			if(StringUtil.isNullOrEmpty(workCalInfo.getId())){
				workCalInfo.setOwner(((User)request.getSession().getAttribute("userSession")));
				workCalInfoService.add(workCalInfo);
			}else{
				workCalInfoService.modify(workCalInfo);
			}
			messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		//如果管理界面任意修改
		}else if(isDashboard == null){
			if(workCalInfo.getId() == null){
				workCalInfo.setOwner(((User)request.getSession().getAttribute("userSession")));
				workCalInfoService.add(workCalInfo);
			}else{
				workCalInfoService.modify(workCalInfo);
			}
			messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		}else{
			//取消各种限制都可以保存
			if(StringUtil.isNullOrEmpty(workCalInfo.getId())){
				workCalInfo.setOwner(((User)request.getSession().getAttribute("userSession")));
				workCalInfoService.add(workCalInfo);
			}else{
				workCalInfoService.modify(workCalInfo);
			}
			messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		}*/
		long balance = workCalInfo.getEndTime().getTime() - workCalInfo.getStartTime().getTime();
		StringBuilder title = new StringBuilder();
		for(int i=0, len = (int)balance/(1000*60*60*24) + 1; i<len; i++){
			if(workCalInfo.getContent().length() >= (i + 1) * 10){
				title.append(workCalInfo.getContent().substring(i*10, (i+1)*10));
			}else{
				title = new StringBuilder(workCalInfo.getContent());
				break;
			}
		}
		while(title.lastIndexOf("\r\n") > 0){
			title.delete(title.lastIndexOf("\r\n"), title.lastIndexOf("\r\n") + 2);
		}
		title.append("...");
		workCalInfo.setTitle(title.toString());
		//workCalInfo.setTitle(workCalInfo.getContent().length() >= 6 ? (workCalInfo.getContent().substring(0, 6) + "...") : workCalInfo.getContent() + "...");
		if(StringUtil.isNullOrEmpty(workCalInfo.getId())){
			workCalInfo.setOwner(((User)request.getSession().getAttribute("userSession")));
			workCalInfoService.add(workCalInfo);
		}else{
			workCalInfoService.modify(workCalInfo);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		
		if("T".equals(isDashboard)){
			return "redirect:/base/dashboard.do";
		}else{
			return "redirect:work-cal-info-list.do";
		}
		
	}
	
	@RequestMapping(value = "work-cal-info-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			workCalInfoService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:work-cal-info-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "work-cal-info-all")
	public List<WorkCalInfo> all(Model model, String isrc, String isjh, String isrz, HttpServletRequest request) {
		WorkCalInfo filter = new WorkCalInfo();
		filter.setOwner((User)request.getSession().getAttribute("userSession"));
		List<WorkCalInfo> workCalInfos = new ArrayList<WorkCalInfo>();
		if("T".equals(isrc)){
			filter.setType("RC");
			workCalInfos.addAll(workCalInfoService.queryForList(filter));
		}
		
		if("T".equals(isjh)){
			filter.setType("JH");
			workCalInfos.addAll(workCalInfoService.queryForList(filter));
		}
		
		if("T".equals(isrz)){
			filter.setType("RZ");
			workCalInfos.addAll(workCalInfoService.queryForList(filter));
		}
		
		/*
		for(WorkCalInfo workCalInfo : workCalInfos){
			String type = workCalInfo.getType();
			if("T".equals(isrc) && "T".equals(isjh) && "T".equals(isrz)){
				list.add(workCalInfo);
			}else if("T".equals(isrc) && type.equals("RC")){
				list.add(workCalInfo);
			}else if("T".equals(isjh) && type.equals("JH")){
				list.add(workCalInfo);
			}else if("T".equals(isrz) && type.equals("RZ")){
				list.add(workCalInfo);
			}
		}*/
		
		return workCalInfos;
	}
	
	@ResponseBody
	@RequestMapping(value = "work-cal-info-get")
	public WorkCalInfo get(Model model, String id) {
		return workCalInfoService.getById(id);
	}
	
	/**
	 * 管理列表中获取日程信息
	 */
	@ResponseBody
	@RequestMapping(value = "work-cal-info-to-show-content")
	public WorkCalInfo toShowContent(Model model, String workCalInfoId) {
		return workCalInfoService.getById(workCalInfoId);
	}
	
	@ResponseBody
	@RequestMapping(value = "work-cal-info-drop")
	public String drop(Model model, String id) {
		//取消各种限制
		workCalInfoService.delete(id);
		return "success";
		
		//只能删除当天的日程数据
		/*DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(dateFormat.format(new Date()).equals(dateFormat.format(workCalInfoService.getById(id).getStartTime()))){
			workCalInfoService.delete(id);
			return "success";
		}else{
			return "fail";
		}*/
	}
	
	/**导出数据
	 * @throws IOException **/
	@RequestMapping(value="work-cal-info-export")
	public void export(String type, String month, String year, String userId, String orgId, 
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(StringUtil.isNullOrEmpty(userId) && !StringUtil.isNullOrEmpty(orgId)){
			new CsvExportor().doExport(request, response, workCalInfoService.exportByOrgId(orgId, type, month, year));
		}else if(!StringUtil.isNullOrEmpty(userId) && StringUtil.isNullOrEmpty(orgId)){
			new CsvExportor().doExport(request, response, workCalInfoService.exportByUserId(userId, type, month, year));
		}
	}
	
	/**
	 * 时间段与类型的组合不能重复
	 */
	@ResponseBody
	@RequestMapping(value = "work-cal-info-check-phase")
	public boolean checkPhase(@RequestParam(required=true, value="phase") String phase, 
			@RequestParam(required=true, value="startTime") Date startTime, 
			@RequestParam(required=true, value="type") String type, String id, HttpServletRequest request) {
		String userId = ((User)request.getSession().getAttribute("userSession")).getId();
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM OUT_WORK_CAL_INFO WHERE PHASE=? AND START_TIME=? AND TYPE=? AND USER_ID=? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, phase, startTime, type, userId, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM OUT_WORK_CAL_INFO WHERE PHASE=? AND START_TIME=? AND TYPE=? AND USER_ID=?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, phase, startTime, type, userId);
			return count == 0;
		}
	}
}
