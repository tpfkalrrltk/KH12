package com.kh.spring22.dao;

import java.util.List;

import com.kh.spring22.dto.PocketmonDto;

public interface PocketmonDao {
	List<PocketmonDto> selectList();

}
