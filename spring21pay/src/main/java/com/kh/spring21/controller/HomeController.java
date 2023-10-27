package com.kh.spring21.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	
@RequestMapping("/")
public String home() {
	return "home";
}
@RequestMapping("/login")
public String login(HttpSession session, @RequestParam String memberId, @RequestParam String memberPw) {
	if(memberId.equals("testuser1") && memberPw.equals("testuser1")) {
		session.setAttribute("name", memberId);
		session.setAttribute("level", "VIP");
	}
	return "redirect:/";
}
@RequestMapping("/logout")
public String logout(HttpSession session) {
	session.removeAttribute("name");
	session.removeAttribute("level");
	return "redirect:/";
	
}
	
}
