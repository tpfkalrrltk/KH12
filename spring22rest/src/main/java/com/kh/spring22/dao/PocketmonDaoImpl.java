package com.kh.spring22.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring22.dto.PocketmonDto;

@Repository
public class PocketmonDaoImpl implements PocketmonDao {
	@Autowired
	SqlSession sqlSession;

	@Override
	public List<PocketmonDto> selectList() {

		return sqlSession.selectList("pocketmon.list");
	}

	@Override
	public void insert(PocketmonDto pocketmonDto) {

		sqlSession.insert("pocketmon.save", pocketmonDto);

	}

	@Override
	public boolean delete(int no) {
		
		return sqlSession.delete("pocketmon.remove", no) > 0;

	}

}
