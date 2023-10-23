package com.kh.spring20.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.kh.spring20.websocket.DefaultWebSocketServer;
import com.kh.spring20.websocket.GroupWebSocketServer;
import com.kh.spring20.websocket.JsonWebSocketServer;
import com.kh.spring20.websocket.MemberWebSocketServer;
import com.kh.spring20.websocket.TimeWebSocketServer;

// 이 클래스는 생성한 웹소캣 서버를 어떤 주소에 할당하도록 설정하는 역할을 한다
@EnableWebSocket
@Configuration
public class WebSocketServerConfiguration implements WebSocketConfigurer {

	@Autowired
	private DefaultWebSocketServer defaultWebSocketServer;
	@Autowired
	private TimeWebSocketServer timeWebSocketServer;
	@Autowired
	private GroupWebSocketServer groupWebSocketServer;
	@Autowired
	private MemberWebSocketServer memberWebSocketServer ;
	@Autowired
	private JsonWebSocketServer jsonWebSocketServer;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
			//등록할때는 주소와 도구를 연결해야 한다(필요하다면 추가 옵션 설정)
			//(주의) 절대로 화면의 주소와 겹치면 안된다.
		registry.addHandler(defaultWebSocketServer, "/ws/default")
					.addHandler(timeWebSocketServer, "/ws/time")
					.addHandler(groupWebSocketServer, "/ws/group");
		
		//아래와 같이 등록하면 HttpSession의 정보를 WebSocketSession으로 옮겨준다
		registry.addHandler(memberWebSocketServer, "/ws/member")
					.addHandler(jsonWebSocketServer,"/ws/json")
					
					.addInterceptors(new HttpSessionHandshakeInterceptor());
	}

}
