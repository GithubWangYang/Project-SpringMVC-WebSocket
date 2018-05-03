package com.wy.websocket;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * stomp websocket的子协议，stomp: simple/streaming text oriented message protocol. 简单/流 文本消息协议, 
 * 选择使用内存中级,还是使用activeMQ等中间件服务器
 * @author tomZ
 * @date 2016年11月3日
 * @desc TODO
 */
@Configuration
@EnableWebSocketMessageBroker
public class MyWebSocketMessageBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {

	/**
	 * 连接的端点，客户端建立连接时需要连接这里配置的端点
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		//为java stomp client提供链接
		registry.addEndpoint("/client")
		.setAllowedOrigins("*")
		.setHandshakeHandler(new MyHandshakeHandler())
		.addInterceptors(new MyHandshakeInterceptor());
		
		//为js客户端提供链接
		registry.addEndpoint("/hello")
		.setAllowedOrigins("*")
		.setHandshakeHandler(new MyHandshakeHandler())
		.addInterceptors(new MyHandshakeInterceptor())
		.withSockJS();
	}
	/**
	 * applicationDestinationPrefixes应用前缀，所有请求的消息将会路由到@MessageMapping的controller上，
	 * enableStompBrokerRelay是代理前缀，而返回的消息将会路由到代理上，所有订阅该代理的将收到响应的消息。
	 * 
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app");
		registry.setUserDestinationPrefix("/user");
		registry.enableSimpleBroker("/topic", "/queue")
//		registry.enableStompBrokerRelay("/topic", "/queue")
		//下面这配置为默认配置，如有变动修改配置启用就可以了
//		.setRelayHost("127.0.0.1") //activeMq服务器地址
//		.setRelayPort(61613)//activemq 服务器服务端口
//		.setClientLogin("guest")	//登陆账户
//		.setClientPasscode("guest") // 
		;
	}
	
	/**
	 * 消息传输参数配置
	 */
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//		super.configureWebSocketTransport(registration);
		registration.setMessageSizeLimit(8192).setSendBufferSizeLimit(8192).setSendTimeLimit(10000);
	}
	
	/**
	 * 输入通道参数设置
	 */
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
//		super.configureClientInboundChannel(registration);
		//线程信息
		registration.taskExecutor().corePoolSize(4).maxPoolSize(8).keepAliveSeconds(60);
	}
	/**
	 * 输出通道参数配置
	 */
	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
//		super.configureClientOutboundChannel(registration);
		//线程信息
		registration.taskExecutor().corePoolSize(4).maxPoolSize(8);
	}
	
	@Override
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
//		return super.configureMessageConverters(messageConverters);
		return true;
	}

}
