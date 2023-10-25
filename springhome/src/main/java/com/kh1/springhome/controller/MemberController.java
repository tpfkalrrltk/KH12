package com.kh1.springhome.controller;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh1.springhome.dao.AdminDao;
import com.kh1.springhome.dao.BoardLikeDao;
import com.kh1.springhome.dao.MemberDao;
import com.kh1.springhome.dto.BoardLikeDto;
import com.kh1.springhome.dto.MemberBlockDto;
import com.kh1.springhome.dto.MemberDto;
import com.kh1.springhome.error.AuthorityException;

@Controller
@RequestMapping("/member")
public class MemberController {
	// @Autowired는 지정한 클래스 및 자식클래스 중 등록된것을 찾아 주입한다.
	@Autowired
	MemberDao memberDao;
	@Autowired
	AdminDao adminDao;

	@GetMapping("/join")
	public String join() {
		return "/WEB-INF/views/member/join.jsp";
	}

	@PostMapping("/join")
	public String join(@ModelAttribute MemberDto memberDto) {
		memberDao.insert(memberDto);
		return "redirect:joinFinish"; // 상대경로!
		// return "redirect:/member/joinFinish"; //절대경로
	}

	@RequestMapping("/joinFinish")
	public String joinFinish() {
		return "/WEB-INF/views/member/joinFinish.jsp";
	}

	@GetMapping("/login")
	public String login() {
		return "/WEB-INF/views/member/login.jsp";
	}

	/**
	 * 로그인과 같이 사용자 별로 관리 되어야하는 상태정보들이 있다. 이때 사용할수 있는 저장소로 HttpSession이 있다. 이저장소는 사용자
	 * 별로 따로 저장되며 외부에서 접근이 불가능하다. 컨트롤러에 선언만하면 사용할수 있으며, key=value형태로 저장된다.
	 * 
	 * [1]추가 - session.setAttribute("key",value); [2]확인 -
	 * session.getAttirbute("key"); [3]삭제 - session.removeAttirbute("key");
	 */

	@PostMapping("/login")
	public String login(@ModelAttribute MemberDto inputDto,HttpSession session) {
		// [1] 사용자가 입력한 아이디로 데이터베이스에서 정보를 조회
		MemberDto findDto = memberDao.selectOne(inputDto.getMemberId());
		// [2] 1번에서 정보가 있다면 비밀번호를 검사(없으면 차단)
		if (findDto == null) {
			return "redirect:login?error";// redirect는 무조건 GetMapping으로 간다
		}

		
		// boolean isCorrectPw = 입력한비밀번호와 DB비밀번호가 같나?
		boolean isCorrectPw = inputDto.getMemberPw().equals(findDto.getMemberPw());

		// [3] 비밀번호가 일치하면 메인페이지로 이동
		if (isCorrectPw) {
			// (주의) 만약 차단된 회원이라면 추가작업을 중지하고 오류 발생
			MemberBlockDto blockDto = 
					adminDao.selectBlockOne(findDto.getMemberId());
		if(blockDto !=null) {throw new AuthorityException("차단된 회원");}

			// 세션에 아이디+등급 저장
			session.setAttribute("name", findDto.getMemberId());
			session.setAttribute("level", findDto.getMemberLevel());
			session.setAttribute("password", inputDto.getMemberPw());
			// 로그인 시간 갱신
			memberDao.updateMemberLogin(inputDto.getMemberId());
			return "redirect:/";
		}
		// [4] 비밀번호가 일치하지 않으면 로그인페이지로 이동
		else {
			return "redirect:login?error";
		}
	}

