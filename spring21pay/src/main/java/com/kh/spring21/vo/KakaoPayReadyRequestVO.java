package com.kh.spring21.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPayReadyRequestVO {
private String partnerOrderId;
private String partnerUserId;
private String itemName;
private int itemPrice;
}
