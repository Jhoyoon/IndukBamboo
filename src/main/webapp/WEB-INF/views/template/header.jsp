<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<c:set var="loginOutLink" value="${sessionScope.id == null ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${sessionScope.id == null ? 'login':'logout'}"/>
<c:if test="${sessionScope.id == null}">
<a href="<c:url value="/register"/>">register</a>
</c:if>
<a href="<c:url value="/"/>">home</a>
<a href="<c:url value="${loginOutLink}"/>">${loginOut}</a>
<a href="<c:url value="/board/list"/>">board</a>
