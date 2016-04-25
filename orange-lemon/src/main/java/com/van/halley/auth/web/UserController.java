package com.van.halley.auth.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.van.ext.data.CsvExportor;
import com.van.ext.data.ExportDataModel;
import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.Role;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.db.persistence.entity.UserBase;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.service.PositionService;
import com.van.service.RoleService;
import com.van.service.UserBaseService;
import com.van.service.UserRoleService;
import com.van.service.UserService;

@Controller
@RequestMapping(value = "/user/")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserBaseService userBaseService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private PositionService positionService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "user-list")
	public String list(Model model, User user, String orgEntityName, @ModelAttribute PageView<User> pageView) {
		if (pageView == null) {
			pageView = new PageView<User>(1);
		}
		
		if(!StringUtil.isNullOrEmpty(orgEntityName)){
			String filterText = "POSITION_ID IN(SELECT ID FROM SYS_AUTH_POSITION WHERE ORG_ENTITY_ID IN(SELECT ID FROM SYS_AUTH_ORG_ENTITY WHERE ORG_ENTITY_NAME LIKE '%" + orgEntityName + "%'))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		userService.query(pageView, user);
		model.addAttribute("pageView", pageView);
		return "/content/user/user-list";
	}

	/** 编辑或新增 */
	@RequestMapping(value = "user-input")
	public String input(Model model, String id) {
		User item = null;
		if (id != null) {
			item = userService.getById(id);
		}

		List<User> users = userService.getAll();
		model.addAttribute("users", users);
		model.addAttribute("positions", positionService.getAll());
		model.addAttribute("item", item);
		return "/content/user/user-input";
	}
	
	/** 保存数据 */
	@RequestMapping(value = "user-save")
	public String save(Model model, User user, String positionId, RedirectAttributes redirectAttributes) {
		// 使用能够同步到工作流引擎的service
		user.setPassword(StringUtil.encodeMd5(user.getPassword()));
		user.setPosition(positionService.getById(positionId));
		if (user.getId() == null) {
			userService.add(user);
			messageHelper.addFlashMessage(redirectAttributes, "操作成功");
		} else {
			userService.modify(user);
			messageHelper.addFlashMessage(redirectAttributes, "操作成功");
		}
		return "redirect:user-list.do";
	}

	/** 删除 */
	@RequestMapping(value = "user-delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		userService.delete(id);
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:user-list.do";
	}

	/** 删除所选的 */
	@RequestMapping(value = "user-remove")
	public String remove(String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			userService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:user-list.do";
	}

	/** 暂时未用 给用户分配角色界面 */
	@RequestMapping(value = "user-allocation")
	public String allocation(Model model, String userId) {
		User user = userService.getById(userId);
		model.addAttribute("user", user);
		List<Role> roles = roleService.getAll();
		model.addAttribute("roles", roles);
		return "/content/user/userRole";
	}

	/** 保存用户分配角色 */
	@RequestMapping(value = "user-role-allocation-save")
	public String allocationSave(Model model, String userId, String[] roleIds,
			RedirectAttributes redirectAttributes) {
		userRoleService.allocation(userId, roleIds);
		messageHelper.addFlashMessage(redirectAttributes, "分配成功");
		return "redirect:user-list.do";
	}

	// 自己修改个人信息
	@RequestMapping(value = "user-profile")
	public String profile(Model model, HttpServletRequest request) {
		UserBase item = null;
		UserBase filter = new UserBase();
		filter.setUserId(((User) request.getSession().getAttribute("userSession")).getId());
		List<UserBase> list = userBaseService.queryForList(filter);
		if(list == null || list.isEmpty()){
			item = new UserBase();
		}else{
			item = list.get(0);
		}
		model.addAttribute("item", item);
		return "/content/user/user-profile";
	}

	@RequestMapping(value = "user-profile-save")
	public String profileSave(Model model, UserBase userBase,
			RedirectAttributes redirectAttributes) {
		if(userBase.getId() == null ){
			userBaseService.add(userBase);
		}else{
			userBaseService.modify(userBase);
		}
		messageHelper.addFlashMessage(redirectAttributes, "更新成功");
		return "redirect:user-profile.do";
	}

	//return userService.getAll();
	//因为user 中包含有对自身的引用，不能够直接转换为json
	@ResponseBody
	@RequestMapping(value = "user-all")
	public List<Map<String, Object>> userAll() {
		String sql = "SELECT U.ID AS id, U.DISPLAY_NAME AS displayName, P.POSITION_NAME AS position, E.ORG_ENTITY_NAME AS orgName, P.UNDER_ORG_ID FROM SYS_AUTH_USER U "+
					 "LEFT JOIN SYS_AUTH_POSITION P ON U.POSITION_ID = P.ID LEFT JOIN SYS_AUTH_ORG_ENTITY E "+
					 "ON ((E.ID = P.UNDER_ORG_ID OR E.ID = (SELECT E2.PARENT_ORG_ID FROM SYS_AUTH_ORG_ENTITY E2 WHERE E2.ID = P.UNDER_ORG_ID)) " +
					 "AND E.TYPE_ID=(SELECT ID FROM SYS_AUTH_ORG_TYPE T WHERE T.ORG_ENTITY_NAME='部门'))";
		return jdbcTemplate.queryForList(sql);
	}
	
	@ResponseBody
	@RequestMapping(value = "user-get-all")
	public List<User> get() {
		return userService.getAll();
	}
	
	@ResponseBody
	@RequestMapping(value = "user-get-coworker")
	public List<User> getCoworker(HttpServletRequest request) {
		String userId = ((User)request.getSession().getAttribute("userSession")).getId();
		return userService.getCoworker(userId);
	}

	// 修改密码
	@RequestMapping(value = "change-password-input")
	public String changePassword(Model model) {
		return "/content/user/change-password-input";
	}

	// 保存修改密码
	@RequestMapping(value = "change-password-save")
	public String changePasswordSave(Model model, String oldPassword,
			String newPassword, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		User currentUser = (User) request.getSession().getAttribute(
				"userSession");
		if (currentUser.getPassword().equals(StringUtil.encodeMd5(oldPassword))) {
			currentUser.setPassword(StringUtil.encodeMd5(newPassword));
			userService.modify(currentUser);
			messageHelper.addFlashMessage(redirectAttributes, "修改密码成功,请重新登陆");
			return "redirect:/base/login.do";
		} else {
			messageHelper.addFlashMessage(redirectAttributes, "原密码错误,请重新修改");
			return "redirect:change-password-input.do";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "user-check-username")
	public boolean checkUserName(@RequestParam(required=true, value="userName") String userName,
			String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM SYS_AUTH_USER WHERE USER_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, userName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM SYS_AUTH_USER WHERE USER_NAME = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, userName);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "user-check-displayname")
	public boolean checkDisplayName(@RequestParam(required=true, value="displayName") String displayName,
			String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM SYS_AUTH_USER WHERE DISPLAY_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, displayName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM SYS_AUTH_USER WHERE DISPLAY_NAME = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, displayName);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "user-check-weixinname")
	public boolean checkWeixinName(@RequestParam(required=true, value="weixinName") String weixinName,
			String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM SYS_AUTH_USER_BASE WHERE WX_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, weixinName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM SYS_AUTH_USER_BASE WHERE WX_NAME = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, weixinName);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "user-base-icon-save", method = RequestMethod.POST)
	public Map<String, Object> add(MultipartHttpServletRequest request)
			throws IOException, FileUploadException {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> map = FileUtil.upload("file", request);
		if (map != null) {
			UserBase userBase = userBaseService.getByUserId(((User)request.getSession().getAttribute("userSession")).getId());
			userBase.setIcon(map.get("fileData"));
			userBaseService.modify(userBase);
			result.put("result", "success");
			//将图片存入项目之下的地址
			FileUtils.copyFile(new File(FileUtil.attachmentPath, map.get("fileData")), new File(FileUtil.projectPath + "icon", map.get("fileData")));
			request.getSession().setAttribute("userSession", userService.getById(((User)request.getSession().getAttribute("userSession")).getId()));
		}else{
			result.put("result", "error");
		}
		
		return result;
	}
	/**导出数据
	 * @throws IOException **/
	@RequestMapping(value="user-export")
	public void export(User user, HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<User> list = userService.queryForList(user);
		String fileName = "user-list";
		ExportDataModel exportDataModel = new ExportDataModel(fileName, list);
		exportDataModel.addHeaders("用户名","姓名","注册时间");
		exportDataModel.addGetValueMethods("getUserName","getDisplayName","getRegTime");
		new CsvExportor().doExport(request, response, exportDataModel);;
	}
	
	/** 重置密码 */
	@RequestMapping(value = "user-reset-password")
	public String resetPassword(String id, RedirectAttributes redirectAttributes) {
		User user = userService.getById(id);
		user.setPassword(StringUtil.encodeMd5("11111111"));
		userService.modify(user);
		messageHelper.addFlashMessage(redirectAttributes, "操作成功");
		return "redirect:user-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "user-invite-weixin")
	public String userInviteWeixin(HttpSession session) {
		String userId = ((User)session.getAttribute("userSession")).getId();
		if(userService.userInviteWeixin(userId)){
			return "success";
		}else{
			return "error";
		}
	}
}