package com.kh.spring20.websocket;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SockJsWebSocketServer extends TextWebSocketHandler {

	// 저장소
	private Set<WebSocketSession> clients = new CopyOnWriteArraySet<>();

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		clients.add(session);
		log.debug("사용자 접속! 현재 {}명", clients.size());
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		clients.remove(session);
		log.debug("사용자 종료! 현재 {}명", clients.size());
	}

}
