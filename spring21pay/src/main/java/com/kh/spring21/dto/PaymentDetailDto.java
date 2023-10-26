package com.kh.spring21.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDetailDto {
private int paymentDetailNo; //하위 결재번호
private int paymentDetailOrigin;//상위 결재 번호
private int paymentDetailProduct;//구매 상품 번호
private String paymentDetailProductName;// 구매 상품명
private int paymentDetailProductPrice;//구매 상품가격
private int paymentDetailProductQty;//구매상품 수량
private String paymentDetailStatus;//구매상품상태(완료/취소)
}
