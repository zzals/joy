package com.se.joy.service.mapper;

import java.util.HashMap;
import java.util.List;

import com.se.joy.model.WordsVO;

public interface WordMapper {
	public List<WordsVO> getWords(HashMap<String, String> param);
	public HashMap<String, String> wordCheck(HashMap<String, String> parma);
	public void insertRecord(HashMap<String, String> param);
	public HashMap<String, String> testCnt(HashMap<String, String> param);
}
