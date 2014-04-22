package com.se.joy.utils;

import java.util.HashMap;

public class CommonUtil {
	
	public static HashMap<String, String> pageNum(HashMap<String, String> param) {
		
		int page = Integer.parseInt(param.get("page"));
		int pageSize = Integer.parseInt(param.get("pageSize"));
		
		int startNum = (page - 1) * pageSize;
		
		param.put("startNum", ""+startNum);
		param.put("endNum", ""+pageSize);
		
		return param;
	}
}
