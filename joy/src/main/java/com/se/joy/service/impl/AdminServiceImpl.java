package com.se.joy.service.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se.joy.common.CodeManager;
import com.se.joy.model.CodeVO;
import com.se.joy.model.MemberVO;
import com.se.joy.model.WordsVO;
import com.se.joy.service.AdminService;
import com.se.joy.service.mapper.AdminMapper;
import com.se.joy.utils.CommonUtil;

@Service
public class AdminServiceImpl implements AdminService {
	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
	@Autowired
	private AdminMapper adminMapper;
	
	public List<WordsVO> getWordList(HashMap<String, String> param) {
		
		param = CommonUtil.pageNum(param);
		
		return adminMapper.getWordList(param);
	}
	
	public int getWordTotCnt(HashMap<String, String> param) {
		return adminMapper.getWordTotCnt(param);
	}
	
	public WordsVO getWordDetail(HashMap<String, String> param) {		
		return adminMapper.getWordDetail(param);
	}
	
	public void wordUpdate(HashMap<String, String> param) {
		adminMapper.wordUpdate(param);
	}
	
	public List<MemberVO> getMemberList(HashMap<String, String> param) {
		return adminMapper.getMemberList(param);
		
	}
	
	public void memberInsert(HashMap<String, String> param) {
		
		// 학교 직접 입력이면 코드에 추가
		if( !(param.get("schoolNm").equals("") || param.get("schoolNm") == null) ){
			String schoolCd = codeInsert(param);
			param.put("schoolCd",schoolCd);
		}
		
		logger.info("code : "+ param.get("schoolCd"));
		
		adminMapper.memberInsert(param);
	}
	
	public MemberVO getMemberDetail(HashMap<String, String> param) {
		return adminMapper.getMemberDetail(param);
		
	}
	
	public void memberUpdate(HashMap<String, String> param) {
		
		// 학교 직접 입력이면 코드에 추가
		if( !(param.get("schoolNm").equals("") || param.get("schoolNm") == null) ){
			String schoolCd = codeInsert(param);
			param.put("schoolCd",schoolCd);
		}
		
		logger.info("code : "+ param.get("schoolCd"));
		
		adminMapper.memberUpdate(param);
	}
	
	public void memberDelete(HashMap<String, String> param) {
		adminMapper.memberDelete(param);
	}
	
	public String codeInsert(HashMap<String, String> param) {
		
		CodeVO codeVo = new CodeVO();
		codeVo.setParent_cd(param.get("schoolGradeCd"));
		codeVo.setCode_nm(param.get("schoolNm"));
		
		CodeManager.newCode(codeVo);
		
		logger.info("code : "+ codeVo.getCode());
		logger.info("code : "+ codeVo.getCode_nm());
		logger.info("code : "+ codeVo.getParent_cd());
		logger.info("code : "+ codeVo.getDepth());
		logger.info("code : "+ codeVo.getSort());
		
		adminMapper.codeInsert(codeVo);
		
		return codeVo.getCode();
	}
	
	public MemberVO getAdminInfo(HashMap<String, String> param){
		return adminMapper.getAdminInfo(param);
	}

}
