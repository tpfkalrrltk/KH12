package com.kh.spring21.vo;

import java.util.List;

import com.kh.spring21.dto.PaymentDetailDto;
import com.kh.spring21.dto.PaymentDto;

import lombok.Data;

@Data
public class PaymentListVO {
 private PaymentDto paymentDto;
 private List<PaymentDetailDto> paymentDetailList;
}
