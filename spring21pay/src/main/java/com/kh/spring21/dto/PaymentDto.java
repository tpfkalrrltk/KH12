package com.kh.spring21.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor
@Builder
public class PaymentDto {
private int paymentNo;
private String paymentMember; //구매회원ID
private String paymentTid; //PG사 결제 거래번호
private String paymentName; //PG사 결제 상품명
private int  paymentPrice; //PG사 결제 가격
private Date paymentTime;
private int paymentRemain;//잔여 결재금액(취소 가능 금액)
}
