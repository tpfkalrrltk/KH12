package oop.test01;

public class Test02 {
	public static void main(String[] args) {
		// 시험 성적 정보 2개를 저장
		// 이름 = 황민하, 국어=90, 영어= 80, 수학= 70
		// 이름 = 피카츄, 국어=75, 영어= 80, 수학= 77
		Grade a = new Grade();
		Grade b =new Grade();
		//a(리모컨) ---> Grade 객체가 생성(이름 국어 영어 수학)
		//b(리모컨) ---> Grade 객체가 생성(이름 국어 영어 수학)
		a.name="황민하";
		a.korean = 90;
		a.english =80;
		a.math =70;
		b.name="피카츄";
		b.korean = 75;
		b.english =80;
		b.math =77;
		//System.out.println(a); 의미없음
		System.out.println(a.name);
		System.out.println(a.korean);
		System.out.println(a.english);
		System.out.println(a.math);
		System.out.println(b.name);
		System.out.println(b.korean);
		System.out.println(b.english);
		System.out.println(b.math);
	}
}
