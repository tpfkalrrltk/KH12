package com.kh.spring20.websocket;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GroupWebSocketServer extends TextWebSocketHandler {
	// 사용자를 저장할 수 있는 저장소
	// private Set<WebSocketSession> clients= new HashSet<>(); //동기화 처리가 안되어있음
	private Set<WebSocketSession> clients = new CopyOnWriteArraySet<>(); // 동기화 처리됨
	// private Set<WebSocketSession> clients = Collections.synchronizedSet(new
	// HashSet<>());//동기화 처리됨

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// 사용자를 저장소에 추가하는 코드
		clients.add(session);
		log.debug("사용자 접속! 현재={}명", clients.size());

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 사용자를 저장소에 제거하는 코드
		clients.remove(session);
		log.debug("사용자 접속 종료! 현재={}명", clients.size());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 사용자가 보낸 메세지를 처리하는 메소드
		// -접속한 모든 사용자에게 메세지를 전달(브로드캐스트, broadcast)

		for (WebSocketSession client : clients) {
			client.sendMessage(message);
		}
	}
}
