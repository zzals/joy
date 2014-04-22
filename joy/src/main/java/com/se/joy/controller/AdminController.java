package com.se.joy.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.se.joy.model.MemberVO;
import com.se.joy.model.WordsVO;
import com.se.joy.service.AdminService;
import com.se.joy.service.WordService;
import com.se.joy.utils.EncryptionUtil;
import com.se.joy.utils.WordRandomUtil;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("/index")
	public ModelAndView index (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception {
		
		ModelMap modelMap = modelAndView.getModelMap();
		
		return modelAndView;
	}
	
	@RequestMapping("/words/list")
	public ModelAndView wordList (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView,
			@RequestParam(required=false) String grade,
			@RequestParam(required=false) String stage,
			@RequestParam(required=false) String step,
			@RequestParam(required=false, defaultValue="1") String page,
			@RequestParam(required=false, defaultValue="20") String pageSize) throws Exception {
		
		ModelMap modelMap = modelAndView.getModelMap();
		
		HashMap<String, String> param = new HashMap<String, String>();
		
		param.put("stage", stage);
		param.put("step", step);
		param.put("page", page);
		param.put("pageSize", pageSize);
		
		List<WordsVO> list = adminService.getWordList(param);
		int wordTotCnt = adminService.getWordTotCnt(param);
		
		modelMap.addAttribute("list", list);
		modelMap.addAttribute("grade", grade);
		modelMap.addAttribute("stage", stage);
		modelMap.addAttribute("step", step);
		modelMap.addAttribute("page", page);
		modelMap.addAttribute("pageSize", pageSize);
		modelMap.addAttribute("wordTotCnt", wordTotCnt);
		
		return modelAndView;
	}
	
	@RequestMapping("/words/modifyForm")
	public ModelAndView wordModifyForm (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView,
			@RequestParam(required=false, defaultValue="1") String page,
			@RequestParam String idx ) throws Exception {
		
		ModelMap modelMap = modelAndView.getModelMap();
		
		HashMap<String, String> param = new HashMap<String, String>();
		
		param.put("idx", idx);
		
		WordsVO detail = adminService.getWordDetail(param);
		
		modelMap.addAttribute("detail", detail);
		modelMap.addAttribute("page", page);
		
		return modelAndView;
	}
	
	@RequestMapping("/words/modify")
	public ModelAndView wordModify (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView,
			@RequestParam String idx,
			@RequestParam String spelling,
			@RequestParam String meaning1,
			@RequestParam String meaning2,
			@RequestParam String meaning3,
			@RequestParam String similar1,
			@RequestParam String similar2,
			@RequestParam String stage,
			@RequestParam String step,
			@RequestParam String page) throws Exception {
		
		ModelMap modelMap = modelAndView.getModelMap();
		
		HashMap<String, String> param = new HashMap<String, String>();
		
		param.put("idx", idx);
		param.put("spelling", spelling);
		param.put("meaning1", meaning1);
		param.put("meaning2", meaning2);
		param.put("meaning3", meaning3);
		param.put("similar1", similar1);
		param.put("similar2", similar2);
		param.put("stage", stage);
		param.put("step", step);
		
		adminService.wordUpdate(param);
		
		modelMap.addAttribute("page", page);
		
		modelAndView.setViewName("redirect:/admin/words/list.do");
		
		return modelAndView;
	}
	
	
	
	
	@RequestMapping("/member/list")
	public ModelAndView memberList (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception {
		
		ModelMap modelMap = modelAndView.getModelMap();
		
		HashMap<String, String> param = new HashMap<String, String>();
		
		List<MemberVO> list = adminService.getMemberList(param);
		
		modelMap.addAttribute("list", list);
		
		return modelAndView;
	}
	
	
	@RequestMapping("/member/insertForm")
	public ModelAndView memberInsertForm (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception {
		
		ModelMap modelMap = modelAndView.getModelMap();
		
		return modelAndView;
	}
	
	@RequestMapping("/member/modifyForm")
	public ModelAndView memberModifyForm (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView,
			@RequestParam String idx) throws Exception {
		
		ModelMap modelMap = modelAndView.getModelMap();
		
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("idx", idx);
		
		MemberVO member = adminService.getMemberDetail(param);
		
		modelMap.addAttribute("member", member);
		
		return modelAndView;
	}
	
	@RequestMapping("/member/insert")
	public ModelAndView memberInsert (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView,
			@RequestParam(required=false) String mId,
			@RequestParam(required=false) String mNm,
			@RequestParam(required=false) String mPwd,
			@RequestParam(required=false) String birth,
			@RequestParam(required=false) String mail,
			@RequestParam(required=false) String mobile,
			@RequestParam(required=false) String parentTel,
			@RequestParam(required=false) String schoolGradeCd,
			@RequestParam(required=false) String schoolCd,
			@RequestParam(required=false, defaultValue="") String schoolNm,
			@RequestParam(required=false) String grade) throws Exception {
		
		ModelMap modelMap = modelAndView.getModelMap();
		
		HashMap<String, String> param = new HashMap<String, String>();
		
		param.put("mId", mId);
		param.put("mNm", mNm);
		param.put("mPwd", EncryptionUtil.encrypt(mPwd));
		param.put("birth", birth);
		param.put("mail", mail);
		param.put("mobile", mobile);
		param.put("parentTel", parentTel);
		param.put("schoolGradeCd", schoolGradeCd);
		param.put("schoolCd", schoolCd);
		param.put("schoolNm", schoolNm);
		param.put("grade", grade);
		
		adminService.memberInsert(param);
		modelAndView.setViewName("redirect:/admin/member/list.do");
		return modelAndView;
	}
	
	@RequestMapping("/member/update")
	public ModelAndView memberUpdate (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView,
			@RequestParam(required=true) String mId,
			@RequestParam(required=true) String mNm,
			@RequestParam(required=false) String mPwd,
			@RequestParam(required=false) String birth,
			@RequestParam(required=false) String mail,
			@RequestParam(required=false) String mobile,
			@RequestParam(required=false) String schoolGradeCd,
			@RequestParam(required=false) String schoolCd,
			@RequestParam(required=false, defaultValue="") String schoolNm,
			@RequestParam(required=false) String grade) throws Exception {
		
		ModelMap modelMap = modelAndView.getModelMap();
		
		HashMap<String, String> param = new HashMap<String, String>();
		
		param.put("mId", mId);
		param.put("mNm", mNm);
		if(mPwd != null && !mPwd.equals("")){
			param.put("mPwd", EncryptionUtil.encrypt(mPwd));			
		}
		param.put("birth", birth);
		param.put("mail", mail);
		param.put("mobile", mobile);
		param.put("schoolGradeCd", schoolGradeCd);
		param.put("schoolCd", schoolCd);
		param.put("schoolNm", schoolNm);
		param.put("grade", grade);
		
		adminService.memberUpdate(param);
		modelAndView.setViewName("redirect:/admin/member/list.do");
		return modelAndView;
	}
	
	@RequestMapping("/member/delete")
	public ModelAndView memberDelete (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView,
			@RequestParam(required=true) String idx)  throws Exception {
		
		ModelMap modelMap = modelAndView.getModelMap();
		
		HashMap<String, String> param = new HashMap<String, String>();
		
		param.put("idx", idx);
		
		adminService.memberDelete(param);
		
		modelAndView.setViewName("redirect:/admin/member/list.do");
		return modelAndView;
	}
	
	@RequestMapping("/loginForm")
	public ModelAndView loginForm (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception {
		
		ModelMap modelMap = modelAndView.getModelMap();
		
		return modelAndView;
	}
		
	
	@RequestMapping(value = "/login")
	public void adminLogin (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView,
			@RequestParam("adminId") String adminId,
			@RequestParam("adminPwd") String adminPwd ) throws Exception {
		
		HashMap<String, String> param = new HashMap<String, String>();
		
		param.put("adminId", adminId);
		param.put("adminPwd", EncryptionUtil.encrypt(adminPwd));
		
		LoginManager login = new LoginManager(request, response);
		
		ModelMap result = modelAndView.getModelMap();
		
		try {
			MemberVO memberVO = adminService.getAdminInfo(param);

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
