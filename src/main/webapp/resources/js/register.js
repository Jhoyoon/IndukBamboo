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
    if(re_idValue.length <= 4 || re_idValue.length >= 21){
        $("#id_check").html('<span style="color: red;">아이디 값은 5 이상 20 이하여야 합니다.</span>');
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
    if (userId.length <= 4 || userId.length >= 21) {
        $("#id_check").html('<span style="color: red;">아이디 값은 5 이상 20 이하 이하여야 합니다.</span>');
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
            $("#error p:first-child").html(err.responseJSON.error);
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
    if (nickname.length <= 1 || nickname.length >= 21) {
        $("#nickname_check").html('<span style="color: red;">별명 값은 2 이상 20 이하여야 합니다.</span>');
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
            $("#error p:first-child").html(err.responseJSON.error);
            $("#error").css("display", "flex");
        }
    });
}