package com.kh.spring21.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.kh.spring21.configuration.KakaoPayProperties;
import com.kh.spring21.vo.KakaoPayReadyRequestVO;
import com.kh.spring21.vo.KakaoPayReadyResponseVO;


@Service
public class KakaoPaySerivceImpl implements KakaoPayService {

	@Autowired
	private KakaoPayProperties kakaoPayProperties;
	@Autowired
	private RestTemplate template;
	@Autowired
	private HttpHeaders headers;

	@Override
	public KakaoPayReadyResponseVO ready(KakaoPayReadyRequestVO request) throws URISyntaxException {

		// 주소 설정
		URI uri = new URI("https://kapi.kakao.com/v1/payment/ready");

		// 바디 설정
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

		// 요청 발송
		HttpEntity entity = new HttpEntity(body, headers);

		KakaoPayReadyResponseVO response = template.postForObject(uri, entity, KakaoPayReadyResponseVO.class);

		return response;
	}

}
