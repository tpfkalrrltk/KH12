package com.kh.spring03.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 어노테이션(Annotation) -역할 정의
public class QuizController {
	@RequestMapping("/dice")
	public String quiz() {
		Random r = new Random();
		int dice = r.nextInt(6) + 1;
		// System.out.println(dice);//의미없음 사용자한테 안보임
		return "랜덤 주사위 값은 ? " + dice;
	}

	@RequestMapping("/lotto")
	public String lotto() {
		Random r = new Random();
		int lotto = r.nextInt(45) + 1;
		return "로또 번호는 " + lotto;

	}

	// 질문답변
	@RequestMapping("/lotto-list")
	public List<Integer> lottoList() {
		List<Integer> list = new ArrayList<>();
		for (int i = 1; i <= 45; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		List<Integer> lotto = list.subList(0, 6);
		Collections.sort(lotto);
		return lotto;
	}

	@RequestMapping("/square")
	// (추가)스프링은 역할을 명시하는 것을 선호한다.
	// -@RequestParam을 변수앞에 붙여 파라미터임을 표시
	public String square(@RequestParam int num) {
		return "숫자 " + num + "의 제곱은 ? " + num * num + " 입니다.";
	}

	@RequestMapping("/subway")
//(추가) 연도가 주어지지 않으면 실행을 안할건가?
	//파라미터가 없어도 실행되어야 하는 상황(ex: 기본값)
	// 만약 brith가 제공되지 않는다면 성인 요금을 부여
	//@RequestParam에 required = false옵션을 주면 없어도됨
	//@RequestParam에 defaultValue를 지정하여 기본값을 지정
	public String subway(@RequestParam(required = false, 
	defaultValue ="2000") int brith) {
		int now = LocalDate.now().getYear();
		int age = now - brith + 1;
		int price;
		if (age <= 19) {
			price =900;
		} else if (age >= 20) {
			price = 1250;
		}
		else price =450;
		return "지하철 요금은 "+ price+"원 입니다.";
	}

	@RequestMapping("/leap")
	public String leap(@RequestParam int year) {
		boolean a = (year % 4 == 0 && year % 100 == 0) || year%400==0;
		if (a) {
			return "윤년입니다.";
		} else
			return "윤년이 아닙니다.";
	}

	@RequestMapping("/circle")
	public String circle(@RequestParam int num) {
		return "원의 반지름" + num + "의 원 넓이는 " +(Double) (num * num * Math.PI) + " 입니다.";
	}

}
