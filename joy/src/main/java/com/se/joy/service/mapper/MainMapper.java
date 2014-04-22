package com.se.joy.service.mapper;

import java.util.HashMap;
import java.util.List;

import com.se.joy.model.CodeVO;
import com.se.joy.model.MemberVO;
import com.se.joy.model.WordsVO;

public interface MainMapper {
	public List<CodeVO> getCodeList(HashMap<String, String> param);

	public MemberVO getMemberInfo(HashMap<String, String> param);
	
	public List<HashMap<String , String>> getWordStep (String code);
}


