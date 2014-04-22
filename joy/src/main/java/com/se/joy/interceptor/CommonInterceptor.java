package com.se.joy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.se.joy.model.LoginVO;


public class CommonInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);
	
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
		
		String uri = request.getRequestURI();
		logger.info("before common controller...[" + uri + "]");
		
		LoginVO loginVo = (LoginVO) WebUtils.getSessionAttribute(request, "MEMBER_LOGIN");
		

			
		if(loginVo == null){
			if( !(uri.indexOf("index") > 0 || uri.indexOf("login") > 0) ){
				logger.info("false");
				response.sendRedirect("/");             
				return false;				
			}
		}
		logger.info("true");
		return true;
	}
	
	

}
