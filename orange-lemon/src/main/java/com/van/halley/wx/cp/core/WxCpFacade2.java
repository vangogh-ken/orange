package com.van.halley.wx.cp.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.van.halley.wx.cp.handler.EventBpmTaskHandler;
import com.van.halley.wx.cp.handler.LocationHandler;
import com.van.halley.wx.cp.handler.MessageHolder;
import com.van.halley.wx.cp.handler.ProxyEventHandler;
import com.van.halley.wx.cp.handler.WX;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpInMemoryConfigStorage;
import me.chanjar.weixin.cp.api.WxCpMessageHandler;
import me.chanjar.weixin.cp.api.WxCpMessageRouter;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutTextMessage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;

public class WxCpFacade2 {
	private static Logger LOG = LoggerFactory.getLogger(WxCpFacade2.class);
	
	private Properties properties;
	
	private WxCpService wxCpService = new WxCpServiceImpl();
	private WxCpInMemoryConfigStorage wxCpConfigStorage;
	private WxCpMessageRouter wxCpMessageRouter;
	private WxCpCryptUtil wxCpCryptUtil;
	
	private WxCpStore wxCpStore = new WxCpStore();;
	private WxCpConsumer wxCpConsumer;
	
	@Autowired
	private MessageHolder messageHolder;
	
	public WxCpFacade2(){
		wxCpMessageRouter = new WxCpMessageRouter(wxCpService);
		wxCpConsumer = new WxCpConsumer();
	}

	public WxCpFacade2(int consumerThreadCount, int thresholdJobCount){
		wxCpMessageRouter = new WxCpMessageRouter(wxCpService);
		wxCpConsumer = new WxCpConsumer(consumerThreadCount, thresholdJobCount);
	}

	@PostConstruct
	public void init() {
		afterSetProperties();
		
		wxCpService.setWxCpConfigStorage(wxCpConfigStorage);
		wxCpCryptUtil = new WxCpCryptUtil(wxCpConfigStorage);
		initHandler();//TODO 简单进行操作调用
		
		wxCpStore.start();
		wxCpConsumer.setWxCpStore(wxCpStore);
		wxCpConsumer.setWxCpMessageRouter(wxCpMessageRouter);
		wxCpConsumer.start();
	}
	
	private void afterSetProperties(){
		wxCpConfigStorage = new WxCpInMemoryConfigStorage();
		wxCpConfigStorage.setCorpId(properties.getProperty("wx.cp.corpId.corpId"));
		wxCpConfigStorage.setCorpSecret(properties.getProperty("wx.cp.corpId.corpSecret"));
		wxCpConfigStorage.setToken(properties.getProperty("wx.cp.corpId.token"));
		wxCpConfigStorage.setAesKey(properties.getProperty("wx.cp.corpId.aesKey"));
		wxCpConfigStorage.setExpiresTime(Long.valueOf(properties.getProperty("wx.cp.corpId.expiresTime")));
		wxCpConfigStorage.setOauth2redirectUri(properties.getProperty("wx.cp.corpId.oauth2redirectUri"));
		
		WxCpAgent.SIMPLE_MESSAGE = properties.getProperty("wx.cp.corpId.agentId.SIMPLE_MESSAGE");
		WxCpAgent.FREIGHT_MESSAGE = properties.getProperty("wx.cp.corpId.agentId.FREIGHT_MESSAGE");
	}

	@PreDestroy
	public void close() {
		wxCpConsumer.stop();
		wxCpStore.stop();
	}
	
	public void proccess(WxCpXmlMessage wxCpXmlMessage){
		wxCpStore.put(wxCpXmlMessage);
		messageHolder.hold(wxCpXmlMessage);
		if(LOG.isDebugEnabled()){
			LOG.info("Catch a message");
		}
	}
	
	public void proccess(HttpServletRequest request){
		String msgSignature = request.getParameter("msg_signature"); 
		String nonce = request.getParameter("nonce"); 
		String timestamp = request.getParameter("timestamp");
		try {
			WxCpXmlMessage wxCpMessage = WxCpXmlMessage.fromEncryptedXml(request.getInputStream(), this.wxCpConfigStorage, timestamp, nonce, msgSignature);
			this.proccess(wxCpMessage);
		} catch (IOException e) {
			LOG.info("Catch a ERROR: {}", e);
		}
	}
	
