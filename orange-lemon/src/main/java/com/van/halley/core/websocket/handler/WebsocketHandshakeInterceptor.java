package com.van.halley.core.websocket.handler;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.van.halley.core.util.Constants;
import com.van.halley.db.persistence.entity.User;

public class WebsocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor{  
    @Override  
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, 
    		WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {  
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                attributes.put(Constants.WEBSOCKET_USER, ((User)session.getAttribute("userSession")));
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);//经过默认的拦截器
    }  
  
    @Override  
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, 
    		WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }  
}  
