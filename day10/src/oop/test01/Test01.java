package oop.test01;

public class Test01 {
	public static void main(String[] args) {
		// 시험 성적 정보 1개를 저장
		// 이름 = 황민하, 국어=90, 영어= 80, 수학= 70
		Grade a = new Grade();
		
		//a(리모컨) ---> Grade 객체가 생성(이름 국어 영어 수학)
		a.name="황민하";
		a.korean = 90;
		a.english =80;
		a.math =70;
		//System.out.println(a); 의미없음
		System.out.println(a.name);
		System.out.println(a.korean);
		System.out.println(a.english);
		System.out.println(a.math);
	}
}
