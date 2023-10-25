package com.kh1.springhome.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kh1.springhome.interceptor.TestInterceptor;
import com.kh1.springhome.interceptor.AdminOwnerInterceptor;
import com.kh1.springhome.interceptor.BoardDefenderInterceptor;
import com.kh1.springhome.interceptor.BoardOwnerInterceptor;
import com.kh1.springhome.interceptor.MemberInterceptor;

/**
 * 
 * 스프링에서 제공하는 설정파일 -application.properties에서 설정할수 없는 내용 들을 설정하는 파일
 * 
 * 만드는법 1.등록(@Configuration) 2.필요시 상속(WebMvcConfigurer)
 * 
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
	@Autowired
	private TestInterceptor testInterceptor;
	@Autowired
	private MemberInterceptor memberInterceptor;
	@Autowired
	private BoardOwnerInterceptor boardOwnerInterceptor;
	@Autowired
	private BoardDefenderInterceptor boardDefenderInterceptor;
	@Autowired
	private AdminOwnerInterceptor adminOwnerInterceptor;

//인터셉터를 추가할수있는 설정 메소드(registry 저장소에 설정)
	// 등록시 주소의 패턴 설정 방법
	// - *이 한개면 동일한 엔드포인트 내에서만 적용
	// - **이 두개면 하위 엔드포인트를 포함하여 적용
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 1.TestInterceptor를 모든 주소처리과정에 간섭할수 있도록 설정하겠다.
		 registry.addInterceptor(testInterceptor).addPathPatterns("/**");// "/**"은 전부다
		// 라는 뜻이다.

		// 2.MemberInterceptor를 회원전용 페이지 처리 과정에서 간섭할수 있도록 설정
		registry.addInterceptor(memberInterceptor)

				// - addPathPatterns를 사용하면 추가할 주소를 설정할 수 있다.
				// - excludePathPatterns를 사용하면 제외할 주소를 설정할 수 있다.
				.addPathPatterns("/member/**", "/board/**","/rest/reply/**") // 화이트리스트 전부다 등록후

				.excludePathPatterns("/member/join*", "/member/login", "/member/find*", "/member/exitFinish", "/board/list",
						"/board/detail","/rest/reply/list");// 허용할것을 등록

		// 3. 게시글 소유자 외 접근을 차단하는 인터셉터 등록
		registry.addInterceptor(boardOwnerInterceptor).addPathPatterns("/board/edit", "/board/delete");

		//[4] 게시글 조회수 중복방지 인터셉터 등록
				registry.addInterceptor(boardDefenderInterceptor)
							.addPathPatterns("/board/detail");
				
		//[5] 관리자용 인터셉터 등록
				registry.addInterceptor(adminOwnerInterceptor)
				.addPathPatterns("/admin/**","/board/deleteByAdmin");
	}
}
