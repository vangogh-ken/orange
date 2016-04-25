package com.van.halley.core.session;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class HttpSessionRegistry {
	private final ConcurrentMap<String, HttpSession> sessions = new ConcurrentHashMap<String,HttpSession>();
	
	public void cancel(HttpSession session){
		sessions.remove(session.getId());
	}
	
	public void regist(HttpSession session){
		sessions.put(session.getId(), session);
	}
	
	public Collection<HttpSession> getSessions(){
		return sessions.values();
	}
}
