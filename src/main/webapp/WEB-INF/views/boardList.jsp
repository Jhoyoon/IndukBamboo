<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
  <title>${param.type}</title>
</head>
<body>
<jsp:include page="template/header.jsp" />
<div id="body_div">
<jsp:include page="template/nav.jsp"/>
<article>
  <h1>이곳은 ${param.type}입니다.</h1>
  <c:set var="T" value="${ph.sc.option == 'T' ? 'selected' : ''}"/>
  <c:set var="A" value='${ph.sc.option == "A" ? "selected" : ""}'/>
  <c:set var="W" value='${ph.sc.option == "W" ? "selected" : ""}'/>
  <form:form method="get">
    <select name="option">
      <option value="T" ${T}>제목만</option>
      <option value="A" ${A}>제목+내용</option>
      <option value="W" ${W}>작성자</option>
    </select>
    <input type="text" name="type" value="${param.type}">
    <input type="text" name="keyword" placeholder="검색 내용을 입력해주세요." value="${ph.sc.keyword}">
    <button>검색</button>
  </form:form>
  <a href="<c:url value="/board/write?type=${param.type}&page=${ph.sc.page}&pageSize=${ph.sc.pageSize}"/>">게시글 작성</a>
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
        <td><a href="<c:url value="/board/read?bno=${board.bno}&page=${ph.sc.page}&pageSize=${ph.sc.pageSize}"/>">${board.title}</a></td>
        <td>${board.writer}</td>
        <td>${board.reg_date}</td>
        <td>${board.view_cnt}</td>
      </tr>
    </c:forEach>
  </table>

  <c:if test="${ph.showPrev}"><a href="<c:url value="/board/list?type=${param.type}&option=${ph.sc.option}&page=${ph.beginPage-1}&pageSize=${ph.sc.pageSize}"/>">&lt;&lt;</a></c:if>
  <c:forEach var="i" begin="${ph.beginPage}" end="${ph.endPage}">
    <c:choose>
      <c:when test="${empty ph.sc.keyword}">
        <a href="<c:url value="/board/list?type=${param.type}&page=${i}&pageSize=${ph.sc.pageSize}"/>">${i}</a>
      </c:when>
      <c:when test="${not empty ph.sc.keyword}">
        <a href="<c:url value="/board/list?type=${param.type}&option=${ph.sc.option}&keyword=${ph.sc.keyword}&page=${i}&pageSize=${ph.sc.pageSize}"/>">${i}</a>
      </c:when>
    </c:choose>
  </c:forEach>

  <c:if test="${ph.showNext}"><a href="<c:url value="/board/list?type=${param.type}&option=${ph.sc.option}&page=${ph.endPage+1}&pageSize=${ph.sc.pageSize}"/>">&gt;&gt;</a></c:if>
</article>
</div>

<jsp:include page="template/footer.jsp" />
</body>
</html>
