package com.kh.spring22.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring22.dto.BookDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BookDaoImpl implements BookDao {

	@Autowired
	SqlSession sqlSession;

	@Override
	public java.util.List<BookDto> List() {

		return sqlSession.selectList("book.list");
	}

	@Override
	public void add(BookDto bookDto) {
		sqlSession.insert("book.add", bookDto);

	}

	@Override
	public boolean edit(int bookId, BookDto bookDto) {
		Map<String, Object> params = Map.of("bookId", bookId, "bookDto", bookDto);
		// params.put("bookId", bookId);
		// params.put("bookDto", bookDto);

		return sqlSession.update("book.edit", params) > 0;
	}

	@Override
	public boolean delete(int bookId) {
		return sqlSession.delete("book.delete", bookId) > 0;
	}

	@Override
	public BookDto selectOne(int bookId) {

		return sqlSession.selectOne("book.selectOne", bookId);
	}

	@Override
	public boolean editUnit(int bookId, BookDto bookDto) {

		Map<String, Object> params = Map.of("bookId", bookId, "bookDto", bookDto);
		// params.put("bookId", bookId);
		// params.put("bookDto", bookDto);
		return sqlSession.update("book.editUnit", params) > 0;
	}

	@Override
	public java.util.List<BookDto> searchBookTitle(String bookTitle) {

		return sqlSession.selectList("findbyBookTitle", bookTitle);
	}
}
