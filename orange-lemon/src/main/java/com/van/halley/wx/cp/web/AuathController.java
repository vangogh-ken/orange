package com.van.halley.wx.cp.web;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.van.halley.db.persistence.entity.UserBase;
import com.van.service.UserBaseService;
import com.van.service.UserService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;

/**
 * @author anxinxx
 *
 */
@Controller
@RequestMapping(value = "/wx/")
public class AuathController {
	private static Logger LOG  = LoggerFactory.getLogger(AuathController.class);
	@Autowired
	private WxCpService wxCpService;
	@Autowired
	private UserBaseService userBaseService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="oauth2")
	public void oauth2(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
	    response.setStatus(HttpServletResponse.SC_OK);
	    String code = request.getParameter("code");
	    LOG.info("接收到请求, code : {}", code);
	    try {
	      String[] res = wxCpService.oauth2getUserInfo(code);
	      LOG.info("解析结果, res : {}", Arrays.toString(res));
	      UserBase userBase = userBaseService.getByWeixinName(res[0]);
	      if(userBase != null){
	    	  request.getSession().setAttribute("userSession", userService.getById(userBase.getUserId()));
	    	  
	    	  //have not hopeUrl means this request is just auth from tencent
	    	  if(request.getSession().getAttribute("hopeUrl") == null){
		    	  wxCpService.userAuthenticated(res[0]);
		    	  response.getWriter().println("<h1>code</h1>");
			      response.getWriter().println(code);
			      response.getWriter().println("<h1>result</h1>");
			      response.getWriter().println("认证成功！");
			      response.getWriter().flush();
				  response.getWriter().close();
		      }else{
		    	  response.sendRedirect((String)request.getSession().getAttribute("hopeUrl"));
		      }
	      }else{
	    	  LOG.error("认证失败");
		      response.getWriter().println("<h1>code</h1>");
		      response.getWriter().println(code);
		      response.getWriter().println("<h1>result</h1>");
		      response.getWriter().println("认证失败，请确认是否为向企业登记合法账号！");
		      response.getWriter().flush();
			  response.getWriter().close();
	      }
	      
	    } catch (WxErrorException e) {
	      LOG.error("解析错误： {}", e);
	      response.getWriter().println("<h1>code</h1>");
	      response.getWriter().println(code);
	      response.getWriter().println("<h1>result</h1>");
	      response.getWriter().println("认证失败，请确认是否为向企业登记合法账号！");
	      response.getWriter().flush();
		  response.getWriter().close();
	    }
	    
	}
}
