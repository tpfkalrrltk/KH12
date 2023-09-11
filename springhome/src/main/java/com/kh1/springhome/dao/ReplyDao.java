package com.kh1.springhome.dao;

import java.util.List;

import com.kh1.springhome.dto.ReplyDto;

public interface ReplyDao {
	
int sequence();

public void insert(ReplyDto replyDto);

List<ReplyDto>selectList(int replyOrigin);
ReplyDto selectOne(int replyNo);

boolean delete(int replyNo);
}
