package com.van.halley.wx.cp.handler;

import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpMessageHandler;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;

/**
 * 
 * @author anxinxx
 *
 */
public abstract class EventDefaultHandler implements WxCpMessageHandler{
	protected String event;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	@Override
	public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager) throws WxErrorException {
		
		String event = wxMessage.getEvent().toUpperCase();
		switch(event){
			case WX.Event.SUBSCRIBE:
				onSubcribe(wxMessage, context, wxCpService, sessionManager);
			break;
			
			case WX.Event.UNSUBSCRIBE:
				onUnSubcribe(wxMessage, context, wxCpService, sessionManager);
			break;
			
			case WX.Event.LOCATION:
				onLocation(wxMessage, context, wxCpService, sessionManager);
			break;
			
		}
		
		return null;
		
	}
	
	public void onSubcribe(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager){
	}
	
	public void onUnSubcribe(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager){
	}
	
	public void onLocation(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager){
	}
	
	public void onClick(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager){
	}
	
	public void onView(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager){
	}
	
	public void onScanCodePush(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager){
	}
	
	public void onScanCodeWaitMsg(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager){
	}
	
	public void onPicSysPhoto(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager){
	}
	
	public void onPicPhotoOrAlbum(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager){
	}
	
	public void onPicWeiXin(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager){
	}
	
	public void onLocationSelect(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager){
	}
	
	public void onEnterAgent(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager){
	}
	
	public void onBatchJobResult(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager){
	}
}
