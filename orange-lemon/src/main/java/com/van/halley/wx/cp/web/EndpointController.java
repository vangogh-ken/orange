package com.van.halley.wx.cp.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.van.halley.util.StringUtil;
import com.van.halley.wx.cp.core.WxCpFacade;

import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;

@Controller
@RequestMapping(value = "/wx/")
public class EndpointController {
	private static Logger LOG = LoggerFactory.getLogger(EndpointController.class);
	
	@Autowired
	private WxCpFacade wxCpFacade;
	@Autowired
	private WxCpService wxCpService;
	@Autowired
	private WxCpCryptUtil wxCpCryptUtil;

	@RequestMapping(value = "endpoint")
	public void handleMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		
		String msgSignature = request.getParameter("msg_signature"); 
		String nonce = request.getParameter("nonce"); 
		String timestamp = request.getParameter("timestamp"); 
		String echostr = request.getParameter("echostr");
		
		LOG.debug("msgSignature : {}, nonce: {}, timestamp: {}, echostr: {},", msgSignature, nonce, timestamp, echostr);
		if (!StringUtil.isNullOrEmpty(echostr)) {
			LOG.info("回调测试");
			if (wxCpService.checkSignature(msgSignature, timestamp, nonce, echostr)) {
				response.getWriter().println(wxCpCryptUtil.decrypt(echostr));// 说明是一个仅仅用来验证的请求，回显echostr
				return;
			} else {
				response.getWriter().println("Illegal Request");// 消息签名不正确，说明不是公众平台发过来的消息
				return;
			}
		} else {
			LOG.info("正式数据");
			wxCpFacade.proccess(request);
		}
	}
}
