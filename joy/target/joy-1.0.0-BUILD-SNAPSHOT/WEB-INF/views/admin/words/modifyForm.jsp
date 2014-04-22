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

});

function wordList(){
	var frm = document.frm;
	frm.action = "<c:url value='/admin/words/list.do' />";
	frm.submit();
}

function wordModify(){
	var frm = document.frm;
	frm.action = "<c:url value='/admin/words/modify.do' />";
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
			<h3>MODIFY</h3>
			<form name="frm" method="post">
				<input type="hidden" name="page" value="${page}" />
				<input type="hidden" name="idx" value="${detail.idx}" />
		
				<div id="">
					<table class="tb_style tb_type1">
						<tbody>
							<tr>
								<th>No</th>
								<td>${detail.idx}</td>
							</tr>
							<tr>
								<th>Spelling</th>
								<td><input type="text" name="spelling" value="${detail.spelling}" /></td>
							</tr>
							<tr>
								<th>Meaning 1</th>
								<td><input type="text" name="meaning1" value="${detail.meaning1}" /></td>
							</tr>
							<tr>
								<th>Meaning 2</th>
								<td><input type="text" name="meaning2" value="${detail.meaning2}" /></td>
							</tr>
							<tr>
								<th>Meaning 3</th>
								<td><input type="text" name="meaning3" value="${detail.meaning3}" /></td>
							</tr>
							<tr>
								<th>similar 1</th>
								<td><input type="text" name="similar1" value="${detail.similar1}" /></td>
							</tr>
							<tr>
								<th>similar 2</th>
								<td><input type="text" name="similar2" value="${detail.similar2}" /></td>
							</tr>
							<tr>
								<th>Stage</th>
								<td>
									<select name="stage" >
										<c:forEach var="map" items="${cd:smPrntLi(detail.stage)}" >
											<option value="${map.code}">${map.code_nm}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th>Step</th>
								<td><input type="text" name="step" value="${detail.step}" /></td>
							</tr>
						</tbody>
					</table>
					<button onclick="wordModify();">수정</button>
					<button onclick="wordList();">목록</button>
				</div>
			</form>
		</div>
		
		<c:import url="/WEB-INF/views/admin/common/footer.jsp"></c:import>
	</div>
</body>
</html>
