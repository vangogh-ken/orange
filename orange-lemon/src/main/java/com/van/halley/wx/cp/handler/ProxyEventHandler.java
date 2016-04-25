package com.van.halley.wx.cp.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpMessageHandler;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;

public class ProxyEventHandler implements WxCpMessageHandler {
	private static Logger LOG = LoggerFactory.getLogger(ProxyEventHandler.class);
	private Map<String, EventDefaultHandler> handles = new HashMap<String, EventDefaultHandler>();
	
	public ProxyEventHandler(){
		//handles.put("EventBpmTaskHandler", new EventBpmTaskHandler());
	}
	
	@Override
	public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager) throws WxErrorException {
		String msgType = wxMessage.getMsgType();
		String event = wxMessage.getEvent().toUpperCase();
		if(!WX.Type.EVENT.equalsIgnoreCase(msgType)){
			LOG.error("not support handle type : {}", msgType);
			return null;
		}else{
			EventDefaultHandler eventHandler = handles.get(event);
			if(eventHandler != null){
				eventHandler.handle(wxMessage, context, wxCpService, sessionManager);
			}
		}
		return null;
	}
	
	/*public static class Event{
		//成员关注
		public static final String SUBSCRIBE = "subscribe";
		
		//取消关注事件
		public static final String UNSUBSCRIBE = "unsubscribe";
		
		//上报地理位置事件
		public static final String LOCATION = "LOCATION";
		
		//点击菜单拉取消息的事件推送
		public static final String CLICK = "click";
		
		//点击菜单跳转链接的事件推送
		public static final String VIEW = "VIEW";
		
		//扫码推事件的事件推送
		public static final String SCANCODE_PUSH = "scancode_push";
		
		//扫码推事件且弹出“消息接收中”提示框的事件推送
		public static final String SCANCODE_WAITMSG = "scancode_waitmsg";
		
		//弹出系统拍照发图的事件推送
		public static final String PIC_SYSPHOTO = "pic_sysphoto";
		
		//弹出拍照或者相册发图的事件推送
		public static final String PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
		
		//弹出微信相册发图器的事件推送
		public static final String PIC_WEIXIN = "pic_weixin";
		
		//弹出地理位置选择器的事件推送
		public static final String LOCATION_SELECT = "location_select";
		
		//成员进入应用的事件推送
		public static final String ENTER_AGENT = "enter_agent";
		
		//异步任务完成事件推送
		public static final String BATCH_JOB_RESULT = "batch_job_result";
	}
*/
}
