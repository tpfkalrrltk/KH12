package com.kh.spring21.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.Servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kh.spring21.configuration.KakaoPayProperties;
import com.kh.spring21.dao.ProductDao;
import com.kh.spring21.dto.ProductDto;
import com.kh.spring21.vo.KakaoPayApproveRequestVO;
import com.kh.spring21.vo.KakaoPayApproveResponseVO;
import com.kh.spring21.vo.KakaoPayCancelRequestVO;
import com.kh.spring21.vo.KakaoPayCancelResponseVO;
import com.kh.spring21.vo.KakaoPayDetailRequestVO;
import com.kh.spring21.vo.KakaoPayDetailResponseVO;
import com.kh.spring21.vo.KakaoPayReadyRequestVO;
import com.kh.spring21.vo.KakaoPayReadyResponseVO;
import com.kh.spring21.vo.PurchaseListVO;
import com.kh.spring21.vo.PurchaseVO;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service

public class KakaoPaySerivceImpl implements KakaoPayService {

	@Autowired
	private KakaoPayProperties kakaoPayProperties;
	@Autowired
	private RestTemplate template;
	@Autowired
	private HttpHeaders headers;
	@Autowired
	private ProductDao productDao;

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
		body.add("total_amount", String.valueOf(request.getItemPrice()));
		body.add("tax_free_amount", "0");

		// 현재 페이지 주소 계산
		String path = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();

		body.add("approval_url", path + "/success");
		body.add("cancel_url", path + "/cancel");
		body.add("fail_url", path + "/fail");

		// 요청 발송
		HttpEntity entity = new HttpEntity(body, headers);

		KakaoPayReadyResponseVO response = template.postForObject(uri, entity, KakaoPayReadyResponseVO.class);

		return response;
	}

	@Override
	public KakaoPayApproveResponseVO approve(KakaoPayApproveRequestVO request) throws URISyntaxException {

		URI uri = new URI("https://kapi.kakao.com/v1/payment/approve");

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("cid", kakaoPayProperties.getCid());
		body.add("tid", request.getTid());
		body.add("partner_order_id", request.getPartnerOrderId());
		body.add("partner_user_id", request.getPartnerUserId());
		body.add("pg_token", request.getPgToken());

		HttpEntity entity = new HttpEntity(body, headers);
		KakaoPayApproveResponseVO response = template.postForObject(uri, entity, KakaoPayApproveResponseVO.class);
		log.debug("결재 승인 완료 ={}", response.getTid());
		return response;
	}

	@Override
	public KakaoPayDetailResponseVO detail(KakaoPayDetailRequestVO request) throws URISyntaxException {
		URI uri = new URI("https://kapi.kakao.com/v1/payment/order");

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("cid", kakaoPayProperties.getCid());
		body.add("tid", request.getTid());

		HttpEntity entity = new HttpEntity(body, headers);

		KakaoPayDetailResponseVO response = template.postForObject(uri, entity, KakaoPayDetailResponseVO.class);
		return response;

	}

	@Override
	public KakaoPayCancelResponseVO cancel(KakaoPayCancelRequestVO request) throws URISyntaxException {
		URI uri = new URI("https://kapi.kakao.com/v1/payment/cancel");

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("cid", kakaoPayProperties.getCid());
		body.add("tid", request.getTid());
		body.add("cancel_amount", String.valueOf(request.getCancelAmount()));
		body.add("cancel_tax_free_amount", "0");

		HttpEntity entity = new HttpEntity(body, headers);
		KakaoPayCancelResponseVO response = template.postForObject(uri, entity, KakaoPayCancelResponseVO.class);
		return response;
	}

	@Override
	public KakaoPayReadyRequestVO convert(PurchaseListVO listVO) {
		//구매목록 추출
		List<PurchaseVO> list = listVO.getProduct();
		
		//결제 사용할 정보를 저장할 변수들
		String name = null;
		int total = 0;
		
		//구매목록을 모두 조사하여 상품정보를 추출 후 필요한 항목을 계산
		for(PurchaseVO vo : list) {
			//vo안에는  상품번호(productNo)와 구매수량(qty)가 있다.
			ProductDto dto = productDao.selectOne(vo.getProductNo());
			if(name==null) {//이름이 없을때에만 이름을 넣어라(최초 이름만 들어감)
				name = dto.getProductName();
			}
			total += dto.getProductPrice() * vo.getQty();//상품가격과 수량을 곱해서 합산하라!
		}
		
		//이름에 구매 수량이 2개 이상이라면 외 ?개를 추가
		if(list.size()>=2) {
			name +=" 외 "+(list.size()-1)+"건";
		}
		
		return KakaoPayReadyRequestVO.builder()
				.partnerOrderId(UUID.randomUUID().toString())
				.partnerUserId(name)
				.itemName(name)
				.itemPrice(total).build();
	}

}
