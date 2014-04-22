<%@ page session="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html >
<head>
<title>JOY WORD MASTER</title>
<c:import url="/WEB-INF/views/common/head.jsp"></c:import>
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
		<c:import url="/WEB-INF/views/common/header.jsp"></c:import>			

		
		<h1 class="h1">JOY WORD MASTER</h1>
		<form name="frm" method="post" >
			<div id="loginForm">
				<p><span class="login">ID</span><input type="text" name="mId" /></p>
				<p><span class="login">PASS</span><input type="password" name="mPwd" /></p>
				<button id="loginBtn" onclick="login();return false;">로그인</button>
			</div>
		</form>		
	</div>

		<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>			

	</center>
</body>
</html>
