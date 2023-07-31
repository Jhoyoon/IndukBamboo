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
<div id="map_back"></div>
<div id="road"></div>
<div id="road2"></div>
<div id="road3"></div>
<jsp:include page="template/header.jsp" />
<div id="map_button">지도 모드 끄기</div>
<div id="none_map">
	<a href="<c:url value="/board/list?type=silver"/>" id="silver_btn">은봉관</a>
	<a href="<c:url value="/board/list?type=yenji"/>" id="yenji_btn">연지스퀘어</a>
	<a href="<c:url value="/board/list?type=playground"/>" id="ground_btn">운동장</a>
	<a href="<c:url value="/board/list?type=virtue"/>" id="virtue_btn">덕관</a>
	<a href="<c:url value="/board/list?type=formative"/>" id="formative_btn">조형관</a>
	<a href="<c:url value="/board/list?type=auditorium"/>" id="church_btn">교회</a>
	<a href="<c:url value="/board/list?type=person"/>" id="person_btn">인관</a>
	<a href="<c:url value="/board/list?type=library"/>" id="library_btn">도서관</a>
	<a href="<c:url value="/board/list?type=kinggod"/>" id="first_engineering_btn">제1공학관</a>
	<a href="<c:url value="/board/list?type=engineering"/>" id="second_engineering_btn">제2공학관</a>
</div>
<article id="map_article">
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

<div id="error" data-error="<c:out value="${error}"/>">
	<p><c:out value="${error}"/></p>
	<p>닫기</p>
</div>
<script src="<c:url value="/resources/js/home.js"/>">
</script>
</body>
</html>
