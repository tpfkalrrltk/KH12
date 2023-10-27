package com.kh.spring21.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
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

import com.kh.spring21.dao.PaymentDao;
import com.kh.spring21.dao.ProductDao;
import com.kh.spring21.dto.PaymentDetailDto;
import com.kh.spring21.dto.PaymentDto;
import com.kh.spring21.dto.ProductDto;
import com.kh.spring21.error.NoTargetException;
import com.kh.spring21.service.KakaoPayService;
import com.kh.spring21.vo.KakaoPayApproveRequestVO;
import com.kh.spring21.vo.KakaoPayApproveResponseVO;
import com.kh.spring21.vo.KakaoPayCancelRequestVO;
import com.kh.spring21.vo.KakaoPayCancelResponseVO;
import com.kh.spring21.vo.KakaoPayDetailRequestVO;
import com.kh.spring21.vo.KakaoPayDetailResponseVO;
import com.kh.spring21.vo.KakaoPayReadyRequestVO;
import com.kh.spring21.vo.KakaoPayReadyResponseVO;
import com.kh.spring21.vo.PurchaseConfirmVO;
import com.kh.spring21.vo.PurchaseListVO;
import com.kh.spring21.vo.PurchaseVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/pay")

public class KakaoPayController {
	@Autowired
	private  KakaoPayService kakaoPayService;
	@Autowired
	private PaymentDao paymentDao;

	@GetMapping("/test1")
	public String test1() {
		// return "/WEB-INF/views/pay/test1.jsp";
		return "pay/test1";
	}

	@PostMapping("/test1")
	public String test1(@ModelAttribute KakaoPayReadyRequestVO request, HttpSession session) throws URISyntaxException {
		request.setPartnerOrderId(UUID.randomUUID().toString());
		KakaoPayReadyResponseVO response = kakaoPayService.ready(request);

		// session에 flash value를 저장(잠시 쓰고 지우는 데이터)
		// -사용자를 거치지 않는 범위 내에서 사용해야 안전하게 쓸 수 있다.
//		session.setAttribute("partnerOrderId", request.getPartnerOrderId());
//		session.setAttribute("partnerUserId", request.getPartnerUserId());
//		session.setAttribute("tid", response.getTid());
		session.setAttribute("approve", KakaoPayApproveRequestVO.builder()// 객체방식
				.partnerOrderId(request.getPartnerOrderId()).partnerUserId(request.getPartnerUserId())
				.tid(response.getTid()).build());

		return "redirect:" + response.getNextRedirectPcUrl();
	}

	@GetMapping("/test1/success")
	public String test1Success(@RequestParam String pg_token, HttpSession session) throws URISyntaxException {

		// session에 저장되어있는 flash value를 꺼내어 pg_token을 추가한뒤 승인 요청
		KakaoPayApproveRequestVO request = (KakaoPayApproveRequestVO) session.getAttribute("approve");
		session.removeAttribute("approve");

		request.setPgToken(pg_token); // 토큰추가

		// 결재 승인 정보
		KakaoPayApproveResponseVO response = kakaoPayService.approve(request);
		return "redirect:successResult";
	}

	@GetMapping("/test1/successResult")
	public String successResult() {
		// return "/WEB-INF/views/pay/successResult.jsp";
		return "pay/successResult";
	}

	@GetMapping("/test1/detail")
	public String detail(Model model, @RequestParam String tid) throws URISyntaxException {
		KakaoPayDetailResponseVO response = kakaoPayService.detail(KakaoPayDetailRequestVO.builder().tid(tid).build());
		model.addAttribute("vo", response);
		// return "/WEB-INF/views/pay/detail.jsp";
		return "pay/detail";
	}

