package com.kh1.springhome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

//스케쥴러를 사용하고 싶다면 사용하겠다는 설정을 해야 한다.
@EnableScheduling

@SpringBootApplication
public class SpringhomeApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SpringhomeApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringhomeApplication.class);
	}

}
