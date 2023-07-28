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
<article id="map_articel">
	<a href="<c:url value="/board/list?type=silver"/>" id="silver">은봉관</a>
	<a href="<c:url value="/board/list?type=yenji"/>" id="yenji">연지스퀘어</a>
	<a href="<c:url value="/board/list?type=playground"/>" id="ground">운동장</a>
	<a href="<c:url value="/board/list?type=virtue"/>" id="virtue">덕관</a>
	<a href="<c:url value="/board/list?type=formative"/>" id="formative">조형관</a>
	<a href="<c:url value="/board/list?type=auditorium"/>" id="church">교회</a>
	<a href="<c:url value="/board/list?type=person"/>" id="person">인관</a>
	<a href="<c:url value="/board/list?type=library"/>" id="library">도서관</a>
	<a href="<c:url value="/board/list?type=kinggod"/>" id="first_engineering">제1공학관</a>
	<a href="<c:url value="/board/list?type=engineering"/>" id="second_engineering">제2공학관</a>
</article>
<jsp:include page="template/footer.jsp" />
</div>
<div id="error">
	<p>${error}</p>
	<p>닫기</p>
</div>
<script>
	$(document).ready(function(){
		includeError();
		removeError();
	});
	let includeError = function (){
		if("${error}" !== undefined && "${error}" !== null && "${error}" !== ""){
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
