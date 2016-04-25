package com.van.core.servlet;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

import com.van.halley.core.session.SessionContextHolder;
import com.van.halley.db.persistence.entity.User;

import me.chanjar.weixin.cp.api.WxCpService;

/**
 * @author Think
 *
 */
public class WeixinOAuth2Filter extends GenericFilterBean {
	@Autowired
	private WxCpService wxCpService;
	/**
	 * oauth2地址
	 */
	private String oauth2Url;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resq,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resq;
		HttpSession session = request.getSession(true);
		String hopeUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + request.getServletPath();
		if(session.getAttribute("userSession") == null){
			session.setAttribute("hopeUrl", hopeUrl);
			//~ 此处必须带上域名与端口，否则weixin无法识别
			String oauth2 = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + oauth2Url;
			response.sendRedirect(wxCpService.oauth2buildAuthorizationUrl(oauth2, "VANBPMRP"));
		}else{
			//设置当前用户
			SessionContextHolder.getContext().setCurrentUser((User)session.getAttribute("userSession"));
			
			filterChain.doFilter(req, resq);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(UrlPatternMatcher.create("/wx/*").matches("/wx/oauth2.do"));
	}

	public String getOauth2Url() {
		return oauth2Url;
	}

	public void setOauth2Url(String oauth2Url) {
		this.oauth2Url = oauth2Url;
	}
}
