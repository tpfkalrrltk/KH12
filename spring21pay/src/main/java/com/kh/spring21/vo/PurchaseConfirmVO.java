package com.kh.spring21.vo;

import com.kh.spring21.dto.ProductDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//구매 확인을 위해 정보를 한번 더 출력하는 용도
public class PurchaseConfirmVO {
private PurchaseVO purchaseVO;
private ProductDto productDto;
public int getTotal() {
	return purchaseVO.getQty() * productDto.getProductPrice();
}
}
