<%@ page session="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/classes/spring/tld/code.tld" prefix="cd" %>
<% request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
	<title>Admin</title>
	
<c:import url="/WEB-INF/views/admin/common/head.jsp"></c:import>

<script type="text/javascript">

$(function(){
	$('select[name=schoolGradeCd]').change(function(){
		var val = $(this).val();
		$.ajax({
			url : "<c:url value='/code/ajaxList.do' />",
			data : {code : val},
			dataType : "html",
			success : function(data){
				console.log(data);
				$('select[name=schoolCd]').html(data);
			}
		});
		
		if(val != "002001000"){
			$('select[name=grade]').val(1);
			$('select[name=grade] > option:gt(2)').hide();
		}else{
			$('select[name=grade] > option').show();
		}
		
		$('input[name=schoolNm]').removeAttr("disabled");
	});
	
	$('select[name=schoolCd]').change(function(){
		var val = $(this).val();
		console.log(val);
		if(val != "" && val != null){
			$('input[name=schoolNm]').attr("disabled","disabled");
		}else{
			$('input[name=schoolNm]').removeAttr("disabled");
		}
	});
});

function pageClick(page){
	var frm = document.frm;
	frm.page.value = page;
	frm.action = "<c:url value='/admin/words/list.do' />";
	frm.submit();
}

function memberInsert(){
	var frm = document.frm;
	
	frm.birth.value = frm.birth1.value+"-"+frm.birth2.value+"-"+frm.birth3.value;
	frm.mail.value = frm.mail1.value+"@"+frm.mail2.value;
	frm.mobile.value = frm.mobile1.value+"-"+frm.mobile2.value+"-"+frm.mobile3.value;
	frm.parentTel.value = frm.prntTel1.value+"-"+frm.prntTel2.value+"-"+frm.prntTel3.value;
	
	frm.action = "<c:url value='/admin/member/insert.do' />";
	frm.submit();
	
}

function nextWord(){
	$.ajax({
		url : "<c:url value='/word/next' />",
		dataType : "html",
		success : function(data){
			$('#wordArea').html(data);
		}
	});
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
			<h3>MEMBER INSERT</h3>
			<form name="frm" method="post">
				<input type="hidden" name="birth" />
				<input type="hidden" name="mail" />
				<input type="hidden" name="mobile" />
				<input type="hidden" name="parentTel" />
				
				<div id="">
					<table class="tb_style tb_type1">
						<tbody>
							<tr>
								<th>Id</th>
								<td><input type="text" name="mId" value="" /></td>
							</tr>
							<tr>
								<th>Name</th>
								<td><input type="text" name="mNm" value="" /></td>
							</tr>
							<tr>
								<th>Password</th>
								<td><input type="password" name="mPwd" value="" /></td>
							</tr>
							<tr>
								<th>Birthday</th>
								<td><select name="birth1" class="select_year"></select>년 
									<select name="birth2" class="select_month"></select>월
									<select name="birth3" class="select_day"></select>일
								</td>
							</tr>
							<tr>
								<th>Mail</th>
								<td><input type="text" name="mail1" value="" />@
									<select name="mail2" >
										<option value="naver.com">naver.com</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>Mobile</th>
								<td><input type="text" name="mobile1" value="010" />-
									<input type="text" name="mobile2" value="" />-
									<input type="text" name="mobile3" value="" />
								</td>
							</tr>
							<tr>
								<th>Parent Tel</th>
								<td><input type="text" name="prntTel1" value="010" />-
									<input type="text" name="prntTel2" value="" />-
									<input type="text" name="prntTel3" value="" />
								</td>
							</tr>
							<tr>
								<th>School</th>
								<td>
									<select name="schoolGradeCd" >
										<c:forEach var="map" items="${cd:cdLi('002000000')}" >
											<option value="${map.code}">${map.code_nm}</option>
										</c:forEach>
									</select>
									<select name="schoolCd" >
										<option value="">직접입력</option>
										<c:forEach var="map" items="${cd:cdLi('002001000')}" >
											<option value="${map.code}">${map.code_nm}</option>
										</c:forEach>					
									</select>
									<input type="text" name="schoolNm" />
								</td>
							</tr>
							<tr>
								<th>Grade</th>
								<td>
									<select name="grade" >
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
									</select>학년
									
								</td>
							</tr>
						</tbody>
					</table>
					<button onclick="memberInsert();">등록</button>
					<button onclick="">목록</button>
				</div>
				
			</form>
		</div>
		
		<c:import url="/WEB-INF/views/admin/common/footer.jsp"></c:import>
	</div>
</body>
</html>
