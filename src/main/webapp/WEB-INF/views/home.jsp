<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<title>인덕 대나무숲</title>
    <link rel="icon" type="image/png" sizes="32x32" href="../../resources/img/favicon.png">
	<script src="https://code.jquery.com/jquery-1.11.3.js"></script>
	<script
			src="https://kit.fontawesome.com/634362e31b.js"
			crossorigin="anonymous"
	></script>
	<link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>">
	<meta property="og:title" content="인덕대나무숲">
	<meta property="og:description" content="인덕대 학생들이 이용하는 게시판입니다.">
	<meta property="og:image" content="../../resources/img/favicon.png">
	<meta property="og:url" content="http://www.indukbamboo.com/">
	<meta property="og:type" content="website">

</head>
<body>
<jsp:include page="template/header.jsp" />
<article id="map_article">
	<div>
		<a id="yenji"  href="<c:url value="/board/list?type=yenji"/>" id="yenji">연지스퀘어(로그인x)</a>
	</div>
		<div>
		<a  href="<c:url value="/board/list?type=silver"/>" id="silver">은봉관</a>
		</div>
	<div>
		<a  href="<c:url value="/board/list?type=playground"/>" id="ground">운동장</a>
	</div>
	<div>
		<a  href="<c:url value="/board/list?type=virtue"/>" id="virtue">덕관</a>
	</div>
	<div>
		<a  href="<c:url value="/board/list?type=formative"/>" id="formative">조형관</a>
	</div>
	<div>
		<a  href="<c:url value="/board/list?type=auditorium"/>" id="church">교회</a>
	</div>
	<div>
		<a  href="<c:url value="/board/list?type=person"/>" id="person">인관</a>
	</div>
	<div>
		<a  href="<c:url value="/board/list?type=library"/>" id="library">도서관</a>
	</div>
	<div>
		<a  href="<c:url value="/board/list?type=kinggod"/>" id="first_engineering">제1공학관</a>
	</div>
	<div>
		<a  href="<c:url value="/board/list?type=engineering"/>" id="second_engineering">제2공학관</a>
	</div>
</article>

<jsp:include page="template/footer.jsp" />

<div id="error" data-error="<c:out value="${error}"/>">
	<p><i style="color:red" class="fa-solid fa-arrow-up"></i><i style="color:red" class="fa-solid fa-arrow-up"></i> <c:out value="${error}"/> <i style="color:red" class="fa-solid fa-arrow-up"></i><i style="color:red" class="fa-solid fa-arrow-up"></i></p>
	<p>닫기</p>
</div>
<script>
	let error = $("#error").data("error");
	$(document).ready(function(){
		includeError();
		removeError();
	});
	let includeError = function (){
		if(error !== undefined && error !== null && error !== ""){
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
