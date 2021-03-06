<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Admin</title>

<c:import url="/WEB-INF/views/admin/common/head.jsp"></c:import>

<script type="text/javascript">
$(function(){
	$('input').on("keydown", function(event){
		if(event.keyCode == 13){
			login();
		}
	});
});

	function goWords(){
		var frm = document.frm,
			validator = Validation(),
			data = {
				ID : frm.adminId,
				Password : frm.adminPwd
			};
			
		validator.config = {
				ID : ['isNonEmpty','isOnlyEngNumber'],
				Password : ['isNonEmpty']
		};
		
		if(!validator.validate(data)){
			return;
		}
		
		$.ajax({
			url  : "<c:url value='/admin/login.do' />",
			type : "POST",
			dataType: 'json',
			data : {
				adminId		: $("input[name=adminId]").val(),
				adminPwd	: $("input[name=adminPwd]").val()
			},
			/* complete : function(res){
				console.log(res);
				if(res.status == 200){
					alert(res);
				}else{
					alert("통신 에러가 발생하였습니다.");
				}
			} */
			success : function (res){
				if (res.msg == "success") {
					alert(res.msg);
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
			<c:import url="/WEB-INF/views/admin/common/header.jsp"></c:import>	
			<ul class="navi">
				<li><a href="<c:url value='/admin/words/list.do' />" title="단어관리" >WORDS</a></li>
				<li><a href="<c:url value='/admin/member/insertForm.do' />" title="학생관리" >MEMBER</a></li>
			</ul>
	</div>

	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>		
</center>
</body>
</html>