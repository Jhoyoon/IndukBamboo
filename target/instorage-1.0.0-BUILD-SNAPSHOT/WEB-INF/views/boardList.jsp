<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
  <title>${param.type}</title>
  <link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>">
  <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
  <script
          src="https://kit.fontawesome.com/634362e31b.js"
          crossorigin="anonymous"
  ></script>
</head>
<body>
<jsp:include page="template/header.jsp" />
<div class="body_div">
<jsp:include page="template/nav.jsp"/>
<article>
  <div class="title"><c:out value="${title} 대나무숲"/></div>
  <div id="write_div">
    <div id="write_div_a">
  <a id="list_write" href="<c:url value="/board/write?type=${param.type}&page=${ph.sc.page}&pageSize=${ph.sc.pageSize}"/>">게시글 작성</a>
    </div>
      <form id="pageSize_form" method="get">
    <select id="select_box" name="pageSize" data-option="<c:out value="${ph.sc.option}"/>" data-page-size="<c:out value="${ph.sc.pageSize}"/>" data-page="<c:out value="${ph.sc.page}"/>">
      <option value="30">30</option>
      <option value="50">50</option>
      <option value="80">80</option>
    </select>
          <input class="pageSize_none" name="option" value="${ph.sc.option}">
    <input class="pageSize_none" type="text" name="type" readonly value="<c:out value="${param.type}"/>">
    <input class="pageSize_none" type="text" name="keyword" readonly value="<c:out value="${ph.sc.keyword}"/>">
    <button class="pageSize_none">검색</button>
  </form>
  </div>
  <table id="table">
    <tr id="up" data-none="<c:out value="${none}"/>">
      <th class="up_column" id="up_number">번호</th>
      <th class="up_column" id="up_title">제목</th>
      <th class="up_column" iㅇd="up_writer">작성자</th>
      <th class="up_column" id="up_date">등록일</th>
      <th class="up_column" id="up_view">조회수</th>
    </tr>
    <c:forEach var="board" items="${list}">
      <tr id="down">
        <td class="down_column" id="down_number"><div class="down_div"><c:out value="${board.bno}"/></div></td>
        <td class="down_column" id="down_title"><div class="down_div"><a href="<c:url value="/board/read?type=${param.type}&bno=${board.bno}&page=${ph.sc.page}&pageSize=${ph.sc.pageSize}"/>"><c:out value="${board.title}"/> <sapn id="title_commentCnt"><c:out value="[${board.comment_cnt}]"/></sapn></a></div></td>
        <td class="down_column" id="down_nickname"><div class="down_div"><c:out value="${board.nickname}"/></div></td>
        <td class="down_column" id="down_date"><div class="down_div"><c:out value="${board.reg_date}"/></div></td>
        <td class="down_column" id="down_view"><div class="down_div"><c:out value="${board.view_cnt}"/></div></td>
      </tr>
    </c:forEach>
  </table>
  <div id="page_div" >
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
  </div>
  <div id="search_div">
    <form:form id="search_form" method="get">
    <select id="option" name="option">
      <option value="T">제목만</option>
      <option value="A">제목+내용</option>
      <option value="W">작성자</option>
    </select>
    <input class="pageSize_none" type="text" name="type" readonly value="<c:out value="${param.type}"/>">
      <input class="pageSize_none" name="pageSize" readonly value="<c:out value="${ph.sc.pageSize}"/>">
    <input id="search_keyword" type="text" name="keyword" ${ph.sc.keyword == "" ? "": "autofocus"} placeholder="검색 내용을 입력해주세요." value="<c:out value="${ph.sc.keyword}"/>">
    <button id="search_button"><i class="fa-solid fa-magnifying-glass"></i></button>
    </form:form>
  </div>
</article>
</div>
<div id="error" data-error="<c:out value="${error}"/>">
  <p><c:out value="${error}"/></p>
  <p>닫기</p>
</div>
<jsp:include page="template/footer.jsp" />
<script src="<c:url value="/resources/js/boardList.js"/>"></script>
</body>
</html>
