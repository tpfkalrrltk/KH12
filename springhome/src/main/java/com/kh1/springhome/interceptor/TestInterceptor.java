package com.kh1.springhome.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 인터셉터(Intercepter)
 -완성된 스프링의 프로젝트 구석구석을 간섭할 수 있도록 만들어진 도구
 
 만드는법
 1.상속을 받아서 자격을 획득 (HandlerInterceptor)
 2.(@Component)
 3.메소드 재정의(preHandle)
 4.설정파일을 만들어서 어느 주소를 간섭할 것인지 설정
 */
@Component
public class TestInterceptor implements HandlerInterceptor{
	
	/**
	 preHandle은 컨트롤러가 실행 되기 직전 시점을 간섭하는 메소드
	 -ture를 반환하면 요청에 대한 작업을 진행하겠다는 의미
	 -false를 반환하면 요청에 대한 작업을 차단하겠다는 의미
	 -자격여부에 따른 접근 가능/차단 여부를 처리할 때 적합
	 -request에는 사용자의 요청정보가 들어있다(Ex : 파라미터, 세션정보....)
	 -response에는 사용자에게 나갈정보가 들어있다(Ex : 화면정보....)
	 -handler에는 이요청을 처리할 대상 컨트롤러/메소드의 정보가 들어있다.
	 */
@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
	System.out.println("preHandle 실행");
	return false;
}

@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	System.out.println("postHandle 실행");
}
@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	System.out.println("afterCompletion 실행");
	}

}
