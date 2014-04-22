package com.se.joy.service;

import java.util.HashMap;
import java.util.List;

import com.se.joy.model.WordsVO;

public interface WordService {
	
	public List<WordsVO> getWords(HashMap<String, String> parma);
	public HashMap<String, String> wordCheck(HashMap<String, String> parma);
	  
	public void insertRecord(HashMap<String, String> parma);
	  
	public HashMap<String, String> testCnt(HashMap<String, String> parma);
}
