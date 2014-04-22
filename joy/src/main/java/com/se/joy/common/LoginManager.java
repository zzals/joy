package com.se.joy.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.util.WebUtils;

import com.se.joy.model.LoginVO;
import com.se.joy.model.MemberVO;



public class LoginManager {
	public final static Logger logger = Logger.getLogger(LoginManager.class);
	
	private static long SESSION_UPDATE_TIME = 5 * 1000L * 60L;
	
	private HttpServletRequest request 		= null;
	private HttpServletResponse response 	= null;
	
	private LoginVO loginVO = new LoginVO();
	

	/**
	 * @param request
	 * @param reponse
	 */
	public LoginManager (HttpServletRequest request, HttpServletResponse response) {
		this.request 	= request;
		this.response 	= response;
		this.setAttribute(this.request);
	}
	
	/**
	* @Method Name  : setAttribute
	* @Date      	: 2013. 11. 21.
	* @Author      	: seongeun
	* @Description 	: 로그인정보
	*
	* @param request
	*/
	private void setAttribute (HttpServletRequest request) {
		this.request.setAttribute("MEMBER_LOGIN", loginVO);
	}
	
	public String loginCheck (MemberVO memberVO) throws IOException {
		String msg = "";
		
		if (memberVO == null) {
			// 아이디 미존재
			msg = "존재하지 않는 아이디입니다.";
		} else {
			memberVO.setLogin(true);

			WebUtils.setSessionAttribute(request, "MEMBER_LOGIN", memberVO);
			
			msg = "success";
		}
		
		return msg;
	}
	
}
