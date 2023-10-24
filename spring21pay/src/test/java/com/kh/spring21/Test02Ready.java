package com.kh.spring21;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;

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
public class Test02Ready {

	@Test
	public  void test() throws URISyntaxException {
		//변하는 정보와 중요한 정보들을 분리하여 모듈로 개발
		
		//결재시마다 변하는 정보 -상품명, 상품가격,주문번호, 구매자ID
		String itemName ="슈퍼 울트라 노트북";
		int itemPrice = 150000;
		String partnerOrderId =UUID.randomUUID().toString();
		String partnerUserId = "testuser1";
		
		//전송 도구 생성
		RestTemplate template = new RestTemplate();
		
		//주소 설정
		URI uri = new URI("https://kapi.kakao.com/v1/payment/ready");
		
		//헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK 6e4d5ed8ab88dd8663f6235234d5a654");
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//바디 설정
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("cid", "TC0ONETIME");
		body.add("partner_order_id", partnerOrderId);
		body.add("partner_user_id", partnerUserId);
		body.add("item_name", itemName);
		body.add("quantity", "1");
		body.add("total_amount",String.valueOf(itemPrice));
		body.add("tax_free_amount", "0");
		body.add("approval_url", "http://localhost:8080/pay/success");
		body.add("cancel_url", "http://localhost:8080/pay/cancel");
		body.add("fail_url", "http://localhost:8080/pay/fail");
		
		//요청 발송
		//HttpEntity<MultiValueMap<String, String>> entity =new HttpEntity<>(headers, body);
		HttpEntity entity =new HttpEntity(body, headers);
		
		Map response=	template.postForObject(uri, entity, Map.class);
		log.debug("response ={}",response);
		log.debug("uri={}",response.get("next_redirect_pc_url"));
		
	}
}
