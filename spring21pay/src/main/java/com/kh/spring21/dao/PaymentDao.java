package com.kh.spring21.dao;

import java.util.List;

import com.kh.spring21.dto.PaymentDetailDto;
import com.kh.spring21.dto.PaymentDto;

public interface PaymentDao {
	int sequence();
	void insert(PaymentDto paymentDto);
	List<PaymentDto> selectList();
	void insertDetail(PaymentDetailDto paymentDetailDto);
	List<PaymentDto> selectTotalList();
	List<PaymentDao> selectTotalListByMember(String paymentMember);
	PaymentDetailDto selectDetail(int paymentDetailNo);
	PaymentDto selectOne(int paymentNo);
	void cancel(PaymentDto paymentDto);
	void cancelDetail(int paymentDetailNo);
	void cancelDetailGroup(int paymentDetailOrigin);

}
