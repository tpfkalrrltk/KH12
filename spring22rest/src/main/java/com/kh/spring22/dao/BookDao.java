package com.kh.spring22.dao;

import java.util.List;

import com.kh.spring22.dto.BookDto;

public interface BookDao {

	List<BookDto>List();
	void add(BookDto bookDto);
	boolean edit(int bookId, BookDto bookDto);
	boolean delete (int bookId);
	BookDto selectOne(int bookId);
	boolean editUnit(int bookId, BookDto bookDto);
	List<BookDto> searchBookTitle(String bookTitle); 
}
