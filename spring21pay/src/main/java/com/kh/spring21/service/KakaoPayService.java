package com.kh.spring21.service;

import java.net.URISyntaxException;

import com.kh.spring21.vo.KakaoPayApproveRequestVO;
import com.kh.spring21.vo.KakaoPayApproveResponseVO;
import com.kh.spring21.vo.KakaoPayReadyRequestVO;
import com.kh.spring21.vo.KakaoPayReadyResponseVO;


public interface KakaoPayService {
	KakaoPayReadyResponseVO ready(KakaoPayReadyRequestVO request) throws URISyntaxException;
	KakaoPayApproveResponseVO approve(KakaoPayApproveRequestVO request) throws URISyntaxException;
//	KakaoPayDetailResponseVO detail(KakaoPayDetailResponseVO request) throws URISyntaxException;
//	KakaoPayCancelResponseVO detail(KakaoPayCancelResponseVO request) throws URISyntaxException;
}
