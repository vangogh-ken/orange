package com.van.halley.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.catalina.Context;
import org.apache.catalina.Manager;
import org.apache.catalina.Session;

/**
 * 用于持有一个Manager对象来管理所有sessions
 * @author ken
 *
 */
@Deprecated
public class SessionUtil {
	public static Context context = null;
	public static List<Session> getSessions(){
		List<Session> sessions = new ArrayList<Session>();
		if(context != null){
			Manager manager = context.getManager();
		    sessions.addAll(Arrays.asList(manager.findSessions()));
		}
		
	    return sessions;
	}
	
	/*
	 * 使用此代码
	 * RequestFacade request
	 * if(SessionUtil.context == null){
		Field requestField = request.getClass().getDeclaredField("request");
		requestField.setAccessible(true);
		Request req = (Request)requestField.get(request);
		SessionUtil.context = req.getContext();
		*/
}
