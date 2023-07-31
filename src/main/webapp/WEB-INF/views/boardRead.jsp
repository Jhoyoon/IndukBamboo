<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<html>
<head>
  <title>Home</title>
  <link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>">
  <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
  <script
          src="https://kit.fontawesome.com/634362e31b.js"
          crossorigin="anonymous"
  ></script>
</head>
<body data-bno="<c:out value='${board.bno}'/>" data-id="<c:out value='${sessionScope.id}'/>">
<jsp:include page="template/header.jsp" />
<div class="body_div" >
<jsp:include page="template/nav.jsp"/>
<article>
  <div class="title">
    <c:out value="${title}"/> 대나무숲
  </div>
  <div id="read_board">
    <div id="board_title_update_remove">
      <div id="board_title">
        <c:out value="${board.title}"/>
      </div>
      <div id="update_remove">
        <c:if test="${board.writer == sessionScope.id}">
        <div id="update_a_div">
          <a id="update_a" href="<c:url value="/board/edit?type=${param.type}&bno=${board.bno}&page=${page}&pageSize=${pageSize}"/>">수정</a>
        </div>
        <div id="remove_a_div">
          <a id="remove_a" href="<c:url value="/board/remove?type=${param.type}&bno=${board.bno}&page=${page}&pageSize=${pageSize}"/>">삭제</a>
        </div>
        </c:if>
      </div>
    </div>
    <div id="board_nickname_time_view_commentcnt">
      <div id="nickname_time">
        <div id="nickname"><c:out value="${nickname}"/></div>
        <div id="reg_date"><c:out value="${board.reg_date}"/></div>
      </div>
      <div id="view_commentcnt">
        <div id="view_cnt">조회수 <c:out value="${board.view_cnt}"/></div>
        <div id="comment_cnt">댓글  <c:out value="${board.comment_cnt}"/></div>
      </div>
    </div>
    <div id="board_content">
      <div><c:out value="${board.content}"/></div>
    </div>
    <h1 id = "comment_h1">전체 댓글 <span><c:out value="${board.comment_cnt}"/></span></h1>
    <div id="commentContainer"></div>
    <div id="replyBox" style="display: none">
      <div id="reply_div">
      <textarea id="replyInput" name="replyInput"></textarea>
      <button id="replySendBtn">저장</button>
      <button id="reply_cancel_btn">취소</button>
      </div>
      </div>
    <div id="comment_send">
      <textarea name="comment" id="comment"></textarea>
      <button id="sendBtn" type="button">SEND</button>
    </div>
  </div>
  <jsp:include page="template/footer.jsp" />
</article>
<div id="error" data-error="<c:out value="${error}"/>">
  <p><c:out value="${error}"/></p>
  <p>닫기</p>
</div>
  <script src="<c:url value="/resources/js/boardRead.js"/>">
  </script>
</body>
</html>
