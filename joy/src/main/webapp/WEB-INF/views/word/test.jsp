<%@ page session="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/classes/spring/tld/code.tld" prefix="cd" %>
<% request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
	<title>Test</title>
<c:import url="/WEB-INF/views/common/head.jsp"></c:import>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript">
var point = 0;
var startTime;

$(function(){
	$('select[name=grade]').change(function(){
		$.ajax({
			type : "POST",
			url : "<c:url value='/word/step.do' />",
			data : {
				grade : $(this).val()
			},
			dataType : "html",
			success : function(data){
				$('select[name=stage]').html(data).change();
			}
		});	
	});
	$('select[name=stage]').change(function(){
		$.ajax({
			type : "POST",
			url : "<c:url value='/word/step.do' />",
			data : {
				stage : $(this).val()
			},
			dataType : "html",
			success : function(data){
				$('select[name=step]').html(data).change();
			}
		});
		 
	});
	$('select[name=step]').change(function(){
		var frm = document.frm;
		frm.action = "<c:url value='/word/test.do' />";
		frm.submit();
	});
	
	$('#wordArea').on('click','#okBtn', function(){
		var frm = document.frm;
		frm.action = "<c:url value='/word/test.do' />";
		frm.submit();
	});
	
//	$('#wordArea').on('click','#nextBtn', wordCheck);
});

function startTest(){
	$.ajax({
		type : "POST",
		url : "<c:url value='/word/testCnt.do' />",
		data : {
			idx : $('#idx').val(),
			meaning : $('input[name=meaning]:checked').val()
		},
		dataType : "json",
		success : function(data){
			if(data != null){
				var cnt = data.cnt;
				if(cnt > 5){
					alert("하루동안 다섯번의 테스트를 하였습니다.");
					return false;
				}else{
					nextWord();
				}
			}else{
				nextWord();
			}	
		}
	});
}

function wordCheck(){
	$('#wordArea').off('click','#nextBtn', wordCheck);
	clearTimeout(startTime);
	
	var checked =  $('input:radio[name=meaning]').is(":checked");
	if(!checked){
		alert("지문을 선택해야 합니다.");
		return false;
	}
	
	$.ajax({
		type : "POST",
		url : "<c:url value='/word/check.do' />",
		data : {
			idx : $('#idx').val(),
			meaning : $('input[name=meaning]:checked').val()
		},
		dataType : "json",
		success : function(data){
			if(data == "SUCCESS"){
				var grade = $('select[name=grade]').val();
				var limit = 30;
				if(grade === "001001000"){
					limit = 25;
				}else if(grade === "001003000"){
					limit = 35;
				}
				point = point + 1;
				if(point > limit){
					alert("다음 단계로 넘어갑니다.");
					point = 0;
					 
					var stage = $('select[name=stage]').val();
					var step = Number($('select[name=step]').val()) + 1;

					$.ajax({
						url : "<c:url value='/word/nextStep.do' />",
						data : {
							stage : stage,
							step : step
						},
						dataType : "html",
						success : function(data){
							$('select[name=step]').val(step);
							nextWordSet(data);
						}
					});
				}else{
					nextWord();	
				}
			}else{
				gameOver();
			}
		}
	});
	
}

function nextWord(){	 
	clearTimeout(startTime);
	
	$.ajax({
		url : "<c:url value='/word/next.do' />",
		async : false,
		dataType : "html",
		success : nextWordSet
	});
}

function nextWordSet(data){
	madeNextDiv(data);

	startTime = setTimeout(function(){
		var html = $('<p>').addClass("col_blue bold mt10").text("TIME OUT!");
		gameOver(html);
	}, 6000);
	
}
function madeNextDiv(data){
	var $div = $('<div>').css({"width":300, "opacity":0, "position":"absolute", "left":"300px"});
	$div.html(data);
	
	 $('.ml').animate({
		    opacity: 0,
		    left: -250,
		  }, 500, function() {
		    // Animation complete.
		    $(this).remove();
	});
	
	$div.animate({
	    opacity: 1,
	    left: 0,
	  }, 500, function() {
	    // Animation complete.
	    $(this).addClass("ml");
	});
	
	$('#wordArea').append($div);
	
	$('#wordArea').on('click', '#nextBtn', wordCheck);

}

function gameOver(data){
	clearTimeout(startTime);
	
	var div = $('<div>').addClass('mt25');
	var p = $('<p>').addClass("col_blue bold mt10").text( point + "점 입니다.");
	var btn = $('<button>').attr("id","okBtn").text("OK");
	if(data != null){
		div.append(data);
	}
	div.append(p);
	div.append(btn);
	madeNextDiv(div);
	
	$("input[name=point]").val(point);
	
	$("#frm").submit(function(e){
		var postData = $(this).serializeArray();
	    $.ajax({
	        url : "<c:url value='/word/record.do' />",
	        type: "POST",
	        data : postData,
	        success:function(data, textStatus, jqXHR) 
	        {
	            //data: return data from server
	        },
	        error: function(jqXHR, textStatus, errorThrown) 
	        {
	            //if fails      
	        }
	    });
	    e.preventDefault(); //STOP default action
	});
	 
	$("#frm").submit();
}

</script>	

</head>
<body>
	<center>
	
	<div id="wrap">
		<c:import url="/WEB-INF/views/common/header.jsp"></c:import>	
		
		<div id="content" >
			<form name="frm" id="frm" method="post" >
				<input type="hidden" name="point" />
				
				<select name="grade" >
					<c:forEach var="map" items="${cd:smPrntLi(grade)}" >
						<option value="${map.code}" ${map.code == grade ? 'selected="selected"' : '' } >${map.code_nm}</option>
					</c:forEach>
				</select>
				<select name="stage" >
					<c:forEach var="map" items="${cd:smPrntLi(stage)}" >
						<option value="${map.code}" ${map.code == stage ? 'selected="selected"' : '' } >${map.code_nm}</option>
					</c:forEach>
				</select>
				<select name="step" >
					<c:forEach var="map" items="${cd:stLi(stage)}" >
						<option value="${map.step}" ${map.step == step ? 'selected="selected"' : '' }>${map.step}</option>
					</c:forEach>
				</select><label class="examLa"> 단계</label> 
			</form>
	
			<div id="wordArea" class="tac">
			
				<button class="strBtn ml" onclick="startTest();">START</button>
			</div>
			
		</div>
		
	</div>
	
		<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>	
	</center>
	
	

</body>
</html>
