package com.kh.spring22.dao;

import java.util.List;

import com.kh.spring22.dto.BookDto;

public interface BookDao {

	List<BookDto>List();
	void add(BookDto bookDto);
	void edit(int bookId, BookDto bookDto);
	void delete (int bookId);
	BookDto selectOne(int bookId);
	boolean editUnit(int bookId, BookDto bookDto);
	List<BookDto> searchBookTitle(String bookTitle); 
}
