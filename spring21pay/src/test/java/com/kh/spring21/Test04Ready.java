package com.kh.spring21;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.kh.spring21.configuration.KakaoPayProperties;
import com.kh.spring21.vo.KakaoPayReadyRequestVO;
import com.kh.spring21.vo.KakaoPayReadyResponseVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class Test04Ready {
	
	@Autowired
	private KakaoPayProperties kakaoPayProperties ;
	
	@Test
	public void test() throws URISyntaxException {
		//변하는 정보와 중요한 정보들을 분리하여 모듈로 개발
		
		//결제 준비 요청 정보를 클래스로 모듈화 (KakaoPayReadyRequestVO)
		KakaoPayReadyRequestVO request = KakaoPayReadyRequestVO.builder()
					.partnerOrderId(UUID.randomUUID().toString())
					.partnerUserId("testuser1")
					.itemName("초코파이 1box")
					.itemPrice(3500)
				.build();
				
		//전송 도구 생성
		RestTemplate template = new RestTemplate();
		
		//(+추가) SNAKE_CASE를 CAMEL_CASE로 처리하도록 추가 도구를 설정
		//ObjectMapper mapper = new ObjectMapper();
		//mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		
		//MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		//converter.setObjectMapper(mapper);
		
		//template.getMessageConverters().add(0, converter);
		
		
		//주소 설정
		URI uri = new URI("https://kapi.kakao.com/v1/payment/ready");
		
		//헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK "+kakaoPayProperties.getKey());
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//바디 설정
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("cid", kakaoPayProperties.getCid());
		body.add("partner_order_id", request.getPartnerOrderId());
		body.add("partner_user_id", request.getPartnerUserId());
		body.add("item_name", request.getItemName());
		body.add("quantity", String.valueOf(request.getItemPrice()));
		body.add("total_amount", "3000");
		body.add("tax_free_amount", "0");
		body.add("approval_url", "http://localhost:8080/pay/success");
		body.add("cancel_url", "http://localhost:8080/pay/cancel");
		body.add("fail_url", "http://localhost:8080/pay/fail");
		
		//요청 발송
		//HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
		HttpEntity entity = new HttpEntity(body, headers);
		
		//Map response =	template.postForObject(uri, entity, Map.class);
		KakaoPayReadyResponseVO response = 
				template.postForObject(uri, entity, KakaoPayReadyResponseVO.class);

		log.debug("response ={}",response);
		log.debug("uri={}",response.getNextRedirectPcUrl());
		
		//다음 테스트를 위해 필요한 값을 출력
		log.debug("결제 페이지={}",response.getNextRedirectPcUrl());
		log.debug("tid={}",response.getTid());
		log.debug("partner_order_id={}",request.getPartnerOrderId());
		log.debug("partner_user_id={}",request.getPartnerUserId());
	}
	
}