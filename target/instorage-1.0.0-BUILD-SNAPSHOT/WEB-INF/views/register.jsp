<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>">
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <script
            src="https://kit.fontawesome.com/634362e31b.js"
            crossorigin="anonymous"
    ></script>
</head>
<jsp:include page="template/header.jsp" />
<form method="post">
    <label for="id">id</label>
    <input type="text" id="id" name="id" placeholder="id를 입력해주세요">

    <label for="pwd">pwd</label>
    <input type="password" id="pwd" name="pwd" placeholder="pwd를 입력해주세요">

    <label for="nickname">nickname</label>
    <input type="text" id="nickname" name="nickname" placeholder="nickname을 입력해주세요">

    <button>제출</button>
</form>
<div id="error">
    <p>${param.error}</p>
    <p>닫기</p>
</div>
<jsp:include page="template/footer.jsp" />
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
<script

        src="https://kit.fontawesome.com/634362e31b.js"
        crossorigin="anonymous"
></script>
</body>
</html>
