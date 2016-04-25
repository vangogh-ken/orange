package com.van.halley.common.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.van.halley.bpm.service.HistoricQueryService;
import com.van.halley.bpm.service.RepositoryQueryService;
import com.van.halley.bpm.support.ProcessDefinitionCache;
import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.ForumTopic;
import com.van.halley.db.persistence.entity.LoginLog;
import com.van.halley.db.persistence.entity.MailReceive;
import com.van.halley.db.persistence.entity.OutMsgInfo;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.service.BpmConfigBasisService;
import com.van.service.CmsArticleService;
import com.van.service.CmsCatalogService;
import com.van.service.ForumTopicService;
import com.van.service.LoginLogService;
import com.van.service.MailReceiveService;
import com.van.service.OutMsgInfoService;
import com.van.service.ResourceService;

/**
 * 登陆, 主页处理
 * Session 中放入的对象: userSession, resources, unreadMsgs, handleTasks, claimTasks
 * 对应为: 用户信息, 用户可用资源, 未读信息, 待办任务, 待领任务
 */
@Controller
@RequestMapping("/base/")
public class BaseController {
	//private static Logger logger = LoggerFactory.getLogger(BaseController.class);
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private LoginLogService loginLogService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private AuthenticationManager myAuthenticationManager;
	@Autowired
	private HistoricQueryService historicQueryService;
	@Autowired
	private CmsArticleService cmsArticleService;
	@Autowired
	private CmsCatalogService cmsCatalogService;
	@Autowired
	private RepositoryQueryService repositoryQueryServiceImpl;
	@Autowired
	private OutMsgInfoService outMsgInfoService;
	@Autowired
	private MailReceiveService mailReceiveService;
	@Autowired
	private ForumTopicService forumTopicService;
	@Autowired
	private BpmConfigBasisService bpmConfigBasisService;

	@RequestMapping("login")
	public String login(Model model, HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		if(request.getSession() != null && request.getSession().getAttribute("SPRING_SECURITY_CONTEXT") != null && 
				request.getSession().getAttribute("SESSION_ID") != null){
			String SESSION_ID = request.getSession().getAttribute("SESSION_ID").toString();
			LoginLog loginLog = loginLogService.getById(SESSION_ID);//====记录登出时间
			if(loginLog != null){
				loginLog.setLogoutTime(new Date());
				loginLogService.modify(loginLog);
			}
			request.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");//移除SPRING_SECURITY_CONTEXT
			request.getSession().setMaxInactiveInterval(0);
			request.getSession().invalidate();
		}
		model.addAttribute("isLogin", "T");
		
		//项目路径
		if(StringUtil.isNullOrEmpty(FileUtil.projectPath)){
			FileUtil.projectPath = request.getServletContext().getRealPath("/");
		}
		return "/common/login";

	}

