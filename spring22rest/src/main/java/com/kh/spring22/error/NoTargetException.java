package com.kh.spring22.error;

import lombok.NoArgsConstructor;

/**
대상이 없을 경우 발생하는 예외
1. Exception을 상속 받는다
		-RuntimeException을 상속받으면 예외 전가를 작성하지 않아도 된다.
2. 기본 생성자와 메세지를 설정할수 있는 생성자를 구현한다
*/

@NoArgsConstructor
public class NoTargetException extends RuntimeException {
	public NoTargetException(String message) {
		super(message);
	
	}
	
	

	
	
}
