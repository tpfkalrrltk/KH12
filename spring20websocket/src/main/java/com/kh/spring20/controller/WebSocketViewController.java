package com.kh.spring20.controller;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring20.dto.MemberDto;

@Controller
public class WebSocketViewController {

	@RequestMapping("/")
	public String home() {
		// return "/WEN-INF/views/home.jsp";
		return "home";
	}

	@RequestMapping("/default")
	public String defaultServer() {
		// return "/WEN-INF/views/default.jsp";
		return "default";
	}

	@RequestMapping("/time")
	public String time() {
		// return "/WEN-INF/views/time.jsp";
		return "time";
	}

	@RequestMapping("/group")
	public String group() {
		// return "/WEN-INF/views/group.jsp";
		return "group";
	}
	@RequestMapping("/member")
	public String member() {
		// return "/WEN-INF/views/member.jsp";
		return "member";
	}

	// 임시 로그인 처리
	@Autowired
	private SqlSession sqlSession;

	@PostMapping("/login")
	public String login(@ModelAttribute MemberDto memberDto, HttpSession Session) {
		MemberDto findDto = sqlSession.selectOne("member.find",memberDto);
		if(findDto != null) {
			boolean pwMatch =findDto.getMemberPw().equals(memberDto.getMemberPw());
			if(pwMatch) {
			Session.setAttribute("name",findDto.getMemberId()); //아이디 
			Session.setAttribute("level", findDto.getMemberLevel()); //등급
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("name");
		session.removeAttribute("level");
		return"redirect:/";
		
	}
	
	@RequestMapping("/json")
	public String json() {
		// return "/WEN-INF/views/json.jsp";
		return "json";
	}
}
