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
  <div id="write_div">
    <div id="write_div_a">
  <a id="list_write" href="<c:url value="/board/write?type=${param.type}&page=${ph.sc.page}&pageSize=${ph.sc.pageSize}"/>">게시글 작성</a>
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
    <tr id="up">
      <th class="up_column" id="up_number">번호</th>
      <th class="up_column" id="up_title">제목</th>
      <th class="up_column" id="up_writer">작성자</th>
      <th class="up_column" id="up_date">등록일</th>
      <th class="up_column" id="up_view">조회수</th>
    </tr>
    <c:forEach var="board" items="${list}">
      <tr id="down">
        <td class="down_column" id="down_number"><div class="down_div">${board.bno}</div></td>
        <td class="down_column" id="down_title"><div class="down_div"><a href="<c:url value="/board/read?type=${param.type}&bno=${board.bno}&page=${ph.sc.page}&pageSize=${ph.sc.pageSize}"/>">${board.title} <sapn id="title_commentCnt">[${board.comment_cnt}]</sapn></a></div></td>
        <td class="down_column" id="down_nickname"><div class="down_div">${board.nickname}</div></td>
        <td class="down_column" id="down_date"><div class="down_div">${board.reg_date}</div></td>
        <td class="down_column" id="down_view"><div class="down_div">${board.view_cnt}</div></td>
      </tr>
    </c:forEach>
  </table>
  <div id="page_div">
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
    <input class="pageSize_none" type="text" name="type" value="${param.type}">
    <input type="text" name="keyword" placeholder="검색 내용을 입력해주세요." value="${ph.sc.keyword}">
    <button id="search_button"><i class="fa-solid fa-magnifying-glass"></i></button>
  </form:form>
  </div>
</article>
</div>
<script>
  $(document).ready(function() {
    let pageSize = "${param.pageSize}";
    $("select[name='pageSize'] option[value='" + pageSize + "']").attr('selected', 'selected');
    let option = "${ph.sc.option}";
    if (option.length === 0) {
      option = 'T';
    } else {
      $("#option option[value='" + option + "']").attr('selected', 'selected');
    }

    let currentPage = ${ph.sc.page}; // 현재 페이지 번호 가져오기
    $("a").each(function() { // 모든 'a' 태그에 대해
      let pageLinkNumber = parseInt($(this).text(), 10); // 페이지 링크 번호를 정수로 가져오기
      if (pageLinkNumber === currentPage) { // 페이지 링크 번호가 현재 페이지 번호와 일치하면
        $(this).css("color", "red"); // 색상을 빨강으로 변경
      }
    });

    $("select[name='pageSize']").change(function() {
      $(this).closest('form').submit();
    });
  });
</script>
<jsp:include page="template/footer.jsp" />
</body>
</html>
