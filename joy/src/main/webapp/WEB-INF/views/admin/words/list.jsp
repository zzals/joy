<%@ page session="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/classes/spring/tld/code.tld" prefix="cd" %>
<% request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
	<title>Home</title>
	
<c:import url="/WEB-INF/views/admin/common/head.jsp"></c:import>

<script type="text/javascript">
$(function(){
	var option = {
        totalSize : ${wordTotCnt},
        pageNo : ${page},
        pageSize : ${pageSize},  // 글 목록 갯수
        pageListSize : 10,  //페이지 갯수
        pageClickFunctionName : 'pageClick'
	};
	$('.pagebar').commonPaging(option);
	
	var gradeVal = '${grade}';
	var stageVal = '${stage}';
	var stepVal  = '${step}';

	if(gradeVal != ''){
		$('select[name=grade]').val(gradeVal);
		gradeSelected(gradeVal, stageVal);
		if(stageVal != ''){
			stageSelected(stageVal, stepVal);
		}
	}
	

	$('select[name=grade]').change(function(){
		var val =  $(this).val();
		if(val == ""){
			$('select[name=stage]').hide();
			$('select[name=step]').hide();
		}else{
			gradeSelected(val, "");
		}
	});
	$('select[name=stage]').change(function(){
		var val = $(this).val();
		if(val == ""){
			$('select[name=step]').hide();
		}else{
			stageSelected(val, "");
		}
	});
	$('#searchBtn').click(function(){	
		var frm = document.frm;
		frm.action = "<c:url value='/admin/words/list.do' />";
		frm.submit();
		
		return false;
	});
	
});

function gradeSelected(gVal, sVal){
	$.ajax({
		type : "POST",
		url : "<c:url value='/word/step.do' />",
		data : {
			grade : gVal
		},
		dataType : "html",
		success : function(data){
			$('select[name=stage]').show();
			$('select[name=stage]').html(data);
			$('select[name=stage]').prepend("<option value=''>전체</option>");
			$('select[name=stage]').val(sVal);
		}
	});
}

function stageSelected(sVal, tVal){
	$.ajax({
		type : "POST",
		url : "<c:url value='/word/step.do' />",
		data : {
			stage : sVal
		},
		dataType : "html",
		success : function(data){
			$('select[name=step]').show();
			$('select[name=step]').html(data);
			$('select[name=step]').prepend("<option value=''>전체</option>");
			$('select[name=step]').val(tVal);
		}
	});
}

function pageClick(page){
	var frm = document.frm;
	frm.page.value = page;
	frm.action = "<c:url value='/admin/words/list.do' />";
	frm.submit();
}

function wordModifyForm(idx){
	var frm = document.frm;
	frm.idx.value = idx;
	frm.action = "<c:url value='/admin/words/modifyForm.do' />";
	frm.submit();
	
}

</script>	
</head>
<body>
	<div id="wrap">
		<c:import url="/WEB-INF/views/admin/common/header.jsp"></c:import>	
		<div class="left">
			<ul class="navi">
				<li><a href="<c:url value='/admin/words/list.do' />" title="단어관리" >WORDS</a></li>
				<li><a href="<c:url value='/admin/member/insertForm.do' />" title="학생관리" >MEMBER</a></li>
			</ul>
		</div>
		<div id="adminContent">	
			<h3>LIST</h3>
			<form name="frm" method="post">
				<input type="hidden" name="page" value="${page}" />
				<input type="hidden" name="pageSize" value="${pageSize}" />
				<input type="hidden" name="idx" value="" />
				<div class="searchBox">
					<select name="grade" >
						<option value="">전체</option>
						<c:forEach var="map" items="${cd:cdLi('001000000')}" >
							<option value="${map.code}">${map.code_nm}</option>
						</c:forEach>
					</select>
					
					<select name="stage" style="display:none;">
						
					</select>
					<select name="step" style="display:none;">
	
					</select>
					<button id="searchBtn" value="" >SEARCH</button>
				</div>
			</form>

			<div id="">
				<table class="tb_style">
					<colgroup>
						<col width="40px;">
						<col width="60px;">
						<col width="60px;">
						<col width="60px;">
						<col width="60px;">
						<col width="60px;">
						<col width="60px;">
						<col width="60px;">
						<col width="40px;">
						<col width="90px;">
					</colgroup>
					<thead>
						<th>No</th>
						<th>Spelling</th>
						<th>Meaning 1</th>
						<th>Meaning 2</th>
						<th>Meaning 3</th>
						<th>similar 1</th>
						<th>similar 2</th>
						<th>Stage</th>
						<th>Step</th>
						<th></th>
					</thead>
					<tbody>
						<c:forEach var="map" items="${list}">
						<tr>
							<td>${map.idx}</td>
							<td class="bold col_purple">${map.spelling}</td>
							<td>${map.meaning1}</td>
							<td>${map.meaning2}</td>
							<td>${map.meaning3}</td>
							<td>${map.similar1}</td>
							<td>${map.similar2}</td>
							<td>${cd:cdNm(map.stage)}</td>
							<td>${map.step}</td>
							<td><button onclick="wordModifyForm('${map.idx}')">수정</button><button>삭제</button></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<ul class="pagebar"></ul>
			</div>
	
		</div>
		<c:import url="/WEB-INF/views/admin/common/footer.jsp"></c:import>
	</div>
</body>
</html>
