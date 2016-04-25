package com.van.halley.wx.cp.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.van.halley.db.persistence.entity.User;
import com.van.halley.db.persistence.entity.UserBase;
import com.van.service.UserBaseService;
import com.van.service.UserService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpMessageHandler;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;

@Configurable(preConstruction=true, dependencyCheck=true, autowire=Autowire.BY_TYPE)
public class LocationHandler implements WxCpMessageHandler{
	private static Logger LOG = LoggerFactory.getLogger(LocationHandler.class);
	
	@Autowired
	private UserBaseService userBaseService;
	@Autowired
	private UserService userService;
	
	@Override
	public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager) throws WxErrorException {
		if(WX.Type.EVENT.equalsIgnoreCase(wxMessage.getMsgType())
				&& WX.Event.LOCATION.equals(wxMessage.getEvent())) {
			WxCpUser wxCpUser = wxCpService.userGet(wxMessage.getFromUserName());
			UserBase userBase = userBaseService.getByWeixinName(wxCpUser.getUserId());
			User user = userService.getById(userBase.getUserId());
			
			LOG.info("用户 {} 当前所在位置: 经度  {}, 纬度  {}, 精度  {}", 
					user.getDisplayName(), wxMessage.getLongitude(), wxMessage.getLatitude(), wxMessage.getPrecision());
		}
		return null;
	}

}
