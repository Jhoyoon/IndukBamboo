let error = $("#error").data("error");
$(document).ready(function(){
    includeError();
    removeError();
});
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
$("#map_button").click(function (){
    if ($("#map_article").css("display") == "none") {
        // 지도가 숨겨져 있다면 지도를 보여주고 버튼의 텍스트를 '지도 모드 끄기'로 바꿈
        $("#map_button").css("position", "absolute");
        $("#map_article").css("display", "block");
        $("#road").css("display", "block");
        $("#road2").css("display", "block");
        $("#road3").css("display", "block");
        $("#map_back").css("display", "block");
        $("#map_button").html("지도 모드 끄기");
        $("#none_map").css("display","none");
    } else {
        // 지도가 보여지고 있다면 지도를 숨기고 버튼의 텍스트를 '지도 모드 켜기'로 바꿈
        $("#map_button").css("position", "");
        $("#map_article").css("display", "none");
        $("#road").css("display","none");
        $("#road2").css("display","none");
        $("#road3").css("display","none");
        $("#map_back").css("display","none");
        $("#map_button").html("지도 모드 켜기");
        $("#none_map").css("display","flex");
    }
});
