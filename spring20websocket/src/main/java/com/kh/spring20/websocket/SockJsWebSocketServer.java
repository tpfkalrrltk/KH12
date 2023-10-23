package com.kh.spring20.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.spring20.vo.ClientVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j @Service
public class SockJsWebSocketServer extends TextWebSocketHandler {
	
	//저장소
	//private Set<WebSocketSession> clients = new CopyOnWriteArraySet<>();
	private Set<ClientVO> clients = new CopyOnWriteArraySet<>();//전체 회원
	private Set<ClientVO> members = new CopyOnWriteArraySet<>();//로그인한 회원
	
	//JSON 변환기
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		ClientVO client = new ClientVO(session);
		clients.add(client);
		
		if(client.isMember()) {
			members.add(client);
		}
		
		log.debug("사용자 접속! 현재 {}명", clients.size());
		log.debug("접속한 사용자 = {}", client);
		
		//모든 접속자에게 접속자 명단을 전송
		sendClientList();
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		ClientVO client = new ClientVO(session);
		clients.remove(client);
		
		if(client.isMember()) {
			members.remove(client);
		}
		
		log.debug("사용자 종료! 현재 {}명", clients.size());
		
		//모든 접속자에게 접속자 명단을 전송
		sendClientList();
	}
	
	//접속자 명단(clients)을 모든 접속자에게 전송하는 메소드
	public void sendClientList() throws IOException {
		//1. clients를 전송 가능한 형태(JSON 문자열)로 변환한다
		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> data = new HashMap<>();
		//data.put("clients", clients);//전체회원 명단(null이 문제가됨)
		data.put("clients", members);//로그인한 회원명단
		String clientJson = mapper.writeValueAsString(data);
		
		//2. 모든 사용자에게 전송
		TextMessage message = new TextMessage(clientJson);
		for(ClientVO client : clients) {
			client.send(message);
		}
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//사용자가 보낸 메세지를 모두에게 broadcast
		ClientVO client = new ClientVO(session);
		if(client.isMember() == false) return;
		
		//(+추가)사용자는 메세지를 JSON 형태로 보내므로 이를 해석해야 한다(ObjectMapper)
		Map params = mapper.readValue(message.getPayload(), Map.class);
		//log.debug("params = {}",params);
		log.debug("DM인가요 = {}",params.get("target") !=null);
		
		//DM일 경우와 아닐경우 구분하여 처리
		boolean isDM = params.get("target") !=null;
		if(isDM) {//DM일 경우
			//정보를 Map에 담아서 변환 후 전송
			Map<String, Object> map = new HashMap<>();
			map.put("dm",true);
			map.put("memberId", client.getMemberId());
			map.put("memberLevel", client.getMemberLevel());
			map.put("content",params.get("content"));
		
			//시간 추가 등
			String messageJson = mapper.writeValueAsString(map);
			TextMessage tm = new TextMessage(messageJson);
			
			for(ClientVO c : members) {
				if(c.getMemberId().equals(params.get("target"))) {//내가 찾던 사람이라면
					c.send(tm); //대상에세 메세지 전송
				}
			
			}
			//수신자에게 target 항목을 추가하여 다시 메세지 전송
			map.put("target", params.get("target"));
			messageJson = mapper.writeValueAsString(map);
			tm = new TextMessage(messageJson);
			
			client.send(tm);//작성자에게 메세지 전송
		}
		else {//전체 채팅일 경우
			
			//정보를 Map에 담아서 변환 후 전송
			Map<String, Object> map = new HashMap<>();
			map.put("memberId", client.getMemberId());
			map.put("memberLevel", client.getMemberLevel());
			map.put("content",params.get("content"));
			//시간 추가 등
			
		
			String messageJson = mapper.writeValueAsString(map);
			TextMessage tm = new TextMessage(messageJson);
			
			for(ClientVO c : clients) {
				c.send(tm);
			}
		}
		
		
	
		
		
	}
	
}