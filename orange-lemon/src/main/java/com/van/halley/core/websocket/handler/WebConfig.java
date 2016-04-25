package com.van.halley.core.websocket.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//暂未使用。现使用interceptor 和 handler进行处理
//@Configuration
//@EnableWebMvc
//@EnableWebSocket
public class WebConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketHandler(), "/websocket.do");
		registry.addHandler(webSocketHandler(), "/sockjs/websocket.do").withSockJS();
	}

	@Bean
	public WebSocketHandler webSocketHandler() {
		return new WebsocketMessageHandler();
	}

	//Allow serving HTML files through the default Servlet
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
