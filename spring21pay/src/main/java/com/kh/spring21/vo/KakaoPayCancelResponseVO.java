package com.kh.spring21.vo;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoPayCancelResponseVO {
private String aid;
private String tid;
private String cid;
private String status;
private String partnerOrderId;
private String partnerUserId;
private String paymentMethodType;
private String itemName;
private String itemCode;
private int quantity;
private Date createdAt;
private Date approvedAt;
private String Date ;
private String payload;
private KakaoPayAmountVO amount,approvedCancelAmount,canceledAmount,cancelAvailableAmount;
}
