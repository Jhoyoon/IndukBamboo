<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
  <title>Home</title>
</head>
<body>
<jsp:include page="template/header.jsp" />
<a href="<c:url value="/board/write?pageSize=${ph.pageSize}"/>">게시글 작성</a>
<c:if test="${not empty msg}">
  <span>${msg}</span>
</c:if>
<table>
  <tr>
    <th>번호</th>
    <th>제목</th>
    <th>이름</th>
    <th>등록일</th>
    <th>조회수</th>
  </tr>
  <c:forEach var="board" items="${list}">
  <tr>
    <td>${board.bno}</td>
    <td><a href="<c:url value="/board/read?bno=${board.bno}&page=${ph.page}&pageSize=${ph.pageSize}"/>">${board.title}</a></td>
    <td>${board.writer}</td>
    <td>${board.reg_date}</td>
    <td>${board.view_cnt}</td>
  </tr>
  </c:forEach>
</table>
<c:if test="${ph.showPrev}"><a href="<c:url value="/board/list?page=${ph.beginPage-1}&pageSize=${ph.pageSize}"/>">&lt;&lt;</a></c:if>
<c:forEach var="i" begin="${ph.beginPage}" end="${ph.endPage}">
  <a href="<c:url value="/board/list?page=${i}&pageSize=${ph.pageSize}"/>">${i}</a>
</c:forEach>
<c:if test="${ph.showNext}"><a href="<c:url value="/board/list?page=${ph.endPage+1}&pageSize=${ph.pageSize}"/>">&gt;&gt;</a></c:if>
<jsp:include page="template/footer.jsp" />
</body>
</html>