	@RequestMapping("loginCheck")
	public String loginCheck(Model model, @RequestParam(value="username", required=true)String username, 
			@RequestParam(value="password", required=true)String password, HttpServletRequest request) {
		try {
			if (!request.getMethod().equals("POST")) {
				request.setAttribute("message", "POST提交！");
				request.setAttribute("error", true);
			}
			if (StringUtil.isNullOrEmpty(username) || StringUtil.isNullOrEmpty(password)) {
				request.setAttribute("message", "用户名或密码不能为空！");
				request.setAttribute("error", true);
				
				model.addAttribute("isLogin", "T");
				return "/common/login";
			}
			// 验证用户账号与密码是否正确
			User user = this.userDao.getByUserName(username);
			password = StringUtil.encodeMd5(password);
			if (user == null || user.getPassword() == null || !user.getPassword().equals(password)) {
				request.setAttribute("message", "用户或密码不正确！");
				request.setAttribute("error", true);
				
				model.addAttribute("isLogin", "T");
				return "/common/login";
			} else if (user.getStatus() == null
					|| !user.getStatus().equals("T")) {
				request.setAttribute("message", "当前用户已禁用！");
				request.setAttribute("error", true);
				
				model.addAttribute("isLogin", "T");
				return "/common/login";
			}
			Authentication authentication = myAuthenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);
			HttpSession session = request.getSession(true);
			session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
			session.setAttribute("userSession", user);// 当验证都通过后，把用户信息放在session里
			
			
			//LoginLog loginLog = new LoginLog();// 记录登录信息
			LoginLog loginLog = loginLogService.getById(session.getId());
			if(loginLog == null){
				loginLog = new LoginLog();
			}
			//loginLog.setId(session.getId());
			loginLog.setUser(user);
			loginLog.setIpAddress(StringUtil.getIPAddress(request));
			//loginLogService.add(loginLog);
			if(loginLog.getId() == null){
				loginLogService.add(loginLog);
			}else{
				loginLogService.modify(loginLog);
			}
			
			session.setAttribute("SESSION_ID", session.getId());//存储登录日志ID。
			session.setAttribute("LOGIN_IP", StringUtil.getIPAddress(request));//存储登录IP
			request.getSession().setAttribute("resources", resourceService.getResourceByPositionId(user.getPosition().getId()));
			Object object = request.getSession().getAttribute("expectUri");
			if(object != null){
				request.getSession().removeAttribute("expectUri");//不为空，且会跳转，则需要remove保存的地址
				String url = object.toString();
				if(!url.contains("dashboard.do")){
					return "redirect:" + url;
				}
			}
			return "redirect:dashboard.do";
		} catch (AuthenticationException ae) {
			request.setAttribute("message", "登录异常，请联系管理员！");
			request.setAttribute("error", true);
			
			model.addAttribute("isLogin", "T");
			return "/common/login";
		}
	}

	/**
	 * @return
	 */
	@RequestMapping("dashboard")
	public String index(Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("userSession");
		
		model.addAttribute("cmsArticles", cmsArticleService.getAll());
		model.addAttribute("cmsCatalogs", cmsCatalogService.getAll());
		PageView pageView = new PageView(1);
		pageView.setOrderBy("CREATE_TIME");
		pageView.setOrder("DESC");
		model.addAttribute("forumTopics", forumTopicService.query(pageView, new ForumTopic()).getResults());
		
		//model.addAttribute("handleTasks", taskQueryService.handle(user.getId(), new PageView(1)).getResults());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("assigneeUserId", user.getId());
		map.put("finished", "F");
		model.addAttribute("handleTasks", historicQueryService.queryHistoricTaskInstance(new PageView(1) , map).getResults());
		
		map = new HashMap<String, Object>();
		map.put("claimUserId", user.getId());
		map.put("finished", "F");
		model.addAttribute("claimTasks", historicQueryService.queryHistoricTaskInstance(new PageView(1) , map).getResults());
		
		model.addAttribute("pds", repositoryQueryServiceImpl.getProcessDefinitionsLatestVerion());
		model.addAttribute("processes", bpmConfigBasisService.getByUserId(user.getId()));
		return "/content/dashboard/dashboard";
	}
	
	@ResponseBody
	@RequestMapping("poll-attention")
	public Map<String, Object> pollAttention(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("userSession");
		if(user == null){
			return map;
		}else{
			MailReceive mailReceive = new MailReceive();
			mailReceive.setStatus("未读");
			mailReceive.setUserId(user.getId());
			map.put("unreadMails", mailReceiveService.query(new PageView(1), mailReceive));
			
			OutMsgInfo msgInfo = new OutMsgInfo();
			msgInfo.setReceiver(user);
			msgInfo.setStatus("未读");
			map.put("unreadMsgs", outMsgInfoService.query(new PageView(1), msgInfo));
			/*List<String> cadidateGroups = new ArrayList<String>();
			if(user.getPosition() != null){
				List<Role> roles = roleService.getRoleByPositionId(user.getPosition().getId());
				for(Role role : roles){
					cadidateGroups.add(role.getRoleName());
				}
			}*/
			
			//map.putAll((Map<String, Object>)managementService.executeCommand(new FindRuningTasksCommand(user.getDisplayName(), cadidateGroups)));
			List<Map<String, String>> results = new ArrayList<Map<String, String>>();
			
			//PageView pageView = taskQueryService.handle(user.getId(), new PageView(1));
			
			PageView pageView = new PageView(1);
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("assigneeUserId", user.getId());
			variables.put("finished", "F");
			pageView = historicQueryService.queryHistoricTaskInstance(pageView, variables);
			
			for(HistoricTaskInstanceEntity task : (List<HistoricTaskInstanceEntity>)pageView.getResults()){
				Map<String, String> singleMap = new HashMap<String, String>();
				singleMap.put("id", task.getId());
				singleMap.put("name", task.getName());
				singleMap.put("processInstanceId", task.getProcessInstanceId());
				singleMap.put("processName", ProcessDefinitionCache.getProcessName(task.getProcessDefinitionId()));
				results.add(singleMap);
			}
			
			pageView.setResults(results);
			map.put("handleTasks", pageView);
			
			results = new ArrayList<Map<String, String>>();
			//pageView = taskQueryService.claim(user.getId(), new PageView(1));
			pageView = new PageView(1);
			variables = new HashMap<String, Object>();
			variables.put("claimUserId", user.getId());
			variables.put("finished", "F");
			pageView = historicQueryService.queryHistoricTaskInstance(pageView, variables);
			
			for(HistoricTaskInstanceEntity task : (List<HistoricTaskInstanceEntity>)pageView.getResults()){
				Map<String, String> singleMap = new HashMap<String, String>();
				singleMap.put("id", task.getId());
				singleMap.put("name", task.getName());
				singleMap.put("processInstanceId", task.getProcessInstanceId());
				singleMap.put("processName", ProcessDefinitionCache.getProcessName(task.getProcessDefinitionId()));
				results.add(singleMap);
			}
			
			pageView.setResults(results);
			map.put("claimTasks", pageView);
			return map;
		}
	}
}
