package com.van.halley.wx.cp.core;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.van.core.spring.ApplicationPropertiesFactoryBean;
import com.van.halley.wx.cp.handler.EnterAgentHandler;
import com.van.halley.wx.cp.handler.EventBpmTaskHandler;
import com.van.halley.wx.cp.handler.LocationHandler;
import com.van.halley.wx.cp.handler.PreHandler;
import com.van.halley.wx.cp.handler.ProxyEventHandler;
import com.van.halley.wx.cp.handler.WX;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpInMemoryConfigStorage;
import me.chanjar.weixin.cp.api.WxCpMessageHandler;
import me.chanjar.weixin.cp.api.WxCpMessageRouter;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutTextMessage;

public class WxCpFacade {
	private static Logger LOG = LoggerFactory.getLogger(WxCpFacade.class);
	
	@Autowired
	private ApplicationPropertiesFactoryBean applicationProperties;
	@Autowired
	private WxCpService wxCpService;
	@Autowired
	private WxCpInMemoryConfigStorage wxCpConfigStorage;
	
	private boolean initialized = false;
	
	private WxCpMessageRouter wxCpMessageRouter;

	//@PostConstruct
	private void initialize() {
		wxCpMessageRouter = new WxCpMessageRouter(wxCpService);
		initHandler();
		afterSetProperties();
	}
	
	private void afterSetProperties(){
		WxCpAgent.DEFAULT = applicationProperties.getProperties().getProperty("wx.cp.corpId.agentId.default");
		WxCpAgent.SIMPLE_MESSAGE = applicationProperties.getProperties().getProperty("wx.cp.corpId.agentId.SIMPLE_MESSAGE");
		WxCpAgent.FREIGHT_MESSAGE = applicationProperties.getProperties().getProperty("wx.cp.corpId.agentId.FREIGHT_MESSAGE");
	}
	
	/**
	 * handle message from servers
	 * @param request
	 */
	public void proccess(HttpServletRequest request){
		if(!initialized){
			initialize();
			initialized = true;
		}
		
		String msgSignature = request.getParameter("msg_signature"); 
		String nonce = request.getParameter("nonce"); 
		String timestamp = request.getParameter("timestamp");
		
		LOG.info("msgSignature : {}, nonce: {}, timestamp: {}", msgSignature, nonce, timestamp);
		try {
			WxCpXmlMessage wxCpMessage = WxCpXmlMessage.fromEncryptedXml(request.getInputStream(), this.wxCpConfigStorage, timestamp, nonce, msgSignature);
			
			LOG.info(wxCpMessage.toString());
			//LOG.info(String.format("系统已接收到消息，类型：%s, 事件: %s, 事件KEY: %s, 内容： %s。", 
					//wxCpMessage.getMsgType(), wxCpMessage.getEvent(), wxCpMessage.getEventKey(), wxCpMessage.getContent()));
			this.proccess(wxCpMessage);
		} catch (IOException e) {
			LOG.error("Catch a ERROR: {}", e);
		}
	}
	
	private void proccess(WxCpXmlMessage wxCpXmlMessage){
		wxCpMessageRouter.route(wxCpXmlMessage);
	}
	
	/**
	 * simple message send
	 * @param wxCpMessage
	 */
	public void proccess(WxCpMessage wxCpMessage){
		try {
			wxCpService.messageSend(wxCpMessage);
		} catch (WxErrorException e) {
			LOG.error("Catch a ERROR: {}", e);
		}
	}
	
	private void initHandler(){
        wxCpMessageRouter
          .rule()
	      .async(true)
	      .handler(new PreHandler())
	      .next()
        
          .rule()
	      .async(true)
	      .msgType(WX.Type.EVENT)
	      .event(WX.Event.ENTER_AGENT)
	      .handler(new EnterAgentHandler())
	      .next()
	      
	      .rule()
	      .async(true)
	      .msgType(WX.Type.EVENT)
	      .event(WX.Event.LOCATION)
	      .handler(new LocationHandler())
	      .next()
        
	      .rule()
	      .async(false)
	      .content("自动回复") // 拦截内容为“哈哈”的消息
	      .handler(new WxCpMessageHandler() {
		        @Override
		        public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context,
		            WxCpService wxCpService, WxSessionManager sessionManager) {
		        	WxCpMessage message = WxCpMessage
		              .TEXT()
		              .agentId(WxCpAgent.DEFAULT)
		              .safe("0")
		              .toUser(wxMessage.getFromUserName())
		              .content("这是服务器自动回复的信息")
		              .build();
		        	
		        	proccess(message);
		          return null;
		        }
		      })
	      .next()
	      
	      /**
	      .rule()
	      .async(false)
	      .handler(new WxCpMessageHandler() {
		        @Override
		        public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context,
		            WxCpService wxCpService, WxSessionManager sessionManager) {
		        	WxCpMessage message = WxCpMessage
		              .TEXT()
		              .agentId(WxCpAgent.DEFAULT)
		              .safe("0")
		              .toUser(wxMessage.getFromUserName())
		              .content(String.format("系统已接收到消息，类型：%s, 事件: %s, 事件KEY: %s, 内容： %s。", 
		            		  wxMessage.getMsgType(), wxMessage.getEvent(), wxMessage.getEventKey(), wxMessage.getContent()))
		              .build();
		        	proccess(message);
		          return null;
		        }
		      })
	      .next()
	      **/
	      
	      .rule()
	      .async(true)
	      .msgType(WX.Type.EVENT)
	      .event(WX.Event.CLICK)
	      .handler(new EventBpmTaskHandler())
	      .end();
        
        LOG.info("Weixin router init handlers complete");
	}

	//~ getter setter
	public WxCpMessageRouter getWxCpMessageRouter() {
		return wxCpMessageRouter;
	}

	public void setWxCpMessageRouter(WxCpMessageRouter wxCpMessageRouter) {
		this.wxCpMessageRouter = wxCpMessageRouter;
	}
	
}
