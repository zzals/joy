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
import com.se.joy.model.LoginVO;
import com.se.joy.model.MemberVO;
import com.se.joy.model.WordsVO;
import com.se.joy.service.WordService;
import com.se.joy.utils.WordRandomUtil;

@Controller
@RequestMapping("/word")
public class WordController {
	private static final Logger logger = LoggerFactory.getLogger(WordController.class);
	
	@Autowired
	private WordService wordService;
	
	@RequestMapping("/test")
	public ModelAndView wordList (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView,
			@RequestParam(required=false, defaultValue="001001000") String grade,
			@RequestParam(required=false, defaultValue="001001010") String stage,
			@RequestParam(required=false, defaultValue="1") String step) throws Exception {
		
		ModelMap modelMap = modelAndView.getModelMap();
		
		HashMap<String, String> param = new HashMap<String, String>();
		
		param.put("grade", grade);
		param.put("stage", stage);
		param.put("step", step);
		
		List<WordsVO> list = wordService.getWords(param);
		
		// 단어 리스트 세션에 저장.
		WebUtils.setSessionAttribute(request, "WORDS_LIST", list);
		WebUtils.setSessionAttribute(request, "EXAMPLE_LIST", list);
		
		modelMap.addAttribute("grade", grade);
		modelMap.addAttribute("stage", stage);
		modelMap.addAttribute("step", step);
		
		return modelAndView;
	}
	
	@RequestMapping({"/step"})
	public ModelAndView wordStep(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView,
			@RequestParam(required=false, defaultValue="") String grade, 
			@RequestParam(required=false, defaultValue="") String stage) throws Exception {
	    
		ModelMap modelMap = modelAndView.getModelMap();
	    
	    modelMap.addAttribute("grade", grade);
	    modelMap.addAttribute("stage", stage);
	    
	    return modelAndView;
	}
	
	@RequestMapping("/next")
	public ModelAndView wordNext (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception {
		
		ModelMap modelMap = modelAndView.getModelMap();
		// 단어 랜덤 선택		
		WordsVO wordVo = WordRandomUtil.randomWord(request);
		//List<WordsVO> exampleList = WordRandomUtil.randomExample(request, wordVo);
		//exampleList.add(wordVo);
		
		List<String> exampleList = WordRandomUtil.similarExample(request, wordVo);
		
		// 보기 리스트를 다시 랜덤으로 
		Collections.shuffle(exampleList);
		
		modelMap.addAttribute("word", wordVo);
		modelMap.addAttribute("list", exampleList);
		
	//	modelAndView.setViewName("/word/test");
		
		return modelAndView;
	}
	
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
	
	@RequestMapping({"/nextStep"})
	public String nextStep(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, 
			@RequestParam(required=false, defaultValue="001001010") String stage, 
			@RequestParam(required=false, defaultValue="1") String step) throws Exception  {
	    
		ModelMap modelMap = modelAndView.getModelMap();
	    
	    HashMap<String, String> param = new HashMap();
	    
	    param.put("stage", stage);
	    param.put("step", step);
	    
	    List<WordsVO> list = this.wordService.getWords(param);
	    

	    WebUtils.setSessionAttribute(request, "WORDS_LIST", list);
	    WebUtils.setSessionAttribute(request, "EXAMPLE_LIST", list);
	    
	    modelMap.addAttribute("stage", stage);
	    modelMap.addAttribute("step", step);
	    
	    modelAndView.setViewName("forward:/word/next");
	    
	    return "forward:/word/next.do";
	}
	
	@RequestMapping({"/testCnt"})
	public void ajaxTestCnt(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception {
	    ModelMap model = modelAndView.getModelMap();
	    
	    MemberVO loginVo = (MemberVO)WebUtils.getSessionAttribute(request, "MEMBER_LOGIN");
	    
	    HashMap<String, String> param = new HashMap();
	    
	    param.put("m_idx", loginVo.getIdx());
	    
	    HashMap<String, String> result = this.wordService.testCnt(param);
	    
	    Gson gson = new Gson();
	    String jsonString = gson.toJson(result);
	    
	    response.setContentType("application/json; charset=UTF-8");
	    response.getWriter().write(jsonString);
	}
	  
	@RequestMapping({"/record"})
	public void recode(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, 
			@RequestParam(required=false, defaultValue="001001000") String grade, 
			@RequestParam(required=false, defaultValue="001001010") String stage, 
			@RequestParam(required=false, defaultValue="1") String step, 
			@RequestParam(required=false, defaultValue="0") String point) throws Exception  {
	    
		MemberVO loginVo = (MemberVO)WebUtils.getSessionAttribute(request, "MEMBER_LOGIN");
	    
	    ModelMap modelMap = modelAndView.getModelMap();
	    
	    HashMap<String, String> param = new HashMap();
	    
	    param.put("m_idx", loginVo.getIdx());
	    param.put("point", point);
	    param.put("stage", stage);
	    param.put("step", step);
	    
	    this.wordService.insertRecord(param);
	    
	    modelMap.addAttribute("grade", grade);
	    modelMap.addAttribute("stage", stage);
	    modelMap.addAttribute("step", step);
	}
	
	
	
}
