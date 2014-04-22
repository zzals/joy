package com.se.joy.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.se.joy.model.LoginVO;
import com.se.joy.model.MemberVO;

public class AuthInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		
		MemberVO loginVo = (MemberVO) WebUtils.getSessionAttribute(request, "MEMBER_LOGIN");
		String uri = request.getRequestURI();
		
		logger.info("AuthInterceptor uri == " + uri);
		
		if( uri.indexOf("admin") > 0 ){
			//관리자 일때
			if(loginVo != null){
				if( !loginVo.getAuth().equals("9") && uri.indexOf("login") == -1 ){
					response.sendRedirect("/admin/loginForm.do");
					return false;
				}
			}else if(loginVo == null && uri.indexOf("login") == -1){
				response.sendRedirect("/admin/loginForm.do");
				return false;
			}
		}else{
			
			if( !(uri.indexOf("login") > 0 || uri.indexOf("logout") > 0) ){
				if(loginVo == null){
					response.sendRedirect("/");
					return false;
				}
			}
		}
							
		
		return true;
	}
	
}
