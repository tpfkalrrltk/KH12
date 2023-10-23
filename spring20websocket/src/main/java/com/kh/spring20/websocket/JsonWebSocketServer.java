package com.kh.spring20.websocket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JsonWebSocketServer extends TextWebSocketHandler {
	private Set<WebSocketSession> clients = new CopyOnWriteArraySet<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		clients.add(session);
		log.debug("사용자 접속={}",clients.size());
		

		//	log.debug("session={}",session.getAttributes());
		
		//session의 추가정보(attributes)를 조사하여 HttpSession의 정보를 추출하여 사용
		Map<String,Object> attr =session.getAttributes();
		String memberId = (String) attr.get("name");
		String memberLevel = (String) attr.get("level");
		log.debug("아이디={}, 등급={}",memberId, memberLevel);

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		clients.remove(session);
		log.debug("사용자 종료={}",clients.size());
	}
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//기존 HTTP 세션의 정보를 조회
		Map<String,Object> attr =session.getAttributes();
		String memberId = (String) attr.get("name");
		String memberLevel = (String) attr.get("level");
		if(memberId ==null || memberLevel ==null) {//비회원이라면
			return;
		}

		//메세지 전송시 여러 정보를 JSON 문자열 형태로 변환하여 전송
		//ex) {"memberId" : "testuser1" , "memberLevel" : "VIP", "content" : "hello!"}
		
		//자바에서 JSON을 생성하는 방법은 여러 가지가 있다(Jackson,Gson,....)
		//- 스프링 부트에 기본 탑재된 jackson-databind의 도구를 사용하여 처리(ObjectMapper)
		
		//FE에게 보낼 메세지 객체 생성
		Map<String,Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("memberlevel", memberLevel);
		map.put("content", message.getPayload());
		//도구를 만들어 JSON으로 변환
		ObjectMapper mapper =new ObjectMapper();
		String str =mapper.writeValueAsString(map);
		//메세지를 생성하여 변환된 내용을 담아 모든 사용자에게 전송
		TextMessage tm = new TextMessage(str);
		for(WebSocketSession client : clients) {
			client.sendMessage(tm);
		}
	}
}
