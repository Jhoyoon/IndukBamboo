<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
  <title>Home</title>
</head>
<body>
<%--<jsp:include page="template/header.jsp" />--%>

<span>${board.title}</span>
<span>${board.writer}</span>
<textarea readonly>${board.content}</textarea>
<span>${board.reg_date}</span>
<span>${board.view_cnt}</span>
<span><a href="<c:url value="/board/list?page=${ph.page}&pageSize=${ph.pageSize}"/>">목록</a></span>
<span><a href="<c:url value="/board/remove?bno=${board.bno}&page=${ph.page}&pageSize=${ph.pageSize}"/>">삭제</a></span>
<%--<jsp:include page="template/footer.jsp" />--%>
</body>
</html>
