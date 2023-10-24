package com.kh.spring21;

import java.net.URISyntaxException;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kh.spring21.service.KakaoPayService;

import com.kh.spring21.vo.KakaoPayReadyRequestVO;
import com.kh.spring21.vo.KakaoPayReadyResponseVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class Test05Ready {

	@Autowired
	KakaoPayService kakaoPayService;
	@Test
	public void test() throws URISyntaxException {
		KakaoPayReadyRequestVO request = KakaoPayReadyRequestVO.builder()
				.partnerOrderId(UUID.randomUUID().toString())
				.partnerUserId("testuser1")
				.itemName("27인치 평면 모니터")
				.itemPrice(30000)
				.build();
		KakaoPayReadyResponseVO response= kakaoPayService.ready(request);
		log.debug("response= {}",response);
		

	}
}
