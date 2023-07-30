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
<body>
<jsp:include page="template/header.jsp" />
<article id="register">
<form method="post" id="register_form">
    <h1>대나무숲 회원가입</h1>
    <div class="form_column">
        <div class="label_column">
    <label for="re_id">아이디</label>
        </div>
    <input type="text" id="re_id" name="id" placeholder="아이디">
    </div>
    <button class="button_id" id="checkId">중복 확인</button>
    <div class="form_error_column" id="id_check"></div>

    <div class="form_column">
        <div class="label_column">
    <label for="re_pwd1">비밀번호</label>
        </div>
    <input type="password" id="re_pwd1" name="pwd" placeholder="비밀번호">
    </div>
    <div  class="form_error_column" id="pwd_check"></div>

    <div class="form_column">
        <div class="label_column">
    <label for="re_pwd2">확인 비밀번호</label>
        </div>
        <input type="password" id="re_pwd2" name="pwd2" placeholder="비밀번호 확인">
    </div>
    <div class="form_error_column" id="pwd2_check"></div>

    <div class="form_column">
        <div class="label_column">
    <label for="re_nickname">별명</label>
    </div>
    <input type="text" id="re_nickname" name="nickname" placeholder="별명">
    </div>
    <button class="button_nick" id="checkNickname">중복 확인</button>
    <div  class="form_error_column" id="nickname_check"></div>

    <button class="button_reg">제출</button>
</form>
</article>
<div id="error" data-error="${error}"/>
    <p><c:out value="${error}"/></p>
    <p>닫기</p>
</div>
<jsp:include page="template/footer.jsp" />
</body>
<script>
    $(document).ready(function(){
        includeError();
        removeError();
        $("#re_id").keyup(checkKeyupId);
        $("#re_pwd1").keyup(checkKeyupPwd);
        $("#re_pwd2").keyup(checkPasswordsMatch);
        $("#re_nickname").keyup(checkKeyupNickname);
        $("#checkId").click(function(e) {
            e.preventDefault();
            buttonCheckId();
        });
        $("#checkNickname").click(function (e){
            e.preventDefault();
            buttonCheckNickname();
        })
    });
    let checkKeyupNickname = function(){
        let re_nickname = $("#re_nickname").val();
        if(!re_nickname){
            $("#nickname_check").html('<span style="color: red;">별명을 입력해주세요.</span>');
            return;
        }
        if(/\s/.test(re_nickname)){
            $("#nickname_check").html('<span style="color: red;">별명 값은 공백을 포함할수 없습니다.</span>');
            return;
        }
        if(re_nickname.length <= 1 || re_nickname.length >= 21){
            $("#nickname_check").html('<span style="color: red;">별명 값은 2 이상 20 이하여야 합니다.</span>');
            return;
        }
        $("#nickname_check").html('<span style="color: blue;">중복 체크를 해주세요!</span>');
    }
    let checkKeyupPwd = function (){
        let re_pwdValue = $("#re_pwd1").val();
        if(!re_pwdValue){
            $("#pwd_check").html('<span style="color: red;">비밀번호를 입력해주세요.</span>');
            return;
        }
        if(/\s/.test(re_pwdValue)){
            $("#pwd_check").html('<span style="color: red;">비밀번호 값은 공백을 포함할수 없습니다.</span>');
            return;
        }
        if(re_pwdValue.length <= 7 || re_pwdValue.length >= 51){
            $("#pwd_check").html('<span style="color: red;">비밀번호 값은 8 이상 50 이하여야 합니다.</span>');
            return;
        }
        $("#pwd_check").html('<span style="color: blue;">비밀번호가 조건을 만족했습니다!</span>');
    }
    let checkKeyupId = function (){
        let re_idValue = $("#re_id").val();
        if(!re_idValue){
            $("#id_check").html('<span style="color: red;">아이디를 입력해주세요.</span>');
            return;
        }
        if(/\s/.test(re_idValue)){
            $("#id_check").html('<span style="color: red;">아이디 값은 공백을 포함할수 없습니다.</span>');
            return;
        }
        if(re_idValue.length <= 4 || re_idValue.length >= 20){
            $("#id_check").html('<span style="color: red;">아이디 값은 5 이상 19 이하여야 합니다.</span>');
            return;
        }
        $("#id_check").html('<span style="color: blue;">중복 체크를 해주세요!</span>');
    }
    let error = $("#error").data("error");

    let includeError = function (){
        if(error !== undefined && error !== null && error !== ""){
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
            $("#pwd2_check").html('<span style="color:red;">비밀번호가 일치하지 않습니다!</span>');
        } else {
            $("#pwd2_check").html('<span style="color:blue;">비밀번호가 일치합니다</span>');
        }
    }

    function buttonCheckId() {
        let userId = $("#re_id").val(); // 아이디 값을 가져옵니다.
        if (!userId) {
            $("#id_check").html('<span style="color: red;">아이디를 입력해주세요.</span>');
            return;
        }
        if (/\s/.test(userId)) {
            $("#id_check").html('<span style="color: red;">아이디 값은 공백을 포함할수 없습니다.</span>');
            return;
        }
        if (userId.length <= 4 || userId.length >= 20) {
            $("#id_check").html('<span style="color: red;">아이디 값은 5 이상 19 이하여야 합니다.</span>');
            return;
        }

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
                $("#error p:first-child").html(err.responseJSON.error;);
                $("#error").css("display", "flex");
            }
        });
    }
    function buttonCheckNickname(){
        let nickname = $("#re_nickname").val();
        if (!nickname) {
            $("#nickname_check").html('<span style="color: red;">별명을 입력해주세요</span>');
            return;
        }
        if (/\s/.test(nickname)) {
            $("#nickname_check").html('<span style="color: red;">별명 값은 공백을 포함할수 없습니다.</span>');
            return;
        }
        if (nickname.length <= 1 || nickname.length >= 20) {
            $("#nickname_check").html('<span style="color: red;">별명 값은 2 이상 21 이하여야 합니다.</span>');
            return;
        }
        $.ajax({
            url: '/instorage/checkNickname',
            type: 'post',
            dataType: 'json',
            contentType: 'application/json', // contentType 추가
            data: JSON.stringify({nickname: nickname}), // 데이터를 JSON 문자열로 변환
            success: function(data) {
                if(data.error === "OK") {
                    $("#nickname_check").html('<span style="color: blue;">사용 가능한 별명입니다.</span>');
                }else{
                    $("#nickname_check").html('<span style="color: red;">'+data.error+'</span>');
                }
            },
            error: function(err) {
                $("#error p:first-child").html(err.responseJSON.error;);
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
