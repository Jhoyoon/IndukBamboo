<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<title>Home</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>">
</head>
<body>
<div id="grid_container">
<jsp:include page="template/header.jsp" />
	<article>
		<a href="<c:url value="/board/list?type=silver"/>">은봉관</a>
		<a href="<c:url value="/board/list?type=auditorium"/>">강당</a>
		<a href="<c:url value="/board/list?type=virtue"/>">덕관</a>
		<a href="<c:url value="/board/list?type=person"/>">인관</a>
		<a href="<c:url value="/board/list?type=kinggod"/>">제1공학관</a>
		<a href="<c:url value="/board/list?type=library"/>">중앙도서관</a>
		<a href="<c:url value="/board/list?type=engineering"/>">제2공학관</a>z
		<a href="<c:url value="/board/list?type=welfare"/>">학생행복스퀘어</a>
		<a href="<c:url value="/board/list?type=formative"/>">조형관</a>
		<a href="<c:url value="/board/list?type=playground"/>">인조잔디구장</a>
		<a href="<c:url value="/board/list?type=yenji"/>">연지스퀘어</a>
	</article>
<jsp:include page="template/footer.jsp" />
</div>
</body>
</html>
