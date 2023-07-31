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
<body>
<jsp:include page="template/header.jsp" />
<article id="register">
<form method="post" id="register_form">
    <h1>대나무숲 회원가입</h1>
    <div class="form_column">
        <div class="label_column">
    <label for="re_id">아이디</label>
        </div>
    <input type="text" id="re_id" name="id" placeholder="아이디">
    </div>
    <button class="button_id" id="checkId">중복 확인</button>
    <div class="form_error_column" id="id_check"></div>

    <div class="form_column">
        <div class="label_column">
    <label for="re_pwd1">비밀번호</label>
        </div>
    <input type="password" id="re_pwd1" name="pwd" placeholder="비밀번호">
    </div>
    <div  class="form_error_column" id="pwd_check"></div>

    <div class="form_column">
        <div class="label_column">
    <label for="re_pwd2">확인 비밀번호</label>
        </div>
        <input type="password" id="re_pwd2" name="pwd2" placeholder="비밀번호 확인">
    </div>
    <div class="form_error_column" id="pwd2_check"></div>

    <div class="form_column">
        <div class="label_column">
    <label for="re_nickname">별명</label>
    </div>
    <input type="text" id="re_nickname" name="nickname" placeholder="별명">
    </div>
    <button class="button_nick" id="checkNickname">중복 확인</button>
    <div  class="form_error_column" id="nickname_check"></div>

    <button class="button_reg">제출</button>
</form>
</article>
<div id="error" data-error="<c:out value="${error}"/>"/>
    <p><c:out value="${error}"/></p>
    <p>닫기</p>
</div>
<jsp:include page="template/footer.jsp" />
</body>
<script src="<c:url value="/resources/js/register.js"/>">
</script>
<script
        src="https://kit.fontawesome.com/634362e31b.js"
        crossorigin="anonymous"
></script>
</body>
</html>
