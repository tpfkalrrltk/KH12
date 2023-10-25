package com.kh.spring21;

import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kh.spring21.service.KakaoPayService;
import com.kh.spring21.vo.KakaoPayCancelRequestVO;
import com.kh.spring21.vo.KakaoPayCancelResponseVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class Test08Cancel {

	@Autowired
private	KakaoPayService kakaopayService;
	@Test
	public void test() throws URISyntaxException {
		KakaoPayCancelRequestVO request = KakaoPayCancelRequestVO.builder()
				.tid("T538a54351b66fd40aa4")
				.cancelAmount(100)
				.build();
		
		KakaoPayCancelResponseVO response =kakaopayService.cancel(request);
		log.debug("response= {}",response);
	}
}
