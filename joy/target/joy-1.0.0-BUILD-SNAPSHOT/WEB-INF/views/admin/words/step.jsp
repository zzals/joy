<%@ page session="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/classes/spring/tld/code.tld" prefix="cd" %>


		<c:if test="${grade != '' }">
			<c:forEach var="map" items="${cd:cdLi(grade)}" >
				<option value="${map.code}">${map.code_nm}</option>
			</c:forEach>
		</c:if>	
		<c:if test="${stage != '' }">
			<c:forEach var="map" items="${cd:stLi(stage)}" >
				<option value="${map.step}" ${map.step == step ? 'selected="selected"' : '' }>${map.step}</option>
			</c:forEach>
		</c:if>	