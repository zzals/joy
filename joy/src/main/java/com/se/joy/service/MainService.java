package com.se.joy.service;

import java.util.HashMap;
import java.util.List;

import com.se.joy.model.CodeVO;
import com.se.joy.model.MemberVO;


public interface MainService {
	
	public List<CodeVO> getCodeList(HashMap<String, String> param);
	
	public MemberVO getMemberInfo(HashMap<String, String> param);
	
	public List<HashMap<String, String>> getWordStep(String codeCd);
}