	@RequestMapping("/test1/cancel")
	public String cancel(@ModelAttribute KakaoPayCancelRequestVO request) throws URISyntaxException {
		KakaoPayCancelResponseVO response =kakaoPayService.cancel(request);
		return "redirect:detail?tid"+request.getTid();
	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Autowired
	private ProductDao productDao;
	
	@RequestMapping("test2")
	public String test2(Model model) {
		model.addAttribute("list",productDao.selectList());
	//	return "WEB-INF/views/pay2/home.jsp";
		return "pay2/home";
	}
	@GetMapping("/test2/purchase")
	public String purchase(@RequestParam int productNo, HttpSession session) throws URISyntaxException {
		//상품정보 조회
		ProductDto productDto = productDao.selectOne(productNo);
		//상품정보를 이용하여 결제준비요청
		KakaoPayReadyRequestVO request =	KakaoPayReadyRequestVO.builder()
				.itemName(productDto.getProductName())
				.itemPrice(productDto.getProductPrice())
				.partnerOrderId(UUID.randomUUID().toString())
				.partnerUserId("testuser1")
				.build();

		KakaoPayReadyResponseVO response =
				kakaoPayService.ready(request);
					
		// session에 flash value를 저장(잠시 쓰고 지우는 데이터)
		// -사용자를 거치지 않는 범위 내에서 사용해야 안전하게 쓸 수 있다.
		session.setAttribute("approve", KakaoPayApproveRequestVO.builder()// 객체방식
				.partnerOrderId(request.getPartnerOrderId()).partnerUserId(request.getPartnerUserId())
				.tid(response.getTid()).build());
		session.setAttribute("productNo", productNo);
		
		//결제페이지를 사용자에게 안내
		return "redirect:"+response.getNextRedirectPcUrl();
	}
	
	//결제성공
	@GetMapping("/test2/purchase/success")
	public String test2Success(HttpSession session, @RequestParam String pg_token) throws URISyntaxException
	{
		// session에 저장되어있는 flash value를 꺼내어 pg_token을 추가한뒤 승인 요청
		KakaoPayApproveRequestVO request = (KakaoPayApproveRequestVO) session.getAttribute("approve");
		
		int productNo =(int) session.getAttribute("productNo");
		session.removeAttribute("approve");
		session.removeAttribute("productNo");

		request.setPgToken(pg_token); // 토큰추가

		// 결재 승인 요청
		KakaoPayApproveResponseVO response = kakaoPayService.approve(request);
		
		// 결제 승인이 완료되었다면 DB에 결제 정보를 저장
		int paymentNo = paymentDao.sequence();
		paymentDao.insert(PaymentDto.builder()
				.paymentNo(paymentNo)
				.paymentMember(response.getPartnerUserId())
			//	.paymentProduct(productNo)
				.paymentTid(response.getTid())
				.paymentName(response.getItemName())
				.paymentPrice(response.getAmount().getTotal())
				.paymentRemain(response.getAmount().getTotal())
				.build());
				
		return "redirect:successResult";
	}	
	@RequestMapping("/test2/purchase/successResult")
	public String test2SuccessResult() {
		return "pay2/successResult";
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping("/test3")
	public String test3(Model model) {
		model.addAttribute("list",productDao.selectList());
		return "pay3/home";
	}
	//결제 확인화면
	@GetMapping("/test3/purchase")
	public String  test3Purchase(@ModelAttribute PurchaseListVO listVO, Model model) {
		List<PurchaseVO> purchaseList =listVO.getProduct();
		List<PurchaseConfirmVO> confirmList = new ArrayList<>();//옮겨담을 리스트
		int total = 0;
		for(PurchaseVO vo: purchaseList)//사용자가 선택한 번호와 수량의 목록을 반복하며
			{
			ProductDto productDto = productDao.selectOne(vo.getProductNo());//상품정보를 구한다.
	
				
			//vo와 dto를 신규객체로 만들어 추가
			PurchaseConfirmVO confirmVO =PurchaseConfirmVO.builder()
				.purchaseVO(vo).productDto(productDto)
				.build();
			
			confirmList.add(confirmVO);//화면에 찍어줄 데이터 추가
			total+=confirmVO.getTotal();//총 구매금액 합산
			}
		model.addAttribute("list",confirmList);
		model.addAttribute("total",total);
		return"pay3/purchase";
		
	}
	
	//결제 처리
	@PostMapping("/test3/purchase")
	public String test3Purchase(@ModelAttribute PurchaseListVO listVO,HttpSession session) throws URISyntaxException {

		
		//listVO에 들어있는 product 항목들을 이용해서 결제 준비 요청 처리 후 결제 페이지로 안내
		// -결제이름은 대표 상품명 외 ?개 와  같이 작성
		// -결제금액은 모든 상품의 가격과 수량의 총합계
		// -결론적으로 만들어야 하는 데이터는 KakaoPayReadyRequestVO
		KakaoPayReadyRequestVO request = kakaoPayService.convert(listVO);
		
		String memberId = (String) session.getAttribute("name");
		request.setPartnerUserId(memberId);
		
		KakaoPayReadyResponseVO response = kakaoPayService.ready(request);
		
		// session에 flash value를 저장(잠시 쓰고 지우는 데이터)
		// -사용자를 거치지 않는 범위 내에서 사용해야 안전하게 쓸 수 있다.
		session.setAttribute("approve", KakaoPayApproveRequestVO.builder()// 객체방식
				.partnerOrderId(request.getPartnerOrderId()).partnerUserId(request.getPartnerUserId())
				.tid(response.getTid()).build());//승인 요청을 위한 준비데이터 ->카카오페이
		session.setAttribute("listVO", listVO);//구매한 상품의 번호와 수량목록 -->우리 DB
		
		
		return "redirect:"+response.getNextRedirectPcUrl();
	}
	@GetMapping("/test3/purchase/success")
	public String test3Success(@RequestParam String pg_token, HttpSession session) throws URISyntaxException {
		//session에  저장한 flash value 추출 및 삭제
		KakaoPayApproveRequestVO request = (KakaoPayApproveRequestVO) session.getAttribute("approve");
		PurchaseListVO listVO = (PurchaseListVO) session.getAttribute("listVO");
		
		session.removeAttribute("approve");
		session.removeAttribute("listVO");
		
		request.setPgToken(pg_token);//토큰 설정
		KakaoPayApproveResponseVO response =kakaoPayService.approve(request);//승인 요청
		
		//DB 작업
		// - 상품을 3개 구매했다면 payment 1회, payment_detail 3회의 insert가 필요(N+1)
		
		//[1]결재 번호 생성
		//int paymentNo =paymentDao.sequence();
		int paymentNo=Integer.parseInt(response.getPartnerOrderId());
		log.debug("그래서 이게 뭔데={}",paymentNo);
		//[2]결재 정보 등록
		paymentDao.insert(PaymentDto.builder()
				.paymentNo(paymentNo)//결재 고유 번호
				.paymentMember(response.getPartnerUserId())//결재자 ID
				.paymentTid(response.getTid())//PG사 거래번호
				.paymentName(response.getItemName())//PG사 결재 상품명
				.paymentPrice(response.getAmount().getTotal())//총 결재액
				.paymentRemain(response.getAmount().getTotal())//총 취소가능금액
				.build());
		//[3] 상품 개수만큼 결재 상세정보를 등록
		List<PurchaseVO> list = listVO.getProduct();
		for(PurchaseVO vo : list) {
			ProductDto productDto = productDao.selectOne(vo.getProductNo());//상품정보 조회
			
			paymentDao.insertDetail(PaymentDetailDto.builder()
					.paymentDetailOrigin(paymentNo)//상위 결재 번호
					.paymentDetailProduct(vo.getProductNo())//상품 번호(vo, productDto)
					.paymentDetailProductName(productDto.getProductName())//상품명(productDto)
					.paymentDetailProductPrice(productDto.getProductPrice())//상품 가격(productDto)
					.paymentDetailProductQty(vo.getQty())//구매수량(vo)
					.build());
		}
		return "redirect:successResult";
	}
	
	@RequestMapping("/test3/purchase/successResult")
	public String test3SuccessResult() {
		return "pay3/successResult";
	}
	
	@RequestMapping("/test3/list")
	public String test3list(Model model) {
		model.addAttribute("list",paymentDao.selectList());
		return "pay3/list";
	}
	
	@RequestMapping("/test3/list2")
	public String test3list2(Model model, HttpSession session) {
		String memberId = (String) session.getAttribute("name");
		//model.addAttribute("list",paymentDao.selectTotalList()); //전체내역
		model.addAttribute("list",paymentDao.selectTotalListByMember(memberId));//나의 내역
		return "/pay3/list2";
	}
	//[1]결제 상세 번호로 PaymentDetailDto를 조회
	//[2] 1번에서 조회한 PaymentDetailDto의 정보로 PaymentDto를 조회
	//[3] 1번에서 취소 금액을 알 수 있다. 2번에서는 거래번호(tid)를 알 수 있다.
	//[4] 3번의 정보로 카카오페이 취소 요청을 보낸다.
	//[5] DB의 정보를 업데이트
	// - 현재항목에 대한 상태를 취소로 변경해야한다(payment_detail)
	// - 결재 대표 정보의 잔여금액을 차감해야한다(payment)
	@RequestMapping("/test3/cancel")
	public String test3Cancel(@RequestParam int paymentDetailNo) throws URISyntaxException {
		PaymentDetailDto paymentDetailDto = paymentDao.selectDetail(paymentDetailNo);//[1]
		//if(paymentDetailDto.getPaymentDetailStatus().equals("취소")) {
		if(paymentDetailDto.isCanceled() || paymentDetailDto == null) {
		throw new NoTargetException();
		}
		
		PaymentDto paymentDto = paymentDao.selectOne(paymentDetailDto.getPaymentDetailOrigin());//[2]
		
		
		//3
		KakaoPayCancelRequestVO request =KakaoPayCancelRequestVO.builder()
				.tid(paymentDto.getPaymentTid())
				.cancelAmount(paymentDetailDto.getPaymentDetailProductPrice() *//상품 판매가
						paymentDetailDto.getPaymentDetailProductQty())//구매수량
				.build();
		
		//4
		KakaoPayCancelResponseVO response =kakaoPayService.cancel(request);
		
		//5
		paymentDao.cancelDetail(paymentDetailNo);
		paymentDao.cancel(PaymentDto.builder()
				.paymentNo(paymentDto.getPaymentNo()) //결재대표 번호
				.paymentRemain(response.getCancelAvailableAmount().getTotal())//잔여금액
				.build());

		
		return "redirect:list2";
	}
	
	//결제 그룹 전체 취소
	//[1] 전달받은 paymentNo로 paymentDto를 조회
	// -잔여금액이 0이라면 차단
	//[2] 1번에서 거래번호(tid)와 잔여금액을 꺼내서 카카오페이에 취소 요청을 전송
	//[3] 취소가 성공하였더라면 DB에서 다음의 항목을 수정
	// -payment에서 잔여금액을 0으로 수정
	// -payment_detail에서 해당 payment_no에 대한 모든 상태를 취소로 변경
	@RequestMapping("/test3/cancelAll")
	public String test3CancelAll(@RequestParam int paymentNo) throws URISyntaxException {
		//1
		PaymentDto paymentDto = paymentDao.selectOne(paymentNo);
		if(paymentDto == null || paymentDto.getPaymentRemain() ==0) {
			throw new NoTargetException("이미 취소된 결재입니다.");
		}
		
		//2
		KakaoPayCancelRequestVO request = KakaoPayCancelRequestVO.builder()
				.tid(paymentDto.getPaymentTid())//거래번호
				.cancelAmount(paymentDto.getPaymentRemain())//잔여금액
				.build();
		KakaoPayCancelResponseVO response =kakaoPayService.cancel(request);
		
		//3
		paymentDao.cancel(paymentDto.builder()
				.paymentNo(paymentNo)
				.paymentRemain(0)
				.build());
		paymentDao.cancelDetailGroup(paymentNo);
		
		return "redirect:list2";

	}
	
	//결제 취소와 결제실패의 경우에도 세션에 저장한 flash value를 삭제해야 한다.
	@RequestMapping("/test3/purchase/cancel")
	public String test3cancel(HttpSession session) {
		session.removeAttribute("approve");
		session.removeAttribute("listVO");
		return "취소했을때 보여줄 페이지";
	}
	@RequestMapping("/test3/purchase/fail")
	public String test3fail(HttpSession session) {
		session.removeAttribute("approve");
		session.removeAttribute("listVO");
		return "실패했을때 보여줄 페이지";
	}
	
}

