package com.kh.spring21.vo;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data@NoArgsConstructor@AllArgsConstructor
@Builder
public class KakaoPayDetailResponseVO {
	private String tid;
	private String cid;
	private String status;
	private String partnerOrderId;
	private String partnerUserId;
	private String paymentMethodType;
	private String itemName;
	private String itemCode;
	private String quantity;
	private Date createdAt;
	private Date approvedAt;
	private  Date canceledAt;
	private KakaoPayAmountVO amount;
	private KakaoPayAmountVO canceledAmount;
	private KakaoPayAmountVO cancelAvailableAmount;
	private KakaoPaySelectedCardInfoVO selectedCardInfo;
//	private KakaoPayPaymentActionDetail[] PaymentActionDetail;
	private List<KakaoPaymentActionDetailVO> paymentActionDetails;
	
}
