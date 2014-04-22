package com.se.joy.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se.joy.model.CodeVO;
import com.se.joy.model.MemberVO;
import com.se.joy.service.MainService;
import com.se.joy.service.mapper.MainMapper;

@Service
public class MainServiceImpl implements MainService {
	
	@Autowired
	private MainMapper mainMapper;
	
	public List<CodeVO> getCodeList(HashMap<String, String> param) {
		
		return mainMapper.getCodeList(param);
	}
	
	public MemberVO getMemberInfo(HashMap<String, String> param) {
		return mainMapper.getMemberInfo(param);
	}
	
	public List<HashMap<String, String>> getWordStep(String codeCd) {
		return mainMapper.getWordStep(codeCd);
	}

}
