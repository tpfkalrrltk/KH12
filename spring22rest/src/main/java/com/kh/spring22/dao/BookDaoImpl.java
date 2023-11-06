package com.kh.spring22.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring22.dto.BookDto;
import com.kh.spring22.error.NoTargetException;

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
	public void edit(int bookId, BookDto bookDto) {
		Map<String, Object> params = Map.of("bookId", bookId, "bookDto", bookDto);
		// params.put("bookId", bookId);
		// params.put("bookDto", bookDto);
		int result = sqlSession.update("book.edit", params);
		if (result == 0)
			throw new NoTargetException();
	}

	@Override
	public void delete(int bookId) {
		int result = sqlSession.delete("book.delete", bookId);
		if (result == 0)
			throw new NoTargetException();
	}

	@Override
	public BookDto selectOne(int bookId) {
		BookDto bookDto = sqlSession.selectOne("book.selectOne", bookId);
		if (bookDto == null)
			throw new NoTargetException();
		return bookDto;

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
