package com.kh1.spring11.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh1.spring11.dto.PocketmonDTO;

@Component// 컨트롤러도 아니고 리파지토리도 아닌 애매한놈들
public class PocketmonMapper implements RowMapper<PocketmonDTO> {

	@Override
	public PocketmonDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PocketmonDTO  dto = new PocketmonDTO();
		dto.setNo(rs.getInt("no"));
		dto.setName(rs.getString("name"));
		dto.setType(rs.getString("type"));
		return dto;
	}

}
