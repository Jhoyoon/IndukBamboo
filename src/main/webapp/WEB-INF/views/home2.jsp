<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.net.URLDecoder" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h1>
    Hello world!
</h1>

<P>  The time on the server is ${serverTime}. </P>
<%--<h1>${URLDecoder.decode(param.msg,"utf-8")}</h1>--%>
<h1>${msg}</h1>
</body>
</html>
