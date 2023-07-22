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
<form:form>
  <c:if test="${not empty param.error}">
    <h1>${param.error}</h1>
  </c:if>
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
