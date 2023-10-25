package com.kh.spring21.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring21.dto.PaymentDto;

@Repository
public class PaymentDaoImpl implements PaymentDao {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public int sequence() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("payment.sequence");
	}

	@Override
	public void insert(PaymentDto paymentDto) {
	sqlSession.insert("payment.save",paymentDto);
		
	}

}
