<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
</head>
<jsp:include page="template/header.jsp" />

<h1>이곳은 register입니다.</h1>

<form:form modelAttribute="userDto" >
<%--<form action="<c:url value="/register"/>" method="post" >--%>
    <form:errors path="id" id="Error"></form:errors>
    <form:errors path="pwd" id="Error"></form:errors>
    <form:errors path="nickname" id="Error"></form:errors>
    <c:if test="${not empty param.msg}">
        <h1>${param.msg}</h1>
    </c:if>
    <label for="id">id</label>
    <input type="text" id="id" name="id" placeholder="id를 입력해주세요">

    <label for="pwd">pwd</label>
    <input type="password" id="pwd" name="pwd" placeholder="pwd를 입력해주세요">

    <label for="nickname">nickname</label>
    <input type="text" id="nickname" name="nickname" placeholder="nickname을 입력해주세요">



    <button>제출</button>
</form:form>
<jsp:include page="template/footer.jsp" />
    <script

        src="https://kit.fontawesome.com/634362e31b.js"
        crossorigin="anonymous"
    ></script>
<script>
    window.onload = function() {
        const errorElement = document.getElementById('Error'); // 에러 메시지 요소 찾기
        if(errorElement) { // 에러 메시지가 출력되었는지 확인
            var iconElement = document.createElement('i'); // <i> 태그 생성
            iconElement.className = 'fa-regular fa-circle-xmark'; // 원하는 아이콘 클래스 지정
            errorElement.insertBefore(iconElement, errorElement.firstChild); // <i> 태그를 에러 메시지 요소의 앞에 추가
        }
    };
</script>
</body>
</html>
