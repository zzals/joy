<%@ page session="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/classes/spring/tld/code.tld" prefix="cd" %>
<% request.setCharacterEncoding("UTF-8"); %>

<option value="">직접입력</option>
<c:forEach var="map" items="${cd:cdLi(code)}" >
	<option value="${map.code}">${map.code_nm}</option>
</c:forEach>

