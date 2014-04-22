package com.se.joy.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.google.gson.Gson;
import com.se.joy.common.CodeManager;
import com.se.joy.common.LoginManager;
import com.se.joy.model.LoginVO;
import com.se.joy.model.MemberVO;
import com.se.joy.model.WordsVO;
import com.se.joy.service.AdminService;
import com.se.joy.service.MainService;
import com.se.joy.service.WordService;
import com.se.joy.utils.EncryptionUtil;
import com.se.joy.utils.WordRandomUtil;

@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	private MainService mainService;
	
	@RequestMapping("/index")
	public ModelAndView index (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView,
			HttpSession session) throws Exception {
		
		ModelMap modelMap = modelAndView.getModelMap();
		
/*		LoginVO loginVo = (LoginVO) WebUtils.getSessionAttribute(request, "MEMBER_LOGIN");
		
		if(loginVo == null){
			modelAndView.addObject("login", false);
		}else{
			modelAndView.addObject("login", true);
		}*/
		
		return modelAndView;
	}
	
	@RequestMapping("/code/ajaxList")
	public ModelAndView wordList (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView,
			@RequestParam(required=false) String code) throws Exception {
		
		ModelMap modelMap = modelAndView.getModelMap();
		
		modelMap.addAttribute("code", code);
		
		return modelAndView;
	}
		
	
	@RequestMapping("/common/loginForm")
	public ModelAndView memberInsertForm (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception {
		
		ModelMap modelMap = modelAndView.getModelMap();
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/login")
	public void memberLogin (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView,
			@RequestParam("mId") String mId,
			@RequestParam("mPwd") String mPwd ) throws Exception {
		
		HashMap<String, String> param = new HashMap<String, String>();
		
		param.put("mId", mId);
		param.put("mPwd", EncryptionUtil.encrypt(mPwd));
		
		LoginManager login = new LoginManager(request, response);
		
		ModelMap result = modelAndView.getModelMap();
		
		try {
			MemberVO memberVO = mainService.getMemberInfo(param);

			String msg = login.loginCheck(memberVO);
			
			logger.info(msg);
			
			result.put("msg", msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		Gson gson = new Gson();
 		String jsonString = gson.toJson(result);
 		logger.info(jsonString);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(jsonString);
	
	}
	
	@RequestMapping(value = "/logout")
	public ModelAndView memberLogout (HttpServletRequest request, HttpServletResponse response, HttpSession session,  ModelAndView modelAndView) throws Exception {
		
		session.removeAttribute("MEMBER_LOGIN");
		modelAndView.setViewName("redirect:/");
		return modelAndView;
	}
	
	/*
	@RequestMapping(value = "/check")
	public void ajaxWordCheck(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView,
			@RequestParam String idx,
			@RequestParam String meaning) throws Exception{
		
		ModelMap model = modelAndView.getModelMap();
		
		logger.debug("meaning ====== " +meaning);
		logger.info("meaning ====== " +meaning);
		
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("idx", idx);
		param.put("meaning", meaning);

		HashMap<String, String> result = wordService.wordCheck(param);
		
		Gson gson = new Gson();
		String jsonString = gson.toJson("SUCCESS");
		if(result == null){
			jsonString = gson.toJson("FALSE");
		}
		
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(jsonString);
	}
	*/
}
