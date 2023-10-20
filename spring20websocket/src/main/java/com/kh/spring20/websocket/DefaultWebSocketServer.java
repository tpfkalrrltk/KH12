package com.kh.spring20.websocket;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 스프링에서 웹소켓 연결을 처리한느 도구(서버) - 상속을 통해 구현(WebSocketHandler /TextWebSocketHandler
 * /BinaryWebSocketHandler) - 등록하여 사용 afterConnectionEstablished는 통신이 연결된 이후
 * 실행되는 메소드 afterConnectionClosed는 통신이 종료된 이후 실행되는 메소드
 */

@Slf4j
@Service
public class DefaultWebSocketServer extends TextWebSocketHandler {

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.debug("사용자 접속!");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.debug("사용자 접속 종료!");
	}

}
