<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Title</title>
</head>

<h1>이곳은 register입니다.</h1>

<form:form modelAttribute="user" >
<%--<form action="<c:url value="/register"/>" method="post" >--%>
    <form:errors path="id"></form:errors>
    <form:errors path="pwd"></form:errors>
    <form:errors path="email"></form:errors>
    <form:errors path="birth"></form:errors>
    <label for="id">id</label>
    <input type="text" id="id" name="id" placeholder="id를 입력해주세요">

    <label for="pwd">pwd</label>
    <input type="password" id="pwd" name="pwd" placeholder="pwd를 입력해주세요">

    <label for="nickname">nickname</label>
    <input type="text" id="nickname" name="nickname" placeholder="nickname을 입력해주세요">

    <label for="email">email</label>
    <input type="text" id="email" name="email" placeholder="email 입력해주세요">

    <label for="birth">birth</label>
    <input type="text" id="birth" name="birth" placeholder="19970505 형태로 입력해주세요">

    <button>제출</button>
</form:form>

</body>
</html>
