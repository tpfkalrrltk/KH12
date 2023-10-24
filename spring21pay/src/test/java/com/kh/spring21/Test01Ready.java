package com.kh.spring21;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class Test01Ready {

	@Test
	public  void test() throws URISyntaxException {
		//웹서버에서 PG사(제3의 서버)로 요청 보내기 위해선 다음 둘 중 하나가 필요
		//[1] RestTemplate
		//[2] WebClient
		
		//전송 도구 생성
		RestTemplate template = new RestTemplate();
		
		//주소 설정
		URI uri = new URI("요청을 보내고 싶은 주소");
		
		//헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.add("key", "value");
		
		//바디 설정
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("key", "value");
		
		//요청 발송
		//HttpEntity<MultiValueMap<String, String>> entity =new HttpEntity<>(headers, body);
		HttpEntity entity =new HttpEntity<>(headers, body);
		
	
		
	}
}
