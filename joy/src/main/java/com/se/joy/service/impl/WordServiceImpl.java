package com.se.joy.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se.joy.model.WordsVO;
import com.se.joy.service.WordService;
import com.se.joy.service.mapper.WordMapper;

@Service
public class WordServiceImpl implements WordService {
	
	@Autowired
	private WordMapper wordMapper;
	
	public List<WordsVO> getWords(HashMap<String, String> param) {
		
		return wordMapper.getWords(param);
	}
	
	public HashMap<String, String> wordCheck(HashMap<String, String> param){
		return wordMapper.wordCheck(param);
	}
	
	  
	public void insertRecord(HashMap<String, String> param) {
	    this.wordMapper.insertRecord(param);
	}
	  
	public HashMap<String, String> testCnt(HashMap<String, String> param) {
	    return this.wordMapper.testCnt(param);
	}
}