	// ------------회원 전용 메뉴------------------
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("name");
		session.removeAttribute("level");
		return "redirect:/";
	}

	@Autowired
	private BoardLikeDao boardLikeDao;

	@RequestMapping("/mypage")
	public String mypage(HttpSession session, Model model) {
		// 1. 세션에서 사용자의 아이디를 꺼낸다!.
		// -세션은 값을 Object로 저장한다(아무거나 넣어야 하니까)
		String memberId = (String) session.getAttribute("name");
		// 2. 가져온 아이디로 회원정보를 조회한다.
		MemberDto memberDto = memberDao.selectOne(memberId);
		// 3. 조회한 정보를 모델에 첨부한다.
		model.addAttribute("memberDto", memberDto);
		// 4. 좋아요 누른 게시글 내역을 모델에 첨부한다.
		model.addAttribute("boardLikeList", boardLikeDao.findByMemberId(memberId));
		// 5. 이회원의 프로필 이미지 정보를 첨부한다.
		model.addAttribute("profile", memberDao.findProfile(memberId));

		return "/WEB-INF/views/member/mypage.jsp";
	}

	@GetMapping("/password")
	public String password() {
		return "/WEB-INF/views/member/password.jsp";
	}

	@PostMapping("/password")
	public String password(HttpSession session, @RequestParam String originPw, @RequestParam String changePw) {
		String memberId = (String) session.getAttribute("name");
		MemberDto memberDto = memberDao.selectOne(memberId);

		// 1. 기존 비밀번호가 일치하는지 판정
		if (memberDto.getMemberPw().equals(originPw)) {// 비밀번호가 일치 한다면
			// 2. 1번이 성공일때 비밀번호를 변경하도록 처리(+개인정보 변경시각 수정)
			memberDao.updateMemberPw(memberId, changePw);
			return "redirect:passwordFinish";
		} else {
			return "redirect:password?error";
		}

	}

	@RequestMapping("/passwordFinish")
	public String passwordFinish() {
		return "/WEB-INF/views/member/passwordFinish.jsp";
	}

	@GetMapping("/change")
	public String change(Model model, HttpSession session) {
		String memberId = (String) session.getAttribute("name");
		MemberDto memberDto = memberDao.selectOne(memberId);
		model.addAttribute("memberDto", memberDto);
		return "/WEB-INF/views/member/change.jsp";
	}

	@PostMapping("/change")
	public String change(@ModelAttribute MemberDto inputDto, HttpSession session) {
		String memberId = (String) session.getAttribute("name");
		// 비밀번호 검사 후 변경 처리 요청
		MemberDto findDto = memberDao.selectOne(memberId);
		if (inputDto.getMemberPw().equals(findDto.getMemberPw())) {// 비밀번호가 일치한다면
			inputDto.setMemberId(memberId);// 아이디를 설정하고
			memberDao.updateMemberInfo(inputDto); // 정보 변경 처리
			return "redirect:mypage";
		} else {// 비밀번호가 일치하지 않는다면 ->다시 입력하도록 되돌려보냄
			return "redirect:change?error";
		}
	}

	@GetMapping("/exit")
	public String exit() {
		return "/WEB-INF/views/member/exit.jsp";
	}

	@PostMapping("/exit")
	public String exit(HttpSession session, @ModelAttribute String memberPw) {
		String memberId = (String) session.getAttribute("name");
		MemberDto memberDto = memberDao.selectOne(memberId);
		if (memberDto.getMemberPw().equals(memberPw)) {

			memberDao.exit(memberPw);
			session.removeAttribute("name"); // 세션에서 name값 삭제
			// session.invalidate();//세션 소멸(비추천)
			return "redirect:/exitFinish";// 절대경로
		} else {
			return "redirect:exit?erorr";
		}
	}
	
	
	@Autowired
	JavaMailSender sender;
	
	// 비밀번호 찾기
	@GetMapping("findPw")
	public String findPw() {
		return "/WEB-INF/views/member/findPw.jsp";
	}

	
	@PostMapping("findPw")
	public String findPw(@ModelAttribute MemberDto memberDto) {
		//[1]아이디로 모든정보 불러오고
		MemberDto findDto =memberDao.selectOne(memberDto.getMemberId());
		//[2]이메일이 일치하는지 확인한다
		boolean isvaild = findDto.getMemberEmail() !=null && 
				findDto.getMemberEmail().equals(memberDto.getMemberEmail());
		
		if(isvaild) {//이메일이 같다면
			
			//이메일 발송코드
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(findDto.getMemberEmail());
			message.setSubject("비밀번호 찾기 결과");
			message.setText(findDto.getMemberId()+"회원님의 비밀번호 :"+findDto.getMemberPw());
			sender.send(message);
			return "redirect:findPwFinish";
		}
		else {//이메일이 다르다면
			return "redirect:findPw?error";
		}

	}
	
	@RequestMapping("/findPwFinish")
	public String findPwFinish() {
		return "/WEB-INF/views/member/findPwFinish.jsp";
	}
}
