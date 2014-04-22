<%@ page session="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<input type="hidden"  name="idx" id="idx" value="${word.idx}" />

<strong class="txt_ar col_blue" style="font-size:50px;">${word.spelling}</strong> 
<div class="tac">
<div  style="margin:auto;width:160px; padding-left:60px;">
	<c:forEach var="map" items="${list}" varStatus="i">
	<%-- <P class="tal mt5" ><input type="radio" name="meaning" id="mean_${i.index}" value="${map.meaning1}" />
		<label for="mean_${i.index}">${map.meaning1}</label></P> --%>
		<P class="tal mt5">
			<input type="radio" name="meaning" id="mean_${i.index}" value="${map}" />
			<label class="examLa" for="mean_${i.index}">${map}</label>
		</P>
	</c:forEach>
</div>
</div>
<div class="mt25">
	<button class="nextBtn" id="nextBtn">NEXT</button>
</div>
