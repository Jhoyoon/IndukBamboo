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
	$("#map_button").click(function (){
		if ($("#map_article").css("display") == "none") {
			// 지도가 숨겨져 있다면 지도를 보여주고 버튼의 텍스트를 '지도 모드 끄기'로 바꿈
			$("#map_button").css("position", "absolute");
			$("#map_article").css("display", "block");
            $("#road").css("display", "block");
            $("#road2").css("display", "block");
            $("#road3").css("display", "block");
            $("#map_back").css("display", "block");
			$("#map_button").html("지도 모드 끄기");
			$("#none_map").css("display","none");
		} else {
			// 지도가 보여지고 있다면 지도를 숨기고 버튼의 텍스트를 '지도 모드 켜기'로 바꿈
			$("#map_button").css("position", "");
			$("#map_article").css("display", "none");
            $("#road").css("display","none");
            $("#road2").css("display","none");
            $("#road3").css("display","none");
            $("#map_back").css("display","none");
			$("#map_button").html("지도 모드 켜기");
			$("#none_map").css("display","flex");
		}
	});

</script>
</body>
</html>