	public void proccess(WxCpMessage wxCpMessage){
		try {
			wxCpService.messageSend(wxCpMessage);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 回调认证
	 * @param msgSignature
	 * @param timestamp
	 * @param nonce
	 * @param data
	 * @return
	 */
	public boolean auth(String msgSignature, String timestamp, String nonce, String echostr){
		Assert.notNull(echostr);
		return wxCpService.checkSignature(msgSignature, timestamp, nonce, echostr);
	}
	
	/**
	 * 解密
	 * @param echostr
	 * @return
	 */
	public String decrypt(String echostr){
		Assert.notNull(echostr);
		return wxCpCryptUtil.decrypt(echostr);
	}

	
	/**
	 * 获取用户信息
	 * @param code
	 * @return
	 */
	public String[] oauth2(String code){
		Assert.notNull(code);
		try {
			return wxCpService.oauth2getUserInfo(code);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取队列中的消息
	 * @return
	 */
	public List<WxCpXmlMessage> getUnhandleMessages(){
		BlockingQueue<WxCpXmlMessage> queue = this.wxCpStore.getQueue();
		return Arrays.asList(queue.toArray(new WxCpXmlMessage[queue.size()]));
	}

	public WxCpConfigStorage getWxCpConfigStorage() {
		return wxCpConfigStorage;
	}

	public WxCpService getWxCpService() {
		return wxCpService;
	}

	public void setWxCpService(WxCpService wxCpService) {
		this.wxCpService = wxCpService;
	}

	public WxCpMessageRouter getWxCpMessageRouter() {
		return wxCpMessageRouter;
	}

	public void setWxCpMessageRouter(WxCpMessageRouter wxCpMessageRouter) {
		this.wxCpMessageRouter = wxCpMessageRouter;
	}

	public WxCpCryptUtil getWxCpCryptUtil() {
		return wxCpCryptUtil;
	}

	public void setWxCpCryptUtil(WxCpCryptUtil wxCpCryptUtil) {
		this.wxCpCryptUtil = wxCpCryptUtil;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	private void initHandler(){
		wxCpMessageRouter = new WxCpMessageRouter(wxCpService);
        wxCpMessageRouter
	      .rule()
	      .async(true)
	      .msgType(WX.Type.EVENT)
	      .event(WX.Event.CLICK)
	      .handler(new EventBpmTaskHandler())
	      .end()
	      
	      .rule()
	      .async(true)
	      .msgType(WX.Type.LOCATION)
	      .handler(new LocationHandler())
	      .end()
        
	      .rule()
	      .async(false)
	      .content("自动回复") // 拦截内容为“哈哈”的消息
	      .handler(new WxCpMessageHandler() {
		        @Override
		        public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context,
		            WxCpService wxCpService, WxSessionManager sessionManager) {
		          WxCpXmlOutTextMessage m = WxCpXmlOutMessage
		              .TEXT()
		              .content("这是服务器自动回复的信息")
		              .fromUser(wxMessage.getToUserName())
		              .toUser(wxMessage.getFromUserName())
		              .build();
		          return m;
		        }
		      })
	      .end()
	      
	      .rule()
	      .async(false)
	      .handler(new WxCpMessageHandler() {
		        @Override
		        public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context,
		            WxCpService wxCpService, WxSessionManager sessionManager) {
		          WxCpXmlOutTextMessage m = WxCpXmlOutMessage
		              .TEXT()
		              .content(String.format("系统已接收到消息，类型：%s, 事件: %s, 事件KEY: %s, 内容： %s。", 
		            		  wxMessage.getMsgType(), wxMessage.getEvent(), wxMessage.getEventKey(), wxMessage.getContent()))
		              .fromUser(wxMessage.getToUserName())
		              .toUser(wxMessage.getFromUserName())
		              .build();
		          return m;
		        }
		      })
	      .end();
        
        
        LOG.info("Weixin router init handlers complete");
        /**
		WxCpMessageHandler handler = new WxCpMessageHandler() {
	        @Override
	        public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context,
	            WxCpService wxCpService, WxSessionManager sessionManager) {
	          WxCpXmlOutTextMessage m = WxCpXmlOutMessage
	              .TEXT()
	              .content("测试加密消息")
	              .fromUser(wxMessage.getToUserName())
	              .toUser(wxMessage.getFromUserName())
	              .build();
	          return m;
	        }
	      };

	      WxCpMessageHandler oauth2handler = new WxCpMessageHandler() {
	        @Override
	        public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context,
	            WxCpService wxCpService, WxSessionManager sessionManager) {
	          String href = "<a href=\"" + wxCpService.oauth2buildAuthorizationUrl(wxCpConfigStorage.getOauth2redirectUri(), null) + "\">测试oauth2</a>";
	          return WxCpXmlOutMessage
	              .TEXT()
	              .content(href)
	              .fromUser(wxMessage.getToUserName())
	              .toUser(wxMessage.getFromUserName()).build();
	        }
	      };

	      wxCpMessageRouter = new WxCpMessageRouter(wxCpService);
	      wxCpMessageRouter
		      .rule()
		      .async(true)
		      .msgType(HandleType.EVENT)
		      .handler(new ProxyEventHandler())
		      .end();
	      
	      wxCpMessageRouter
	          .rule()
	          .async(false)
	          .content("哈哈") // 拦截内容为“哈哈”的消息
	          .handler(handler)
	          .end()
	          
	          .rule()
	          .async(false)
	          .content("oauth")
	          .handler(oauth2handler)
	          .end()
	      ;
	      **/
	}
}
