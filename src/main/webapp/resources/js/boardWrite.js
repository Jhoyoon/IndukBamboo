$(document).ready(function(){
    includeError();
    removeError();
});

let error = $("error").data("error");
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