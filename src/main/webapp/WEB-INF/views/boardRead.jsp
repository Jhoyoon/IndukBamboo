<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
  <title>Home</title>
</head>
<body>
<%--<jsp:include page="template/header.jsp" />--%>
<c:if test="${not empty msg}">
  <span>${msg}</span>
</c:if>

<span>${board.title}</span>
<span>${board.writer}</span>
<textarea readonly>${board.content}</textarea>
<span>${board.reg_date}</span>
<span>${board.view_cnt}</span>
<span><a href="<c:url value="/board/list?page=${ph.page}&pageSize=${ph.pageSize}"/>">목록</a></span>
<c:if test="${board.writer == sessionScope.id}">
  <span><a href="<c:url value="/board/remove?bno=${board.bno}&page=${ph.page}&pageSize=${ph.pageSize}"/>">삭제</a></span>
</c:if>
<c:if test="${board.writer == sessionScope.id}">
  <span><a href="<c:url value="/board/edit?bno=${board.bno}&page=${ph.page}&pageSize=${ph.pageSize}"/>">수정</a></span>
</c:if>
<%--<jsp:include page="template/footer.jsp" />--%>
</body>
</html>
