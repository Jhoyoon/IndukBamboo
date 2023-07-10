<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
  <title>Home</title>
</head>
<body>
<h1>${userDto.id}</h1>
<h1>${userDto.pwd}</h1>
<h1>${userDto.nickname}</h1>
<h1>${userDto.email}</h1>
<h1>${userDto.birth}</h1>
<h1>${userDto.birthFinal}</h1>
</body>
</html>
