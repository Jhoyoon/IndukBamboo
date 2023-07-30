<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<c:url var="formUrl" value="${mode == 'edit' ? '/board/edit':'/board/write'}"/>
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
<body>
<jsp:include page="template/header.jsp" />
<div class="body_div">
<jsp:include page="template/nav.jsp"/>
<article id="write_article">
    <div class="title" id="write_out_title"><c:out value="${title}"/> 대나무숲</div>
    <div id="write_article_div">
    <form id="write_form" action="${formUrl}" method="post">
        <h1>제목</h1>
        <input  id="input_title" type="text" placeholder="제목은 최대 100자입니다." name="title" value="<c:out value="${boardDto.title}"/>">
        <h1>내용</h1>
        <textarea id="input_content" name="content"><c:out value="${boardDto.content}"/></textarea>
        <input readonly class="write_input_none" type="number" placeholder="pageSize" name="pageSize" value="<c:out value="${param.pageSize}"/>">
        <input readonly class="write_input_none" type="page" placeholder="page" name="page" value="<c:out value="${param.page}"/>">
        <input readonly class="write_input_none" type="text" name="type" value="<c:out value="${param.type}"/>">
        <input readonly class="write_input_none" type="number" placeholder="bno" name="bno" value="<c:out value="${boardDto.bno}"/>">
        <div id="button_div">
            <button>작성</button>
        </div>
    </form>
    </div>
</article>
</div>
<jsp:include page="template/footer.jsp" />
<div id="error">
    <p><c:out value="${error}"/></p>
    <p>닫기</p>
</div>
<script>
    $(document).ready(function(){
        includeError();
        removeError();
    });
    let includeError = function (){
        if("${error}" !== undefined && "${error}" !== null && "${error}" !== ""){
            $("#error").css("display","flex");
        }
    }
    let removeError = function (){
        $("#error p:last-child").click(function(){
            $("#error").css("display", "none");
        });
    }
    $("#button_div button").click(function(e){
        e.preventDefault();
        if (titleCheck() && contentCheck()) {
            $("#write_form").submit();
        }
    })
    function titleCheck(){
        let title = $("#input_title").val();
        if(title.length == 0){
            $("#error").css("display","flex");
            $("#error p:first-child").html("제목을 입력해 주세요!");
            return false;
        }else if(title.length >= 100){
            $("#error").css("display","flex");
            $("#error p:first-child").html("제목은 최대 100글자 입력 가능합니다.");
            return false;
        }
        return true;
    }
    function contentCheck(){
        let content = $("#input_content").val();
        if(content.length == 0){
            $("#error").css("display","flex");
            $("#error p:first-child").html("내용을 입력해 주세요!");
            return false;
        }
        return true;
    }
</script>
</body>
</html>
