package com.van.halley.wx.cp.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.van.service.UserBaseService;

import me.chanjar.weixin.cp.bean.WxCpXmlMessage;

/**
 * Hold last 20 messages from user, that used to as context to feed back to.
 * @author anxinxx
 *
 */
@Component
public class MessageHolder {
	private static final int defalutHoldCount = 5;
	
	private final ConcurrentMap<String, Map<String, List<WxCpXmlMessage>>> messages = new ConcurrentHashMap<String, Map<String, List<WxCpXmlMessage>>>();
	
	@Autowired
	private UserBaseService userBaseService;
	
	public List<WxCpXmlMessage> getEventMessageByPrincipal(String userId){
		return messages.get(userId).get(WX.Type.EVENT);
	}
	
	public List<WxCpXmlMessage> getTextMessageByPrincipal(String userId){
		return messages.get(userId).get(WX.Type.TEXT);
	}
	
	public WxCpXmlMessage getPrevious(String userId, String handleType){
		List<WxCpXmlMessage> list = messages.get(userId).get(handleType);
		if(list != null && !list.isEmpty()){
			return list.get(list.size() - 1);
		}else{
			return null;
		}
	}
	
	/**
	 * get the last event key for relate the context to
	 * @param userId
	 * @param eventType
	 * @return
	 */
	public String getPreviousAction(String userId){
		List<WxCpXmlMessage> list = getEventMessageByPrincipal(userId);
		for(WxCpXmlMessage message : list){
			if(WX.Event.CLICK.equals(message.getEvent())){
				return message.getEventKey();
			}
		}
		return null;
	}
	
	public WxCpXmlMessage getPreviousActionMessage(String userId){
		List<WxCpXmlMessage> list = getEventMessageByPrincipal(userId);
		for(WxCpXmlMessage message : list){
			if(WX.Event.CLICK.equals(message.getEvent())){
				return message;
			}
		}
		return null;
	}
	
	/**
	 * get the last event key for relate the context to
	 * @param userId
	 * @return
	 */
	public String getLastText(String userId){
		List<WxCpXmlMessage> list = messages.get(userId).get(WX.Type.TEXT);
		if(list != null && !list.isEmpty()){
			for(int i=list.size(); i>0; i-- ){
				WxCpXmlMessage item = list.get(i - 1);
				
				if(userId.equals(item.getFromUserName()) 
						&& "TEXT".equalsIgnoreCase(item.getMsgType())){
					return item.getContent();
				}
			}
		}
		return null;
	}
	
	
	
	/**
	 * hold the message in the concurrentMap
	 * @param message
	 */
	public void hold(WxCpXmlMessage message){
		String userId = userBaseService.getByWeixinName(message.getFromUserName()).getUserId();
		Map<String, List<WxCpXmlMessage>> map = messages.get(userId);
		if(map == null){
			map = new HashMap<String, List<WxCpXmlMessage>>();
			messages.put(userId, map);
		}
		
		List<WxCpXmlMessage> list = map.get(message.getMsgType());
		if(list == null){
			list = new ArrayList<WxCpXmlMessage>();
			list.add(message);
			map.put(message.getMsgType(), list);
		}else{
			if(list.size() == defalutHoldCount){
				synchronized (list) {
					list.remove(0);
					list.add(message);
					map.put(message.getMsgType(), list);
				}
			}
		}
	}
}
