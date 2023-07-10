<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
  <title>login</title>
</head>
<body>
<jsp:include page="template/header.jsp" />
<h1>이곳은 login입니다.</h1>
<c:if test="${not empty param.error}">
  <span>${param.error}</span>
</c:if>
<form:form>
  <label for="id">id</label>
  <input type="text" id="id" name="id" placeholder="id를 입력해주세요" value="${cookie.id.value}">

  <label for="pwd">pwd</label>
  <input type="password" id="pwd" name="pwd" placeholder="pwd를 입력해주세요">

  <input type="text" value="${param.uri}"/>
  <button>로그인</button>

  <label for="rememberId">아이디 기억하기</label>
  <input type="checkbox" id="rememberId" name="rememberId" ${empty cookie.id.value ? "" : "checked"}>
</form:form>

<jsp:include page="template/footer.jsp" />
</body>
</html>
