package com.kh1.springhome.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh1.springhome.dto.PocketmonDto;
import com.kh1.springhome.mapper.PocketmonMapper;

@Repository
public class PocketmonDaoImpl implements PocketmonDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	PocketmonMapper pocketmonMapper;

	@Override
	public int sequence() {
		String sql = "select pocketmon_seq.nextval from dual";

		return jdbcTemplate.queryForObject(sql, int.class);
	}

	@Override
	public void insert(PocketmonDto pocketmonDto) {
		String sql = "insert into pocketmon(no,name,type) values(?,?,?)";
		Object[] data = { pocketmonDto.getNo(), pocketmonDto.getName(), pocketmonDto.getType() };
		jdbcTemplate.update(sql, data);
	}

}