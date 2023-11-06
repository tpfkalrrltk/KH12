package com.kh.spring22.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring22.dao.BookDao;
import com.kh.spring22.dto.BookDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping("/book")
@Slf4j
@Tag(name = "책 리액트용 백앤드", description = "설명란")
public class BookRestController {
	@Autowired
	BookDao bookDao;

	@GetMapping("/")
	public List<BookDto> list() {
		return bookDao.List();
	}

	@PostMapping("/")
	public void add(@RequestBody BookDto bookDto) {
		bookDao.add(bookDto);
	}

	@DeleteMapping("/{bookId}")
	public void delete(@PathVariable int bookId) {
		bookDao.delete(bookId);
	}

	@PutMapping("/{bookId}")
	public void edit(@PathVariable int bookId, @RequestBody BookDto bookDto) {
		bookDao.edit(bookId, bookDto);

	}

	@GetMapping("/{bookId}")
	public ResponseEntity<BookDto> select(@PathVariable int bookId) {
		BookDto bookDto = bookDao.selectOne(bookId);
		if (bookDto != null) {
			return ResponseEntity.ok().body(bookDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PatchMapping("/{bookId}")
	public ResponseEntity<String> editUnit(@PathVariable int bookId, @RequestBody BookDto bookDto) {
		if (bookDto.isEmpty()) {
			ResponseEntity.badRequest().build();
		}
		log.debug("책={}", bookDto);
		log.debug("아이디={}", bookId);
		boolean result = bookDao.editUnit(bookId, bookDto);
		return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();

	}

	@GetMapping("/search/{bookTitle}")
	public List<BookDto> searchBook(@PathVariable String bookTitle) {
		return bookDao.searchBookTitle(bookTitle);
	}

}
