package com.se.joy.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.se.joy.model.CodeVO;
import com.se.joy.service.MainService;

@Component
public class CodeManager {
	private static final Logger logger = LoggerFactory.getLogger(CodeManager.class);
	@Autowired
	public MainService mainService;
	@Autowired
	private static MainService st_mainService;
	/** 코드(정보) 맵 */
	private static LinkedHashMap<String, CodeVO> codeMap = new LinkedHashMap<String, CodeVO>();
	/** 목록 맵 */
	private static LinkedHashMap<String, List<CodeVO>> listMap = new LinkedHashMap<String, List<CodeVO>>();
	/** step 목록 맵 */
	private static LinkedHashMap<String, List<HashMap<String, String>>> stepListMap = new LinkedHashMap<String, List<HashMap<String, String>>>();
	
	@PostConstruct
	public void setInitialize() {

		try {
			HashMap<String, String> param = new HashMap<String, String>();
			List<CodeVO> list = mainService.getCodeList(param);
			List<CodeVO> listSub = new ArrayList<CodeVO>();
			listSub.addAll(list);

			if(list.size() > 0) {
				List<CodeVO> topList = new ArrayList<CodeVO>();
				
				CodeVO row = null;
				String codeCd = null;

				for(int i=0, len=list.size(); i<len; i++){
					row = list.get(i);
					//최상위 코드 등록
					if (row.getDepth() == 0 ) {
						CodeVO topCodeVo = CodeVO.infoVO(row);
						topList.add(topCodeVo);
					}
					
					codeCd = row.getCode();
					CodeVO codeVo = CodeVO.infoVO(row);
					codeMap.put(codeCd, codeVo);
					
					List<CodeVO> subList = new ArrayList<CodeVO>();
					CodeVO rowSub = null;
					for(int j=0, slen = listSub.size(); j < slen; j++){

						rowSub = listSub.get(j);
						if(codeCd.equals(rowSub.getParent_cd())){
							CodeVO subCodeVo = CodeVO.infoVO(rowSub);
							subList.add(subCodeVo);
							
							// class의 하위 코드일때만 스텝 가져옴.
							String subCodeCd = subCodeVo.getCode();
							if(subCodeCd.substring(0, 3).equals("001")){
								List<HashMap<String, String>> stepList = mainService.getWordStep(subCodeCd);
								stepListMap.put(subCodeCd, stepList);
							}
						}
					}
					
					if(subList.size() > 0) {
						listMap.put(codeCd, subList);
					}
					
				}
				
				listMap.put("", topList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* @Method Name  :
	* @Date      	: 2013. 12. 4.
	* @Author      	: seongeun
	* @Description 	: 코드 이름
	*
	* @param code
	* @return
	*/
	public static String codeName(String code) {
		CodeVO codeVo = codeMap.get(code);
		return codeVo.getCode_nm();
	}
	
	/**
	* @Method Name  :
	* @Date      	: 2013. 12. 9.
	* @Author      	: seongeun
	* @Description 	: 하위 코드 리스트
	*
	* @param code
	* @return
	*/
	public static List<CodeVO> codeList(String code) {
		List<CodeVO> list = new ArrayList<CodeVO>();
		if(listMap.containsKey(code)){
			list = listMap.get(code);
		}
		return list;
	}
	
	/**
	* @Method Name  :
	* @Date      	: 2013. 12. 9.
	* @Author      	: seongeun
	* @Description 	: step list
	*
	* @param code
	* @return
	*/
	public static List<HashMap<String,String>> stepList(String code) {
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		if(stepListMap.containsKey(code)){
			list = stepListMap.get(code);
		}
		return list;
	}
	
	/**
	* @Method Name  :
	* @Date      	: 2013. 12. 9.
	* @Author      	: seongeun
	* @Description 	: 같은 부모 코드를 가지고 있는 코드 리스트
	*
	* @param code
	* @return
	*/
	public static List<CodeVO> sameParentList(String code) {
		List<CodeVO> list = new ArrayList<CodeVO>();
		CodeVO codeVo = codeMap.get(code);
		String parent = codeVo.getParent_cd();
		if(listMap.containsKey(parent)){
			list = listMap.get(parent);
		}
		return list;
	}
	
	/**
	* @Method Name  :
	* @Date      	: 2013. 12. 9.
	* @Author      	: seongeun
	* @Description 	: 새로운 코드 만들기
	*
	* @param newCode
	*/
	public static void newCode(CodeVO newCode) {
		//부모 코드와 이름은 가지고 있음.
		String prntCd = newCode.getParent_cd();
		List<CodeVO> list = codeList(prntCd);
		int newSort = 0,
			newDepth = 0,
			oldNum = 0,
			newNum = 0;
		Iterator<CodeVO> itr = list.iterator();
		if(list.size() > 0){
			//같은 그룹의 코드들이 이미 있을때
			while(itr.hasNext()){
				CodeVO codeVo = itr.next();
				newNum = codeVo.getSort();
				if(oldNum < newNum){
					newDepth = codeVo.getDepth();
					newSort = codeVo.getSort() + 1;
					oldNum = newNum;
				}
			}
		}else{
			// 처음 만들어지는 하위 코드 일때
			CodeVO codeVo = codeMap.get(prntCd);
			newDepth = codeVo.getDepth() + 1;
			newSort = 1;
		}
		
		int len = (""+newSort).length();
		String newCode_cd = prntCd.substring(0, 9-len);
		
		newCode.setCode(newCode_cd+newSort);
		newCode.setDepth(newDepth);
		newCode.setSort(newSort);
		
		//listMap에 넣어줌.
		List<CodeVO> subList = listMap.get(prntCd);
		if(subList == null){
			subList = new ArrayList<CodeVO>();
			subList.add(newCode);
			listMap.put(prntCd, subList);
		}else{
			subList.add(newCode);
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
	}
	
}
