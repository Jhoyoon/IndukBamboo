<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>">
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <script
            src="https://kit.fontawesome.com/634362e31b.js"
            crossorigin="anonymous"
    ></script>
</head>
<jsp:include page="template/header.jsp" />
<form method="post">
    <label for="id">아이디</label>
    <input type="text" id="re_id" name="id" placeholder="아이디를 입력해주세요">

    <button id="checkId">중복 확인</button>
    <div id="id_check"></div>

    <label for="pwd">비밀번호</label>
    <input type="password" id="re_pwd1" name="pwd" placeholder="비밀번호를 입력해주세요">

    <label for="pwd">확인 비밀번호</label>
    <input type="password" id="re_pwd2" name="pwd2" placeholder="비밀번호를 다시 입력해주세요">

    <label for="nickname">nickname</label>
    <input type="text" id="re_nickname" name="nickname" placeholder="사이트에서 사용할 별명을 입력해주세요">

    <button>제출</button>
</form>
<div id="error">
    <p>${error}</p>
    <p>닫기</p>
</div>
<jsp:include page="template/footer.jsp" />
<script>
    $(document).ready(function(){
        includeError();
        removeError();
        $("#re_pwd2").keyup(checkPasswordsMatch);
        $("#checkId").click(function(e) {
            e.preventDefault();
            checkDuplicateId();
        });
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
    function checkPasswordsMatch() {
        let pwd1 = $("#re_pwd1").val();
        let pwd2 = $("#re_pwd2").val();

        // 일치하지 않으면 메시지를 표시
        if (pwd1 !== pwd2) {
            $("#pwd2_mismatch").remove(); // 이미 있는 메시지 제거
            $("#re_pwd2").after('<p id="pwd2_mismatch" style="color:red;">비밀번호가 일치하지 않습니다!</p>');
        } else {
            $("#pwd2_mismatch").remove(); // 일치하면 메시지 제거
            $("#re_pwd2").after('<p id="pwd2_mismatch" style="color:green;">비밀번호가 일치합니다</p>');
        }
    }

    function checkDuplicateId() {
        let userId = $("#re_id").val(); // 아이디 값을 가져옵니다.

        // 서버에 POST 요청을 보내서 중복 검사를 합니다.
        $.ajax({
            url: '/instorage/checkId',
            type: 'post',
            dataType: 'json',
            contentType: 'application/json', // contentType 추가
            data: JSON.stringify({id: userId}), // 데이터를 JSON 문자열로 변환
            success: function(data) {
                    if(data.error === "OK") {
                        $("#id_check").html('<span style="color: blue;">사용 가능한 아이디입니다.</span>');
                    }else{
                        $("#id_check").html('<span style="color: red;">'+data.error+'</span>');
                        }
            },
            error: function(err) {
                let errMsg = err.responseJSON.error;
                $("#error p:first-child").html(errMsg);
                $("#error").css("display", "flex");
            }
        });
    }
</script>
<script

        src="https://kit.fontawesome.com/634362e31b.js"
        crossorigin="anonymous"
></script>
</body>
</html>
