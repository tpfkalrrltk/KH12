package com.kh1.springhome.conroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	//http://localhost:8080/
@RequestMapping("/")
public String home() {
	return "/WEB-INF/views/home.jsp";
	
}
}
