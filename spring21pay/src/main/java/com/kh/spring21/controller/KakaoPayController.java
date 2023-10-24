package com.kh.spring21.controller;

import java.net.URISyntaxException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring21.service.KakaoPayService;
import com.kh.spring21.vo.KakaoPayReadyRequestVO;
import com.kh.spring21.vo.KakaoPayReadyResponseVO;

@Controller
@RequestMapping("/pay")
public class KakaoPayController {
@Autowired
private KakaoPayService kakaoPayService;
	
	@GetMapping("/test1")
	public String test1() {
		//return "/WEB-INF/views/pay/test1.jsp";
		return "pay/test1";
	}
	@PostMapping("/test1")
	public String test1(@ModelAttribute KakaoPayReadyRequestVO request) throws URISyntaxException {
		request.setPartnerOrderId(UUID.randomUUID().toString());
		KakaoPayReadyResponseVO response = kakaoPayService.ready(request);
		return "redirect:"+response.getNextRedirectPcUrl();
	}
//	@GetMapping("/test1/success")
//	public String test1Success(@RequestParam String pg_token) {
//		
//	}
}
