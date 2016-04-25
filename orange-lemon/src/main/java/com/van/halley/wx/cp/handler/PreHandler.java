package com.van.halley.wx.cp.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpMessageHandler;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;

/**
 * prepare handler for handle all messages
 * @author anxinxx
 *
 */
@Configurable(preConstruction=true, dependencyCheck=true, autowire=Autowire.BY_TYPE)
public class PreHandler implements WxCpMessageHandler{
	@Autowired
	private MessageHolder messageHolder;
	@Override
	public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager) throws WxErrorException {
		messageHolder.hold(wxMessage);;
		return null;
	}

}
