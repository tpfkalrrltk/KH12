package com.kh.spring22.dao;

import java.util.List;

import com.kh.spring22.dto.PocketmonDto;

public interface PocketmonDao {
	List<PocketmonDto> selectList();

	void insert(PocketmonDto pocketmonDto);
	
	boolean delete(int no);
	PocketmonDto selelctOne(int no);

	boolean edit(int no, PocketmonDto pocketmonDto);

	boolean editUnit(int no, PocketmonDto pocketmonDto);

}
