package com.van.core.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.GenericFilterBean;

import com.van.halley.core.session.SessionContextHolder;
import com.van.halley.db.persistence.entity.Resource;
import com.van.halley.db.persistence.entity.User;
import com.van.service.ResourceService;

/**
 * 本Filter 用于重新进入系统,对菜单跳转的控制以及保存当前菜单
 * @author Think
 *
 */
public class SessionExpiredFilter extends GenericFilterBean {
	/**
	 * 登录地址
	 */
	private String loginUrl;
	private ResourceService resourceService;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resq,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resq;
		HttpSession session = request.getSession(true);
		String params = request.getQueryString() == null ? "" : ("?" + request.getQueryString());
		String url = request.getServletPath();
		User currentUser = (session == null || session.getAttribute("userSession") == null) ? null : (User)session.getAttribute("userSession");
		if(session == null || currentUser == null 
				|| currentUser.getPosition() == null
				|| currentUser.getPosition().getOrgEntity() == null){
			if(!isAsyncRequest(request, response)){
				request.getSession().setAttribute("expectUri", url + params);//如果不是异步请求，才会保留其原始url
			}
			response.sendRedirect(request.getContextPath() + loginUrl);
		}else{
			//设置当前用户
			SessionContextHolder.getContext().setCurrentUser(currentUser);
			//只有正确跳转到请求页面的时候才需要将请求的当前菜单保存到session,确保前台页面菜单正确
			Resource filter = new Resource();
			filter.setResourceUrl(url);
			Resource resource = resourceService.queryForOne(filter);
			
			if(resource != null){
				request.getSession().setAttribute("currentResource", resource);
			}else{
				filter.setResourceUrl(url + params);
				resource = resourceService.queryForOne(filter);
				if(resource != null){
					request.getSession().setAttribute("currentResource", resource);
				}else{
					//~ 系统若没有匹配当前菜单，则使用主页菜单
					filter = new Resource();
					filter.setResourceKey("DASHBOARD");
					request.getSession().setAttribute("currentResource", resourceService.queryForOne(filter));
				}
			}
			filterChain.doFilter(req, resq);
		}
	}
		
	/**
	 * 判断是否为异步请求
	 */
	public boolean isAsyncRequest(HttpServletRequest request, HttpServletResponse response){
		boolean flag = false;
		if(request.getHeader("x-requested-with") != null && 
				request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
			try {
				PrintWriter writer = response.getWriter();
				writer.write("timeout");
				writer.flush();
				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public static void main(String[] args) {
		System.out.println(UrlPatternMatcher.create("/base/login*").matches("/base/loginCheck.do"));
	}
	
	public String getLoginUrl() {
		return loginUrl;
	}
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

}
