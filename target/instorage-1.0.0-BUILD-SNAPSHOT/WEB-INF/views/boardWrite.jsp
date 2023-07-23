<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
  <title>Home</title>
</head>
<body>
<jsp:include page="template/header.jsp" />
<h1>이곳은 ${mode == "edit" ? "edit" : "write"}입니다.</h1>
<form action="<c:url value="/board/write"/>" method="post">
    <input type="text" name="type" value="${param.type}">
    <input type="number" placeholder="bno" name="bno" value="${boardDto.bno}">
    <input type="text" placeholder="title" name="title" value="${boardDto.title}" >
    <input type="text" placeholder="content" name="content" value="${boardDto.content}">
    <input type="number" placeholder="pageSize" name="pageSize" value="${param.pageSize}">
    <input type="page" placeholder="page" name="page" value="${param.page}">
    <button>작성</button>
</form>
<jsp:include page="template/footer.jsp" />
</body>
</html>
