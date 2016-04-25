package com.van.halley.wx.cp.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.van.halley.db.persistence.entity.UserBase;
import com.van.halley.wx.cp.handler.support.BpmTaskSupport;
import com.van.service.UserBaseService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpMessageHandler;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;

@Configurable(preConstruction=true, dependencyCheck=true, autowire=Autowire.BY_TYPE)
public class EventBpmTaskHandler implements WxCpMessageHandler{
	private static Logger LOG = LoggerFactory.getLogger(EventBpmTaskHandler.class);
	private static final String HANDLE_TASKS = "HANDLE_TASKS";
	private static final String CLAIM_TASKS = "CLAIM_TASKS";
	private static final String FINISHED_TASKS = "FINISHED_TASKS";
	
	@Autowired
	private UserBaseService userBaseService;
	@Autowired
	private BpmTaskSupport bpmTaskSupport;
	
	@Override
	public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService,
			WxSessionManager sessionManager) throws WxErrorException {
		WxCpUser wxCpUser = wxCpService.userGet(wxMessage.getFromUserName());
		UserBase userBase = userBaseService.getByWeixinName(wxCpUser.getUserId());
		if(userBase != null){
			LOG.info("sysUserId: {}, weixinUserId: {}", userBase.getUserId(), wxCpUser.getUserId());
			if(WX.Type.EVENT.equalsIgnoreCase(wxMessage.getMsgType()) 
					&& WX.Event.CLICK.equalsIgnoreCase(wxMessage.getEvent())){
				String eventKey = wxMessage.getEventKey();
				
				if(HANDLE_TASKS.equalsIgnoreCase(eventKey)){
					bpmTaskSupport.handleTasksQuery(userBase.getUserId(), wxCpUser.getUserId(), wxCpService);
				}else if(CLAIM_TASKS.equalsIgnoreCase(eventKey)){
					bpmTaskSupport.claimTasksQuery(userBase.getUserId(), wxCpUser.getUserId(), wxCpService);
				}else if(FINISHED_TASKS.equalsIgnoreCase(eventKey)){
					bpmTaskSupport.finishedTasksQuery(userBase.getUserId(), wxCpUser.getUserId(), wxCpService);
				}else{
					LOG.error("错误路由到本处理器, msgType {}, event {}, eventKey {}", 
							wxMessage.getMsgType(),
							wxMessage.getEvent(), 
							wxMessage.getEventKey());
				}
			}else{
				LOG.error("错误路由到本处理器, msgType {}, event {}, eventKey {}", 
						wxMessage.getMsgType(),
						wxMessage.getEvent(), 
						wxMessage.getEventKey());
			}
		}else{
			LOG.error("未予系统权限相关联, 微信账号 {}", wxCpUser.getUserId());
		}
		
		return null;
	}
}
