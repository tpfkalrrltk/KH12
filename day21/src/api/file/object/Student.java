package api.file.object;

import java.io.Serializable;

//클래스를 만들때 파일로도 저장할 계획이라민 마킹(Marking)을 해야함.
// java.io.Serializable 인터페이스를 상속받으면 해결

// 1.클래스의 버전을 설정할수 있다.
// 의도적으로 클래스를 관리할 수 있다.

// 2.의도적으로 필드중에서 특정 항목을 입출력에서 제외 시킬수 있다.
// 저장하지 않을 필드에 transient 키워드를 추가
public class Student implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private transient int score;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Student(String name, int score) {
		this.setName(name);
		this.setScore(score);
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}