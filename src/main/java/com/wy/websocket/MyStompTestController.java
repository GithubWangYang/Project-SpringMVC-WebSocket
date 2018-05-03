package com.wy.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

/**
 * 
 * @author tom
 * @version 2016-06-12
 */
@Controller
public class MyStompTestController {
	/**
	 * @MessageMapping 是app路由地址，客户端请求将交由其处理，@SendTo是返回消息路由到指定地址，订阅该地址的将接收到消息
	 * @param incoming
	 * @return
	 */
	@MessageMapping("/hi")
	@SendTo("/topic/hi")
	public String handleHi(String incoming) {
		System.out.println("receive message : " + incoming);
		return "hello, " + incoming; 
	}
	/**
	 * 订阅，当有客户端订阅该内容，会有一次性响应
	 * @return
	 */
	@SubscribeMapping("/subscribeme")
	public String subscribeThing() {
		System.out.println("subscribe message called.");
		return "thank you subscribe my channel";
	}
	
}