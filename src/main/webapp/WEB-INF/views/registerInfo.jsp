<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
    <title>Home</title>
</head>
<body>
<%--<jsp:include page="template/header.jsp" />--%>

<h1>${userDto.id}</h1>
<h1>${userDto.pwd}</h1>
<h1>${userDto.nickname}</h1>
<h1>${userDto.reg_date}</h1>

<%--<jsp:include page="template/footer.jsp" />--%>
</body>
</html>
