package com.van.halley.wx.cp.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.van.halley.db.persistence.entity.UserBase;
import com.van.halley.wx.cp.core.WxCpAgent;
import com.van.service.UserBaseService;
import com.van.service.UserService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpMessageHandler;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;

@Configurable(preConstruction=true, dependencyCheck=true, autowire=Autowire.BY_TYPE)
public class EnterAgentHandler implements WxCpMessageHandler{
	private static Logger LOG = LoggerFactory.getLogger(EventBpmTaskHandler.class);
	
	@Autowired
	private UserBaseService userBaseService;
	@Autowired
	private UserService userService;

	@Override
	public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager) throws WxErrorException {
		WxCpUser wxCpUser = wxCpService.userGet(wxMessage.getFromUserName());
		UserBase userBase = userBaseService.getByWeixinName(wxCpUser.getUserId());
		StringBuilder text = new StringBuilder();
		text.append("欢迎您，" + userService.getById(userBase.getUserId()).getDisplayName() + "。请点击菜单进行操作！ ");
		WxCpMessage wxCpMessage = WxCpMessage.TEXT().agentId(WxCpAgent.DEFAULT).toUser(wxCpUser.getUserId()).content(text.toString()).build();
		try {
			wxCpService.messageSend(wxCpMessage);
		} catch (WxErrorException e) {
			LOG.error("待办任务发送失败： {}", e);
		}
		
		return null;
	}

}
