package com.kh.spring20.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring20.dto.ChatDto;

@Repository
public class ChatDtoImpl  implements ChatDao{

	@Autowired
private	SqlSession sqlSession;
	
	@Override
	public void insert(ChatDto dto) {
		sqlSession.insert("chat.add",dto);
		
	}

	@Override
	public List<ChatDto> list() {
	
		return sqlSession.selectList("chat.list");
	}
	

}
