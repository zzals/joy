<%@ page session="true" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.springframework.web.util.WebUtils"%>
<%@page import="com.se.joy.model.MemberVO"%>
<%
MemberVO loginVo = (MemberVO) WebUtils.getSessionAttribute(request, "MEMBER_LOGIN");
%>
		<c:set var="login" value="<%= loginVo %>" />
		<div id="header">
			<div class="fl">
				<h2 class="logo"><a href="<c:url value='/admin/index.do' />">JWM ADMIN</a></h2>
			</div>
			<div id="util">
				<c:if test="${login == null}" >
				<a href="<c:url value='/' />">LOGIN</a>
				</c:if>
				<c:if test="${login != null}" >
				<span>${login.m_nm}</span>
				<a href="<c:url value='/logout.do' />">LOGOUT</a>
				</c:if>
				<c:if test="${login.auth == 9}" >
				<a href="<c:url value='/index.do' />">TEST</a>
				</c:if>
			</div>
		</div>
		