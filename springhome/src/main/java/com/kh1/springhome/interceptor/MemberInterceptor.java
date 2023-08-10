package com.kh1.springhome.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh1.springhome.error.AuthorityException;

// 회원인지 아닌지  검사하여 비회원을 차단하는 인터셉터 구현
// 세션에서 name이라는 이름의 값이 있으면 회원, 없으면 비회원
@Component
public class MemberInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 세션이 주어지지 않으므로 요청정보에서 세션 객체를 추출해서 사용
		HttpSession session = request.getSession();
		String memberId = (String) session.getAttribute("name");

		boolean isLogin = memberId != null;
		if (isLogin) {// 회원이면
			return true;
		} else {// 비회원이면
			// 1.-차단 +로그인페이지로 리다이렉트
			//response.sendRedirect("/member/login"); //절대 경로로 지정해야 할듯 오류 방지
			
			
			// 2.권한없음 오류 발생
			//response.sendError(401);
			//return false;
			
			
			// 3.내가만든 예외 처리
			throw new AuthorityException("로그인 후 이용 가능");
		}
	}
}
