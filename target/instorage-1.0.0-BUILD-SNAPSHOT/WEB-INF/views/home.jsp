<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<title>Home</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>">
	<script src="https://code.jquery.com/jquery-1.11.3.js"></script>
	<script
			src="https://kit.fontawesome.com/634362e31b.js"
			crossorigin="anonymous"
	></script>
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
		<a href="<c:url value="/board/list?type=engineering"/>">제2공학관</a>
		<a href="<c:url value="/board/list?type=welfare"/>">학생행복스퀘어</a>
		<a href="<c:url value="/board/list?type=formative"/>">조형관</a>
		<a href="<c:url value="/board/list?type=playground"/>">인조잔디구장</a>
		<a href="<c:url value="/board/list?type=yenji"/>">연지스퀘어</a>
	</article>
<jsp:include page="template/footer.jsp" />
</div>
<script>
	$(document).ready(function(){
		includeError();
		removeError();
	});
	let includeError = function (){
		if("${param.error}" !== undefined && "${param.error}" !== null && "${param.error}" !== ""){
			$("#error").css("display","flex");
		}
	}
	let removeError = function (){
		$("#error p:last-child").click(function(){
			$("#error").css("display", "none");
		});
	}
</script>
</body>
</html>
