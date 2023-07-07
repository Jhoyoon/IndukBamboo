<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
  <title>Home</title>
</head>
<body>
<h1>${user.id}</h1>
<h1>${user.pwd}</h1>
<h1>${user.nickname}</h1>
<h1>${user.email}</h1>
<h1>${user.birth}</h1>
<h1>${user.birthFinal}</h1>
</body>
</html>
