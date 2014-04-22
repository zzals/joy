package com.se.joy.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.WebUtils;

import com.se.joy.model.WordsVO;

public class WordRandomUtil {

	public static WordsVO randomWord (HttpServletRequest request) throws Exception {
		Random random = new Random();
		//세션에 담긴 단어 리스트를 가져옴 
		List<WordsVO> list = (List<WordsVO>)WebUtils.getSessionAttribute(request, "WORDS_LIST");
		
		//랜덤 선택
		int len = list.size();
		int idx = random.nextInt(len);
		WordsVO wordVo = list.get(idx);
		
		//선택된 단어는 지우고 다시 세션에 저장.
		list.remove(idx);
		if(len == 1){
		
		}
		WebUtils.setSessionAttribute(request, "WORDS_LIST", list);
		
		return wordVo;
	}
	
	public static List<WordsVO> randomExample (HttpServletRequest request, WordsVO wordVo) throws Exception {
		
		List<WordsVO> list = (List<WordsVO>)WebUtils.getSessionAttribute(request, "EXAMPLE_LIST");
		
		Random random = new Random();
		List<WordsVO> exampleList = new ArrayList<WordsVO>();
		
		WordsVO word = null;
		
		int flag = 0, idx, num = 0;
		int len = list.size();
		boolean chk = true;
		
		do{
			idx = random.nextInt(len);
			
			if(flag == idx){ //같은 단어가 나오면 다시 실행 하기 위해
				chk = true;
			}else{
				word = list.get(idx);
				if(wordVo.getIdx() != word.getIdx()){ // 지문과 같은 단어가 나오면 다시 실행
					exampleList.add(word);
					flag = idx;
					num ++;		
				}
			}
			
			if(num == 2){ // 보기는 2개 까지만
				chk = false;
			}
			
		}while(chk);
		
		return exampleList;
	}
	
	  public static List<String> similarExample(HttpServletRequest request, WordsVO wordVo) throws Exception {
		  Random random = new Random();
		  List<String> exampleList = new ArrayList();
	    
		  String[] example = { wordVo.getMeaning1(), wordVo.getSimilar1(), wordVo.getSimilar2() };
		  int len = example.length;
		  int tmp = 0;
	    
		  int[] flag = new int[len];
		  for (int i = 0; i < len; i++) {
			  flag[i] = i;
		  }
		  for (int i = 0; i < len; i++)
		  {
			  int idx = random.nextInt(len);
			  tmp = flag[i];
			  flag[i] = flag[idx];
			  flag[idx] = tmp;
		  }
		  for (int i = 0; i < len; i++)
		  {
			  String val = example[flag[i]];
			  exampleList.add(val);
		  }
		  return exampleList;
	  }
}
