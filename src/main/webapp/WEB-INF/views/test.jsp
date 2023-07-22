<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <link rel="stylesheet" href="<c:url value='/resources/css/test.css'/>">
</head>
<body>
<h2 class="test">Comment test</h2>
<button id="sendBtn" type="button">SEND</button>
<input name="comment" id="comment">
<h3 id="commentTest"></h3>
<div id="replyBox" style="display: none">
    <input id="replyInput" name="replyInput">
    <button id="replySendBtn">저장</button>
</div>
<script src="<c:url value="/resources/js/comment.js"/>"></script>
<%--<script src="/instorage/resources/js/comment.js"></script>--%>
</body>
</html>