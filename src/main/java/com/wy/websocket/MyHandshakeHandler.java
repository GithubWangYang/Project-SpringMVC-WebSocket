package com.wy.websocket;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/**
 * stomp 处理器
 * @author tomZ
 * @date 2016年11月4日
 * @desc TODO
 */
public class MyHandshakeHandler extends DefaultHandshakeHandler {

	///该方法可以重写用来为用户 添加标识 返回principal
	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		// TODO Auto-generated method stub
		return super.determineUser(request, wsHandler, attributes);
	}
	
}
