package com.van.halley.core.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.van.halley.db.persistence.entity.OutMsgInfo;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.wx.cp.core.WxCpAgent;
import com.van.halley.wx.cp.core.WxCpFacade;
import com.van.service.OutMsgInfoService;
import com.van.service.UserService;

import me.chanjar.weixin.cp.bean.WxCpMessage;

/**
 * 创建消息中心
 * @author ken
 *
 */
public abstract class MessageAdapter {
	private static Logger LOG = LoggerFactory.getLogger(MessageAdapter.class);
	@Autowired
	private UserService userService;
	@Autowired
	private OutMsgInfoService outMsgInfoService;
	@Autowired
	private WxCpFacade wxCpFacade;
	/**
	 * 推送站内消息
	 * @param senderUserId
	 * @param receiverUserId
	 * @param messgeText
	 */
	protected void processMessage(String senderUserId, String receiverUserId, String msgType, String messgeText){
		User sender = senderUserId == null ? userService.getByDisplayName("管理员") : userService.getById(senderUserId);
		User receiver = receiverUserId == null ? userService.getByDisplayName("管理员") : userService.getById(receiverUserId);
		processMessage(sender, receiver, msgType, messgeText);
		
		processWxMessage(receiverUserId, messgeText);
	}
	
	protected void processMessage(User sender, User receiver, String msgType, String messgeText){
		if(sender == null){
			sender = userService.getByDisplayName("管理员");
		}
		OutMsgInfo message = new OutMsgInfo();
		message.setMsgType(msgType);
		message.setSender(sender);
		message.setReceiver(receiver);
		message.setContent(messgeText);
		message.setStatus("未读");
		message.setHandled("F");
		outMsgInfoService.add(message);
		
		LOG.info("任务提醒消息发送成功。 发件人：{}，收件人：{}，内容：{}", sender.getDisplayName(), receiver.getDisplayName(), messgeText);
	}
	/**
	 * 推送短信
	 */
	protected void processSmsMessage(String senderUserId, String receiverUserId, String messgeText){
		//TODO
	}
	
	/**
	 * 推送微信
	 */
	protected void processWxMessage(String receiverUserId, String messgeText){
		User user = userService.getById(receiverUserId);
		processWxMessage(user, messgeText);
		/*if(user != null && user.getUserBase() != null && user.getUserBase().getWeixinName() != null){
			WxCpMessage wxCpMessage = WxCpMessage
					.TEXT()
					.agentId(WxCpAgent.DEFAULT)
		            .safe("0")
		            .toUser(user.getUserBase().getWeixinName())
		            .content(messgeText)
		            .build();
			wxCpFacade.proccess(wxCpMessage);
		}else{
			LOG.info("任务提醒微信推送失败。 收件人：{}，内容：{}", user.getDisplayName(), messgeText);
		}*/
	}
	
	protected void processWxMessage(User receiver, String messgeText){
		if(receiver != null && receiver.getUserBase() != null && receiver.getUserBase().getWeixinName() != null){
			WxCpMessage wxCpMessage = WxCpMessage
					.TEXT()
					.agentId(WxCpAgent.DEFAULT)
		            .safe("0")
		            .toUser(receiver.getUserBase().getWeixinName())
		            .content(messgeText)
		            .build();
			wxCpFacade.proccess(wxCpMessage);
		}else{
			LOG.info("任务提醒微信推送失败。 收件人：{}，内容：{}", receiver.getDisplayName(), messgeText);
		}
	}
	
	/**
	 * 推送邮件
	 */
	protected void processMail(String senderUserId, String receiverUserId, String messgeText){
		//TODO
	}
}
