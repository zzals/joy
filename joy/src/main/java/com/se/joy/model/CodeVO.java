package com.se.joy.model;

import java.util.HashMap;

public class CodeVO {
	
	private String code;
	private String parent_cd;
	private String code_nm;
	private int depth;
	private int sort;
	private int state;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getParent_cd() {
		return parent_cd;
	}
	public void setParent_cd(String parent_cd) {
		this.parent_cd = parent_cd;
	}
	public String getCode_nm() {
		return code_nm;
	}
	public void setCode_nm(String code_nm) {
		this.code_nm = code_nm;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	public static CodeVO infoVO(CodeVO info) {
		CodeVO codeVO = new CodeVO();
		codeVO.setCode(info.getCode());
		codeVO.setParent_cd(info.getParent_cd());
		codeVO.setCode_nm(info.getCode_nm());
		codeVO.setDepth(info.getDepth());	
		codeVO.setSort(info.getSort());
		codeVO.setState(info.getState());
		return codeVO;
	}	
	
}
