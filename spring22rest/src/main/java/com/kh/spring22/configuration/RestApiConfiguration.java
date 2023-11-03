package com.kh.spring22.configuration;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
	info = @Info(
			title ="테스트용 REST API 서비스",
			description = "KH정보교육원 백엔드 수업자료",
			version = "v1.0",
			contact = @Contact(
					name ="황민하",
					email = "tpfkalrrltk@naver.com",
					url = "http://github.com/tpfkalrrltk"
					)
			
			
			
			)	
		
		
		
		)
@Configuration
public class RestApiConfiguration {

}
