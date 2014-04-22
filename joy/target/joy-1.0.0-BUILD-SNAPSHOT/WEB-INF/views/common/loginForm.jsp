<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>

<c:import url="/WEB-INF/views/admin/common/head.jsp"></c:import>

<script type="text/javascript">
$(function(){
	$('input').on("keydown", function(event){
		if(event.keyCode == 13){
			login();
		}
	});
});

	function login(){
		var frm = document.frm,
			validator = Validation(),
			data = {
				ID : frm.mId,
				Password : frm.mPwd
			};
			
		validator.config = {
				ID : ['isNonEmpty','isOnlyEngNumber'],
				Password : ['isNonEmpty']
		};
		
		if(!validator.validate(data)){
			return;
		}
		
		$.ajax({
			url  : '<c:url value="/login.do" />',
			type : "POST",
			dataType: 'json',
			data : {
				mId		: $("input[name=mId]").val(),
				mPwd	: $("input[name=mPwd]").val()
			},
			complete : function(res){
				if(!res.status == 200){
					alert("통신 에러가 발생하였습니다.");
				}
			},
			success : function (res){
				if (res.msg == "success") {
					location.href = '<c:url value="/index.do" />';
				} else {
					alert(res.msg);
				}
			}
		});
	}
</script>
</head>
<body>
	<center>
	
	<div id="wrap">
		<div id="header">
			<div id="util">
				<%-- <a href="<c:url value='/common/loginForm.do' />">LOGOUT</a> --%>
				<a href="<c:url value='/admin/index.do' />">ADMIN</a>
			</div>
			
		</div>
		<h1 class="h1">JOY-WORDMASTER</h1>
		<form name="frm" method="post" >
			<div>
				<p><span class="login">ID</span><input type="text" name="mId" /></p>
				<p><span class="login">PASSWORD</span><input type="password" name="mPwd" /></p>
			</div>
		</form>
		<button onclick="login();">로그인</button>
		
	</div>
	
	<!-- <div id="footer">
		copyright seongeun
	</div> -->
	</center>
	
</body>
</html>