package com.kh.spring20.websocket;


import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TimeWebSocketServer extends TextWebSocketHandler{
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.debug("사용자 접속={}",session);
		
		//접속한 사용자에게 현재시각을 전달
		TextMessage message = new TextMessage(LocalDateTime.now().toString());
		session.sendMessage(message);
	}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.debug("사용자 종료={}",session);
		log.debug("타입웹소켓 종료={}",status);
	}


}
