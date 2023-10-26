package com.kh.spring21.dao;

import com.kh.spring21.dto.PaymentDetailDto;
import com.kh.spring21.dto.PaymentDto;

public interface PaymentDao {
	int sequence();
	void insert(PaymentDto paymentDto);
	void insertDetail(PaymentDetailDto paymentDetailDto);
}
