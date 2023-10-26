package com.kh.spring21.dao;

import java.util.List;

import com.kh.spring21.dto.PaymentDetailDto;
import com.kh.spring21.dto.PaymentDto;

public interface PaymentDao {
	int sequence();
	void insert(PaymentDto paymentDto);
	List<PaymentDto> selectList();
	void insertDetail(PaymentDetailDto paymentDetailDto);
}
