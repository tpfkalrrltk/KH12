package com.kh1.springhome.dao;

import java.util.List;

import com.kh1.springhome.dto.BoardDto;
import com.kh1.springhome.dto.BoardListDto;

public interface BoardDao {
	void write(BoardDto boardDto);

	public List<BoardListDto> list();

	public int sequence();

	public BoardDto detail(int board_no);

	public boolean edit(BoardDto dto);

	public boolean updateDetail(int board_no);
	
	public boolean updateLike(int board_no);

	public boolean delete(int board_no);

	public List<BoardListDto> selectList(String type, String keyword);

	Integer selectMax(String board_wirter);
}