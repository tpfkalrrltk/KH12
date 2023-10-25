package com.kh.spring21.controller;

import java.net.URISyntaxException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring21.service.KakaoPayService;
import com.kh.spring21.vo.KakaoPayApproveRequestVO;
import com.kh.spring21.vo.KakaoPayApproveResponseVO;
import com.kh.spring21.vo.KakaoPayDetailRequestVO;
import com.kh.spring21.vo.KakaoPayDetailResponseVO;
import com.kh.spring21.vo.KakaoPayReadyRequestVO;
import com.kh.spring21.vo.KakaoPayReadyResponseVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/pay")
@Slf4j
public class KakaoPayController {
@Autowired
private KakaoPayService kakaoPayService;
	
	@GetMapping("/test1")
	public String test1() {
		//return "/WEB-INF/views/pay/test1.jsp"; 
		return "pay/test1";
	}
	@PostMapping("/test1")
	public String test1(@ModelAttribute KakaoPayReadyRequestVO request, HttpSession session) throws URISyntaxException {
		request.setPartnerOrderId(UUID.randomUUID().toString());
		KakaoPayReadyResponseVO response = kakaoPayService.ready(request);
		
		//session에 flash value를 저장(잠시 쓰고 지우는 데이터)
		// -사용자를 거치지 않는 범위 내에서 사용해야 안전하게 쓸 수 있다.
//		session.setAttribute("partnerOrderId", request.getPartnerOrderId());
//		session.setAttribute("partnerUserId", request.getPartnerUserId());
//		session.setAttribute("tid", response.getTid());
		session.setAttribute("approve", KakaoPayApproveRequestVO.builder()//객체방식
				.partnerOrderId(request.getPartnerOrderId())
				.partnerUserId(request.getPartnerUserId())
				.tid(response.getTid())
				.build());	 
		
		return "redirect:"+response.getNextRedirectPcUrl();
	}
	@GetMapping("/test1/success")
	public String test1Success(@RequestParam String pg_token, HttpSession session) throws URISyntaxException {
		
		//session에 저장되어있는 flash value를 꺼내어 pg_token을 추가한뒤 승인 요청
		KakaoPayApproveRequestVO request = (KakaoPayApproveRequestVO) session.getAttribute("approve");
		session.removeAttribute("approve");
	
		request.setPgToken(pg_token); //토큰추가
		
		//결재 승인 정보
		KakaoPayApproveResponseVO response = kakaoPayService.approve(request);
		return "redirect:successResult";
	}
	
	@GetMapping("/test1/successResult")
	public String successResult() {
		//return "/WEB-INF/views/pay/successResult.jsp";
		return "pay/successResult";
	}
	@GetMapping("/test1/detail")
	public String detail(Model model, @RequestParam String tid) throws URISyntaxException {
		KakaoPayDetailResponseVO response = 
				kakaoPayService.detail(KakaoPayDetailRequestVO.builder()
						.tid(tid)
						.build());
		model.addAttribute("vo",response);
		//return "/WEB-INF/views/pay/detail.jsp";
		return "pay/detail";
	}
}
