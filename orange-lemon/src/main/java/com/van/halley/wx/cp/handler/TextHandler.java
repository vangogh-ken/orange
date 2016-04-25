package com.van.halley.wx.cp.handler;

import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.van.halley.wx.cp.handler.support.BpmTaskSupport;
import com.van.service.UserBaseService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpMessageHandler;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;


public class TextHandler implements WxCpMessageHandler{
	@Autowired
	private MessageHolder messageHolder;
	@Autowired
	private UserBaseService userBaseService;
	@Autowired
	private BpmTaskSupport bpmTaskSupport;
	
	@Override
	public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager) throws WxErrorException {
		String userId = userBaseService.getByWeixinName(wxMessage.getFromUserName()).getUserId();
		WxCpXmlMessage actionMessage = messageHolder.getPreviousActionMessage(userId);
		if(actionMessage == null || actionMessage.getCreateTime() < wxMessage.getCreateTime()){
			//do not doing anything
		}else if(NumberUtils.isNumber(wxMessage.getContent())){
			String evenKey = actionMessage.getEventKey();
			if(WX.Event.KEY.CLAIM_TASKS.equals(evenKey)){
				bpmTaskSupport.claimTask(NumberUtils.toInt(wxMessage.getContent(), 0), userId);
			}else if(WX.Event.KEY.HANDLE_TASKS.equals(evenKey)){
				bpmTaskSupport.handleTask(NumberUtils.toInt(wxMessage.getContent(), 0), userId);
			}else if(WX.Event.KEY.FINISHED_TASKS.equals(evenKey)){
				bpmTaskSupport.finishedTask(NumberUtils.toInt(wxMessage.getContent(), 0), userId);
			}
		}
		return null;
	}

}
