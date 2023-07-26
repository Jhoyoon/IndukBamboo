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
<div id="body_div">
<jsp:include page="template/nav.jsp"/>
<article>
  <div id="title">${title} 대나무숲</div>
  <div id="search_div">
    <div>
  <a href="<c:url value="/board/write?type=${param.type}&page=${ph.sc.page}&pageSize=${ph.sc.pageSize}"/>">게시글 작성</a>
    </div>
      <form id="pageSize_form" method="get">
    <select name="pageSize">
      <option value="30">30</option>
      <option value="50">50</option>
      <option value="80">80</option>
    </select>
    <input class="pageSize_none" type="text" name="type" value="${param.type}">
    <input class="pageSize_none" type="text" name="keyword" placeholder="검색 내용을 입력해주세요." value="${ph.sc.keyword}">
    <button class="pageSize_none">검색</button>
  </form>
  </div>
  <table id="table">
    <tr id="tr">
      <th class="th" id="th1">번호</th>
      <th class="th" id="th2">제목</th>
      <th class="th" id="th3">작성자</th>
      <th class="th" id="th4">등록일</th>
      <th class="th" id="th5">조회수</th>
    </tr>
    <c:forEach var="board" items="${list}">
      <tr id="tr2">
        <td class="td" id="td1">${board.bno}</td>
        <td class="td" id="td2"><a href="<c:url value="/board/read?type=${param.type}&bno=${board.bno}&page=${ph.sc.page}&pageSize=${ph.sc.pageSize}"/>">${board.title}</a></td>
        <td class="td" id="td3">${board.nickname}</td>
        <td class="td" id="td4">${board.reg_date}</td>
        <td class="td" id="td5">${board.view_cnt}</td>
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
  <form:form method="get">
    <select id="option" name="option">
      <option value="T">제목만</option>
      <option value="A">제목+내용</option>
      <option value="W">작성자</option>
    </select>
    <input class="pageSize_none" type="text" name="type" value="${param.type}">
    <input type="text" name="keyword" placeholder="검색 내용을 입력해주세요." value="${ph.sc.keyword}">
    <button class="button">검색</button>
  </form:form>
</article>
</div>
<script>
  $(document).ready(function() {
    let pageSize = "${param.pageSize}"; // param에 있는 pageSize 값 가져오기
    $("select[name='pageSize'] option[value='" + pageSize + "']").attr('selected', 'selected'); // 해당 값과 일치하는 option에 selected 속성 추가
    let option = "${ph.sc.option}"; // param에 있는 option 값 가져오기
    if (option.length === 0) { // option 값이 없으면 기본값 'T' 선택
      option = 'T';
    }else{
      $("#option option[value='" + option + "']").attr('selected', 'selected'); // 해당 값과 일치하는 option에 selected 속성 추가

    }

  });
  $("select[name='pageSize']").change(function() {
    $(this).closest('form').submit();
  });
</script>
<jsp:include page="template/footer.jsp" />
</body>
</html>
