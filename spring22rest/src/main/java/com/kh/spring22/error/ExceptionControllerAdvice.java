package com.kh.spring22.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
//(annotations = {RestController.class})	
(basePackages = { "com.kh.spring22.restcontroller" })
@Slf4j
public class ExceptionControllerAdvice {

	// [1] NoTargetException이 발생하면 사용자에게 404를 반환
	@ExceptionHandler(NoTargetException.class)
	public ResponseEntity<?> error404(Exception e) {
		log.warn("404 발생",e);
		return ResponseEntity.notFound().build();
	}

	// [2] 그 외 예외가 발생하면 사용자에게 500을 반환
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> error500(Exception e){
		log.error("오류 발생",e);
		return ResponseEntity.internalServerError().body("server error");
	}
}
