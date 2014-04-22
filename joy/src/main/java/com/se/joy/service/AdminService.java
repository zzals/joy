package com.se.joy.service;

import java.util.HashMap;
import java.util.List;

import com.se.joy.model.MemberVO;
import com.se.joy.model.WordsVO;

public interface AdminService {
	
	public List<WordsVO> getWordList(HashMap<String, String> param);
	public int getWordTotCnt(HashMap<String, String> param);
	public WordsVO getWordDetail(HashMap<String, String> param);
	public void wordUpdate(HashMap<String, String> param);
	
	public List<MemberVO> getMemberList(HashMap<String, String> param);
	public void memberInsert(HashMap<String, String> param);
	public MemberVO getMemberDetail(HashMap<String, String> param);
	public void memberUpdate(HashMap<String, String> param);
	public void memberDelete(HashMap<String, String> param);
	
	
	public MemberVO getAdminInfo(HashMap<String, String> param);
}
