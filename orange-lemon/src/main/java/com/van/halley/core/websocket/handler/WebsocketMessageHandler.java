package com.van.halley.core.websocket.handler;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.van.halley.core.session.HttpSessionRegistry;
import com.van.halley.core.util.Constants;
import com.van.halley.db.persistence.entity.OutMsgInfo;
import com.van.halley.db.persistence.entity.User;
import com.van.service.OutMsgInfoService;

public class WebsocketMessageHandler extends TextWebSocketHandler{
protected Logger LOG = LoggerFactory.getLogger(WebsocketMessageHandler.class);
	//同步类型的集合
	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<String, WebSocketSession>();
	@Autowired
	private OutMsgInfoService outMsgInfoService;
	@Autowired
	private HttpSessionRegistry httpSessionRegistry;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		LOG.debug("connection builded...");
		
		User currentUser = (User) session.getAttributes().get(Constants.WEBSOCKET_USER);
		if(currentUser != null){
			synchronized (sessions) {
				sessions.put(currentUser.getId(), session);
			}
			
			Collection<HttpSession> list = httpSessionRegistry.getSessions();
			for(HttpSession item : list){
				//以5秒以内算为刚刚登陆，则推送消息
				if(item.getAttribute("userSession") != null &&
						((User)item.getAttribute("userSession")).getId().equals(currentUser.getId()) &&
						(item.getLastAccessedTime() - item.getCreationTime()) < 1000*5){
					
					OutMsgInfo filter = new OutMsgInfo();
					filter.setStatus("未读");
					filter.setReceiver(currentUser);
					TextMessage message = new TextMessage("你有 " + outMsgInfoService.queryForList(filter).size() + " 封未读信息");
					session.sendMessage(message);
				}
			}
		}
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		LOG.debug("Received: " + message);
	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		super.handleTransportError(session, exception);
		LOG.error("connection failed {}", exception);
		synchronized (sessions) {
			sessions.remove(session);
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		LOG.debug("connection is closed {}", status);
		synchronized (sessions) {
			sessions.remove(session);
		}
	}
	
	/**
	 * 发送消息给所有用户
	 * @param messageText
	 * @throws IOException
	 */
	public void sendMessageToUsers(String messageText){
		for(WebSocketSession session : sessions.values()){
			if(session.isOpen()){
				try {
					session.sendMessage(new TextMessage(messageText));
				} catch (IOException e) {
					LOG.error("connection failed {}", e);
				}
			}
		}
	}
	
	/**
	 * 发送消息给指定用户
	 * @param userId
	 * @param messageText
	 */
	public boolean sendMessageToUser(String userId, String messageText){
		boolean flag = false;
		WebSocketSession session = sessions.get(userId);
		if(session != null && session.isOpen()){
			try {
				session.sendMessage(new TextMessage(messageText));
				flag = true;
			} catch (IOException e) {
				LOG.error("connection failed {}", e);
			}
		}
		
		return flag;
	}
}
