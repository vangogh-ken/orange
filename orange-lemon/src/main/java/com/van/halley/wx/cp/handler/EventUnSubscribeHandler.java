package com.van.halley.wx.cp.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.van.halley.db.persistence.entity.UserBase;
import com.van.service.UserBaseService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpMessageHandler;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;

@Configurable(preConstruction=true, dependencyCheck=true, autowire=Autowire.BY_TYPE)
public class EventUnSubscribeHandler implements WxCpMessageHandler{
	private static Logger LOG = LoggerFactory.getLogger(EventUnSubscribeHandler.class);
	
	@Autowired
	private UserBaseService userBaseService;
	
	@Override
	public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager) throws WxErrorException {
		// TODO Auto-generated method stub
		WxCpUser wxCpUser = wxCpService.userGet(wxMessage.getFromUserName());
		UserBase userBase = userBaseService.getByWeixinName(wxCpUser.getUserId());
		
		if(WX.Type.EVENT.equalsIgnoreCase(wxMessage.getMsgType()) 
				&& WX.Event.UNSUBSCRIBE.equalsIgnoreCase(wxMessage.getEvent())){
			if(userBase != null){
				wxCpService.userCreate(wxCpUser);
				wxCpService.invite(wxCpUser.getUserId(), "Welcome");
			}
		}else{
			LOG.error("错误路由到本处理器, msgType {}, event {}, eventKey {}", 
					wxMessage.getMsgType(),
					wxMessage.getEvent(), 
					wxMessage.getEventKey());
		}
		return null;
	}

}
