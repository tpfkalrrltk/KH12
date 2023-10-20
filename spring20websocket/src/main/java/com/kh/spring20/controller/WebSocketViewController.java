package com.kh.spring20.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class WebSocketViewController {

	@RequestMapping("/")
	public String home() {
		// 	return "/WEN-INF/views/home.jsp";
		return "home";
	}
	@RequestMapping("/default")
	public String defaultServer() {
		// 	return "/WEN-INF/views/default.jsp";
		return "default";
	}
	@RequestMapping("/time")
	public String time() {
		return "time";
	}
}
