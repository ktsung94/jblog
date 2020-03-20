package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.jblog.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		// 1. Handler 종류 확인
		if(handler instanceof HandlerMethod == false) {
			// DefaultServletHandler가 처리하는 경우(보통, assets의 정적 자원 접근)
			return true;
		}

		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;

		// 3. Method의 @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

		// 해야할 것
		// 4. Method에 @Auth가 없으면.. Type에 붙어 있는지 확인한다.
		if(auth == null) {
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
		}

		// 5. Type 이나 Method 둘 다 @Auth가 적용이 안되어 있는 경우
		if(auth == null) {
			return true;
		}

		// 6. 인증 여부 확인(@Auth가 붙어 있기 때문)
		HttpSession session = request.getSession(false);	// false는 안써줘도됨
		if(session == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}

		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}

		String id = authUser.getId();  
		String url = request.getRequestURI();
		
		String[] urlToken = url.split("/");
		
		if(urlToken[2].equals(id)) {
			return true;
		} else {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
	}

}
