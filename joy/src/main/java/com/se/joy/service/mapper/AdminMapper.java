package com.se.joy.service.mapper;

import java.util.HashMap;
import java.util.List;

import com.se.joy.model.CodeVO;
import com.se.joy.model.MemberVO;
import com.se.joy.model.WordsVO;

public interface AdminMapper {
	public List<WordsVO> getWordList(HashMap<String, String> param);
	public int getWordTotCnt(HashMap<String, String> param);
	public WordsVO getWordDetail(HashMap<String, String> param);
	public void wordUpdate(HashMap<String, String> param);
	
	public List<MemberVO> getMemberList(HashMap<String, String> parma);
	public void memberInsert(HashMap<String, String> parma);
	public MemberVO getMemberDetail(HashMap<String, String> param);
	public void memberUpdate(HashMap<String, String> parma);
	public void memberDelete(HashMap<String, String> param);
	
	public void codeInsert(CodeVO codeVo);
	
	public MemberVO getAdminInfo(HashMap<String, String> param);
}
